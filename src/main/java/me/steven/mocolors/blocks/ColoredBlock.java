package me.steven.mocolors.blocks;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface ColoredBlock {
    default boolean setColor(World world, BlockPos pos, int color) {
        return false;
    }

    @Nullable
    default Item convert() {
        return null;
    }
}
