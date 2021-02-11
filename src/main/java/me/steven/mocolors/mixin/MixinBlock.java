package me.steven.mocolors.mixin;

import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import me.steven.mocolors.utils.ConvertableBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class MixinBlock implements ColoredBlock {

    @Override
    public boolean setColor(World world, BlockPos pos, int color) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof ColoredBlockEntity)) {
            BlockState newState = ConvertableBlocks.convert(block);
            if (newState == null) return false;
            for (Property prop : blockState.getProperties()) {
                newState = newState.with(prop, blockState.get(prop));
            }
            world.setBlockState(pos, newState);
        }
        if (blockEntity == null) blockEntity = world.getBlockEntity(pos);
        ((ColoredBlockEntity) blockEntity).setColor(color);
        blockEntity.markDirty();
        if (!world.isClient())
            ((ColoredBlockEntity) blockEntity).sync();

        return true;
    }
}
