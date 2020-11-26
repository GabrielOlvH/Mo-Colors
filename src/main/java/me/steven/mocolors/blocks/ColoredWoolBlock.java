package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
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
}
