package net.alaricj.alaricmod.worldgen.biome.surface;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DREAMLAND_DIRT = makeStateRule(ModBlocks.DREAMLAND_DIRT.get());
    private static final SurfaceRules.RuleSource DEPLETED_DREAMLAND_DIRT = makeStateRule(ModBlocks.DEPLETED_DREAMLAND_DIRT.get());


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.CLIFFS_OF_ASPIRATION),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, DEPLETED_DREAMLAND_DIRT)),


                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        ));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

}