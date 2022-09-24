package rndm_access.assorteddiscoveries.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import rndm_access.assorteddiscoveries.common.block.ADCattailBlock;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

public class ADCattailFeature extends Feature<ProbabilityConfig> {
    public ADCattailFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        BlockPos origin = context.getOrigin();

        return placeCattail(context.getWorld(), context.getRandom(), origin.getX(), origin.getZ());
    }

    private boolean placeCattail(StructureWorldAccess world, Random random, int xOrigin, int zOrigin) {
        BlockPos lowerPlacementPos = getRandomPos(random, world, xOrigin, zOrigin);
        BlockPos upperPlacementPos = lowerPlacementPos.up();
        BlockState lowerHalf = ADBlocks.CATTAIL.getDefaultState();
        BlockState upperHalf = lowerHalf.with(ADCattailBlock.HALF, DoubleBlockHalf.UPPER);
        boolean canPlace = lowerHalf.canPlaceAt(world, lowerPlacementPos) && world.getBlockState(upperPlacementPos).isAir();

        if (canPlace) {
            world.setBlockState(lowerPlacementPos, lowerHalf, 2);
            world.setBlockState(upperPlacementPos, upperHalf, 2);
            return true;
        }
        return false;
    }

    private BlockPos getRandomPos(Random random, StructureWorldAccess world, int xOrigin, int zOrigin) {
        int x = offsetOriginCoordinate(xOrigin, random);
        int z = offsetOriginCoordinate(zOrigin, random);
        int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR, x, z);

        return new BlockPos(x, y, z);
    }

    private int offsetOriginCoordinate(int origin, Random random) {
        return origin + random.nextInt(8) - random.nextInt(8);
    }
}
