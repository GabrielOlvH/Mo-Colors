package me.steven.mocolors.blocks.models;

import com.mojang.datafixers.util.Pair;
import me.steven.mocolors.MoColors;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public class ColoredBrickModel extends ColoredBakedModel {

    protected BakedModel brickBakedModel;
    private Sprite sprite;
    private final SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier(MoColors.MOD_ID, "block/gray_bricks"));

    @Override
    public BakedModel getBaseBakedModel() {
        return brickBakedModel;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.singleton(spriteId);
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        brickBakedModel = loader.getOrLoadModel(new Identifier(MoColors.MOD_ID, "block/colored_bricks")).bake(loader, textureGetter, rotationContainer, modelId);
        sprite = textureGetter.apply(spriteId);
        return this;
    }
}
