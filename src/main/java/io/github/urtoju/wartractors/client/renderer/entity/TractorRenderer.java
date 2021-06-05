package io.github.urtoju.wartractors.client.renderer.entity;

import io.github.urtoju.wartractors.entities.TractorEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TractorRenderer extends EntityRenderer<TractorEntity> {
    public TractorRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TractorEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        entity.getRenderer().render(this.dispatcher, entity, yaw, tickDelta, matrices, vertexConsumers, light);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(TractorEntity entity) {
        return entity.getRenderer().getTexure(entity);
    }
}
