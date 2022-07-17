package rndm_access.assorteddiscoveries.common;

import dev.architectury.registry.fuel.FuelRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.core.*;

public class AssortedDiscoveries implements ModInitializer {
	public static final ItemGroup MOD_GROUP = FabricItemGroupBuilder.build(ADReference.makeId("mod_group"),
			() -> new ItemStack(ADItems.CALCITE_BRICKS));
	public static final Logger LOGGER = LoggerFactory.getLogger(ADReference.MOD_ID);

	@Override
	public void onInitialize() {
		// General Registries
		ADBlocks.registerBlocks();
		ADItems.registerItems();
		ADBlockEntityTypes.registerBlockEntityTypes();
		ADParticleTypes.registerParticleTypes();
		ADScreenHandlerTypes.registerScreenHandlerTypes();
		ADRecipeTypes.registerRecipeTypes();
		ADRecipeSerializers.registerSerializers();
		ADPaintingVariants.registerPaintingMotives();
		ADSoundEvents.registerSoundEvents();
		AssortedDiscoveries.registerFuel();

		// Entity Registries
		ADVillagerTypes.registerVillagerTypes();
		ADPointOfInterestTypes.registerPointOfInterestTypes();
		ADVillagerProfessions.registerVillagerProfessions();
		ADVillagerOffers.registerVillagerTradeOffers();

		// World Generation Registries
		ADFeature.registerFeatures();
		ADConfiguredFeatures.registerConfiguredFeatures();
		AssortedDiscoveries.addFeaturesToBiomes();
	}

	private static void addFeaturesToBiomes() {
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_CATTAIL),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_CATTAIL);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.ORE_SMOKY_QUARTZ),
				GenerationStep.Feature.UNDERGROUND_ORES, ADPlacedFeatureKeys.ORE_SMOKY_QUARTZ);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_HUGE_PURPLE_MUSHROOM),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_HUGE_PURPLE_MUSHROOM);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_BLUEBERRY_BUSH),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_BLUEBERRY_COMMON);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_BLUEBERRY_BUSH),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_BLUEBERRY_RARE);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_WITCHS_CRADLE),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_WITCHS_CRADLE_COMMON);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_WITCHS_CRADLE),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_WITCHS_CRADLE_RARE);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.PATCH_ENDER_PLANTS),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_ENDER_PLANTS);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.BLOOD_KELP),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.BLOOD_KELP);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.ORE_BAUXITE),
				GenerationStep.Feature.UNDERGROUND_ORES, ADPlacedFeatureKeys.ORE_BAUXITE_LOWER);
		BiomeModifications.addFeature(BiomeSelectors.tag(ADBiomeTags.ORE_BAUXITE),
				GenerationStep.Feature.UNDERGROUND_ORES, ADPlacedFeatureKeys.ORE_BAUXITE_UPPER);
	}

	private static void registerFuel() {
		FuelRegistry.register(4000, ADItems.DRIED_BLOOD_KELP_BLOCK);
	}
}
