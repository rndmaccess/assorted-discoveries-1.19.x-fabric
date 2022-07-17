package rndm_access.assorteddiscoveries.common.core;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

import java.util.Set;

public class ADPointOfInterestTypes {
    public static final RegistryKey<PointOfInterestType> LUMBERJACK = of("lumberjack");

    public static void registerPointOfInterestTypes() {
        PointOfInterestHelper.register(ADReference.makeId("lumberjack"), 1, 1, ADBlocks.WOODCUTTER);

        AssortedDiscoveries.LOGGER.info("Registered point of interest types.");
    }

    private static RegistryKey<PointOfInterestType> of(String path) {
        return RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, ADReference.makeId(path));
    }
}