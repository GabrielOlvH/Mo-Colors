package me.steven.mocolors.blocks.models;

import me.steven.mocolors.MoColors;
import me.steven.mocolors.blocks.ColoredBlockEntity;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class ColoredBrickStairsModel extends ColoredBrickModel {

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext ctx) {
        ColoredBlockEntity blockEntity = (ColoredBlockEntity) blockRenderView.getBlockEntity(blockPos);
        ctx.pushTransform((q) -> {
            q.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            int rawColor = blockEntity.getColor();
            int color = 255 << 24 | rawColor;
            q.spriteColor(0, color, color, color, color);
            return true;
        });
        BlockState vanillaStairs = Blocks.BRICK_STAIRS.getDefaultState();
        for (Property prop : blockState.getProperties()) {
            vanillaStairs = vanillaStairs.with(prop, blockState.get(prop));
        }
        BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(vanillaStairs);
        ctx.fallbackConsumer().accept(model);
        ctx.popTransform();
    }

    @Override
    public ModelTransformation getTransformation() {
        return MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier("brick_stairs"), "inventory")).getTransformation();
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        super.bake(loader, textureGetter, rotationContainer, modelId);
        brickBakedModel = loader.getOrLoadModel(new Identifier(MoColors.MOD_ID, "block/colored_bricks_stairs")).bake(loader, textureGetter, rotationContainer, modelId);
        return this;
    }
}
