package io.github.treesoid.wartractors.tractors.wheels;

import io.github.treesoid.wartractors.WarTractors;
import io.github.treesoid.wartractors.client.renderer.entity.model.WheelModel;
import io.github.treesoid.wartractors.entities.TractorEntity;
import io.github.treesoid.wartractors.util.WheelSide;
import io.github.treesoid.wartractors.util.tractortypes.ITractorWheel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

public class BasicWheel implements ITractorWheel {
    public static final BasicWheel INSTANCE = new BasicWheel();
    public static final Identifier IDENTIFIER = new Identifier(WarTractors.modid, "basic_wheel");
    @Environment(EnvType.CLIENT)
    public static final WheelModel MODEL = new WheelModel();
    private static final Identifier TEXTURE = new Identifier(WarTractors.modid, "textures/entities/tractor/wheel.png");

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int maxCarryWeight() {
        return 0;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void render(TractorEntity entity, WheelSide side, EntityRenderDispatcher dispatcher, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        if (side.isRight()) {
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        }
        if (side.isBack()) {
            matrices.scale(1f, 1.3f, 1.3f);
        }
        MODEL.render(matrices, vertexConsumers.getBuffer(MODEL.getLayer(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();
    }
}
