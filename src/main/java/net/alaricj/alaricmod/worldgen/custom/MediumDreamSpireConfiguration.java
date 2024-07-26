package net.alaricj.alaricmod.worldgen.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MediumDreamSpireConfiguration implements FeatureConfiguration {
    public final BlockStateProvider blockPlacer;

    MediumDreamSpireGrower dreamSpireGrower;

    public static final Codec<MediumDreamSpireConfiguration> CODEC = RecordCodecBuilder.create((getter) -> {
        return getter.group(BlockStateProvider.CODEC.fieldOf("block_placer").forGetter( (getter1) -> {
            return getter1.blockPlacer;
        }), MediumDreamSpireGrower.CODEC.fieldOf("dream_spire_grower").forGetter( (getter2) -> {
            return getter2.dreamSpireGrower;
        })).apply(getter, MediumDreamSpireConfiguration::new);
    });

    public MediumDreamSpireConfiguration(BlockStateProvider blockPlacer, MediumDreamSpireGrower dreamSpireGrower) {
        this.blockPlacer = blockPlacer;
        this.dreamSpireGrower = dreamSpireGrower;
    }
    public static class MediumDreamSpireGrower {
        public final int spireHeight;
        //Percent of height above which to reduce spire layer width
        public final float initialSlimmingHeightPercent;

        //How much to reduce layer width by per vertical block height above initialSlimmingHeight
        public final float slimmingFactor;

        //Width of the central square to each spire "row"
        public final int spireCoreWidth;
        //Horizontal randomness in spire generation
        public final int horizontalLayerOffsetRandomness;

        public final int layerHeight;

        //How many extra layers should be placed in the base of spire (below slimming height)
        public final int extraBaseLayers;

        //Percent of horizontalLayerOffsetRandomness to apply to extra layers
        public final int extraLayerOffsetRandomness;
        public static Codec<MediumDreamSpireGrower> CODEC = RecordCodecBuilder.create((initialGetter) -> {
            return initialGetter.group(Codec.INT.fieldOf("spire_height").forGetter((getter) -> {
                return getter.spireHeight;
            }), Codec.FLOAT.fieldOf("initial_slimming_height_percent").forGetter( (getter) -> {
                return getter.initialSlimmingHeightPercent;
            }), Codec.FLOAT.fieldOf("slimming_factor").forGetter( (getter) -> {
                return getter.slimmingFactor;
            }), Codec.INT.fieldOf("spire_core_width").forGetter( (getter) -> {
                return getter.spireCoreWidth;
            }), Codec.INT.fieldOf("horizontal_layer_offset_randomness").forGetter((getter) -> {
                return getter.horizontalLayerOffsetRandomness;
            }), Codec.INT.fieldOf("layer_height").forGetter((getter) -> {
                return getter.layerHeight;
            }), Codec.INT.fieldOf("extra_base_layers").forGetter((getter) -> {
                return getter.extraBaseLayers;
            }), Codec.INT.fieldOf("extra_layer_offset_randomness_percent").forGetter( (getter) -> {
                return getter.extraLayerOffsetRandomness;
            })).apply(initialGetter, MediumDreamSpireGrower::new);
        });

        public MediumDreamSpireGrower(int spireHeight, float initialSlimmingHeightPercent, float slimmingFactor, int spireCoreWidth, int horizontalLayerOffsetRandomness, int layerHeight, int extraBaseLayers, int extraLayerOffsetRandomness) {
            this.spireHeight = spireHeight;
            this.initialSlimmingHeightPercent = initialSlimmingHeightPercent;
            this.slimmingFactor = slimmingFactor;
            this.spireCoreWidth = spireCoreWidth;
            this.horizontalLayerOffsetRandomness = horizontalLayerOffsetRandomness;
            this.layerHeight = layerHeight;
            this.extraBaseLayers = extraBaseLayers;
            this.extraLayerOffsetRandomness = extraLayerOffsetRandomness;
        }
    }
}
