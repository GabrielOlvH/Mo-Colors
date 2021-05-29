package me.steven.mocolors.blocks.models;

import me.steven.mocolors.MoColors;
import me.steven.mocolors.blocks.ColoredSlabBlockEntity;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class ColoredBrickSlabModel extends ColoredBrickModel {

    private BakedModel bottomModel;
    private BakedModel topModel;

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext ctx) {
        ColoredSlabBlockEntity blockEntity = (ColoredSlabBlockEntity) blockRenderView.getBlockEntity(blockPos);
        ctx.pushTransform((q) -> {
            q.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            return true;
        });
        SlabType type = blockState.get(SlabBlock.TYPE);
        if (type == SlabType.TOP || type == SlabType.DOUBLE) {
            ctx.pushTransform((q) -> {
                int rawColor = blockEntity.getTopColor();
                int color = 255 << 24 | rawColor;
                q.spriteColor(0, color, color, color, color);
                return true;
            });
            ctx.fallbackConsumer().accept(topModel);
            ctx.popTransform();
        }

        if (type == SlabType.BOTTOM || type == SlabType.DOUBLE) {
            ctx.pushTransform((q) -> {
                int rawColor = blockEntity.getBottomColor();
                int color = 255 << 24 | rawColor;
                q.spriteColor(0, color, color, color, color);
                return true;
            });
            ctx.fallbackConsumer().accept(bottomModel);
            ctx.popTransform();
        }
        ctx.popTransform();
    }


    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        super.bake(loader, textureGetter, rotationContainer, modelId);
        brickBakedModel = loader.getOrLoadModel(new Identifier(MoColors.MOD_ID, "block/colored_bricks_slab")).bake(loader, textureGetter, rotationContainer, modelId);
        bottomModel = loader.getOrLoadModel(new Identifier("block/slab")).bake(loader, textureGetter, rotationContainer, modelId);
        topModel = loader.getOrLoadModel(new Identifier("block/slab_top")).bake(loader, textureGetter, rotationContainer, modelId);
        return this;
    }
}
