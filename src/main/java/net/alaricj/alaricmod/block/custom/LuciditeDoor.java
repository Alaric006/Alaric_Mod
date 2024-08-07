package net.alaricj.alaricmod.block.custom;

import net.alaricj.alaricmod.worldgen.dimension.ModDimensions;
import net.alaricj.alaricmod.worldgen.portal.ModTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class LuciditeDoor extends DoorBlock {

    public static final BooleanProperty CAN_TELEPORT_TO_DREAMLAND = BooleanProperty.create("can_teleport_to_dreamland");

    public LuciditeDoor(Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
        this.registerDefaultState(this.defaultBlockState().setValue(CAN_TELEPORT_TO_DREAMLAND, true));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            handleKaupenPortal(pPlayer, pPos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    public static int getLightLevel(BlockState doorBlockState) {
        return getCanTeleportToDreamland(doorBlockState) ? 4 : 0;
    }



    private void handleKaupenPortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.ALARIC_DIM_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.ALARIC_DIM_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == ModDimensions.ALARIC_DIM_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, false));
                }
            }
        }
    }
    public static boolean getCanTeleportToDreamland(BlockState pBlockState) {
        return pBlockState.getValue(CAN_TELEPORT_TO_DREAMLAND); //TODO: Make CAN_TELEPORT_TO_DREAMLAND depend on time
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(CAN_TELEPORT_TO_DREAMLAND);
    }
}
