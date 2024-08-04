package net.alaricj.alaricmod.item.custom;

import net.alaricj.alaricmod.block.custom.ChangelingBushBlock;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import net.alaricj.alaricmod.block.custom.ChangelingBushBlock;
import org.jetbrains.annotations.Nullable;


public class ChangelingBerry extends ItemNameBlockItem {


    public ChangelingBushBlock.ChangelingBushType SEED_TYPE;
    public ChangelingBerry(Block pBlock, ChangelingBushBlock.ChangelingBushType seedType, Properties pProperties) {
        super(pBlock, pProperties);
        this.SEED_TYPE = seedType;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        BlockState blockState = super.getPlacementState(pContext);
        return blockState != null ? blockState.setValue(ChangelingBushBlock.BUSH_TYPE, ChangelingBushBlock.ChangelingBushType.DreamBush): null;
    }
}

