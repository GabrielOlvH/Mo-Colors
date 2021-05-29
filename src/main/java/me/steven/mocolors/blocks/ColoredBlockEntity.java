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

public class ColoredBlockEntity extends BlockEntity implements BlockEntityClientSerializable, RenderAttachmentBlockEntity {

    private int color = -1;

    public ColoredBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoColors.COLORED_BLOCK_ENTITY_TYPE, pos, blockState);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int rgb) {
        this.color = rgb;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
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

    @Override
    public @Nullable Object getRenderAttachmentData() {
        return color;
    }
}
