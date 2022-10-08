package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import rndm_access.assorteddiscoveries.ADReference;

public class ADEntityTypeTags {
    public static final TagKey<EntityType<?>> SNAPDRAGON_TELEPORT_IMMUNE_ENTITY_TYPES = of("snapdragon_teleport_immune_entity_types");
    public static final TagKey<EntityType<?>> BLUEBERRY_BUSH_IMMUNE_ENTITY_TYPES = of("blueberry_bush_immune_entity_types");
    public static final TagKey<EntityType<?>> WITCHS_CRADLE_IMMUNE_ENTITY_TYPES = of("witchs_cradle_immune_entity_types");

    private static TagKey<EntityType<?>> of(String path) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, ADReference.makeId(path));
    }
}
