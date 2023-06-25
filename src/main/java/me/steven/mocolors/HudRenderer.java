package me.steven.mocolors;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import me.steven.mocolors.utils.ColorUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Locale;

public class HudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        ColorUtils.getColor().ifPresent((color) -> {
            int x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
            int y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2;
            Text colorPickTxt = Text.literal("Ctrl to pick color");
            int width = textRenderer.getWidth(colorPickTxt);
            drawContext.drawText(textRenderer, colorPickTxt.asOrderedText(), x, y + 8,-1, true);
            ScreenDrawing.coloredRect(drawContext, x, y + 18, width, textRenderer.fontHeight, 255 << 24 | color);
            drawContext.drawText(textRenderer, Text.literal("#" + Integer.toHexString(color).toUpperCase(Locale.ROOT)).asOrderedText(), x, y + 19, getTextColor(color), false);
        });
    }

    private float getLuminance(int rgb) {
        int r = (rgb >> 16) & 255;
        int g = (rgb >> 8) & 255;
        int b = (rgb >> 0) & 255;
        return (float) (0.2126 * r + 0.7152 * g + 0.0722 * b);
    }

    public int getTextColor(int bgColor) {
        float luminance = this.getLuminance(bgColor);
        return luminance < 140 ? 0xFFFFFF : 0x000000;
    }
}
