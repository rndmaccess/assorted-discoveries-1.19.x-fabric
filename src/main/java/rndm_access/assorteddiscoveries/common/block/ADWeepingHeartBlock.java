package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADItems;
import rndm_access.assorteddiscoveries.common.core.ADParticleTypes;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

public class ADWeepingHeartBlock extends Block implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_3;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public ADWeepingHeartBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ADItems.WEEPING_HEART_SEEDS.getDefaultStack();
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = (double)i + random.nextDouble();
        double e = (double)j + 0.7D;
        double f = (double)k + random.nextDouble();

        if(state.get(AGE) >= 2) {
            world.addParticle(ADParticleTypes.FALLING_WEEPING_HEART_NECTAR, d, e, f, 0.0D, 0.0D, 0.0D);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for(int l = 0; l < 14; ++l) {
                mutable.set(i + MathHelper.nextInt(random, -10, 10),
                        j - random.nextInt(10),
                        k + MathHelper.nextInt(random, -10, 10));
                BlockState blockState = world.getBlockState(mutable);

                if (!blockState.isFullCube(world, mutable)) {
                    world.addParticle(ADParticleTypes.WEEPING_HEART_AIR_NECTAR,
                            (double)mutable.getX() + random.nextDouble(),
                            (double)mutable.getY() + random.nextDouble(),
                            (double)mutable.getZ() + random.nextDouble(),
                            0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);
        boolean holdingBoneMeal = heldStack.isOf(Items.BONE_MEAL);
        boolean holdingBucket = heldStack.isOf(Items.BUCKET);

        if (!holdingBoneMeal && holdingBucket && state.get(AGE) == 3) {
            givePlayerNectar(player, hand);
            resetWeepingHeart(state, world, pos);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private void givePlayerNectar(PlayerEntity player, Hand hand) {
        ItemStack nectarBucketStack = new ItemStack(ADItems.WEEPING_HEART_NECTAR_BUCKET);
        
        player.playSound(SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if(player.isCreative()) {
            PlayerInventory playerInventory = player.getInventory();

            if(!playerInventory.contains(nectarBucketStack)) {
                player.giveItemStack(nectarBucketStack);
            }
        } else {
            player.getStackInHand(hand).decrement(1);

            if(player.getStackInHand(hand).isEmpty()) {
                player.setStackInHand(hand, nectarBucketStack);
            } else {
                player.giveItemStack(nectarBucketStack);
            }
        }
    }

    private void resetWeepingHeart(BlockState state, World world, BlockPos pos) {
        BlockState blockState = state.with(AGE, 0);

        world.setBlockState(pos, blockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState supportingBlock = world.getBlockState(pos.up());

        return supportingBlock.isIn(CBlockTags.WEEPING_HEART_PLANTABLE_ON) && !world.isWater(pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos)
                ? Blocks.AIR.getDefaultState()
                : state;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 3;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(3, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), 2);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 3 && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            BlockState blockState = state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
