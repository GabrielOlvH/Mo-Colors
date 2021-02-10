package me.steven.mocolors.blocks;

import me.steven.mocolors.MoColors;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.CompoundTag;

public class ColoredBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private int color;

    public ColoredBlockEntity() {
        super(MoColors.COLORED_BLOCK_ENTITY_TYPE);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int rgb) {
        this.color = rgb;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.color = tag.getInt("rgb");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("rgb", color);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.color = tag.getInt("rgb");
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, null, null, 8);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putInt("rgb", color);
        return tag;
    }
}
