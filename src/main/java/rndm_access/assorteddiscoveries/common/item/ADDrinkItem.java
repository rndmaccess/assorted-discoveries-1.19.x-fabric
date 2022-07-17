package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ADDrinkItem extends Item {
    private final Item returnItem;

    public ADDrinkItem(Item.Settings settings) {
        super(settings);
        this.returnItem = Items.GLASS_BOTTLE;
    }

    public ADDrinkItem(Item.Settings settings, Item returnItemIn) {
        super(settings);
        this.returnItem = returnItemIn;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity playerEntity) {
            if (playerEntity.isCreative()) {
                playerEntity.giveItemStack(new ItemStack(this.returnItem, 1));
                return super.finishUsing(stack, world, user);
            }
        }
        return user.eatFood(world, stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
