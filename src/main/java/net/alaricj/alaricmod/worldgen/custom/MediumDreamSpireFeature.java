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
        int northDisplacement = 0;
        int eastDisplacement = 0;
        float layerWidth = (float) dreamSpireGrower.spireCoreWidth;
        //Build spire from top to bottom, row by row
        for(int i = -4; i < dreamSpireGrower.spireHeight; i += dreamSpireGrower.layerHeight) {
            //If layer y pos above initialSlimmingHeight reduce next layer width by slimming factor, adjusting for layerHeight
            int initialSlimmingHeight = (int) dreamSpireGrower.initialSlimmingHeightPercent * dreamSpireGrower.spireHeight;
            if(i >= initialSlimmingHeight) {
                layerWidth -= dreamSpireGrower.layerHeight * dreamSpireGrower.slimmingFactor;
            }

            //Place next spire layer with added displacements. If extraBaseLayers > 0, add extra layers as well
            int extraLayerNorthDisplacement = 0;
            int extraLayerEastDisplacement = 0;
            for(int layerCounter = 0; layerCounter < ((i < dreamSpireGrower.spireHeight * dreamSpireGrower.initialSlimmingHeightPercent) ? dreamSpireGrower.extraBaseLayers + 1 : 1); layerCounter++) {
                placeSpireLayer(pPos.above(i).north(northDisplacement + extraLayerNorthDisplacement).east(eastDisplacement + extraLayerEastDisplacement), config.blockPlacer, (int) layerWidth, dreamSpireGrower.layerHeight, randomSource, worldLevel);
                extraLayerEastDisplacement = randomSource.nextIntBetweenInclusive(-dreamSpireGrower.extraLayerOffsetRandomness, dreamSpireGrower.extraLayerOffsetRandomness);
                extraLayerNorthDisplacement = randomSource.nextIntBetweenInclusive(-dreamSpireGrower.extraLayerOffsetRandomness, dreamSpireGrower.extraLayerOffsetRandomness);
            }
            //Update displacements for next layer
            northDisplacement += randomSource.nextIntBetweenInclusive(0, dreamSpireGrower.horizontalLayerOffsetRandomness);
            eastDisplacement += randomSource.nextIntBetweenInclusive(0, dreamSpireGrower.horizontalLayerOffsetRandomness);
        }
        return true;
    }
    //Places spire layer
    private void placeSpireLayer(BlockPos anchorBlockPos, BlockStateProvider blockPlacer, int coreWidth, int layerHeight, RandomSource randomSource, WorldGenLevel worldLevel) {
        for(int y = 0; y < layerHeight; y++) {
            for (int x = 0; x < coreWidth + 2; x++) {
                for (int z = 0; z < coreWidth + 2; z++) {
                    if (!(x == 0 && (z == 0 || z == coreWidth + 1) || x == coreWidth + 1 && (z == 0 || z == coreWidth + 1))) {
                        tryPlaceBlock(anchorBlockPos.east(x).north(z).above(y), blockPlacer, randomSource, worldLevel);
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
