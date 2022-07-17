package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import java.util.Objects;

public class ADBloodKelpPlantBlock extends KelpPlantBlock {
    public static final BooleanProperty LIT = Properties.LIT;

    public ADBloodKelpPlantBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ADBlocks.BLOOD_KELP;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return Objects.requireNonNull(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos))
                .with(LIT, state.get(LIT));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + random.nextDouble();
        double z = pos.getZ() + random.nextDouble();

        if (state.get(LIT)) {
            world.addParticle(ADParticleTypes.BLOOD_KELP_SPORE, x, y, z, 0.0D, 0.0D, 0.0D);
        }
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
