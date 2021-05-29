package me.steven.mocolors.blocks;

import me.steven.mocolors.MoColors;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ColoredSlabBlockEntity extends BlockEntity implements BlockEntityClientSerializable, RenderAttachmentBlockEntity {

    private int topColor = -1;
    private int bottomColor = -1;

    public ColoredSlabBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoColors.COLORED_SLAB_BLOCK_ENTITY_TYPE, pos, blockState);
    }

    public int getTopColor() {
        return topColor;
    }

    public void setTopColor(int topColor) {
        this.topColor = topColor;
    }

    public int getBottomColor() {
        return bottomColor;
    }

    public void setBottomColor(int bottomColor) {
        this.bottomColor = bottomColor;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.topColor = tag.getInt("top");
        this.bottomColor = tag.getInt("bottom");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putInt("top", topColor);
        tag.putInt("bottom", bottomColor);
        return super.writeNbt(tag);
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        this.topColor = tag.getInt("top");
        this.bottomColor = tag.getInt("bottom");
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, null, null, 8);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        tag.putInt("top", topColor);
        tag.putInt("bottom", bottomColor);
        return tag;
    }

    @Override
    public @Nullable Object getRenderAttachmentData() {
        return new int[] {topColor, bottomColor};
    }
}
