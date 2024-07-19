package net.alaricj.alaricmod.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModDreamSpirePlacement {

    public static List<PlacementModifier> mediumDreamSpirePlacement() {
        return ImmutableList.<PlacementModifier>builder()
                .add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
                .add(BiomeFilter.biome())
                .build();
    }
}
