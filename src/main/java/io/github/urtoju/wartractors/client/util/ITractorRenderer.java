package io.github.urtoju.wartractors.client.util;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.client.renderer.entity.model.WheelModel;
import io.github.urtoju.wartractors.entities.TractorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public interface ITractorRenderer {
    WheelModel WHEEL_MODEL = new WheelModel();
    Identifier WHEEL_TEXTURE = new Identifier(WarTractors.modid, "textures/entities/tractor/wheel.png");

    void render(EntityRenderDispatcher dispatcher, TractorEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    Identifier getTexure(TractorEntity entity);
}
