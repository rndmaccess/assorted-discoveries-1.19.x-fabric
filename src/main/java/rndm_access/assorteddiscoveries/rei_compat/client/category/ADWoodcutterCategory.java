package rndm_access.assorteddiscoveries.rei_compat.client.category;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.rei_compat.ADCategoryIdentifiers;
import rndm_access.assorteddiscoveries.rei_compat.client.display.ADWoodcuttingDisplay;

import java.util.List;

public class ADWoodcutterCategory implements DisplayCategory<ADWoodcuttingDisplay> {
    @Override
    public CategoryIdentifier<? extends ADWoodcuttingDisplay> getCategoryIdentifier() {
        return ADCategoryIdentifiers.WOOD_CUTTING;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ADBlocks.WOODCUTTER);
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + ADReference.MOD_ID + ".wood_cutting");
    }

    @Override
    public List<Widget> setupDisplay(ADWoodcuttingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)).markInput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }
}
