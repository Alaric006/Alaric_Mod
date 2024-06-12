package net.alaricj.alaricmod.worldgen.biome;

import net.alaricj.alaricmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.RegionType;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes(){
        Regions.register(new ModOverworldRegion(new ResourceLocation(TutorialMod.MOD_ID, "overworld"), RegionType.OVERWORLD, 2));
    }
}
