package net.alaricj.alaricmod.worldgen.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.alaricj.alaricmod.worldgen.tree.ModTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PineTrunkPlacer extends TrunkPlacer {
    private static final int  BRANCH_LENGTH = 5;

    public static final Codec<PineTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70261_) -> {
        return trunkPlacerParts(p_70261_).apply(p_70261_, PineTrunkPlacer::new);
    });

    public PineTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.PINE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource randomSource, int pTreeFreeHeight, BlockPos blockPos, TreeConfiguration treeConfiguration) {
        setDirtAt(levelSimulatedReader, pBlockSetter, randomSource, blockPos.below(), treeConfiguration);

        int height = pTreeFreeHeight + randomSource.nextInt(heightRandA, heightRandA + 3) + randomSource.nextInt(heightRandB, heightRandB + 2);

        for (int i = 0; i < height; i++) {
            placeLog(levelSimulatedReader, pBlockSetter, randomSource, blockPos.above(i), treeConfiguration);
            boolean isBranchLayer = (i % 2 == 0 && randomSource.nextBoolean());
            if (isBranchLayer) {
                if (randomSource.nextBoolean()) {
                    for (int x = 1; x < BRANCH_LENGTH + 1; x++) {
                        pBlockSetter.accept(blockPos.above(i).relative(Direction.NORTH, x), treeConfiguration.trunkProvider.getState(randomSource, blockPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
                    }
                }
                if (randomSource.nextBoolean()) {
                    for (int x = 1; x < BRANCH_LENGTH; x++) {
                        pBlockSetter.accept(blockPos.above(i).relative(Direction.SOUTH, x), treeConfiguration.trunkProvider.getState(randomSource, blockPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
                    }
                }
                if (randomSource.nextBoolean()) {
                    for (int x = 1; x < BRANCH_LENGTH + 1; x++) {
                        pBlockSetter.accept(blockPos.above(i).relative(Direction.EAST, x), treeConfiguration.trunkProvider.getState(randomSource, blockPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                    }
                }
                if (randomSource.nextBoolean()) {
                    for (int x = 1; x < BRANCH_LENGTH + 1; x++) {
                        pBlockSetter.accept(blockPos.above(i).relative(Direction.WEST, x), treeConfiguration.trunkProvider.getState(randomSource, blockPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
                    }
                }
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(blockPos.above(height), 0, false));
    }
}