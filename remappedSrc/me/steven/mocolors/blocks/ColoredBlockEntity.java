package me.steven.mocolors.blocks;

import me.steven.mocolors.MoColors;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;

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
    public void readNbt(BlockState state, NbtCompound tag) {
        super.readNbt(state, tag);
        this.color = tag.getInt("c");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putInt("c", color);
        return super.writeNbt(tag);
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        this.color = tag.getInt("c");
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, null, null, 8);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        tag.putInt("c", color);
        return tag;
    }
}
