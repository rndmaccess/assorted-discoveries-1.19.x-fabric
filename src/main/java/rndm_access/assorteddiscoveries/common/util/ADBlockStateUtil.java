package rndm_access.assorteddiscoveries.common.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import rndm_access.assorteddiscoveries.common.core.ADBlockTags;

public final class ADBlockStateUtil {
    public static boolean isSnowSlabOrStairs(WorldAccess world, BlockPos pos, BlockState state) {
        boolean isCovered = state.isSideSolidFullSquare(world, pos, Direction.DOWN);
        return state.isIn(ADBlockTags.SNOW_STAIRS) && isCovered || state.isIn(ADBlockTags.SNOW_SLABS) && isCovered;
    }

    public static boolean isBloodKelpLit(Random random) {
        float randomNumPicked = random.nextFloat();

        return randomNumPicked < 0.3F;
    }
}
