package net.alaricj.alaricmod.item;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TutorialMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LUCIDITE.get()))
                    .title(Component.translatable("creative_tab.tutorial_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.LUCIDITE.get());
                        output.accept(ModItems.RAW_SAPPHIRE.get());
                        output.accept(ModItems.PINE_CONE.get());
                        output.accept(ModItems.METAL_DETECTOR.get());
                        output.accept(ModItems.SAPPHIRE_STAFF.get());
                        output.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                        output.accept(ModBlocks.SAPPHIRE_STAIRS.get());
                        output.accept(ModBlocks.SAPPHIRE_BUTTON.get());
                        output.accept(ModBlocks.SAPPHIRE_SLAB.get());
                        output.accept(ModBlocks.SAPPHIRE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.SAPPHIRE_FENCE.get());
                        output.accept(ModBlocks.SAPPHIRE_FENCE_GATE.get());
                        output.accept(ModBlocks.SAPPHIRE_WALL.get());
                        output.accept(ModBlocks.LUCIDITE_DOOR.get());
                        output.accept(ModBlocks.SAPPHIRE_TRAPDOOR.get());
                        output.accept(ModBlocks.RAW_SAPPHIRE_BLOCK.get());
                        output.accept(ModBlocks.SOUND_BLOCK.get());
                        output.accept(ModBlocks.LUCIDITE_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_LUCIDITE_ORE.get());
                        output.accept(ModBlocks.DREAMLAND_DIRT.get());
                        output.accept(ModBlocks.DEPLETED_DREAMLAND_DIRT.get());
                        output.accept(ModBlocks.DREAM_GLASS.get());
                        output.accept(ModBlocks.GEM_POLISHING_STATION.get());

                        output.accept(ModBlocks.PINE_LOG.get());
                        output.accept(ModBlocks.STRIPPED_PINE_LOG.get());
                        output.accept(ModBlocks.PINE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_PINE_WOOD.get());
                        output.accept(ModBlocks.PINE_LEAVES.get());
                        output.accept(ModBlocks.PINE_SAPLING.get());

                        output.accept(ModBlocks.PINE_PLANKS.get());
                        output.accept(ModItems.PINE_SIGN.get());
                        output.accept(ModItems.PINE_BOAT.get());
                        output.accept(ModItems.PINE_CHEST_BOAT.get());
                        output.accept(ModItems.PINE_HANGING_SIGN.get());

                        output.accept(ModItems.STRAWBERRY_SEEDS.get());
                        output.accept(ModItems.CORN_SEEDS.get());
                        output.accept(ModItems.STRAWBERRY.get());
                        output.accept(ModItems.CORN.get());
                        output.accept(ModBlocks.CATMINT.get());
                        output.accept(ModItems.BAR_BRAWL_MUSIC_DISC.get());
                        output.accept(ModItems.DICE.get());
                        output.accept(ModItems.SAPPHIRE_SWORD.get());
                        output.accept(ModItems.SAPPHIRE_PICKAXE.get());
                        output.accept(ModItems.SAPPHIRE_AXE.get());
                        output.accept(ModItems.SAPPHIRE_SHOVEL.get());
                        output.accept(ModItems.SAPPHIRE_HOE.get());
                        output.accept(ModBlocks.MOD_PORTAL.get());

                        output.accept(ModItems.RHINO_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
