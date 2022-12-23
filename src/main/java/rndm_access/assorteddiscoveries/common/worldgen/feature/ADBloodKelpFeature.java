package rndm_access.assorteddiscoveries.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import rndm_access.assorteddiscoveries.common.block.ADBloodKelpBlock;
import rndm_access.assorteddiscoveries.common.block.ADBloodKelpPlantBlock;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.util.ADBlockStateUtil;

public class ADBloodKelpFeature extends Feature<DefaultFeatureConfig> {
    public ADBloodKelpFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos originPos = context.getOrigin();
        Random random = context.getRandom();
        int x = originPos.getX();
        int z = originPos.getZ();
        int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR, x, z);
        BlockPos.Mutable pos = new BlockPos(x, y, z).mutableCopy();

        return placeBloodKelpStalk(world, random, pos);
    }

    private boolean placeBloodKelpStalk(StructureWorldAccess world, Random random, BlockPos.Mutable pos) {
        boolean isWater = world.getFluidState(pos).isOf(Fluids.WATER);
        int maxLength = 1 + random.nextInt(10);

        for (int length = 0; length <= maxLength && isWater; ++length) {
            BlockState state = ADBlocks.BLOOD_KELP.getDefaultState();
            BlockState plantState = ADBlocks.BLOOD_KELP_PLANT.getDefaultState();
            boolean canSustainPlant = plantState.canPlaceAt(world, pos);
            boolean isAboveEmpty = world.getFluidState(pos.up()).isEmpty();

            if (canSustainPlant) {
                if (isAboveEmpty || length == maxLength) {
                    world.setBlockState(pos, state
                            .with(ADBloodKelpBlock.LIT, ADBlockStateUtil.isBloodKelpLit(random))
                            .with(ADBloodKelpBlock.AGE, random.nextInt(4) + 20), 2);
                    return true;
                }
                world.setBlockState(pos, plantState
                        .with(ADBloodKelpPlantBlock.LIT, ADBlockStateUtil.isBloodKelpLit(random)), 2);
            }
            pos.move(Direction.UP);
            isWater = world.getFluidState(pos).isOf(Fluids.WATER);
        }
        return false;
    }
}
