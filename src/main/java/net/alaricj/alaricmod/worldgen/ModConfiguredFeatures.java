package net.alaricj.alaricmod.worldgen;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.worldgen.custom.MediumDreamSpireConfiguration;
import net.alaricj.alaricmod.worldgen.custom.MediumDreamSpireFeature;
import net.alaricj.alaricmod.worldgen.custom.ModFeature;
import net.alaricj.alaricmod.worldgen.tree.custom.PineFoliagePlacer;
import net.alaricj.alaricmod.worldgen.tree.custom.PineTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LUCIDITE_ORE_KEY = registerKey("lucidite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SAPPHIRE_ORE_KEY = registerKey("nether_sapphire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_SAPPHIRE_ORE_KEY = registerKey("end_sapphire_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_KEY = registerKey("pine");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAM_SPIRE_MEDIUM_KEY = registerKey("dream_spire_medium");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepSlateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);
        HolderGetter<Feature<?>> features = context.lookup(Registries.FEATURE);

        List<OreConfiguration.TargetBlockState> overworldLuciditeOres = List.of(OreConfiguration.target(stoneReplaceables, ModBlocks.LUCIDITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepSlateReplaceables, ModBlocks.DEEPSLATE_LUCIDITE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_LUCIDITE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldLuciditeOres, 9));

        register(context, PINE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PINE_LOG.get()),
                new PineTrunkPlacer(5, 4, 3),
                BlockStateProvider.simple(ModBlocks.PINE_LEAVES.get()),
                new PineFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        final MediumDreamSpireConfiguration.MediumDreamSpireGrower dreamSpireGrower = new MediumDreamSpireConfiguration.MediumDreamSpireGrower(
                50,
                0.5f,
                0.2f,
                12,
                2,
                2
        );

        register(context, DREAM_SPIRE_MEDIUM_KEY, ModFeature.DREAM_SPIRE_MEDIUM_FEATURE.get(),
                new MediumDreamSpireConfiguration(BlockStateProvider.simple(ModBlocks.DREAMLAND_DIRT.get().defaultBlockState()),
                        dreamSpireGrower
                ));

    }
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TutorialMod.MOD_ID, name));
    }
    private static  <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                           ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
