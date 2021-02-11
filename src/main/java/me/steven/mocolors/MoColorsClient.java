package me.steven.mocolors;

import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredSlabBlockEntity;
import me.steven.mocolors.gui.PainterScreen;
import me.steven.mocolors.gui.PainterScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;

public class MoColorsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MoColors.COLORED_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MoColors.COLORED_SLIME, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(MoColors.COLORED_GLASS_PANE, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register(new ColorProvider(), MoColors.COLORED_GLASS, MoColors.COLORED_GLASS_PANE, MoColors.COLORED_SLIME, MoColors.COLORED_WOOL, MoColors.COLORED_CONCRETE, MoColors.COLORED_BRICKS, MoColors.COLORED_BRICKS_STAIRS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (state.getBlock() instanceof ColoredBlock && world != null) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof ColoredSlabBlockEntity) {
                    SlabType type = state.get(SlabBlock.TYPE);
                    if (type == SlabType.BOTTOM || type == SlabType.DOUBLE)
                        return ((ColoredSlabBlockEntity) blockEntity).getBottomColor();
                    else
                        return ((ColoredSlabBlockEntity) blockEntity).getTopColor();
                } else {
                    // minecraft is shit calls getColor with the BlockPos of the particle, not the actual block, without this every walking/fall particle would be black.
                    ClientPlayerEntity player = MinecraftClient.getInstance().player;
                    blockEntity = world.getBlockEntity(player.getBlockPos().down());
                    if (blockEntity instanceof ColoredSlabBlockEntity) {
                        double f = player.getPos().y - player.getBlockPos().getY();
                        return f < 0.5 ? ((ColoredSlabBlockEntity) blockEntity).getTopColor() : ((ColoredSlabBlockEntity) blockEntity).getBottomColor();
                    }
                }
            }
            return 0;
        }, MoColors.COLORED_BRICKS_SLAB);
        ScreenRegistry.<PainterScreenHandler, PainterScreen>register(MoColors.DYE_MIXER_TYPE, (handler, inv, text) -> new PainterScreen(handler, inv.player, text));
    }
}
