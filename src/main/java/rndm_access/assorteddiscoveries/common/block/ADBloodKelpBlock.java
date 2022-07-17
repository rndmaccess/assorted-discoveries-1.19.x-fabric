package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.core.ADItems;
import rndm_access.assorteddiscoveries.common.core.ADParticleTypes;
import rndm_access.assorteddiscoveries.common.util.ADBlockStateUtil;

import java.util.Objects;

public class ADBloodKelpBlock extends KelpBlock {
    public static final BooleanProperty LIT = Properties.LIT;

    public ADBloodKelpBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    @Override
    @SuppressWarnings("depreciated")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean holdingBoneMeal = player.getStackInHand(hand).isOf(Items.BONE_MEAL);
        Random random = Random.create();

        if (!holdingBoneMeal && state.get(LIT)) {
            player.giveItemStack(new ItemStack(ADItems.BLOOD_KELP_SEED_CLUSTER, random.nextInt(3) + 1));
            world.setBlockState(pos, state.with(LIT, false));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    protected Block getPlant() {
        return ADBlocks.BLOOD_KELP_PLANT;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return Objects.requireNonNull(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos))
                .with(LIT, state.get(LIT));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = pos.getX() + (random.nextDouble() / 2.0);
        double y = pos.getY() + (random.nextDouble() / 2.0);
        double z = pos.getZ() + (random.nextDouble() / 2.0);

        if (state.get(LIT)) {
            world.addParticle(ADParticleTypes.BLOOD_KELP_SPORE, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos newStemPos = pos.offset(this.growthDirection);
        boolean isStemLit = state.get(LIT);
        int age = state.get(AGE);

        if (age < 25 && this.chooseStemState(world.getBlockState(newStemPos))) {
            world.setBlockState(pos, this.getPlant().getDefaultState().with(ADBloodKelpPlantBlock.LIT, isStemLit), 2);
            world.setBlockState(newStemPos, this.getBloodKelpState(random).with(AGE, age).cycle(AGE));
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos newStemPos = pos.mutableCopy().offset(this.growthDirection);
        int age = Math.min(state.get(AGE) + 1, 25);

        if(this.chooseStemState(world.getBlockState(newStemPos))) {
            world.setBlockState(pos, this.getPlant().getDefaultState().with(ADBloodKelpPlantBlock.LIT, state.get(LIT)), 2);
            world.setBlockState(newStemPos, this.getBloodKelpState(random).with(AGE, age));
        }
    }

    public BlockState getBloodKelpState(Random random) {
        return this.getDefaultState().with(LIT, ADBlockStateUtil.isBloodKelpLit(random));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, AGE);
    }
}
