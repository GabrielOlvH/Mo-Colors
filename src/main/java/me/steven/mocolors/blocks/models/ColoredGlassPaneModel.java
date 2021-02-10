package me.steven.mocolors.blocks.models;

import me.steven.mocolors.blocks.ColoredBlockEntity;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class ColoredGlassPaneModel extends ColoredBakedModel {

    private BakedModel paneNoSide;
    private BakedModel paneNoSideRotY270;
    private BakedModel paneNoSideAlt;
    private BakedModel paneNoSideAltRotY90;
    private BakedModel paneSide;
    private BakedModel paneSideRotY90;
    private BakedModel paneSideAlt;
    private BakedModel paneSideAltRotY90;
    private BakedModel panePost;
    private BakedModel itemModel;
    private Sprite sprite;
    private final SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("block/white_stained_glass"));

    @Override
    public BakedModel getBaseBakedModel() {
        return paneNoSide;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext ctx) {
        ctx.pushTransform((q) -> {
            ColoredBlockEntity blockEntity = (ColoredBlockEntity) blockRenderView.getBlockEntity(blockPos);
            int rawColor = blockEntity.getColor();
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
            int rawColor = itemStack.getOrCreateTag().getInt("Color");
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
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        paneNoSide = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside")).bake(loader, textureGetter, rotationContainer, modelId);
        paneNoSideRotY270 = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside")).bake(loader, textureGetter, ModelRotation.X0_Y270, modelId);
        paneNoSideAlt = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside_alt")).bake(loader, textureGetter, rotationContainer, modelId);
        paneNoSideAltRotY90 = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_noside_alt")).bake(loader, textureGetter, ModelRotation.X0_Y90, modelId);
        panePost = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_post")).bake(loader, textureGetter, rotationContainer, modelId);
        paneSide = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side")).bake(loader, textureGetter, rotationContainer, modelId);
        paneSideRotY90 = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side")).bake(loader, textureGetter, ModelRotation.X0_Y90, modelId);
        paneSideAlt = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side_alt")).bake(loader, textureGetter, rotationContainer, modelId);
        paneSideAltRotY90 = loader.getOrLoadModel(new Identifier("block/white_stained_glass_pane_side_alt")).bake(loader, textureGetter, ModelRotation.X0_Y90, modelId);
        sprite = textureGetter.apply(spriteId);
        return this;
    }
}
