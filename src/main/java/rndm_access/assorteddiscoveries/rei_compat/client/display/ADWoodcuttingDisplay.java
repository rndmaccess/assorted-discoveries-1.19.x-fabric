package rndm_access.assorteddiscoveries.rei_compat.client.display;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;
import rndm_access.assorteddiscoveries.common.item.crafting.ADWoodcuttingRecipe;
import rndm_access.assorteddiscoveries.rei_compat.ADCategoryIdentifiers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ADWoodcuttingDisplay extends BasicDisplay {
    public ADWoodcuttingDisplay(ADWoodcuttingRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(
                EntryIngredients.of(recipe.getOutput())), Optional.ofNullable(recipe.getId()));
    }

    public ADWoodcuttingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ADCategoryIdentifiers.WOOD_CUTTING;
    }

    public static BasicDisplay.Serializer<ADWoodcuttingDisplay> serializer() {
        return BasicDisplay.Serializer.ofSimple(ADWoodcuttingDisplay::new);
    }
}
