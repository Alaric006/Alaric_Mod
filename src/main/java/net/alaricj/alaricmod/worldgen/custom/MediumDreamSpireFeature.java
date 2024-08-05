package net.alaricj.alaricmod.worldgen.custom;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import net.alaricj.alaricmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.function.Function;

public class MediumDreamSpireFeature extends Feature<MediumDreamSpireConfiguration> {
    public MediumDreamSpireFeature(Codec<MediumDreamSpireConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MediumDreamSpireConfiguration> pContext) {
        BlockPos pPos = pContext.origin();
        WorldGenLevel worldLevel = pContext.level();
        RandomSource randomSource = pContext.random();
        MediumDreamSpireConfiguration config = pContext.config();
        MediumDreamSpireConfiguration.MediumDreamSpireGrower dreamSpireGrower = config.dreamSpireGrower;
        int directionModifierNorth = randomSource.nextIntBetweenInclusive(-1, 1);
        int directionModifierEast = randomSource.nextIntBetweenInclusive(-1, 1);
        int northDisplacement = 0;
        int eastDisplacement = 0;
        float layerWidth = (float) dreamSpireGrower.spireCoreWidth.sample(randomSource);
        int spireHeight = (int) (((float) dreamSpireGrower.spireHeight.sample(randomSource)) * layerWidth / 16d);
        //Build spire from top to bottom, row by row
        for(int i = -4; i < spireHeight; i += dreamSpireGrower.layerHeight) {
            //If layer y pos above initialSlimmingHeight reduce next layer width by slimming factor, adjusting for layerHeight
            int initialSlimmingHeight = (int) dreamSpireGrower.initialSlimmingHeightPercent * spireHeight;
            if(i >= initialSlimmingHeight) {
                layerWidth -= dreamSpireGrower.layerHeight * dreamSpireGrower.slimmingFactor;
            }

            //Place next spire layer with added displacements. If extraBaseLayers > 0, add extra layers as well
            int extraLayerNorthDisplacement = 0;
            int extraLayerEastDisplacement = 0;
            for(int layerCounter = 0; layerCounter < ((i < spireHeight * dreamSpireGrower.initialSlimmingHeightPercent) ? dreamSpireGrower.extraBaseLayers + 1 : 1); layerCounter++) {
                //Calculate if ore can be placed at current spire layer height
                boolean canPlaceOre = i > dreamSpireGrower.oreSpawnHeightPercent * spireHeight;

                //Place each spire
                placeSpireLayer(pPos.above(i).north(northDisplacement + extraLayerNorthDisplacement).east(eastDisplacement + extraLayerEastDisplacement),
                        config.blockPlacer, config.specialOreProvider, (int) layerWidth, dreamSpireGrower.layerHeight,
                        canPlaceOre, randomSource, dreamSpireGrower, worldLevel);
                extraLayerEastDisplacement = randomSource.nextIntBetweenInclusive(-dreamSpireGrower.extraLayerOffsetRandomness, dreamSpireGrower.extraLayerOffsetRandomness);
                extraLayerNorthDisplacement = randomSource.nextIntBetweenInclusive(-dreamSpireGrower.extraLayerOffsetRandomness, dreamSpireGrower.extraLayerOffsetRandomness);
            }
            //Update displacements for next layer
            northDisplacement += randomSource.nextIntBetweenInclusive(-dreamSpireGrower.horizontalLayerOffsetRandomness + directionModifierNorth, dreamSpireGrower.horizontalLayerOffsetRandomness + directionModifierNorth);
            eastDisplacement += randomSource.nextIntBetweenInclusive(-dreamSpireGrower.horizontalLayerOffsetRandomness + directionModifierEast, dreamSpireGrower.horizontalLayerOffsetRandomness + directionModifierEast);
        }
        return true;
    }

    //Places spire layer
    private void placeSpireLayer(BlockPos anchorBlockPos, BlockStateProvider blockPlacer, BlockStateProvider specialOrePlacer, int coreWidth, int layerHeight, boolean canPlaceOre, RandomSource randomSource, MediumDreamSpireConfiguration.MediumDreamSpireGrower dreamSpireGrower, WorldGenLevel worldLevel) {
        for(int y = 0; y < layerHeight; y++) {
            for (int x = 0; x < coreWidth + 2; x++) {
                for (int z = 0; z < coreWidth + 2; z++) {
                    if (!(x == 0 && (z == 0 || z == coreWidth + 1) || x == coreWidth + 1 && (z == 0 || z == coreWidth + 1))) {
                        boolean placeSpecialOre = x > 1 && x < coreWidth + 1 && z > 1 && z < coreWidth + 1 && canPlaceOre && randomSource.nextFloat() < dreamSpireGrower.oreSpawnChance;
                            tryPlaceBlock(anchorBlockPos.east(x).north(z).above(y),
                                    placeSpecialOre ? specialOrePlacer : blockPlacer,
                                    randomSource, worldLevel);
                        }
                    }
                }
            }
        }
        private void tryPlaceBlock(BlockPos blockPos, BlockStateProvider blockStateProvider, RandomSource randomSource, WorldGenLevel level) {
        if(level.getBlockState(blockPos) == Blocks.AIR.defaultBlockState()) {
            level.setBlock(blockPos, blockStateProvider.getState(randomSource, blockPos), 4);
        }
    }
}
