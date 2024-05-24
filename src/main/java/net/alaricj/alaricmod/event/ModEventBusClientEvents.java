package net.alaricj.alaricmod.event;

import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.block.entity.ModBlockEntities;
import net.alaricj.alaricmod.block.entity.renderer.GemPolishingBlockEntityRenderer;
import net.alaricj.alaricmod.entity.client.ModModelLayers;
import net.alaricj.alaricmod.entity.client.RhinoModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber (modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RHINO_LAYER, RhinoModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.GEM_POLISHING_STATION_BE.get(), GemPolishingBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
    }
}
