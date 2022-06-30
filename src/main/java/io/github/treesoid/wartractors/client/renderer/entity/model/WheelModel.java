package io.github.treesoid.wartractors.client.renderer.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class WheelModel extends EntityModel<Entity> {
    public final ModelPart wheel;
    public final ModelPart outline;
    public final ModelPart inside;
    public final ModelPart indent;

    public WheelModel() {
        textureWidth = 64;
        textureHeight = 64;
        wheel = new ModelPart(this);
        wheel.setPivot(0.0F, 16.0F, 0.0F);


        outline = new ModelPart(this);
        outline.setPivot(5.5F, 8.0F, 0.0F);
        wheel.addChild(outline);
        outline.setTextureOffset(28, 0).addCuboid(-8.0F, -14.0F, -6.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(16, 16).addCuboid(-8.0F, -1.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);
        outline.setTextureOffset(0, 10).addCuboid(-8.0F, -16.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);
        outline.setTextureOffset(31, 12).addCuboid(-8.0F, -2.0F, 2.0F, 5.0F, 1.0F, 3.0F, 0.0F, false);
        outline.setTextureOffset(12, 31).addCuboid(-8.0F, -15.0F, 2.0F, 5.0F, 1.0F, 3.0F, 0.0F, false);
        outline.setTextureOffset(30, 8).addCuboid(-8.0F, -15.0F, -5.0F, 5.0F, 1.0F, 3.0F, 0.0F, false);
        outline.setTextureOffset(26, 23).addCuboid(-8.0F, -2.0F, -5.0F, 5.0F, 1.0F, 3.0F, 0.0F, false);
        outline.setTextureOffset(36, 42).addCuboid(-8.0F, -6.0F, 6.0F, 5.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(41, 26).addCuboid(-8.0F, -6.0F, -7.0F, 5.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(40, 0).addCuboid(-8.0F, -13.0F, -7.0F, 5.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(12, 42).addCuboid(-8.0F, -13.0F, 6.0F, 5.0F, 3.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(0, 37).addCuboid(-8.0F, -11.0F, 7.0F, 5.0F, 6.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(12, 35).addCuboid(-8.0F, -11.0F, -8.0F, 5.0F, 6.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(39, 21).addCuboid(-8.0F, -10.0F, -4.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(24, 39).addCuboid(-8.0F, -10.0F, 3.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(33, 48).addCuboid(-8.0F, -6.0F, -3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(9, 48).addCuboid(-8.0F, -6.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(47, 36).addCuboid(-8.0F, -11.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(41, 30).addCuboid(-8.0F, -11.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(46, 19).addCuboid(-8.0F, -11.0F, -3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(12, 23).addCuboid(-8.0F, -5.0F, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F, false);
        outline.setTextureOffset(16, 10).addCuboid(-8.0F, -12.0F, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F, false);
        outline.setTextureOffset(44, 12).addCuboid(-8.0F, -4.0F, 5.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(34, 46).addCuboid(-8.0F, -3.0F, 4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(10, 46).addCuboid(-8.0F, -14.0F, 4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(43, 9).addCuboid(-8.0F, -14.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(12, 28).addCuboid(-8.0F, -4.0F, -6.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(46, 46).addCuboid(-8.0F, -3.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        outline.setTextureOffset(43, 16).addCuboid(-8.0F, -14.0F, 5.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);

        inside = new ModelPart(this);
        inside.setPivot(5.5F, 8.0F, 0.0F);
        wheel.addChild(inside);
        inside.setTextureOffset(0, 17).addCuboid(-7.0F, -11.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
        inside.setTextureOffset(44, 4).addCuboid(-7.0F, -10.0F, 2.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        inside.setTextureOffset(24, 44).addCuboid(-7.0F, -10.0F, -3.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        inside.setTextureOffset(3, 1).addCuboid(-7.5F, -9.75F, 0.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        inside.setTextureOffset(3, 3).addCuboid(-7.5F, -9.75F, -1.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        inside.setTextureOffset(0, 2).addCuboid(-7.5F, -7.25F, -1.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        inside.setTextureOffset(0, 0).addCuboid(-7.5F, -7.25F, 0.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        indent = new ModelPart(this);
        indent.setPivot(5.5F, 8.0F, 0.0F);
        wheel.addChild(indent);
        indent.setTextureOffset(16, 0).addCuboid(-7.0F, -4.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
        indent.setTextureOffset(37, 37).addCuboid(-7.0F, -5.0F, -4.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
        indent.setTextureOffset(49, 38).addCuboid(-7.0F, -6.0F, -4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(49, 32).addCuboid(-7.0F, -12.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(0, 49).addCuboid(-7.0F, -12.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(48, 41).addCuboid(-7.0F, -5.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(45, 48).addCuboid(-7.0F, -4.0F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(39, 32).addCuboid(-7.0F, -4.0F, 2.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        indent.setTextureOffset(32, 16).addCuboid(-7.0F, -6.0F, 3.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);
        indent.setTextureOffset(27, 27).addCuboid(-7.0F, -10.0F, 4.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
        indent.setTextureOffset(48, 43).addCuboid(-7.0F, -13.0F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(25, 34).addCuboid(-7.0F, -12.0F, 3.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);
        indent.setTextureOffset(20, 49).addCuboid(-7.0F, -13.0F, -5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(28, 3).addCuboid(-7.0F, -15.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        indent.setTextureOffset(12, 17).addCuboid(-7.0F, -12.0F, -4.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(0, 0).addCuboid(-7.0F, -14.0F, -4.0F, 4.0F, 2.0F, 8.0F, 0.0F, false);
        indent.setTextureOffset(0, 44).addCuboid(-7.0F, -10.0F, -7.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        indent.setTextureOffset(0, 27).addCuboid(-7.0F, -12.0F, -6.0F, 4.0F, 8.0F, 2.0F, 0.0F, false);
        indent.setTextureOffset(9, 50).addCuboid(-7.0F, -4.0F, -5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        wheel.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }
}
