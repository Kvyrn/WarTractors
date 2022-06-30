package io.github.treesoid.wartractors.client.renderer.entity;

import io.github.treesoid.wartractors.entities.TractorEntity;
import io.github.treesoid.wartractors.util.WheelSide;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

public class TractorRenderer extends EntityRenderer<TractorEntity> {
    public TractorRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    //TODO: weapon rendering
    @Override
    public void render(TractorEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, 1.9f, 0);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - yaw));
        entity.getChassis().render(entity, this.dispatcher, yaw, tickDelta, matrices, vertexConsumers, light);
        for (WheelSide wheelSide : WheelSide.values()) {
            matrices.push();
            entity.getChassis().translateForWheel(matrices, wheelSide);
            entity.getWheel().render(entity, wheelSide, this.dispatcher, yaw, tickDelta, matrices, vertexConsumers, light);
            matrices.pop();
        }
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(TractorEntity entity) {
        return null;
    }
}
