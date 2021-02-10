package me.steven.mocolors.gui;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.function.Supplier;

public class WColorVisualizer extends WWidget {
    private Supplier<Integer> colorProvider = () -> -1;

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        ScreenDrawing.coloredRect(x - 1, y - 1, width + 2, height + 2, -1);
        int color = 255 << 24 | colorProvider.get();
        ScreenDrawing.coloredRect(x, y, width, height, color);
    }

    public void setColorProvider(Supplier<Integer> color) {
        this.colorProvider = color;
    }

    public Supplier<Integer> getColorProvider() {
        return colorProvider;
    }
}
