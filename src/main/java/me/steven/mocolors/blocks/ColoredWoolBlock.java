package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColoredWoolBlock extends Block implements BlockEntityProvider, ColoredBlock {
    public ColoredWoolBlock() {
        super(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).nonOpaque());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ColoredBlockEntity(pos, state);
    }

    @Override
    public @Nullable Item getCleanItem() {
        return Items.WHITE_WOOL;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        getDroppedStacks(state, (ServerWorld)world, pos, blockEntity, player, stack).forEach((itemStack) -> {
            itemStack.getOrCreateNbt().putInt("Color", ((ColoredBlockEntity)blockEntity).getColor());
            dropStack(world, pos, itemStack);
        });
        state.onStacksDropped((ServerWorld)world, pos, stack);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        int color = itemStack.getOrCreateNbt().getInt("Color");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ColoredBlockEntity) {
            ((ColoredBlockEntity) blockEntity).setColor(color);
            blockEntity.markDirty();
            if (!world.isClient())
                ((ColoredBlockEntity) blockEntity).sync();
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = super.getPickStack(world, pos, state);
        ColoredBlockEntity blockEntity = (ColoredBlockEntity) world.getBlockEntity(pos);
        stack.getOrCreateNbt().putInt("Color", blockEntity.getColor());
        return stack;
    }
}
