package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

public class ADWeepingHeartNectarBucketItem extends AliasedBlockItem {
    public ADWeepingHeartNectarBucketItem(Settings settings) {
        super(ADBlocks.WEEPING_HEART, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult result = super.useOnBlock(context);
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();

        if (player != null && !player.isCreative() && result.isAccepted()) {
            player.setStackInHand(hand, new ItemStack(Items.BUCKET));
        }
        return result;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity && !((PlayerEntity) user).isCreative()) {
            stack.decrement(1);
        }
        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
