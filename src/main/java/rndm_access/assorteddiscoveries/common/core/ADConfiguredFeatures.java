package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

/**
 * These are used within the code itself. The other configured and placed features are done in json and
 * are referenced through {@link  ADPlacedFeatureKeys} class.
 */
public class ADConfiguredFeatures {
    public static final RegistryEntry<ConfiguredFeature<?, ?>> HUGE_PURPLE_MUSHROOM = register("huge_purple_mushroom",
            Feature.HUGE_RED_MUSHROOM, new HugeMushroomFeatureConfig(
                    BlockStateProvider.of(ADBlocks.PURPLE_MUSHROOM_BLOCK
                            .getDefaultState().with(MushroomBlock.DOWN, false)),
                    BlockStateProvider.of(Blocks.MUSHROOM_STEM.getDefaultState()
                            .with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)), 2));

    public static void registerConfiguredFeatures() {
        AssortedDiscoveries.LOGGER.info("Registered configured features");
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<?, ?>> register(String path, F feature, FC config) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, ADReference.makeId(path), new ConfiguredFeature<>(feature, config));
    }
}
