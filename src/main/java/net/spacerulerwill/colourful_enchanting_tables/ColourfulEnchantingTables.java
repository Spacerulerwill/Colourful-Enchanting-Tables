package net.spacerulerwill.colourful_enchanting_tables;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Set;

public class ColourfulEnchantingTables implements ModInitializer {
    public static final String MOD_ID = "colourful_enchanting_tables";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final Block WHITE_ENCHANTING_TABLE = registerEnchantingTable("white_enchanting_table", MaterialColor.WHITE);
    public static final Block LIGHT_GRAY_ENCHANTING_TABLE = registerEnchantingTable("light_gray_enchanting_table", MaterialColor.LIGHT_GRAY);
    public static final Block GRAY_ENCHANTING_TABLE = registerEnchantingTable("gray_enchanting_table", MaterialColor.GRAY);
    public static final Block BLACK_ENCHANTING_TABLE = registerEnchantingTable("black_enchanting_table", MaterialColor.BLACK);
    public static final Block BROWN_ENCHANTING_TABLE = registerEnchantingTable("brown_enchanting_table", MaterialColor.BROWN);
    public static final Block ORANGE_ENCHANTING_TABLE = registerEnchantingTable("orange_enchanting_table", MaterialColor.ORANGE);
    public static final Block YELLOW_ENCHANTING_TABLE = registerEnchantingTable("yellow_enchanting_table", MaterialColor.YELLOW);
    public static final Block LIME_ENCHANTING_TABLE = registerEnchantingTable("lime_enchanting_table", MaterialColor.LIME);
    public static final Block GREEN_ENCHANTING_TABLE = registerEnchantingTable("green_enchanting_table", MaterialColor.GREEN);
    public static final Block CYAN_ENCHANTING_TABLE = registerEnchantingTable("cyan_enchanting_table", MaterialColor.CYAN);
    public static final Block LIGHT_BLUE_ENCHANTING_TABLE = registerEnchantingTable("light_blue_enchanting_table", MaterialColor.LIGHT_BLUE);
    public static final Block BLUE_ENCHANTING_TABLE = registerEnchantingTable("blue_enchanting_table", MaterialColor.BLUE);
    public static final Block PURPLE_ENCHANTING_TABLE = registerEnchantingTable("purple_enchanting_table", MaterialColor.PURPLE);
    public static final Block MAGENTA_ENCHANTING_TABLE = registerEnchantingTable("magenta_enchanting_table", MaterialColor.MAGENTA);
    public static final Block PINK_ENCHANTING_TABLE = registerEnchantingTable("pink_enchanting_table", MaterialColor.PINK);

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    private static Block registerEnchantingTable(String name, MaterialColor mapColor) {
        Identifier id = new Identifier(MOD_ID, name);
        Block.Settings settings = ((BlockSettingsExtension) Block.Settings.copy(Blocks.ENCHANTING_TABLE)).colourful_enchanting_tables$mapColor(mapColor);
        Block block = new EnchantingTableBlock(settings);
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, id, block);
    }


    @Override
    public void onInitialize() {
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