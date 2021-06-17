package io.github.urtoju.wartractors.util.tractortypes;

import io.github.urtoju.wartractors.entities.TractorEntity;
import io.github.urtoju.wartractors.util.IIdentified;
import io.github.urtoju.wartractors.util.WheelSide;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;

public interface ITractorWheel extends IIdentified {
    int size();
    int maxCarryWeight();
    @Environment(EnvType.CLIENT)
    void render(TractorEntity entity, WheelSide side, EntityRenderDispatcher dispatcher, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
}
