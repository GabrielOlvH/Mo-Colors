package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ColoredWoolBlock extends Block implements BlockEntityProvider, ColoredBlock {
    public ColoredWoolBlock() {
        super(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).nonOpaque());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new ColoredBlockEntity();
    }

    @Override
    public @Nullable Item getCleanItem() {
        return Items.WHITE_WOOL;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = super.getPickStack(world, pos, state);
        ColoredBlockEntity blockEntity = (ColoredBlockEntity) world.getBlockEntity(pos);
        stack.getOrCreateTag().putInt("Color", blockEntity.getColor());
        return stack;
    }
}
