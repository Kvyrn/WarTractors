package io.github.urtoju.wartractors.client.renderer.entity.model;

import io.github.urtoju.wartractors.entities.TractorEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TractorModel extends EntityModel<TractorEntity> {
    private final ModelPart body;

    public TractorModel() {
        textureWidth = 128;
        textureHeight = 128;
        body = new ModelPart(this);
        body.setPivot(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 0).addCuboid(-7.0F, -16.0F, 0.0F, 16.0F, 13.0F, 27.0F, 0.0F, false);
        body.setTextureOffset(59, 0).addCuboid(-7.0F, -16.0F, -15.0F, 16.0F, 13.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(0, 10).addCuboid(-10.0F, -19.0F, 23.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addCuboid(8.0F, -19.0F, 23.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(49, 40).addCuboid(-7.0F, -6.0F, -13.0F, 16.0F, 3.0F, 13.0F, 0.0F, false);
        body.setTextureOffset(0, 59).addCuboid(-7.0F, -16.0F, -13.0F, 2.0F, 11.0F, 13.0F, 0.0F, false);
        body.setTextureOffset(53, 56).addCuboid(7.0F, -16.0F, -13.0F, 2.0F, 11.0F, 13.0F, 0.0F, false);
        body.setTextureOffset(38, 59).addCuboid(7.0F, -33.0F, -15.0F, 2.0F, 17.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(46, 59).addCuboid(7.0F, -33.0F, 0.0F, 2.0F, 17.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(30, 59).addCuboid(-7.0F, -33.0F, -15.0F, 2.0F, 17.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(20, 0).addCuboid(-7.0F, -33.0F, 0.0F, 2.0F, 17.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(0, 40).addCuboid(-7.0F, -35.0F, -15.0F, 16.0F, 2.0F, 17.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    @Override
    public void setAngles(TractorEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
}
