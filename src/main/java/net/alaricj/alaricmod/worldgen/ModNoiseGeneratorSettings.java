package net.alaricj.alaricmod.worldgen;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class ModNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> DREAMLAND = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(TutorialMod.MOD_ID, "dreamland"));

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> pContext) {
        pContext.register(DREAMLAND, dreamland(pContext, false, false));
    }
    public static NoiseGeneratorSettings dreamland(BootstapContext<?> pContext, boolean pAmplified, boolean pLarge) {
        final NoiseSettings DREAMLAND_NOISE_SETTINGS = NoiseSettings.create(-64, 384, 1, 2);
        return new NoiseGeneratorSettings(DREAMLAND_NOISE_SETTINGS, Blocks.STONE.defaultBlockState(), Blocks.WATER.defaultBlockState(),
                ModNoiseRouterData.dreamland(pContext.lookup(Registries.DENSITY_FUNCTION), pContext.lookup(Registries.NOISE), pLarge, pAmplified),
                ModSurfaceRules.makeRules(), (new OverworldBiomeBuilder()).spawnTarget(), 63, false, true, true, false);
    }
}
