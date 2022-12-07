package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.block.ADGrassSlabBlock;
import rndm_access.assorteddiscoveries.common.core.ADBlockTags;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

@Mixin(PlantBlock.class)
public class ADPlantBlockMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_canPlaceAt(BlockState state, WorldView world, BlockPos pos,
                                                CallbackInfoReturnable<Boolean> info) {
        BlockState floor = world.getBlockState(pos.down());

        // Add the ability to plant plants on blocks in the dirt slabs tag.
        if(floor.isIn(ADBlockTags.DIRT_SLABS) && floor.get(ADGrassSlabBlock.TYPE) != SlabType.BOTTOM) {
            info.setReturnValue(true);
        }

        if(floor.isIn(ADBlockTags.NETHER_PLANTER_BOXES) && state.isIn(CBlockTags.NETHER_PLANTER_BOX_PLANTS)) {
            info.setReturnValue(true);
        }

        if(floor.isIn(ADBlockTags.OVERWORLD_PLANTER_BOXES) && state.isIn(CBlockTags.OVERWORLD_PLANTER_BOX_PLANTS)) {
            info.setReturnValue(true);
        }
    }
}
