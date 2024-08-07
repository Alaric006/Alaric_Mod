package net.alaricj.alaricmod.worldgen;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.block.custom.ChangelingBushBlock;
import net.alaricj.alaricmod.worldgen.custom.MediumDreamSpireConfiguration;
import net.alaricj.alaricmod.worldgen.custom.MediumDreamSpireConfiguration.MediumDreamSpireGrower;
import net.alaricj.alaricmod.worldgen.custom.ModFeature;
import net.alaricj.alaricmod.worldgen.tree.custom.PineFoliagePlacer;
import net.alaricj.alaricmod.worldgen.tree.custom.PineTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LUCIDITE_ORE_KEY = registerKey("lucidite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SAPPHIRE_ORE_KEY = registerKey("nether_sapphire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_SAPPHIRE_ORE_KEY = registerKey("end_sapphire_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_KEY = registerKey("pine");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAM_SPIRE_MEDIUM_KEY = registerKey("dream_spire_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LILURID_TREE = registerKey("lilurid_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAM_BERRY_BUSH_PATCH = registerKey("dream_berry_bush_patch");



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

        register(context, DREAM_BERRY_BUSH_PATCH, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CHANGELING_BUSH.get().defaultBlockState().setValue(ChangelingBushBlock.AGE, 2).setValue(ChangelingBushBlock.BUSH_TYPE, ChangelingBushBlock.ChangelingBushType.DreamBush))), List.of(ModBlocks.DREAMLAND_DIRT.get()))
        );

        UniformInt spireSize = UniformInt.of(8, 16);
        //Create dreamSpireGrower for DreamSpireMediumConfiguration
        final MediumDreamSpireConfiguration.MediumDreamSpireGrower dreamSpireGrower = new MediumDreamSpireConfiguration.MediumDreamSpireGrower(
                UniformInt.of(60, 80),
                0.5f,
                0.2f,
                spireSize,
                2,
                2,
                2,
                1,
                0.6f,
                0.03f
        );

        register(context, DREAM_SPIRE_MEDIUM_KEY, ModFeature.DREAM_SPIRE_MEDIUM_FEATURE.get(),
                new MediumDreamSpireConfiguration(BlockStateProvider.simple(ModBlocks.DREAMLAND_DIRT.get().defaultBlockState()),
                        BlockStateProvider.simple(ModBlocks.LUCIDITE_ORE.get().defaultBlockState()),
                        dreamSpireGrower));
        //Add lilurid tree
        //TODO: Play with tree configuration away from Azalea tree copied features
        register(context, LILURID_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.LILURID_LOG.get()),
                new BendingTrunkPlacer(4, 2, 0, 3, UniformInt.of(2, 3)),
                BlockStateProvider.simple(ModBlocks.DREAM_GLASS.get()),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50),
                new TwoLayersFeatureSize(1, 0 ,1)).dirt(BlockStateProvider.simple(ModBlocks.DREAMLAND_DIRT.get())).build());
    }
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TutorialMod.MOD_ID, name));
    }
    private static  <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                           ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
