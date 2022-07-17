package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADSoundEvents {
    public static final SoundEvent BLOCK_MUSHROOM_BOUNCE = register("block.mushroom_bounce");
    public static final SoundEvent UI_WOODCUTTER_TAKE_RESULT = register("ui.woodcutter.take_result");

    public static void registerSoundEvents() {
        AssortedDiscoveries.LOGGER.info("Registered sound events.");
    }

    /**
     * @param name The name for the sound event.
     * @return new sound event.
     */
    private static SoundEvent register(String name) {
        return Registry.register(Registry.SOUND_EVENT, ADReference.makeId(name), new SoundEvent(ADReference.makeId(name)));
    }
}
