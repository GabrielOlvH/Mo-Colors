package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlimeBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ColoredSlimeBlock extends SlimeBlock implements BlockEntityProvider, ColoredBlock {
    public ColoredSlimeBlock() {
        super(FabricBlockSettings.copyOf(Blocks.SLIME_BLOCK).nonOpaque());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new ColoredBlockEntity();
    }

    @Override
    public @Nullable Item convert() {
        return Items.SLIME_BLOCK;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = super.getPickStack(world, pos, state);
        ColoredBlockEntity blockEntity = (ColoredBlockEntity) world.getBlockEntity(pos);
        stack.getOrCreateTag().putInt("Color", blockEntity.getColor());
        return stack;
    }
}
