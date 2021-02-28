package me.steven.mocolors.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.netty.buffer.Unpooled;
import me.steven.mocolors.MoColors;
import me.steven.mocolors.items.PainterItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.util.Locale;
import java.util.regex.Pattern;

public class PainterScreenHandler extends SyncedGuiDescription  {

    public static final Identifier PAINTER_SCREEN_BG = new Identifier(MoColors.MOD_ID, "textures/gui/painter_bg.png");

    private static final Pattern VALID_REGEX = Pattern.compile("#[A-F0-9]+");

    public static final Identifier SCREEN_ID = new Identifier(MoColors.MOD_ID, "painter_screen");

    private final WTextField hexCode = new WTextField();

    public PainterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(MoColors.DYE_MIXER_TYPE, syncId, playerInventory);
        WGridPanel root = new WGridPanel();
        this.rootPanel = root;

        WColorVisualizer colorVisualizer = new WColorVisualizer();
        root.add(colorVisualizer, 3, 2);
        colorVisualizer.setLocation(4 * 18, 2 * 18 + 1);
        colorVisualizer.setColorProvider(() -> {
            if (VALID_REGEX.matcher(hexCode.getText().toUpperCase(Locale.ROOT)).matches()) {
                return Integer.decode("0x" + hexCode.getText().substring(1));
            } else return -1;
        });


        WLabel label = new WLabel("Color (#RRGGBB)");
        root.add(label, 0, 1);
        label.setLocation(0, 22);

        ItemStack mainHandStack = playerInventory.getMainHandStack();
        int color = PainterItem.getColor(mainHandStack);
        hexCode.setText("#" + Integer.toHexString(color).toUpperCase(Locale.ROOT));
        hexCode.setTextPredicate((s) -> VALID_REGEX.matcher(s.toUpperCase(Locale.ROOT)).matches());
        hexCode.setMaxLength(7);
        root.add(hexCode, 0, 2);
        hexCode.setSize(50, 20);

        root.validate(this);
    }

    @Override
    public void addPainters() {
        if (this.rootPanel != null && !this.fullscreen) {
            this.rootPanel.setBackgroundPainter((x, y, panel) ->
                    ScreenDrawing.texturedRect(x - 6, y - 6, panel.getWidth() + 12, panel.getHeight() + 12, PAINTER_SCREEN_BG, -1));
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        if (world.isClient) {
            if (!VALID_REGEX.matcher(hexCode.getText().toUpperCase(Locale.ROOT)).matches()) return;
            int i;
            try {
                i = Integer.decode("0x" + hexCode.getText().substring(1));
            } catch (NumberFormatException e) {
                LogManager.getLogger("PainterGUI").warn("Received false match! " + hexCode.toString());
                return;
            }
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeInt(i);
            ClientPlayNetworking.send(MoColors.UPDATE_PAINTER_COLOR_PACKET, buf);
        }
    }
}
