package me.steven.mocolors.compat.dash;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import me.steven.mocolors.blocks.models.ColoredBasicModel;
import me.steven.mocolors.blocks.models.ColoredBrickStairsModel;
import net.minecraft.client.render.model.BakedModel;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashConstructor;
import net.oskarstrom.dashloader.api.annotation.DashObject;
import net.oskarstrom.dashloader.api.enums.ConstructorMode;
import net.oskarstrom.dashloader.model.DashModel;

@DashObject(ColoredBrickStairsModel.class)
public class DashBrickStairsModel  implements DashModel {

    @Serialize(order = 0)
    public int sprite;
    @Serialize(order = 1)
    public int model;

    public DashBrickStairsModel(ColoredBrickStairsModel model, DashRegistry registry) {
        this.sprite = registry.createSpritePointer(model.sprite);
        this.model = registry.createModelPointer(model.model);
    }

    public DashBrickStairsModel(
            @Deserialize("sprite") int sprite,
            @Deserialize("model") int model) {
        this.sprite = sprite;
        this.model = model;
    }

    @Override
    public BakedModel toUndash(DashRegistry registry) {
        ColoredBrickStairsModel coloredBasicModel = new ColoredBrickStairsModel();
        coloredBasicModel.model = registry.getModel(model);
        coloredBasicModel.sprite = registry.getSprite(sprite);
        return coloredBasicModel;
    }

    @Override
    public int getStage() {
        return 3;
    }
}
