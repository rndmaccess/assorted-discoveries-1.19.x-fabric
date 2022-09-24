package rndm_access.assorteddiscoveries.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
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
        BlockPos placementPos = new BlockPos(x, y, z);

        return generateBloodKelp(world, random, placementPos);
    }

    private boolean generateBloodKelp(StructureWorldAccess world, Random random, BlockPos placementPos) {
        boolean isWater = world.getFluidState(placementPos).isOf(Fluids.WATER);

        if (isWater) {
            BlockState bloodKelpState = ADBlocks.BLOOD_KELP.getDefaultState();
            BlockState bloodKelpPlantState = ADBlocks.BLOOD_KELP_PLANT.getDefaultState();
            int maxLength = 1 + random.nextInt(10);

            for (int length = 0; length <= maxLength; ++length) {
                boolean canSustainBloodKelpPlant = bloodKelpPlantState.canPlaceAt(world, placementPos);
                boolean isAboveEmpty = world.getFluidState(placementPos.up()).isEmpty();

                if (canSustainBloodKelpPlant && isWater) {
                    if (isAboveEmpty || length == maxLength) {
                        world.setBlockState(placementPos, bloodKelpState
                                .with(ADBloodKelpBlock.LIT, ADBlockStateUtil.isBloodKelpLit(random))
                                .with(ADBloodKelpBlock.AGE, random.nextInt(4) + 20), 2);
                        return true;
                    } else {
                        world.setBlockState(placementPos, bloodKelpPlantState
                                .with(ADBloodKelpPlantBlock.LIT, ADBlockStateUtil.isBloodKelpLit(random)), 2);
                    }
                }
                placementPos = placementPos.up();
                isWater = world.getFluidState(placementPos).isOf(Fluids.WATER);
            }
        }
        return false;
    }
}
