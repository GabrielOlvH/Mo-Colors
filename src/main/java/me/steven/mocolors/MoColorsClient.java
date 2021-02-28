package me.steven.mocolors;

import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import me.steven.mocolors.blocks.ColoredSlabBlockEntity;
import me.steven.mocolors.blocks.models.*;
import me.steven.mocolors.gui.PainterScreen;
import me.steven.mocolors.gui.PainterScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class MoColorsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HudRenderCallback.EVENT.register(new HudRenderer());

        ColoredBakedModel glassBakedModel = new ColoredGlassBakedModel();
        ColoredBakedModel slimeBakedModel = new ColoredSlimeBakedModel();
        ColoredBakedModel glassPaneBakedModel = new ColoredGlassPaneModel();
        ColoredBakedModel woolBakedModel = new ColoredWoolBakedModel();
        ColoredBakedModel concreteBakedModel = new ColoredConcreteBakedModel();
        ColoredBakedModel brickBakedModel = new ColoredBrickModel();
        ColoredBakedModel brickSlabBakedModel = new ColoredBrickSlabModel();
        ColoredBakedModel brickStairsBakedModel = new ColoredBrickStairsModel();
        ModelLoadingRegistry.INSTANCE.registerVariantProvider((res) -> (modelId, ctx) -> {
            if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_glass"))
                return glassBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_slime"))
                return slimeBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_glass_pane"))
                return glassPaneBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_wool"))
                return woolBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_concrete"))
                return concreteBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_bricks"))
                return brickBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_bricks_stairs"))
                return brickStairsBakedModel;
            else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_bricks_slab"))
                return brickSlabBakedModel;
            return null;
        });


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

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (Screen.hasControlDown()) {
                ItemStack stack = MinecraftClient.getInstance().player.getMainHandStack();
                ClientWorld world = MinecraftClient.getInstance().world;
                HitResult hit = MinecraftClient.getInstance().crosshairTarget;
                if (world != null && stack.getItem() == MoColors.PAINTER_ITEM && hit != null && hit.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = new BlockPos(((BlockHitResult) hit).getBlockPos());
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();
                    if (block instanceof ColoredBlock) {
                        BlockEntity blockEntity = world.getBlockEntity(pos);
                        if (!(blockEntity instanceof ColoredBlockEntity)) return;
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeInt(((ColoredBlockEntity) blockEntity).getColor());
                        ClientPlayNetworking.send(MoColors.PAINTER_COLOR_PICK_PACKET, buf);
                    }
                }
            }
        });
    }
}
