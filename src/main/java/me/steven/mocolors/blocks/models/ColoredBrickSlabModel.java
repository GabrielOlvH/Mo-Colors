package me.steven.mocolors.blocks.models;

import me.steven.mocolors.MoColors;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
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

    public BakedModel bottomModel;
    public BakedModel topModel;

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext ctx) {
        Object obj = ((RenderAttachedBlockView) blockRenderView).getBlockEntityRenderAttachment(blockPos);
        int[] rawColor = obj == null ? new int[]{-1, -1} : (int[]) obj;
        ctx.pushTransform((q) -> {
            q.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            return true;
        });
        SlabType type = blockState.get(SlabBlock.TYPE);
        if (type == SlabType.TOP || type == SlabType.DOUBLE) {
            ctx.pushTransform((q) -> {
                int color = 255 << 24 | rawColor[0];
                q.spriteColor(0, color, color, color, color);
                return true;
            });
            ctx.fallbackConsumer().accept(topModel);
            ctx.popTransform();
        }

        if (type == SlabType.BOTTOM || type == SlabType.DOUBLE) {
            ctx.pushTransform((q) -> {
                int color = 255 << 24 | rawColor[1];
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
        model = loader.getOrLoadModel(new Identifier(MoColors.MOD_ID, "block/colored_bricks_slab")).bake(loader, textureGetter, rotationContainer, modelId);
        bottomModel = loader.getOrLoadModel(new Identifier("block/slab")).bake(loader, textureGetter, rotationContainer, modelId);
        topModel = loader.getOrLoadModel(new Identifier("block/slab_top")).bake(loader, textureGetter, rotationContainer, modelId);
        return this;
    }
}
