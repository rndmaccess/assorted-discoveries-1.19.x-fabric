package rndm_access.assorteddiscoveries.common.core;

import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADVillagerProfessions {
    public static final VillagerProfession LUMBERJACK = VillagerProfessionBuilder.create()
            .workstation(ADPointOfInterestTypes.LUMBERJACK).id(ADReference.makeId("lumberjack"))
            .workSound(ADSoundEvents.UI_WOODCUTTER_TAKE_RESULT).build();

    private static void register(String name, VillagerProfession profession) {
        Registry.register(Registry.VILLAGER_PROFESSION, ADReference.makeId(name), profession);
    }

    /**
     * Called during mod initialization to register every villager profession.
     */
    public static void registerVillagerProfessions() {
        register("lumberjack", LUMBERJACK);

        AssortedDiscoveries.LOGGER.info("Registered villager professions");
    }
}