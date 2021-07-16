package me.steven.mocolors.compat.dash;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import me.steven.mocolors.blocks.models.ColoredBrickSlabModel;
import me.steven.mocolors.items.PainterBakedModel;
import net.minecraft.client.render.model.BakedModel;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashConstructor;
import net.oskarstrom.dashloader.api.annotation.DashObject;
import net.oskarstrom.dashloader.api.enums.ConstructorMode;
import net.oskarstrom.dashloader.model.DashModel;

@DashObject(PainterBakedModel.class)
public class DashPainterModel implements DashModel {

    @Serialize(order = 0)
    public int sprite;
    @Serialize(order = 1)
    public int handleModel;
    @Serialize(order = 2)
    public int rollerModel;

    public DashPainterModel(PainterBakedModel model, DashRegistry registry) {
        this.sprite = registry.createSpritePointer(model.stickSprite);
        this.handleModel = registry.createModelPointer(model.handleModel);
        this.rollerModel = registry.createModelPointer(model.rollerModel);
    }

    public DashPainterModel(
            @Deserialize("sprite") int sprite,
            @Deserialize("handleModel") int handleModel,
            @Deserialize("rollerModel") int rollerModel) {
        this.sprite = sprite;
        this.handleModel = handleModel;
        this.rollerModel = rollerModel;
    }


    @Override
    public BakedModel toUndash(DashRegistry registry) {
        PainterBakedModel model = new PainterBakedModel();
        model.stickSprite = registry.getSprite(sprite);
        model.handleModel = registry.getModel(handleModel);
        model.rollerModel = registry.getModel(rollerModel);
        return model;
    }

    @Override
    public int getStage() {
        return 3;
    }
}
