package me.steven.mocolors.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class PainterScreen extends CottonInventoryScreen<PainterScreenHandler> {
    public PainterScreen(PainterScreenHandler description, PlayerEntity player, Text title) {
        super(description, player, title);
    }
}
