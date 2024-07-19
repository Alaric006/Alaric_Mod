package net.alaricj.alaricmod.worldgen.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MediumDreamSpireConfiguration implements FeatureConfiguration {
    public final BlockStateProvider blockPlacer;
    public static final Codec<MediumDreamSpireConfiguration> CODEC = BlockStateProvider.CODEC.fieldOf("block_placer").xmap(MediumDreamSpireConfiguration::new,
            (getter1) -> {return getter1.blockPlacer;}
    ).codec();

    public MediumDreamSpireConfiguration(BlockStateProvider blockPlacer) {
        this.blockPlacer = blockPlacer;
    }
}
