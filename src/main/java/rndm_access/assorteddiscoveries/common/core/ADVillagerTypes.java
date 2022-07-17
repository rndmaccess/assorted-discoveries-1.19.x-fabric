package rndm_access.assorteddiscoveries.common.core;

import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerTypeHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADVillagerTypes {
    @SafeVarargs
    private static void register(String path, RegistryKey<Biome>... biomes) {
        VillagerType type = VillagerTypeHelper.register(ADReference.makeId(path));

        for(RegistryKey<Biome> biome : biomes) {
            VillagerTypeHelper.addVillagerTypeToBiome(biome, type);
        }
    }

    public static void registerVillagerTypes() {
        register("crimson", BiomeKeys.CRIMSON_FOREST);
        register("warped", BiomeKeys.WARPED_FOREST);
        register("forest", BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST,
                BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.DARK_FOREST);

        AssortedDiscoveries.LOGGER.info("Registered villager types.");
    }
}