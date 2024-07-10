package net.alaricj.alaricmod.datagen;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TutorialMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ModBlocks.SAPPHIRE_BLOCK.get(),
                ModBlocks.RAW_SAPPHIRE_BLOCK.get(),
                ModBlocks.SOUND_BLOCK.get()
                );
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                ModBlocks.LUCIDITE_DOOR.get(),
                ModBlocks.LUCIDITE_ORE.get(),
                ModBlocks.DEEPSLATE_LUCIDITE_ORE.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.LUCIDITE_ORE.get(),
                ModBlocks.DEEPSLATE_LUCIDITE_ORE.get(),
                ModBlocks.SAPPHIRE_BLOCK.get(),
                ModBlocks.RAW_SAPPHIRE_BLOCK.get(),
                ModBlocks.SOUND_BLOCK.get(),
                ModBlocks.LUCIDITE_DOOR.get()
        );
        this.tag(BlockTags.FENCES).add(
                ModBlocks.SAPPHIRE_FENCE.get()
        );
        this.tag(BlockTags.FENCE_GATES).add(
                ModBlocks.SAPPHIRE_FENCE_GATE.get()
        );
        this.tag(BlockTags.WALLS).add(
                ModBlocks.SAPPHIRE_WALL.get()
        );

        this.tag(BlockTags.LOGS_THAT_BURN).add(
                ModBlocks.PINE_LOG.get(),
                ModBlocks.STRIPPED_PINE_LOG.get(),
                ModBlocks.PINE_WOOD.get(),
                ModBlocks.STRIPPED_PINE_WOOD.get(),
                ModBlocks.LILURID_LOG.get());

        this.tag(BlockTags.PLANKS).add(
                ModBlocks.PINE_PLANKS.get()
        );
        this.tag(BlockTags.LEAVES).add(
                ModBlocks.PINE_LEAVES.get()
        );
    }
}
