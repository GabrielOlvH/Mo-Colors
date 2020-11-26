package me.steven.mocolors.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ColoredGlassPaneBlock extends PaneBlock implements BlockEntityProvider {
    public ColoredGlassPaneBlock() {
        super(FabricBlockSettings.copyOf(Blocks.WHITE_STAINED_GLASS_PANE).nonOpaque());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new ColoredBlockEntity();
    }
}
