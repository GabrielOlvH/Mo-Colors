package me.steven.mocolors.mixin;

import me.steven.mocolors.MoColors;
import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import me.steven.mocolors.utils.ConvertableBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
            if (ConvertableBlocks.WOOL_BLOCKS.contains(block)) {
                world.setBlockState(pos, MoColors.COLORED_WOOL.getDefaultState());
            } else if (ConvertableBlocks.CONCRETE_BLOCKS.contains(block)) {
                world.setBlockState(pos, MoColors.COLORED_CONCRETE.getDefaultState());
            } else if (block == Blocks.SLIME_BLOCK) {
                world.setBlockState(pos, MoColors.COLORED_SLIME.getDefaultState());
            } else if (ConvertableBlocks.GLASS_BLOCKS.contains(block)) {
                world.setBlockState(pos, MoColors.COLORED_GLASS.getDefaultState());
            } else if (ConvertableBlocks.GLASS_PANE_BLOCKS.contains(block)) {
                world.setBlockState(pos, MoColors.COLORED_GLASS_PANE.getDefaultState());
                for (Direction direction : Direction.values()) {
                    BlockState state = world.getBlockState(pos);
                    BlockPos sidePos = pos.offset(direction);
                    world.setBlockState(pos, state.getStateForNeighborUpdate(direction, world.getBlockState(sidePos), world, pos, sidePos));
                }
            } else return false;
        }
        if (blockEntity == null) blockEntity = world.getBlockEntity(pos);
        ((ColoredBlockEntity)blockEntity).setColor(color);
        blockEntity.markDirty();
        if (!world.isClient())
            ((ColoredBlockEntity)blockEntity).sync();
        
        return true;
    }
}
