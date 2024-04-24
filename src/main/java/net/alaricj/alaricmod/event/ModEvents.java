package net.alaricj.alaricmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.alaricj.alaricmod.TutorialMod;
import net.alaricj.alaricmod.item.ModItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber (modid = TutorialMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomVillagerTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).add((pTrader, pRandom) -> {
                return new MerchantOffer(
                        new ItemStack(Items.EMERALD, 2),
                        new ItemStack(ModItems.STRAWBERRY.get(), 12),
                        10,
                        8,
                        0.02f
                );
            });
        }
    }
    @SubscribeEvent
    public static void addCustomWanderingTraderTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 12),
                new ItemStack(ModItems.SAPPHIRE_STAFF.get(), 1),
                1,
                20,
                0.02f
        ));
    }
}
