package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ADFoodComponents {
    public static final FoodComponent FRIED_EGG;
    public static final FoodComponent GARLIC;
    public static final FoodComponent GREEN_ONION;
    public static final FoodComponent BOK_CHOY;
    public static final FoodComponent NOODLE_SOUP;
    public static final FoodComponent PUDDING;
    public static final FoodComponent BERRY_PUDDING;
    public static final FoodComponent CARAMEL_APPLE;
    public static final FoodComponent WITCHS_CRADLE_BRANCH;
    public static final FoodComponent WITCHS_CRADLE_SOUP;
    public static final FoodComponent BLOOD_VILE;
    public static final FoodComponent BLUEBERRIES;
    public static final FoodComponent SWEET_BERRY_JUICE;
    public static final FoodComponent BLUEBERRY_JUICE;
    public static final FoodComponent SPRUCE_CONE;
    public static final FoodComponent FORESTS_BOUNTY;

    private static FoodComponent stew(int hunger) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(0.6F).build();
    }

    static {
        FRIED_EGG = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).build();
        GARLIC = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build();
        GREEN_ONION = new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).build();
        BOK_CHOY = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build();
        NOODLE_SOUP = stew(10);
        PUDDING = stew(6);
        BERRY_PUDDING = stew(8);
        CARAMEL_APPLE = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F).build();
        WITCHS_CRADLE_BRANCH = new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build();
        WITCHS_CRADLE_SOUP = new FoodComponent.Builder().hunger(6).saturationModifier(1.0F)
                .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000, 0, true, true), 1.0F)
                .alwaysEdible().build();
        BLOOD_VILE = new FoodComponent.Builder().hunger(3).saturationModifier(0.6F).build();
        BLUEBERRIES = new FoodComponent.Builder().hunger(3).saturationModifier(0.2F).build();
        SWEET_BERRY_JUICE = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build();
        BLUEBERRY_JUICE = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build();
        SPRUCE_CONE = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build();
        FORESTS_BOUNTY = stew(5);
    }
}
