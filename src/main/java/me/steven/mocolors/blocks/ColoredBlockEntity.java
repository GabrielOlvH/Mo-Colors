package me.steven.mocolors.blocks;

import me.steven.mocolors.MoColors;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.concurrent.ThreadLocalRandom;

public class ColoredBlockEntity extends BlockEntity {

    private int rgb;

    public ColoredBlockEntity() {
        super(MoColors.COLORED_BLOCK_ENTITY_TYPE);
        rgb = ThreadLocalRandom.current().nextInt();
    }

    public int getRGB() {
        return rgb;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        this.rgb = tag.getInt("rgb");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("rgb", rgb);
        return tag;
    }
}
