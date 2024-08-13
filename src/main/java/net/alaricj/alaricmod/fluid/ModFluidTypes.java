package net.alaricj.alaricmod.fluid;

import net.alaricj.alaricmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TutorialMod.MOD_ID);

    public static final RegistryObject<FluidType> VOID_LAVA_FLUID_TYPE;

    static {
        final ResourceLocation VOID_LAVA_STILL = new ResourceLocation("block/lava_still");
        final ResourceLocation VOID_LAVA_FLOWING = new ResourceLocation("block/lava_flow");
        final ResourceLocation VOID_LAVA_OVERLAY = new ResourceLocation("block/lava_overlay");
        final int VOID_LAVA_TINT_COLOR = /*0xFF0D0A50*/ 0xFF171473;
        final Vector3f VOID_LAVA_FOG_COLOR = new Vector3f(191f / 255f, 33f / 255f, 48f / 255f);
        final FluidType.Properties VOID_LAVA_PROPERTIES = FluidType.Properties.create().lightLevel(10).canHydrate(false).adjacentPathType(BlockPathTypes.BLOCKED)
                .canConvertToSource(false).supportsBoating(false).canDrown(false).viscosity(15).density(45)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA).
                sound(SoundActions.FLUID_VAPORIZE, SoundEvents.LAVA_EXTINGUISH);

        VOID_LAVA_FLUID_TYPE = registerFluid("void_lava_fluid", new BaseFluidType(VOID_LAVA_STILL, VOID_LAVA_FLOWING, VOID_LAVA_OVERLAY, VOID_LAVA_TINT_COLOR, VOID_LAVA_FOG_COLOR, VOID_LAVA_PROPERTIES));
    }


    private static RegistryObject<FluidType> registerFluid(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
