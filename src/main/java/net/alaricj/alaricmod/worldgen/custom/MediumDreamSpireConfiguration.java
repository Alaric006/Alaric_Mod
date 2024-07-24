package net.alaricj.alaricmod.worldgen.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MediumDreamSpireConfiguration implements FeatureConfiguration {
    public final BlockStateProvider blockPlacer;
    public final int spireHeight;

    //Width of the central square to each spire "row"
    public final int spireCoreWidth;
    public static final Codec<MediumDreamSpireConfiguration> CODEC = RecordCodecBuilder.create((getter) -> {
        return getter.group(BlockStateProvider.CODEC.fieldOf("block_placer").forGetter( (getter1) -> {
            return getter1.blockPlacer;
        }), Codec.INT.fieldOf("spire_height").forGetter( (getter2) -> {
            return getter2.spireHeight;
        }), Codec.INT.fieldOf("spire_core_width").forGetter( (getter3) -> {
            return getter3.spireCoreWidth;
        })).apply(getter, MediumDreamSpireConfiguration::new);
    });

    public MediumDreamSpireConfiguration(BlockStateProvider blockPlacer, int spireHeight, int spireCoreWidth) {
        this.blockPlacer = blockPlacer;
        this.spireHeight = spireHeight;
        this.spireCoreWidth = spireCoreWidth;
    }
}
