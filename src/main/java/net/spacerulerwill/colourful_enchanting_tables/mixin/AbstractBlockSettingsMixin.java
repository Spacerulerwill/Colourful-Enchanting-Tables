package net.spacerulerwill.colourful_enchanting_tables.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.spacerulerwill.colourful_enchanting_tables.AbstractBlockSettingsExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(AbstractBlock.Settings.class)
public class AbstractBlockSettingsMixin implements AbstractBlockSettingsExtension {
    @Shadow
    private Function<BlockState, MapColor> materialColorFactory;

    @Unique
    public AbstractBlock.Settings colourful_enchanting_tables$mapColor(MapColor color) {
        this.materialColorFactory = (state) -> color;
        return (AbstractBlock.Settings) (Object) this;
    }
}
