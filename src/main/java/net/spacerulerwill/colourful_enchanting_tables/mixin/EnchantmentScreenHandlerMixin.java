package net.spacerulerwill.colourful_enchanting_tables.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.spacerulerwill.colourful_enchanting_tables.ColourfulEnchantingTables;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {
    @Shadow @Final private ScreenHandlerContext context;

    @Unique
    private static final Block[] COLOURED_ENCHANTING_TABLES = new Block[] {
            ColourfulEnchantingTables.WHITE_ENCHANTING_TABLE,
            ColourfulEnchantingTables.LIGHT_GRAY_ENCHANTING_TABLE,
            ColourfulEnchantingTables.GRAY_ENCHANTING_TABLE,
            ColourfulEnchantingTables.BLACK_ENCHANTING_TABLE,
            ColourfulEnchantingTables.BROWN_ENCHANTING_TABLE,
            ColourfulEnchantingTables.ORANGE_ENCHANTING_TABLE,
            ColourfulEnchantingTables.YELLOW_ENCHANTING_TABLE,
            ColourfulEnchantingTables.LIME_ENCHANTING_TABLE,
            ColourfulEnchantingTables.GREEN_ENCHANTING_TABLE,
            ColourfulEnchantingTables.CYAN_ENCHANTING_TABLE,
            ColourfulEnchantingTables.LIGHT_BLUE_ENCHANTING_TABLE,
            ColourfulEnchantingTables.BLUE_ENCHANTING_TABLE,
            ColourfulEnchantingTables.PURPLE_ENCHANTING_TABLE,
            ColourfulEnchantingTables.MAGENTA_ENCHANTING_TABLE,
            ColourfulEnchantingTables.PINK_ENCHANTING_TABLE
    };

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        for (Block block : COLOURED_ENCHANTING_TABLES) {
            if (canUse(this.context, player, block)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    protected EnchantmentScreenHandlerMixin(int syncId) {
        super(null, syncId);
    }
}
