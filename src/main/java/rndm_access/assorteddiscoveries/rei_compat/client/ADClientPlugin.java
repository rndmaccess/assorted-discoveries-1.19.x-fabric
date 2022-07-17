package rndm_access.assorteddiscoveries.rei_compat.client;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.recipe.RecipeManager;
import rndm_access.assorteddiscoveries.common.core.ADItems;
import rndm_access.assorteddiscoveries.common.core.ADRecipeTypes;
import rndm_access.assorteddiscoveries.common.item.crafting.ADWoodcuttingRecipe;
import rndm_access.assorteddiscoveries.rei_compat.ADCategoryIdentifiers;
import rndm_access.assorteddiscoveries.rei_compat.client.category.ADWoodcutterCategory;
import rndm_access.assorteddiscoveries.rei_compat.client.display.ADWoodcuttingDisplay;

import java.util.List;
import java.util.Objects;

public class ADClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ADWoodcutterCategory());

        registry.addWorkstations(ADCategoryIdentifiers.WOOD_CUTTING, EntryStacks.of(ADItems.WOODCUTTER));
        registry.addWorkstations(BuiltinPlugin.CAMPFIRE, EntryStacks.of(ADItems.WHITE_CAMPFIRE),
                EntryStacks.of(ADItems.ORANGE_CAMPFIRE), EntryStacks.of(ADItems.MAGENTA_CAMPFIRE),
                EntryStacks.of(ADItems.LIGHT_BLUE_CAMPFIRE), EntryStacks.of(ADItems.YELLOW_CAMPFIRE),
                EntryStacks.of(ADItems.LIME_CAMPFIRE), EntryStacks.of(ADItems.PINK_CAMPFIRE),
                EntryStacks.of(ADItems.GRAY_CAMPFIRE), EntryStacks.of(ADItems.LIGHT_GRAY_CAMPFIRE),
                EntryStacks.of(ADItems.CYAN_CAMPFIRE), EntryStacks.of(ADItems.PURPLE_CAMPFIRE),
                EntryStacks.of(ADItems.BLUE_CAMPFIRE), EntryStacks.of(ADItems.BROWN_CAMPFIRE),
                EntryStacks.of(ADItems.GREEN_CAMPFIRE), EntryStacks.of(ADItems.RED_CAMPFIRE),
                EntryStacks.of(ADItems.BLACK_CAMPFIRE));
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(ADCategoryIdentifiers.WOOD_CUTTING, ADWoodcuttingDisplay.serializer());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        RecipeManager manager = Objects.requireNonNull(MinecraftClient.getInstance().getServer()).getRecipeManager();
        List<ADWoodcuttingRecipe> woodcuttingRecipes = manager.listAllOfType(ADRecipeTypes.WOODCUTTING);

        // Register each wood cutting recipe display.
        for(ADWoodcuttingRecipe woodcuttingRecipe : woodcuttingRecipes) {
            registry.add(new ADWoodcuttingDisplay(woodcuttingRecipe));
        }
    }
}
