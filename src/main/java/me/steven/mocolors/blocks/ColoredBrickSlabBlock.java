package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColoredBrickSlabBlock extends SlabBlock implements BlockEntityProvider, ColoredBlock {

    public ColoredBrickSlabBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BRICKS));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new ColoredSlabBlockEntity();
    }

    @Override
    public @Nullable Item getCleanItem() {
        return Items.BRICK_SLAB;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        SlabType type = state.get(TYPE);
        if (type == SlabType.TOP || type == SlabType.DOUBLE) {
            ItemStack topStack = new ItemStack(this);
            topStack.getOrCreateTag().putInt("Color", ((ColoredSlabBlockEntity) blockEntity).getTopColor());
            dropStack(world, pos, topStack);
        }
        if (type == SlabType.BOTTOM || type == SlabType.DOUBLE) {
            ItemStack bottomStack = new ItemStack(this);
            bottomStack.getOrCreateTag().putInt("Color", ((ColoredSlabBlockEntity) blockEntity).getBottomColor());
            dropStack(world, pos, bottomStack);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        int color = itemStack.getOrCreateTag().getInt("Color");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ColoredSlabBlockEntity) {
            ColoredSlabBlockEntity slabBlockEntity = ((ColoredSlabBlockEntity) blockEntity);
            SlabType type = state.get(TYPE);
            if (type == SlabType.TOP)
                slabBlockEntity.setTopColor(color);
            else if (type == SlabType.BOTTOM)
                slabBlockEntity.setBottomColor(color);
            blockEntity.markDirty();
            if (!world.isClient())
                ((ColoredSlabBlockEntity) blockEntity).sync();
        }
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            BlockEntity blockEntity = ctx.getWorld().getBlockEntity(blockPos);
            if (blockEntity instanceof ColoredSlabBlockEntity) {
                ColoredSlabBlockEntity slabBlockEntity = ((ColoredSlabBlockEntity) blockEntity);
                if (blockState.get(TYPE) == SlabType.BOTTOM) {
                    slabBlockEntity.setTopColor(ctx.getStack().getOrCreateTag().getInt("Color"));
                } else if (blockState.get(TYPE) == SlabType.TOP) {
                    slabBlockEntity.setBottomColor(ctx.getStack().getOrCreateTag().getInt("Color"));
                }
                slabBlockEntity.markDirty();
                if (!ctx.getWorld().isClient())
                    slabBlockEntity.sync();
            }
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5D)) ? blockState2 : blockState2.with(TYPE, SlabType.TOP);
        }
    }
}
