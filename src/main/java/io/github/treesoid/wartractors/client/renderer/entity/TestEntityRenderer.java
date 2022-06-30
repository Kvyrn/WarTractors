package io.github.treesoid.wartractors.client.renderer.entity;

import io.github.treesoid.wartractors.WarTractors;
import io.github.treesoid.wartractors.client.renderer.entity.model.WheelModel;
import io.github.treesoid.wartractors.entities.TestEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TestEntityRenderer extends EntityRenderer<TestEntity> {
    public static final Identifier WHEEL_TEXTURE = new Identifier(WarTractors.modid, "textures/entities/tractor/wheel.png");
    private final WheelModel model = new WheelModel();

    public TestEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TestEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        model.wheel.pitch = MathHelper.lerp(tickDelta, entity.age - 1, entity.age) / 10;
        matrices.translate(0, -0.5f, 0);
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(TestEntity entity) {
        return WHEEL_TEXTURE;
    }
}
