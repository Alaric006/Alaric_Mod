package net.alaricj.alaricmod.block.custom;

import com.google.common.collect.Lists;
import net.alaricj.alaricmod.block.ModBlocks;
import net.alaricj.alaricmod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraftforge.common.ForgeHooks.onCropsGrowPost;
import static net.minecraftforge.common.ForgeHooks.onCropsGrowPre;

public class ChangelingBushBlock extends BushBlock implements BonemealableBlock {

    //Type of the current bush block
    public static final EnumProperty<ChangelingBushType> BUSH_TYPE = ChangelingBushTypeProperty.create("soil_type");

    public static final IntegerProperty SOIL_STRENGTH = IntegerProperty.create("soil_strength", 0, 1);
    public static final VoxelShape SAPLING_SHAPE = Block.box(3d, 0d, 3d, 13d, 8d, 13d);
    private static final VoxelShape BUSH_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public static final float BONEMEAL_ADVANCE_AGE_CHANCE = 0.3f;

    public static final int MAX_AGE = 2;

    public static final int DREAM_BUSH_LIGHT_LEVEL = 12;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;



    public ChangelingBushBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
        pBuilder.add(BUSH_TYPE);
        pBuilder.add(SOIL_STRENGTH);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = pState.getValue(AGE);
        if(i < MAX_AGE && pLevel.getRawBrightness(pPos.above(), 0) > 12 && onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt(5) == 0)) {
            BlockState newBlockState = age(pLevel, pPos, pState);
            onCropsGrowPost(pLevel, pPos, newBlockState);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        for(ChangelingBushType bushType : ChangelingBushType.values()) {
            for(ChangelingBushType.SoilStrength soilStrength : bushType.blockSoilStrengths) {
                if(pState.is(soilStrength.block)) {
                    return true;
                }
            }
        }
        return false;
    }


    //Code modified from SweetBerryBushBlock
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int i = pState.getValue(AGE);
        boolean flag = i == MAX_AGE;
        if (!flag && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i > 1) {
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(pState.getValue(BUSH_TYPE).DropItem().get(), j));
            pLevel.playSound((Player)null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            BlockState blockstate = pState.setValue(AGE, Integer.valueOf(1));
            pLevel.setBlock(pPos, blockstate, 2);
            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockstate));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(pState.getValue(AGE) == 0) {
            return SAPLING_SHAPE;
        } else {
            return BUSH_SHAPE;
        }
    }
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < MAX_AGE;
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        if(pRandom.nextFloat() < BONEMEAL_ADVANCE_AGE_CHANCE) {
            age(pLevel, pPos, pState);
        }
    }

    private BlockState age(ServerLevel pLevel, BlockPos pPos, BlockState pState) {
        if (pState.getValue(AGE) < MAX_AGE) {
            BlockState newState = pState.setValue(AGE, pState.getValue(AGE) + 1);
            Block soilBlock = pLevel.getBlockState(pPos.below()).getBlock();
            ChangelingBushType outputBushType = getOutputBushType(pState.getValue(BUSH_TYPE), soilBlock);
            newState = newState.setValue(BUSH_TYPE, outputBushType);
            pLevel.setBlock(pPos, newState, 2);
            return newState;
        }
        return pState;
    }

    private ChangelingBushType getOutputBushType(ChangelingBushType bushType, Block soilBlock) {
        if(bushType == ChangelingBushType.NothingBush) {
            return ChangelingBushType.NothingBush;
        }
        else if(bushType == ChangelingBushType.DreamBush) {
            if(soilBlock == ModBlocks.DREAMLAND_DIRT.get()) {
                return ChangelingBushType.DreamBush;
            } else {
                return ChangelingBushType.NothingBush;
            }
        } else {
            return ChangelingBushType.NothingBush;
        }
    }

    public static int getLightLevel(BlockState pState) {
        if(pState.getValue(BUSH_TYPE) == ChangelingBushType.DreamBush) {
            return DREAM_BUSH_LIGHT_LEVEL;
        }
        return 0;
    }

    public enum ChangelingBushType implements StringRepresentable {
       NothingBush(0, "nothing_bush", () -> Items.SWEET_BERRIES, List.of(new SoilStrength(ModBlocks.DEPLETED_DREAMLAND_DIRT.get(),0 ))),
        DreamBush(1, "dream_berry_bush", () -> ModItems.DREAM_BERRY.get(), List.of(new SoilStrength(ModBlocks.DREAMLAND_DIRT.get(), 0)));

        //TODO: Change placeOnBlock from temporary SAPPHIRE_BLOCK once block is made
       /*VoidBush(2, "void_bush", , List.of(new SoilStrength(ModBlocks.SAPPHIRE_BLOCK.get(), 0))),
        //TODO: Don't forget here too
        EverythingBush(2, "everything_bush", , List.of(
                new SoilStrength(ModBlocks.DREAMLAND_DIRT.get(), 0),
                new SoilStrength(ModBlocks.SAPPHIRE_BLOCK.get(), 0)
        ));*/

        //Unique identifier for each variant
        final int identifier;
        final String name;

        public Supplier<Item> DropItem() {
            return DropItem;
        }

        final Supplier<Item> DropItem;
        public final Collection<SoilStrength> blockSoilStrengths;
        ChangelingBushType(int identifier, String name, Supplier<Item> dropItem, Collection<SoilStrength> blockSoilStrengths) {
            this.identifier = identifier;
            this.name = name;
            DropItem = dropItem;
            this.blockSoilStrengths = blockSoilStrengths;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
        public static class SoilStrength {

            public Block block;
            public int strength;

            public SoilStrength(Block block, int strength) {
                this.block = block;
                this.strength = strength;
            }

            public Block getBlock() {
                return block;
            }

            public int getStrength() {
                return strength;
            }
        }
    }
    public static class ChangelingBushTypeProperty extends EnumProperty<ChangelingBushType> {
        protected ChangelingBushTypeProperty(String pName, Collection<ChangelingBushType> pValues) {
            super(pName, ChangelingBushType.class, pValues);
        }
        public static ChangelingBushTypeProperty create(String name) {
            return new ChangelingBushTypeProperty(name, Lists.newArrayList(
                    ChangelingBushType.NothingBush, ChangelingBushType.DreamBush));
        }
    }
}
