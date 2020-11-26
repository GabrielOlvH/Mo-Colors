package me.steven.mocolors.blocks.models;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ColoredGlassBakedModel extends ColoredBakedModel {

    private BakedModel glassBakedModel;
    private Sprite sprite;
    private final SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("block/white_stained_glass"));

    @Override
    public BakedModel getBaseBakedModel() {
        return glassBakedModel;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        glassBakedModel = loader.getOrLoadModel(new Identifier("block/white_stained_glass")).bake(loader, textureGetter, rotationContainer, modelId);
        sprite = textureGetter.apply(spriteId);
        return this;
    }
}
