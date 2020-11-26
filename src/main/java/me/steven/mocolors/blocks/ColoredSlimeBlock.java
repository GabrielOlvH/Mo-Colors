package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
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
}
