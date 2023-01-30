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
import rndm_access.assorteddiscoveries.common.block.ADBloodKelp;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

public class ADBloodKelpFeature extends Feature<DefaultFeatureConfig> implements ADBloodKelp {
    public ADBloodKelpFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos originPos = context.getOrigin();
        int x = originPos.getX();
        int z = originPos.getZ();
        int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR, x, z);
        BlockPos.Mutable placePos = new BlockPos(x, y, z).mutableCopy();

        return placeBloodKelpStalk(world, context.getRandom(), placePos);
    }

    private boolean placeBloodKelpStalk(StructureWorldAccess world, Random random, BlockPos.Mutable placePos) {
        int maxLength = 1 + random.nextInt(10);
        BlockState state = ADBlocks.BLOOD_KELP.getDefaultState();
        BlockState plantState = ADBlocks.BLOOD_KELP_PLANT.getDefaultState();
        boolean canSustainPlant = state.canPlaceAt(world, placePos);
        boolean isInWater = world.getFluidState(placePos).isOf(Fluids.WATER);

        if(!canSustainPlant || !isInWater) {
            return false;
        }

        // Place a stalk of blood kelp.
        for (int length = 0; length <= maxLength; ++length) {
            boolean isAboveEmpty = world.getFluidState(placePos.up()).isEmpty();

            // Top off the blood kelp stalk with a head block.
            if (isAboveEmpty || length == maxLength) {
                world.setBlockState(placePos, state
                        .with(ADBloodKelpBlock.LIT, ADBloodKelp.isLit(random))
                        .with(ADBloodKelpBlock.AGE, random.nextInt(4) + 20), 2);
                return true;
            }

            // Place the next blood kelp body block.
            world.setBlockState(placePos, plantState
                    .with(ADBloodKelpPlantBlock.LIT, ADBloodKelp.isLit(random)), 2);

            placePos.move(Direction.UP);
        }
        return false;
    }
}
