package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ADDrinkItem extends Item {
    public ADDrinkItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity playerEntity) {
            if (!playerEntity.isCreative()) {
                stack.decrement(1);
                playerEntity.giveItemStack(new ItemStack(Items.GLASS_BOTTLE, 1));
                return stack;
            }
        }
        return user.eatFood(world, stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
