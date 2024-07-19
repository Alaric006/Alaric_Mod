package net.alaricj.alaricmod.worldgen.custom;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

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
        if(pPos.getY() < worldLevel.getMinBuildHeight() + 5) {
            return false;
        }
        for(BlockPos pos1 : BlockPos.betweenClosed(pPos, pPos.offset(3, 3, 3))) {
            worldLevel.setBlock(pos1, config.blockPlacer.getState(randomSource, pos1), 4);
        }
        return true;
    }
}
