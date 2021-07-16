package me.steven.mocolors.compat.dash;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import me.steven.mocolors.blocks.models.ColoredBasicModel;
import me.steven.mocolors.blocks.models.ColoredBrickSlabModel;
import net.minecraft.client.render.model.BakedModel;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashConstructor;
import net.oskarstrom.dashloader.api.annotation.DashObject;
import net.oskarstrom.dashloader.api.enums.ConstructorMode;
import net.oskarstrom.dashloader.model.DashModel;

@DashObject(ColoredBrickSlabModel.class)
public class DashBrickSlabModel implements DashModel {

    @Serialize(order = 0)
    public int sprite;
    @Serialize(order = 1)
    public int topModel;
    @Serialize(order = 2)
    public int bottomModel;

    public DashBrickSlabModel(ColoredBrickSlabModel model, DashRegistry registry) {
        this.sprite = registry.createSpritePointer(model.sprite);
        this.topModel = registry.createModelPointer(model.topModel);
        this.bottomModel = registry.createModelPointer(model.bottomModel);
    }

    public DashBrickSlabModel(
            @Deserialize("sprite") int sprite,
            @Deserialize("topModel") int topModel,
            @Deserialize("bottomModel") int bottomModel) {
        this.sprite = sprite;
        this.topModel = topModel;
        this.bottomModel = bottomModel;
    }

    @Override
    public BakedModel toUndash(DashRegistry registry) {
        ColoredBrickSlabModel coloredBasicModel = new ColoredBrickSlabModel();
        coloredBasicModel.topModel = registry.getModel(topModel);
        coloredBasicModel.bottomModel = registry.getModel(bottomModel);
        coloredBasicModel.sprite = registry.getSprite(sprite);
        return coloredBasicModel;
    }

    @Override
    public int getStage() {
        return 3;
    }
}

