package net.alaricj.alaricmod.worldgen.custom;

import com.mojang.serialization.Codec;
import net.alaricj.alaricmod.TutorialMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public abstract class ModFeature<FC extends FeatureConfiguration> extends Feature<FC> {

    public static DeferredRegister<Feature<?>> MOD_FEATURES = DeferredRegister.create(Registries.FEATURE, TutorialMod.MOD_ID);

    public static Feature<MediumDreamSpireConfiguration> DREAM_SPIRE_MEDIUM_FEATURE_VALUE =new MediumDreamSpireFeature(MediumDreamSpireConfiguration.CODEC);
    
    public static final RegistryObject<Feature<MediumDreamSpireConfiguration>> DREAM_SPIRE_MEDIUM_FEATURE = MOD_FEATURES.register("dream_spire_medium_feature",
            () -> DREAM_SPIRE_MEDIUM_FEATURE_VALUE);
    public ModFeature(Codec pCodec) {
        super(pCodec);
    }

    public static void register(IEventBus eventBus) {
        MOD_FEATURES.register(eventBus);
    }

}
