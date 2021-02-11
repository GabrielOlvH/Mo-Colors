package me.steven.mocolors.utils;

import com.google.common.collect.Lists;
import me.steven.mocolors.MoColors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.List;

public class ConvertableBlocks {
    public static final List<Block> WOOL_BLOCKS = Lists.newArrayList(
            Blocks.WHITE_WOOL,
            Blocks.ORANGE_WOOL,
            Blocks.MAGENTA_WOOL,
            Blocks.LIGHT_BLUE_WOOL,
            Blocks.YELLOW_WOOL,
            Blocks.LIME_WOOL,
            Blocks.PINK_WOOL,
            Blocks.GRAY_WOOL,
            Blocks.LIGHT_GRAY_WOOL,
            Blocks.CYAN_WOOL,
            Blocks.PURPLE_WOOL,
            Blocks.BLUE_WOOL,
            Blocks.BROWN_WOOL,
            Blocks.GREEN_WOOL,
            Blocks.RED_WOOL,
            Blocks.BLACK_WOOL
    );

    public static final List<Block> CONCRETE_BLOCKS = Lists.newArrayList(
            Blocks.WHITE_CONCRETE,
            Blocks.ORANGE_CONCRETE,
            Blocks.MAGENTA_CONCRETE,
            Blocks.LIGHT_BLUE_CONCRETE,
            Blocks.YELLOW_CONCRETE,
            Blocks.LIME_CONCRETE,
            Blocks.PINK_CONCRETE,
            Blocks.GRAY_CONCRETE,
            Blocks.LIGHT_GRAY_CONCRETE,
            Blocks.CYAN_CONCRETE,
            Blocks.PURPLE_CONCRETE,
            Blocks.BLUE_CONCRETE,
            Blocks.BROWN_CONCRETE,
            Blocks.GREEN_CONCRETE,
            Blocks.RED_CONCRETE,
            Blocks.BLACK_CONCRETE
    );

    public static final List<Block> GLASS_BLOCKS = Lists.newArrayList(
            Blocks.WHITE_STAINED_GLASS,
            Blocks.ORANGE_STAINED_GLASS,
            Blocks.MAGENTA_STAINED_GLASS,
            Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS,
            Blocks.LIME_STAINED_GLASS,
            Blocks.PINK_STAINED_GLASS,
            Blocks.GRAY_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS,
            Blocks.CYAN_STAINED_GLASS,
            Blocks.PURPLE_STAINED_GLASS,
            Blocks.BLUE_STAINED_GLASS,
            Blocks.BROWN_STAINED_GLASS,
            Blocks.GREEN_STAINED_GLASS,
            Blocks.RED_STAINED_GLASS,
            Blocks.BLACK_STAINED_GLASS
    );

    public static final List<Block> GLASS_PANE_BLOCKS = Lists.newArrayList(
            Blocks.WHITE_STAINED_GLASS_PANE,
            Blocks.ORANGE_STAINED_GLASS_PANE,
            Blocks.MAGENTA_STAINED_GLASS_PANE,
            Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
            Blocks.YELLOW_STAINED_GLASS_PANE,
            Blocks.LIME_STAINED_GLASS_PANE,
            Blocks.PINK_STAINED_GLASS_PANE,
            Blocks.GRAY_STAINED_GLASS_PANE,
            Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
            Blocks.CYAN_STAINED_GLASS_PANE,
            Blocks.PURPLE_STAINED_GLASS_PANE,
            Blocks.BLUE_STAINED_GLASS_PANE,
            Blocks.BROWN_STAINED_GLASS_PANE,
            Blocks.GREEN_STAINED_GLASS_PANE,
            Blocks.RED_STAINED_GLASS_PANE,
            Blocks.BLACK_STAINED_GLASS_PANE
    );

    public static BlockState convert(Block block) {
        if (ConvertableBlocks.WOOL_BLOCKS.contains(block)) {
            return MoColors.COLORED_WOOL.getDefaultState();
        } else if (ConvertableBlocks.CONCRETE_BLOCKS.contains(block)) {
            return MoColors.COLORED_CONCRETE.getDefaultState();
        } else if (block == Blocks.SLIME_BLOCK) {
            return MoColors.COLORED_SLIME.getDefaultState();
        } else if (block == Blocks.BRICKS) {
            return MoColors.COLORED_BRICKS.getDefaultState();
        } else if (block == Blocks.BRICK_STAIRS) {
            return MoColors.COLORED_BRICKS_STAIRS.getDefaultState();
        } else if (block == Blocks.BRICK_SLAB) {
            return MoColors.COLORED_BRICKS_SLAB.getDefaultState();
        } else if (ConvertableBlocks.GLASS_BLOCKS.contains(block)) {
            return MoColors.COLORED_GLASS.getDefaultState();
        } else if (ConvertableBlocks.GLASS_PANE_BLOCKS.contains(block)) {
            return MoColors.COLORED_GLASS_PANE.getDefaultState();
        } else return null;
    }
}
