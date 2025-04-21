package net.spacerulerwill.colourful_enchanting_tables.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.MaterialColor;
import net.spacerulerwill.colourful_enchanting_tables.BlockSettingsExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Block.Settings.class)
public class BlockSettingsMixin implements BlockSettingsExtension {
    @Shadow
    private MaterialColor materialColor;

    @Unique
    public Block.Settings colourful_enchanting_tables$mapColor(MaterialColor color) {
        this.materialColor = color;
        return (Block.Settings) (Object) this;
    }
}
