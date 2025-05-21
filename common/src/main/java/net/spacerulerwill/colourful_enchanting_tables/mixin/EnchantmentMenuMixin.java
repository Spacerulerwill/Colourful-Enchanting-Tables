package net.spacerulerwill.colourful_enchanting_tables.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.block.Block;
import net.spacerulerwill.colourful_enchanting_tables.Common;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {
    @Shadow
    @Final
    private ContainerLevelAccess access;

    protected EnchantmentMenuMixin(int syncId) {
        super(null, syncId);
    }

    @Shadow
    public abstract boolean stillValid(Player p_39463_);

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void injected(Player player, CallbackInfoReturnable<Boolean> cir) {
        for (Block block : Common.registeredEnchantingTables) {
            if (stillValid(this.access, player, block)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }
}
