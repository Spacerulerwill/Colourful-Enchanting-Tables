package net.spacerulerwill.colourful_enchanting_tables;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

public class ColourfulEnchantingTables implements ModInitializer {
    public static final String MOD_ID = "colourful_enchanting_tables";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block WHITE_ENCHANTING_TABLE = registerEnchantingTable("white_enchanting_table", MapColor.WHITE);
    public static final Block LIGHT_GRAY_ENCHANTING_TABLE = registerEnchantingTable("light_gray_enchanting_table", MapColor.LIGHT_GRAY);
    public static final Block GRAY_ENCHANTING_TABLE = registerEnchantingTable("gray_enchanting_table", MapColor.GRAY);
    public static final Block BLACK_ENCHANTING_TABLE = registerEnchantingTable("black_enchanting_table", MapColor.BLACK);
    public static final Block BROWN_ENCHANTING_TABLE = registerEnchantingTable("brown_enchanting_table", MapColor.BROWN);
    public static final Block ORANGE_ENCHANTING_TABLE = registerEnchantingTable("orange_enchanting_table", MapColor.ORANGE);
    public static final Block YELLOW_ENCHANTING_TABLE = registerEnchantingTable("yellow_enchanting_table", MapColor.YELLOW);
    public static final Block LIME_ENCHANTING_TABLE = registerEnchantingTable("lime_enchanting_table", MapColor.LIME);
    public static final Block GREEN_ENCHANTING_TABLE = registerEnchantingTable("green_enchanting_table", MapColor.GREEN);
    public static final Block CYAN_ENCHANTING_TABLE = registerEnchantingTable("cyan_enchanting_table", MapColor.CYAN);
    public static final Block LIGHT_BLUE_ENCHANTING_TABLE = registerEnchantingTable("light_blue_enchanting_table", MapColor.LIGHT_BLUE);
    public static final Block BLUE_ENCHANTING_TABLE = registerEnchantingTable("blue_enchanting_table", MapColor.BLUE);
    public static final Block PURPLE_ENCHANTING_TABLE = registerEnchantingTable("purple_enchanting_table", MapColor.PURPLE);
    public static final Block MAGENTA_ENCHANTING_TABLE = registerEnchantingTable("magenta_enchanting_table", MapColor.MAGENTA);
    public static final Block PINK_ENCHANTING_TABLE = registerEnchantingTable("pink_enchanting_table", MapColor.PINK);

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    private static Block registerEnchantingTable(String name, MapColor mapColor) {
        Identifier id = new Identifier(MOD_ID, name);
        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE).mapColor(mapColor);
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