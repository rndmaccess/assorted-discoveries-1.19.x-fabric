package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import rndm_access.assorteddiscoveries.common.core.ADEntityTypeTags;
import rndm_access.assorteddiscoveries.common.core.ADItems;

public class ADGlassVialItem extends Item {
    public ADGlassVialItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (stack.getItem().equals(ADItems.GLASS_VIAL)) {

            if (entity instanceof AnimalEntity && entity.getHealth() > 0.0 && entity.getType().isIn(ADEntityTypeTags.ANIMALS_THAT_GIVE_BLOOD)) {
                AnimalEntity animalTarget = (AnimalEntity)(entity);

                if (animalTarget.damage(DamageSource.GENERIC, 1)) {
                    user.giveItemStack(new ItemStack(ADItems.BLOOD_VIAL));
                    animalTarget.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
                    stack.decrement(1);
                    return ActionResult.CONSUME;
                }
            }
        }
        return ActionResult.PASS;
    }
}
