package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADItems;
import rndm_access.assorteddiscoveries.common.core.ADParticleTypes;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

public class ADWeepingHeartBlock extends Block implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_3;

    public ADWeepingHeartBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ADItems.WEEPING_HEART_SEEDS.getDefaultStack();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int xOrigin = pos.getX();
        int yOrigin = pos.getY();
        int zOrigin = pos.getZ();
        double fallingX = xOrigin + random.nextDouble();
        double fallingY = yOrigin + 0.7D;
        double fallingZ = zOrigin + random.nextDouble();

        // When the weeping heart has nectar play the particles.
        if(state.get(AGE) >= 2) {
            // Play dripping particles under the plant.
            world.addParticle(ADParticleTypes.FALLING_WEEPING_HEART_NECTAR, fallingX, fallingY, fallingZ,
                    0.0D, 0.0D, 0.0D);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            // Play air particles that float around the plant.
            for(int l = 0; l < 14; ++l) {
                int floatingXOrigin = xOrigin + MathHelper.nextInt(random, -10, 10);
                int floatingYOrigin = yOrigin - random.nextInt(10);
                int floatingZOrigin = zOrigin + MathHelper.nextInt(random, -10, 10);

                mutable.set(floatingXOrigin, floatingYOrigin, floatingZOrigin);
                BlockState blockState = world.getBlockState(mutable);

                if (!blockState.isFullCube(world, mutable)) {
                    double floatingX = mutable.getX() + random.nextDouble();
                    double floatingY = mutable.getY() + random.nextDouble();
                    double floatingZ = mutable.getZ() + random.nextDouble();

                    world.addParticle(ADParticleTypes.WEEPING_HEART_AIR_NECTAR, floatingX, floatingY, floatingZ,
                            0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);

        // Fill the held bucket with nectar when the player clicks the plant.
        if (!heldStack.isOf(Items.BONE_MEAL) && heldStack.isOf(Items.BUCKET) && state.get(AGE) == 3) {
            this.givePlayerNectar(player, hand);
            this.clearNectar(state, world, pos);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private void givePlayerNectar(PlayerEntity player, Hand hand) {
        ItemStack nectarBucket = new ItemStack(ADItems.WEEPING_HEART_NECTAR_BUCKET);
        
        player.playSound(SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if(player.isCreative()) {
            PlayerInventory playerInventory = player.getInventory();

            if(!playerInventory.contains(nectarBucket)) {
                player.giveItemStack(nectarBucket);
            }
        } else {
            player.getStackInHand(hand).decrement(1);

            if(player.getStackInHand(hand).isEmpty()) {
                player.setStackInHand(hand, nectarBucket);
            } else {
                player.giveItemStack(nectarBucket);
            }
        }
    }

    private void clearNectar(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(AGE, 1));
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
        world.setBlockState(pos, state.with(AGE, Math.min(3, state.get(AGE) + 1)), 2);
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
