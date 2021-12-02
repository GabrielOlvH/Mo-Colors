package me.steven.mocolors.items;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import me.steven.mocolors.MoColors;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class PainterBakedModel implements UnbakedModel, BakedModel, FabricBakedModel {

    private static final SpriteIdentifier STICK_SPRITE_ID = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("item/stick"));
    public Sprite stickSprite = null;

    public BakedModel handleModel;
    public BakedModel rollerModel;

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        context.fallbackConsumer().accept(handleModel);
        context.pushTransform((q) -> {
            int color = 255 << 24 | stack.getOrCreateNbt().getInt("Color");
            q.spriteColor(0, color, color, color, color);
            return true;
        });
        context.fallbackConsumer().accept(rollerModel);
        context.popTransform();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return null;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return stickSprite;
    }

    @Override
    public ModelTransformation getTransformation() {
        return handleModel.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Lists.newArrayList(
                new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("mocolors", "item/painter_roller")),
                new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("mocolors", "item/painter_handle"))
        );
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        rollerModel = loader.getOrLoadModel(new ModelIdentifier(new Identifier(MoColors.MOD_ID, "painter_roller"), "inventory")).bake(loader, textureGetter, rotationContainer, modelId);
        handleModel = loader.getOrLoadModel(new ModelIdentifier(new Identifier(MoColors.MOD_ID, "painter_handle"), "inventory")).bake(loader, textureGetter, rotationContainer, modelId);
        stickSprite = textureGetter.apply(STICK_SPRITE_ID);
        return this;
    }
}
