package net.spacerulerwill.colourful_enchanting_tables;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;

import static net.spacerulerwill.colourful_enchanting_tables.Constants.MOD_ID;


public class Common {
    private static final ColouredEnchantingTableData[] COLOURED_ENCHANTING_TABLE_DATA = new ColouredEnchantingTableData[]{
            new ColouredEnchantingTableData("white", MapColor.SNOW),
            new ColouredEnchantingTableData("light_gray", MapColor.COLOR_LIGHT_GRAY),
            new ColouredEnchantingTableData("gray", MapColor.COLOR_GRAY),
            new ColouredEnchantingTableData("black", MapColor.COLOR_BLACK),
            new ColouredEnchantingTableData("brown", MapColor.COLOR_BROWN),
            new ColouredEnchantingTableData("orange", MapColor.COLOR_ORANGE),
            new ColouredEnchantingTableData("yellow", MapColor.COLOR_YELLOW),
            new ColouredEnchantingTableData("lime", MapColor.COLOR_LIGHT_GREEN),
            new ColouredEnchantingTableData("green", MapColor.COLOR_GREEN),
            new ColouredEnchantingTableData("cyan", MapColor.COLOR_CYAN),
            new ColouredEnchantingTableData("light_blue", MapColor.COLOR_LIGHT_BLUE),
            new ColouredEnchantingTableData("blue", MapColor.COLOR_BLUE),
            new ColouredEnchantingTableData("purple", MapColor.COLOR_PURPLE),
            new ColouredEnchantingTableData("magenta", MapColor.COLOR_MAGENTA),
            new ColouredEnchantingTableData("pink", MapColor.COLOR_PINK)
    };
    public static final List<Block> registeredEnchantingTables = new ArrayList<>(COLOURED_ENCHANTING_TABLE_DATA.length);
    public static Supplier<List<ColouredEnchantingTableRegistryData>> enchantingTableRegistryDataSupplier = Suppliers.memoize(Common::createEnchantingTables);

    private static ColouredEnchantingTableRegistryData createEnchantingTable(Common.ColouredEnchantingTableData data) {
        String name = data.colourName() + "_enchanting_table";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
        ResourceKey<Block> blockResourceKey = ResourceKey.create(Registries.BLOCK, id);
        BlockBehaviour.Properties blockSettings = BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE).setId(blockResourceKey).mapColor(data.mapColor());
        Block block = new ColouredEnchantingTableBlock(blockSettings);

        ResourceKey<Item> itemResourceKey = ResourceKey.create(Registries.ITEM, id);
        Item.Properties itemSettings = new Item.Properties().useBlockDescriptionPrefix().setId(itemResourceKey);
        BlockItem blockItem = new BlockItem(block, itemSettings);

        return new ColouredEnchantingTableRegistryData(
                id, block, blockItem
        );
    }

    private static List<ColouredEnchantingTableRegistryData> createEnchantingTables() {
        List<ColouredEnchantingTableRegistryData> result = new ArrayList<>(COLOURED_ENCHANTING_TABLE_DATA.length);
        for (Common.ColouredEnchantingTableData colouredEnchantingTableDatum : COLOURED_ENCHANTING_TABLE_DATA) {
            result.add(createEnchantingTable(colouredEnchantingTableDatum));
        }
        return result;
    }


    public record ColouredEnchantingTableRegistryData(ResourceLocation id, Block block, BlockItem blockItem) {
    }

    private record ColouredEnchantingTableData(String colourName, MapColor mapColor) {
    }
}