package me.steven.mocolors.blocks.models;

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class ColoredGlassPaneModel extends ColoredBakedModel {

    public BakedModel paneNoSide;
    public BakedModel paneNoSideRotY270;
    public BakedModel paneNoSideAlt;
    public BakedModel paneNoSideAltRotY90;
    public BakedModel paneSide;
    public BakedModel paneSideRotY90;
    public BakedModel paneSideAlt;
    public BakedModel paneSideAltRotY90;
    public BakedModel panePost;
    public BakedModel itemModel;
    public Sprite sprite;
    private final SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("block/white_stained_glass"));

    @Override
    public BakedModel getBaseBakedModel() {
        return paneNoSide;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext ctx) {
        int rawColor = getColor(blockRenderView, blockPos);
        ctx.pushTransform((q) -> {
            int color = 255 << 24 | rawColor;
            q.spriteColor(0, color, color, color, color);
            return true;
        });
        ctx.fallbackConsumer().accept(panePost);
        if (blockState.get(PaneBlock.NORTH)) ctx.fallbackConsumer().accept(paneSide);
        else ctx.fallbackConsumer().accept(paneNoSide);
        if (blockState.get(PaneBlock.EAST)) ctx.fallbackConsumer().accept(paneSideRotY90);
        else ctx.fallbackConsumer().accept(paneNoSideAlt);
        if (blockState.get(PaneBlock.SOUTH)) ctx.fallbackConsumer().accept(paneSideAlt);
        else ctx.fallbackConsumer().accept(paneNoSideAltRotY90);
        if (blockState.get(PaneBlock.WEST)) ctx.fallbackConsumer().accept(paneSideAltRotY90);
        else ctx.fallbackConsumer().accept(paneNoSideRotY270);
        ctx.popTransform();
    }

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext ctx) {
        if (itemModel == null)
            itemModel = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier("white_stained_glass_pane"), "inventory"));
        ctx.pushTransform((q) -> {
            int rawColor = itemStack.getOrCreateNbt().getInt("Color");
            int color = 255 << 24 | rawColor;
            q.spriteColor(0, color, color, color, color);
            return true;
        });
        ctx.fallbackConsumer().accept(itemModel);
        ctx.popTransform();
    }

    @Override
    public ModelTransformation getTransformation() {
        return MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier("white_stained_glass_pane"), "inventory")).getTransformation();
    }

    @Override
    public Sprite getParticleSprite() {
        return sprite;
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        paneNoSide = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside")).bake(baker, textureGetter, rotationContainer, modelId);
        paneNoSideRotY270 = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside")).bake(baker, textureGetter, ModelRotation.X0_Y270, modelId);
        paneNoSideAlt = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside_alt")).bake(baker, textureGetter, rotationContainer, modelId);
        paneNoSideAltRotY90 = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside_alt")).bake(baker, textureGetter, ModelRotation.X0_Y90, modelId);
        panePost = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_post")).bake(baker, textureGetter, rotationContainer, modelId);
        paneSide = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side")).bake(baker, textureGetter, rotationContainer, modelId);
        paneSideRotY90 = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side")).bake(baker, textureGetter, ModelRotation.X0_Y90, modelId);
        paneSideAlt = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side_alt")).bake(baker, textureGetter, rotationContainer, modelId);
        paneSideAltRotY90 = baker.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side_alt")).bake(baker, textureGetter, ModelRotation.X0_Y90, modelId);
        sprite = textureGetter.apply(spriteId);
        return this;
    }
}
