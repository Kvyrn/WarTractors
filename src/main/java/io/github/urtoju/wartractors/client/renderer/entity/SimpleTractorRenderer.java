package io.github.urtoju.wartractors.client.renderer.entity;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.client.renderer.entity.model.TractorModel;
import io.github.urtoju.wartractors.client.renderer.entity.model.WheelModel;
import io.github.urtoju.wartractors.entities.SimpleTractorEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

public class SimpleTractorRenderer extends EntityRenderer<SimpleTractorEntity> {
    private static final Identifier TEXTURE = new Identifier(WarTractors.modid, "textures/entities/tractor.png");
    public static final Identifier WHEEL_TEXTURE = new Identifier(WarTractors.modid, "textures/entities/tractor_common/wheel.png");
    private final TractorModel model = new TractorModel();
    private final WheelModel wheel = new WheelModel();

    public SimpleTractorRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SimpleTractorEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, 1.9f, 0);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - yaw));

        matrices.push();
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(getTexture(entity)));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();

        VertexConsumer wheelConsumer = vertexConsumers.getBuffer(wheel.getLayer(WHEEL_TEXTURE));
        //front wheels start
        matrices.push();
        matrices.translate(0, -2.4f, -1.3f);

        //left
        matrices.push();
        matrices.translate(-0.59375f, 0, 0);
        wheel.render(matrices, wheelConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();

        //right
        matrices.push();
        matrices.translate(0.71875f, 0, 0);
        //wheel.wheel.yaw = 3.1f;
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        wheel.render(matrices, wheelConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1,1);
        wheel.wheel.yaw = 0f;
        matrices.pop();

        //front wheels end
        matrices.pop();

        //back wheels start
        matrices.push();
        matrices.translate(0, -2.5f, 0.5f);

        //left
        matrices.push();
        matrices.translate(-0.59375f, 0, 0);
        matrices.scale(1f, 1.3f, 1.3f);
        wheel.render(matrices, wheelConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();

        //right
        matrices.push();
        matrices.translate(0.71875f, 0, 0);
        matrices.scale(1f, 1.3f, 1.3f);
        wheel.render(matrices, wheelConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();

        //back wheels end
        matrices.pop();

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SimpleTractorEntity entity) {
        return TEXTURE;
    }
}
