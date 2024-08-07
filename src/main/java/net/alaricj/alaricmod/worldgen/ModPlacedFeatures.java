package net.alaricj.alaricmod.worldgen;

import com.google.common.collect.ImmutableList;
import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> LUCIDITE_ORE_PLACED_KEY = registerKey("sapphire_ore_placed");
    public static final ResourceKey<PlacedFeature> PINE_PLACED_KEY = registerKey("pine_placed");

    public static final ResourceKey<PlacedFeature> DREAM_SPIRE_MEDIUM_PLACED_KEY = registerKey("dream_spire_medium_placed");

    public static final ResourceKey<PlacedFeature> LILURID_TREE_PLACED_KEY = registerKey("lilurid_tree_placed_key");

    public static ResourceKey<PlacedFeature> DREAM_BERRY_BUSH_PATCH_PLACED_KEY = registerKey("dream_berry_bush_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, LUCIDITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LUCIDITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, PINE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PINE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2), ModBlocks.PINE_SAPLING.get()));
        register(context, DREAM_SPIRE_MEDIUM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DREAM_SPIRE_MEDIUM_KEY),
                mediumDreamSpirePlacement(25));
        register(context, LILURID_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LILURID_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.1f, 1), ModBlocks.LILURID_SAPLING.get()));
        register(context, DREAM_BERRY_BUSH_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DREAM_BERRY_BUSH_PATCH), berryPatchPlacement(3, 8));
    }

    public static List<PlacementModifier> berryPatchPlacement(int count, int onceEveryRarity) {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), RarityFilter.onAverageOnceEvery(onceEveryRarity));
    }
    public static List<PlacementModifier> mediumDreamSpirePlacement(int rarity) {
        return ImmutableList.<PlacementModifier>builder()
                .add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
                .add(BiomeFilter.biome())
                .add(RarityFilter.onAverageOnceEvery(rarity))
                .add(NoiseBasedCountPlacement.of(16, 10.0d, 0.0d))
                .add()
                .build();
    }
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TutorialMod.MOD_ID, name));
    }
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
