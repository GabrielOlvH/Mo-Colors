package me.steven.mocolors;

import me.steven.mocolors.gui.PainterScreen;
import me.steven.mocolors.gui.PainterScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

public class MoColorsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MoColors.COLORED_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MoColors.COLORED_SLIME, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MoColors.COLORED_GLASS_PANE, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register(new ColorProvider(), MoColors.COLORED_GLASS, MoColors.COLORED_GLASS_PANE, MoColors.COLORED_SLIME, MoColors.COLORED_WOOL, MoColors.COLORED_CONCRETE, MoColors.COLORED_BRICKS, MoColors.COLORED_BRICKS_SLAB, MoColors.COLORED_BRICKS_STAIRS);
        ScreenRegistry.<PainterScreenHandler, PainterScreen>register(MoColors.DYE_MIXER_TYPE, (handler, inv, text) -> new PainterScreen(handler, inv.player, text));
    }
}
