package me.steven.mocolors.blocks.models;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
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
    public Sprite getParticleSprite() {
        return sprite;
    }

  /*  @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.singleton(spriteId);
    }*/

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        this.model = baker.getOrLoadModel(this.modelId).bake(baker, textureGetter, rotationContainer, modelId);
        this.sprite = textureGetter.apply(this.spriteId);
        return this;
    }
}
