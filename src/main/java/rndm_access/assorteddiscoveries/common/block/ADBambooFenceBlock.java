package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import rndm_access.assorteddiscoveries.common.core.ADBlockTags;

public class ADBambooFenceBlock extends FenceBlock {
    public static final BooleanProperty UP = Properties.UP;

    public ADBambooFenceBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(NORTH, false).with(SOUTH, false)
                .with(WEST, false).with(EAST, false).with(WATERLOGGED, false).with(UP, false));
    }

    @Override
    public boolean canConnect(BlockState state, boolean neighborIsFullSquare, Direction dir) {
        Block block = state.getBlock();
        boolean isGate = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, dir);

        return !cannotConnect(state) && neighborIsFullSquare || isGate || state.isIn(ADBlockTags.BAMBOO_FENCES);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean isWaterlogged = state.get(WATERLOGGED);
        Direction.Axis axis = direction.getAxis();

        if (isWaterlogged) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if(axis.isHorizontal()) {
            state = state.with(FACING_PROPERTIES.get(direction), this.hasNeighborConnection(world, neighborPos, direction));
        }

        if(axis.isVertical()) {
            state = state.with(UP, this.hasBambooFenceAbove(world, pos));
        }
        return state;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        boolean isWater = world.getFluidState(pos).isOf(Fluids.WATER);

        return this.getDefaultState()
                .with(NORTH, this.hasNeighborConnection(world, pos.north(), Direction.NORTH))
                .with(SOUTH, this.hasNeighborConnection(world, pos.south(), Direction.SOUTH))
                .with(WEST, this.hasNeighborConnection(world, pos.west(), Direction.WEST))
                .with(EAST, this.hasNeighborConnection(world, pos.east(), Direction.EAST))
                .with(WATERLOGGED, isWater)
                .with(UP, this.hasBambooFenceAbove(world, pos));
    }

    private boolean hasNeighborConnection(WorldAccess world, BlockPos neighborPos, Direction direction) {
        BlockState neighborState = world.getBlockState(neighborPos);
        Direction oppDir = direction.getOpposite();

        return this.canConnect(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, oppDir), oppDir);
    }

    private boolean hasBambooFenceAbove(WorldAccess world, BlockPos pos) {
        BlockState state = world.getBlockState(pos.up());

        return state.isIn(ADBlockTags.BAMBOO_FENCES);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, WEST, EAST, WATERLOGGED, UP);
    }
}
