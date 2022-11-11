package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADItems;

public class ADCornCropBlock extends BeetrootsBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final VoxelShape SMALL = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    public static final VoxelShape MEDIUM = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public ADCornCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AGE)) {
            case 0 -> SMALL;
            case 1 -> MEDIUM;
            default -> VoxelShapes.fullCube();
        };
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.get(HALF);

        if(direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)
                && !neighborState.isOf(this) && state.get(AGE) > 1) {
            return Blocks.AIR.getDefaultState();
        }
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : state;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState floor = world.getBlockState(blockPos);

        if(state.get(HALF) != DoubleBlockHalf.UPPER) {
            return super.canPlantOnTop(floor, world, blockPos);
        }
        return floor.isOf(this) && floor.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Only grow the bottom of the plant to prevent three high corns and faster corn growth.
        if(state.get(HALF) == DoubleBlockHalf.LOWER) {
            boolean isYoung = state.get(AGE) < 1;

            if(isYoung || canGrowInto(world, pos)) {
                if (random.nextInt(3) != 0 && world.getBaseLightLevel(pos, 0) >= 9) {
                    int age = this.getAge(state);

                    if (age < this.getMaxAge()) {
                        float f = getAvailableMoisture(this, world, pos);

                        if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                            int nextAge = age + 1;

                            world.setBlockState(pos, this.withAge(nextAge), 2);

                            // After stage 1 grow another block.
                            if (nextAge > 1) {
                                world.setBlockState(pos.up(), this.withAge(nextAge).with(HALF, DoubleBlockHalf.UPPER), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canGrowInto(ServerWorld world, BlockPos pos) {
        BlockState topState = world.getBlockState(pos.up());

        return topState.isAir() || topState.isOf(this);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ADItems.CORN_COB;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }
}
