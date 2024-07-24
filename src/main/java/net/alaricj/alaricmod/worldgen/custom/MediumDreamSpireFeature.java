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
        //Build spire from top to bottom, row by row
        for(int i = -4; i < config.spireHeight; i++) {
            placeSpireLayer(pPos.above(i), config.blockPlacer, config.spireCoreWidth, randomSource, worldLevel);
        }
        return true;
    }
    //Places on spire
    private void placeSpireLayer(BlockPos anchorBlockPos, BlockStateProvider blockPlacer, int coreWidth, RandomSource randomSource, WorldGenLevel worldLevel) {
        for(int x = 0; x < coreWidth + 2; x++) {
            for(int z = 0; z < coreWidth + 2; z++) {
                if(!(x == 0 && (z == 0 || z == coreWidth + 1) || x == coreWidth + 1 && (z == 0 || z == coreWidth + 1))) {
                    tryPlaceBlock(anchorBlockPos.east(x).north(z), blockPlacer, randomSource, worldLevel);
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
