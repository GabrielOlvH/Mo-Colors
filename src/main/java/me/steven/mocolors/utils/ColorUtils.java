package me.steven.mocolors.utils;

import me.steven.mocolors.MoColors;
import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import me.steven.mocolors.blocks.ColoredSlabBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.OptionalInt;

public class ColorUtils {
    @Environment(EnvType.CLIENT)
    public static OptionalInt getColor() {
        ItemStack stack = MinecraftClient.getInstance().player.getMainHandStack();
        ClientWorld world = MinecraftClient.getInstance().world;
        HitResult hit = MinecraftClient.getInstance().crosshairTarget;
        if (world != null && stack.getItem() == MoColors.PAINTER_ITEM && hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = new BlockPos(((BlockHitResult) hit).getBlockPos());
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block instanceof ColoredBlock) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof ColoredBlockEntity) {
                    return OptionalInt.of(((ColoredBlockEntity) blockEntity).getColor());
                } else if (blockEntity instanceof ColoredSlabBlockEntity) {
                    boolean isTop = hit.getPos().y - pos.getY() > 0.5;
                    return OptionalInt.of(isTop ? ((ColoredSlabBlockEntity) blockEntity).getTopColor() : ((ColoredSlabBlockEntity) blockEntity).getBottomColor());
                }
            }
        }

        return OptionalInt.empty();
    }
}
