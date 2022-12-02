package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import rndm_access.assorteddiscoveries.ADReference;

public class CBlockTags {
    public static final TagKey<Block> OVERWORLD_PLANTER_BOX_PLANTS = of("overworld_planter_box_plants");
    public static final TagKey<Block> NETHER_PLANTER_BOX_PLANTS = of("nether_planter_box_plants");

    /**
     * @param path The name of the tag.
     * @return The tag from the location or an empty tag if none exists.
     */
    private static TagKey<Block> of(String path) {
        return TagKey.of(Registry.BLOCK_KEY, ADReference.makeCommonId(path));
    }
}
