package me.steven.mocolors.blocks.models;

import me.steven.mocolors.MoColors;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ColoredBrickSlabModel extends ColoredBrickModel {

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        brickBakedModel = loader.getOrLoadModel(new Identifier(MoColors.MOD_ID, "block/colored_bricks_slab")).bake(loader, textureGetter, rotationContainer, modelId);
        return this;
    }
}
