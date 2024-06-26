package net.alaricj.alaricmod.item.custom;

import net.alaricj.alaricmod.entity.custom.ModBoatEntity;
import net.alaricj.alaricmod.entity.custom.ModChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE;
    private final ModBoatEntity.Type type;
    private final boolean hasChest;

    public ModBoatItem(boolean pHasChest, ModBoatEntity.Type pType, Item.Properties pProperties) {
        super(pProperties);
        this.hasChest = pHasChest;
        this.type = pType;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack $$3 = pPlayer.getItemInHand(pHand);
        HitResult $$4 = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        if ($$4.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass($$3);
        } else {
            Vec3 $$5 = pPlayer.getViewVector(1.0F);
            double $$6 = 5.0;
            List<Entity> $$7 = pLevel.getEntities(pPlayer, pPlayer.getBoundingBox().expandTowards($$5.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
            if (!$$7.isEmpty()) {
                Vec3 $$8 = pPlayer.getEyePosition();
                Iterator var11 = $$7.iterator();

                while(var11.hasNext()) {
                    Entity $$9 = (Entity)var11.next();
                    AABB $$10 = $$9.getBoundingBox().inflate((double)$$9.getPickRadius());
                    if ($$10.contains($$8)) {
                        return InteractionResultHolder.pass($$3);
                    }
                }
            }

            if ($$4.getType() == HitResult.Type.BLOCK) {
                Boat boat = this.getBoat(pLevel, $$4);
                if(boat instanceof ModBoatEntity) {
                    ((ModBoatEntity) boat).setVariant(type);
                } else if(boat instanceof ModChestBoatEntity) {
                    ((ModChestBoatEntity) boat).setVariant(type);
                }
                boat.setYRot(pPlayer.getYRot());
                if (!pLevel.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail($$3);
                } else {
                    if (!pLevel.isClientSide) {
                        pLevel.addFreshEntity(boat);
                        pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, $$4.getLocation());
                        if (!pPlayer.getAbilities().instabuild) {
                            $$3.shrink(1);
                        }
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess($$3, pLevel.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass($$3);
            }
        }
    }

    private Boat getBoat(Level pLevel, HitResult pHitResult) {
        return (Boat) (this.hasChest ? new ModChestBoatEntity(pLevel, pHitResult.getLocation().x, pHitResult.getLocation().y, pHitResult.getLocation().z) : new ModBoatEntity(pLevel, pHitResult.getLocation().x, pHitResult.getLocation().y, pHitResult.getLocation().z));
    }

    static {
        ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }
}
