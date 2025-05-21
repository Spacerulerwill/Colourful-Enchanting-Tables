package net.spacerulerwill.colourful_enchanting_tables;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static net.spacerulerwill.colourful_enchanting_tables.Constants.LOGGER;


public class ColourfulEnchantingTables implements ModInitializer {
    private static void addItemsToFunctionalBlocksGroup(FabricItemGroupEntries entries) {
        Common.registeredEnchantingTables.forEach(entries::accept);
    }

    @Override
    public void onInitialize() {
        Common.enchantingTableRegistryDataSupplier.get().forEach((data) -> {
            Common.registeredEnchantingTables.add(Registry.register(BuiltInRegistries.BLOCK, ResourceKey.create(Registries.BLOCK, data.id()), data.block()));
            Registry.register(BuiltInRegistries.ITEM, ResourceKey.create(Registries.ITEM, data.id()), data.blockItem());
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(ColourfulEnchantingTables::addItemsToFunctionalBlocksGroup);
        Common.registeredEnchantingTables.forEach(BlockEntityType.ENCHANTING_TABLE::addSupportedBlock);
        LOGGER.info("Colourful Enchanting Tables is initialised!");
    }
}