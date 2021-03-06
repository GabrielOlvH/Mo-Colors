package me.steven.mocolors.blocks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface ColoredBlock {
    default boolean setColor(ItemUsageContext ctx, World world, BlockPos pos, int color) {
        return false;
    }

    @Nullable
    default Item getCleanItem() {
        return null;
    }
}
