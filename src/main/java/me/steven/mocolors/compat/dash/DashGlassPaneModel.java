package me.steven.mocolors.compat.dash;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import me.steven.mocolors.blocks.models.ColoredBasicModel;
import me.steven.mocolors.blocks.models.ColoredGlassPaneModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.util.Identifier;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.api.annotation.DashObject;
import net.oskarstrom.dashloader.model.DashModel;

@DashObject(ColoredGlassPaneModel.class)
public class DashGlassPaneModel implements DashModel {

    @Serialize(order = 0)
    public int sprite;
    @Serialize(order = 1)
    public int paneNoSide;
    @Serialize(order = 2)
    public int paneNoSideRotY270;
    @Serialize(order = 3)
    public int paneNoSideAlt;
    @Serialize(order = 4)
    public int paneNoSideAltRotY90;
    @Serialize(order = 5)
    public int paneSide;
    @Serialize(order = 6)
    public int paneSideRotY90;
    @Serialize(order = 7)
    public int paneSideAlt;
    @Serialize(order = 8)
    public int paneSideAltRotY90;
    @Serialize(order = 9)
    public int panePost;

    public DashGlassPaneModel(ColoredGlassPaneModel model, DashRegistry registry) {
        this.sprite = registry.createSpritePointer(model.sprite);
        this.paneNoSide = registry.createModelPointer(model.paneNoSide);
        this.paneNoSideRotY270 = registry.createModelPointer(model.paneNoSideRotY270);
        this.paneNoSideAlt = registry.createModelPointer(model.paneNoSideAlt);
        this.paneNoSideAltRotY90 = registry.createModelPointer(model.paneNoSideAltRotY90);
        this.panePost = registry.createModelPointer(model.panePost);
        this.paneSide = registry.createModelPointer(model.paneSide);
        this.paneSideRotY90 = registry.createModelPointer(model.paneSideRotY90);
        this.paneSideAlt = registry.createModelPointer(model.paneSideAlt);
        this.paneSideAltRotY90 = registry.createModelPointer(model.paneSideAltRotY90);
    }

    public DashGlassPaneModel(
            @Deserialize("sprite") int sprite,
            @Deserialize("paneNoSide") int paneNoSide,
            @Deserialize("paneNoSideRotY270") int paneNoSideRotY270,
            @Deserialize("paneNoSideAlt") int paneNoSideAlt,
            @Deserialize("paneNoSideAltRotY90") int paneNoSideAltRotY90,
            @Deserialize("paneSide") int paneSide,
            @Deserialize("paneSideRotY90") int paneSideRotY90,
            @Deserialize("paneSideAlt") int paneSideAlt,
            @Deserialize("paneSideAltRotY90") int paneSideAltRotY90,
            @Deserialize("panePost") int panePost) {
        this.sprite = sprite;
        this.paneNoSide = paneNoSide;
        this.paneNoSideRotY270 = paneNoSideRotY270;
        this.paneNoSideAlt = paneNoSideAlt;
        this.paneNoSideAltRotY90 = paneNoSideAltRotY90;
        this.panePost = panePost;
        this.paneSide = paneSide;
        this.paneSideRotY90 = paneSideRotY90;
        this.paneSideAlt = paneSideAlt;
        this.paneSideAltRotY90 = paneSideAltRotY90;
    }

    @Override
    public BakedModel toUndash(DashRegistry registry) {
        ColoredGlassPaneModel model = new ColoredGlassPaneModel();
        model.sprite = registry.getSprite(sprite);
        model.paneNoSide = registry.getModel(paneNoSide);
        model.paneNoSideRotY270 = registry.getModel(paneNoSideRotY270);
        model.paneNoSideAlt = registry.getModel(paneNoSideAlt);
        model.paneNoSideAltRotY90 = registry.getModel(paneNoSideAltRotY90);
        model.panePost = registry.getModel(panePost);
        model.paneSide = registry.getModel(paneSide);
        model.paneSideRotY90 = registry.getModel(paneSideRotY90);
        model.paneSideAlt = registry.getModel(paneSideAlt);
        model.paneSideAltRotY90 = registry.getModel(paneSideAltRotY90);
        return model;
    }

    @Override
    public int getStage() {
        return 3;
    }
}
