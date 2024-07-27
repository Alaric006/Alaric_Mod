package net.alaricj.alaricmod.datagen.loot;

import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.block.custom.StrawberryCropBlock;
import net.alaricj.alaricmod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SAPPHIRE_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_SAPPHIRE_BLOCK.get());

        this.dropSelf(ModBlocks.DREAMLAND_DIRT.get());
        this.dropSelf(ModBlocks.DEPLETED_DREAMLAND_DIRT.get());
        this.dropSelf(ModBlocks.LILURID_LOG.get());
        this.dropSelf(ModBlocks.LILURID_SAPLING.get());

        this.dropSelf(ModBlocks.SOUND_BLOCK.get());
        this.createOreDrop(ModBlocks.LUCIDITE_ORE.get(), ModItems.LUCIDITE.get());
        this.add(ModBlocks.LUCIDITE_ORE.get(),
                block -> createOreDrop(ModBlocks.LUCIDITE_ORE.get(), ModItems.LUCIDITE.get()));
        this.add(ModBlocks.DEEPSLATE_LUCIDITE_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_LUCIDITE_ORE.get(), ModItems.LUCIDITE.get()));
        this.dropSelf(ModBlocks.SAPPHIRE_STAIRS.get());
        this.dropSelf(ModBlocks.SAPPHIRE_BLOCK.get());
        this.dropSelf(ModBlocks.SAPPHIRE_BUTTON.get());
        this.dropSelf(ModBlocks.SAPPHIRE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_TRAPDOOR.get());
        this.dropSelf(ModBlocks.SAPPHIRE_FENCE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_FENCE_GATE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_WALL.get());

        this.add(ModBlocks.SAPPHIRE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SAPPHIRE_SLAB.get()));
        this.add(ModBlocks.LUCIDITE_DOOR.get(), block -> createDoorTable(ModBlocks.LUCIDITE_DOOR.get()));
        //TODO: Add loot table for DREAM_GLASS having a rare change to drop dream shard

        LootItemCondition.Builder strawberryCropConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));

        LootItemCondition.Builder cornCropConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 8));

        this.add(ModBlocks.STRAWBERRY_CROP.get(), createCropDrops(ModBlocks.STRAWBERRY_CROP.get(), ModItems.STRAWBERRY.get(), ModItems.STRAWBERRY_SEEDS.get(), strawberryCropConditionBuilder));
        this.add(ModBlocks.CORN_CROP.get(), createCropDrops(ModBlocks.CORN_CROP.get(), ModItems.CORN.get(), ModItems.CORN_SEEDS.get(), cornCropConditionBuilder));
        this.dropSelf(ModBlocks.CATMINT.get());
        this.add(ModBlocks.POTTED_CATMINT.get(), createPotFlowerItemTable(ModBlocks.CATMINT.get()));

        this.dropSelf(ModBlocks.GEM_POLISHING_STATION.get());

        this.dropSelf(ModBlocks.PINE_LOG.get());
        this.dropSelf(ModBlocks.PINE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_PINE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_PINE_WOOD.get());
        this.dropSelf(ModBlocks.PINE_PLANKS.get());
        this.add(ModBlocks.PINE_LEAVES.get(),
                block -> createLeavesDrops(block, ModBlocks.PINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
        );
        this.dropSelf(ModBlocks.PINE_SAPLING.get());

        this.add(ModBlocks.PINE_SIGN.get(), block -> createSingleItemTable(ModBlocks.PINE_SIGN.get()));
        this.add(ModBlocks.PINE_WALL_SIGN.get(), block -> createSingleItemTable(ModBlocks.PINE_SIGN.get()));
        this.add(ModBlocks.PINE_HANGING_SIGN.get(), block -> createSingleItemTable(ModBlocks.PINE_HANGING_SIGN.get()));
        this.add(ModBlocks.PINE_WALL_HANGING_SIGN.get(), block -> createSingleItemTable(ModBlocks.PINE_HANGING_SIGN.get()));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
