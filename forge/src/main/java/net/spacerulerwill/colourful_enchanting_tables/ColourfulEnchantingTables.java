package net.spacerulerwill.colourful_enchanting_tables;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.List;

@Mod(Constants.MOD_ID)
public class ColourfulEnchantingTables {
    public ColourfulEnchantingTables(FMLJavaModLoadingContext context) {
        context.getModEventBus().register(EventHandler.class);
    }

    private static class EventHandler {
        @SubscribeEvent
        public static void register(RegisterEvent event) {
            event.register(
                    BuiltInRegistries.BLOCK.key(),
                    registry -> {
                        List<Common.ColouredEnchantingTableRegistryData> tables = Common.enchantingTableRegistryDataSupplier.get();
                        tables.forEach((data) -> {
                            registry.register(data.id(), data.block());
                            ((BlockEntityTypeExtension) BlockEntityType.ENCHANTING_TABLE).colourful_enchanting_tables$addSupportedBlock(data.block());
                            Common.registeredEnchantingTables.add(data.block());
                        });
                    }
            );

            event.register(
                    BuiltInRegistries.ITEM.key(),
                    registry -> {
                        List<Common.ColouredEnchantingTableRegistryData> tables = Common.enchantingTableRegistryDataSupplier.get();
                        tables.forEach((data) -> {
                            registry.register(data.id(), data.blockItem());
                        });
                    }
            );
        }

        /*
        @SubscribeEvent
        public static void idk(BlockEntityTypeAddBlocksEvent event) {
            event.modify(BlockEntityType.ENCHANTING_TABLE, Common.registeredEnchantingTables.toArray(new Block[0]));
        }*/

        @SubscribeEvent
        public static void buildContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                Common.registeredEnchantingTables.forEach(event::accept);
            }
        }
    }
}