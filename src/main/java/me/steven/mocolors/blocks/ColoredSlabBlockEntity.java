package me.steven.mocolors.blocks;

import me.steven.mocolors.MoColors;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.CompoundTag;

public class ColoredSlabBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private int topColor;
    private int bottomColor;

    public ColoredSlabBlockEntity() {
        super(MoColors.COLORED_SLAB_BLOCK_ENTITY_TYPE);
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
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.topColor = tag.getInt("top");
        this.bottomColor = tag.getInt("bottom");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("top", topColor);
        tag.putInt("bottom", bottomColor);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.topColor = tag.getInt("top");
        this.bottomColor = tag.getInt("bottom");
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, null, null, 8);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putInt("top", topColor);
        tag.putInt("bottom", bottomColor);
        return tag;
    }
}
