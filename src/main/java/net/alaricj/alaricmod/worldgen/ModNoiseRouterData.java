package net.alaricj.alaricmod.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoiseRouterData extends NoiseRouterData {
    protected static NoiseRouter dreamland(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter< NormalNoise.NoiseParameters> pNoiseParameters, boolean pLarge, boolean pAmplified) {
        return overworld(pDensityFunctions, pNoiseParameters, pLarge, pAmplified);
    }
}
