package me.steven.mocolors;

import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public class ColorProvider implements BlockColorProvider {
    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (state.getBlock() instanceof ColoredBlock && world != null) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ColoredBlockEntity) {
                return ((ColoredBlockEntity) blockEntity).getRGB();
            }
        }
        return 0;
    }
}
