package net.spacerulerwill.colourful_enchanting_tables;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

public class ColourfulEnchantingTables implements ModInitializer {
	public static final String MOD_ID = "colourful_enchanting_tables";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block WHITE_ENCHANTING_TABLE = registerBlock("white_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block LIGHT_GRAY_ENCHANTING_TABLE = registerBlock("light_gray_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block GRAY_ENCHANTING_TABLE = registerBlock("gray_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block BLACK_ENCHANTING_TABLE = registerBlock("black_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block BROWN_ENCHANTING_TABLE = registerBlock("brown_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block ORANGE_ENCHANTING_TABLE = registerBlock("orange_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block YELLOW_ENCHANTING_TABLE = registerBlock("yellow_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block LIME_ENCHANTING_TABLE = registerBlock("lime_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block GREEN_ENCHANTING_TABLE = registerBlock("green_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block CYAN_ENCHANTING_TABLE = registerBlock("cyan_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block LIGHT_BLUE_ENCHANTING_TABLE = registerBlock("light_blue_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block BLUE_ENCHANTING_TABLE = registerBlock("blue_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block PURPLE_ENCHANTING_TABLE = registerBlock("purple_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block MAGENTA_ENCHANTING_TABLE = registerBlock("magenta_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));
	public static final Block PINK_ENCHANTING_TABLE = registerBlock("pink_enchanting_table", new EnchantingTableBlock(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));

	private static Item registerBlockItem(String name, Block block) {
		return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), new BlockItem(block, new Item.Settings()));
	}

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
	}

	private static void addItemsToFunctionalBlocksGroup(FabricItemGroupEntries entries) {
		entries.add(WHITE_ENCHANTING_TABLE);
		entries.add(LIGHT_GRAY_ENCHANTING_TABLE);
		entries.add(GRAY_ENCHANTING_TABLE);
		entries.add(BLACK_ENCHANTING_TABLE);
		entries.add(BROWN_ENCHANTING_TABLE);
		entries.add(ORANGE_ENCHANTING_TABLE);
		entries.add(YELLOW_ENCHANTING_TABLE);
		entries.add(LIME_ENCHANTING_TABLE);
		entries.add(GREEN_ENCHANTING_TABLE);
		entries.add(CYAN_ENCHANTING_TABLE);
		entries.add(LIGHT_BLUE_ENCHANTING_TABLE);
		entries.add(BLUE_ENCHANTING_TABLE);
		entries.add(PURPLE_ENCHANTING_TABLE);
		entries.add(MAGENTA_ENCHANTING_TABLE);
		entries.add(PINK_ENCHANTING_TABLE);
	}

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ColourfulEnchantingTables::addItemsToFunctionalBlocksGroup);
		editEnchantingTableBlockEntity();
		LOGGER.info("Colourful Enchanting Tables is initialised!");
	}

	private void editEnchantingTableBlockEntity() {
		MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
		try {
			Field enchantingTableField = BlockEntityType.class.getDeclaredField(resolver.mapFieldName(
					"intermediary",
					resolver.unmapClassName("intermediary", BlockEntityType.class.getName()),
					"field_11912",
					"Lnet/minecraft/class_2591;"
			));
			enchantingTableField.setAccessible(true);
			BlockEntityType<?> enchantingTableType = (BlockEntityType<?>) enchantingTableField.get(null);
			Field blocksField = BlockEntityType.class.getDeclaredField(resolver.mapFieldName(
					"intermediary",
					resolver.unmapClassName("intermediary", BlockEntityType.class.getName()),
					"field_19315",
					"Ljava/util/Set;"
			));
			blocksField.setAccessible(true);
			Set<Block> currentBlocks = (Set<Block>) blocksField.get(enchantingTableType);
			Set<Block> newBlocks = ImmutableSet.<Block>builder()
					.addAll(currentBlocks)
					.add(WHITE_ENCHANTING_TABLE)
					.add(LIGHT_GRAY_ENCHANTING_TABLE)
					.add(GRAY_ENCHANTING_TABLE)
					.add(BLACK_ENCHANTING_TABLE)
					.add(BROWN_ENCHANTING_TABLE)
					.add(ORANGE_ENCHANTING_TABLE)
					.add(YELLOW_ENCHANTING_TABLE)
					.add(LIME_ENCHANTING_TABLE)
					.add(GREEN_ENCHANTING_TABLE)
					.add(CYAN_ENCHANTING_TABLE)
					.add(LIGHT_BLUE_ENCHANTING_TABLE)
					.add(BLUE_ENCHANTING_TABLE)
					.add(PURPLE_ENCHANTING_TABLE)
					.add(MAGENTA_ENCHANTING_TABLE)
					.add(PINK_ENCHANTING_TABLE)
					.build();

			blocksField.set(enchantingTableType, newBlocks);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}