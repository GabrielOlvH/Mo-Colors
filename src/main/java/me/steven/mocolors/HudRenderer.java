package me.steven.mocolors;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Locale;

public class HudRenderer implements HudRenderCallback {
    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        ItemStack stack = MinecraftClient.getInstance().player.getMainHandStack();
        ClientWorld world = MinecraftClient.getInstance().world;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        HitResult hit = MinecraftClient.getInstance().crosshairTarget;
        if (world != null && stack.getItem() == MoColors.PAINTER_ITEM && hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = new BlockPos(((BlockHitResult) hit).getBlockPos());
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block instanceof ColoredBlock) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (!(blockEntity instanceof ColoredBlockEntity)) return;
                int x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
                int y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2;
                LiteralText colorPickTxt = new LiteralText("Ctrl to pick color");
                int width = textRenderer.getWidth(colorPickTxt);
                textRenderer.draw(matrices, colorPickTxt, x, y + 8, -1);
                int color = ((ColoredBlockEntity) blockEntity).getColor();
                ScreenDrawing.coloredRect(x, y + 18, width, textRenderer.fontHeight, 255 << 24 | color);
                textRenderer.draw(matrices, new LiteralText("#" + Integer.toHexString(color).toUpperCase(Locale.ROOT)), x, y + 19, getTextColor(color));
            }
        }
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
