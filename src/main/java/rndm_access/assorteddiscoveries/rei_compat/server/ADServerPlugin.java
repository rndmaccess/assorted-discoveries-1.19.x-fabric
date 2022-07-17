package rndm_access.assorteddiscoveries.rei_compat.server;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import rndm_access.assorteddiscoveries.rei_compat.ADCategoryIdentifiers;
import rndm_access.assorteddiscoveries.rei_compat.client.display.ADWoodcuttingDisplay;

public class ADServerPlugin implements REIServerPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(ADCategoryIdentifiers.WOOD_CUTTING, ADWoodcuttingDisplay.serializer());
    }
}
