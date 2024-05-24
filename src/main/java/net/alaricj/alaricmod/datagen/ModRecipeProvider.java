package net.alaricj.alaricmod.datagen;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.item.ModItems;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPPHIRE.get(), ModBlocks.SAPPHIRE_ORE.get(), ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");
        oreSmelting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
                .save(pWriter);

        planksFromLog(ModBlocks.PINE_LOG, ModBlocks.PINE_PLANKS, pWriter);
        planksFromLog(ModBlocks.STRIPPED_PINE_LOG, ModBlocks.PINE_PLANKS, pWriter);
        planksFromLog(ModBlocks.PINE_WOOD, ModBlocks.PINE_PLANKS, pWriter);
        planksFromLog(ModBlocks.STRIPPED_PINE_WOOD, ModBlocks.PINE_PLANKS, pWriter);

        woodFromLogs(ModBlocks.PINE_LOG, ModBlocks.PINE_WOOD, pWriter);
        woodFromLogs(ModBlocks.STRIPPED_PINE_LOG, ModBlocks.STRIPPED_PINE_WOOD, pWriter);

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        Iterator var9 = pIngredients.iterator();

        while (var9.hasNext()) {
            ItemLike itemlike = (ItemLike) var9.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult, pExperience,
                    pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike),
                    has(itemlike)).save(pFinishedRecipeConsumer,
                    TutorialMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void planksFromLog(RegistryObject<Block> logBlock, RegistryObject<Block> plankBlock, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock.get(), 4)
                .group("planks")
                .requires(logBlock.get(), 1).
                unlockedBy(getHasName(logBlock.get()), has(logBlock.get()))
                .save(pWriter, TutorialMod.MOD_ID + ":" + getItemName(plankBlock.get()) + "_from_" + getItemName(logBlock.get()));
    }

    protected static void woodFromLogs(RegistryObject<Block> logBlock, RegistryObject<Block> woodBlock, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, woodBlock.get(), 3)
                .group("bark")
                .pattern("LL")
                .pattern("LL")
                .define('L', logBlock.get())
                .unlockedBy(getHasName(logBlock.get()), has(logBlock.get()))
                .save(pWriter, TutorialMod.MOD_ID + ":" + getItemName(woodBlock.get()) + "_from_" + getItemName(logBlock.get()));
    }
}
