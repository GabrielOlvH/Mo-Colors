package me.steven.mocolors.blocks.models;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ColoredBasicModel extends ColoredBakedModel {

    private final Identifier modelId;
    private final SpriteIdentifier spriteId;

    public BakedModel model;
    public Sprite sprite;

    public ColoredBasicModel(Identifier modelId, SpriteIdentifier spriteId) {
        this.modelId = modelId;
        this.spriteId = spriteId;
    }

    @Override
    public BakedModel getBaseBakedModel() {
        return model;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        this.model = loader.getOrLoadModel(this.modelId).bake(loader, textureGetter, rotationContainer, modelId);
        this.sprite = textureGetter.apply(this.spriteId);
        return this;
    }
}
