package me.steven.mocolors.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ColoredGlassBlock extends GlassBlock implements BlockEntityProvider {
    public ColoredGlassBlock() {
        super(FabricBlockSettings.copyOf(Blocks.GLASS));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new ColoredBlockEntity();
    }
}
