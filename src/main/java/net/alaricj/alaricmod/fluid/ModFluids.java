package net.alaricj.alaricmod.fluid;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TutorialMod.MOD_ID);


    public static final RegistryObject<FlowingFluid> SOURCE_VOID_LAVA = FLUIDS.register("void_lava_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.VOID_LAVA_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_VOID_LAVA = FLUIDS.register("flowing_void_lava_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.VOID_LAVA_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties VOID_LAVA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.VOID_LAVA_FLUID_TYPE, ModFluids.SOURCE_VOID_LAVA, ModFluids.FLOWING_VOID_LAVA)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.VOID_LAVA_BLOCK).bucket(ModItems.VOID_LAVA_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
