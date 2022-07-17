package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rndm_access.assorteddiscoveries.common.util.ADBlockStateUtil;

@Mixin(LeavesBlock.class)
public class ADLeavesBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"))
    private void assorteddiscoveries_randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

        // Drop spruce cones when spruce leaves decay.
        if(!state.get(LeavesBlock.PERSISTENT) && state.get(LeavesBlock.DISTANCE) == 7) {
            ADBlockStateUtil.dropSpruceCone(state, world, pos, random, 0.01F);
        }
    }
}
