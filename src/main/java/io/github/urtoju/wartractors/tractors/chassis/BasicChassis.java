package io.github.urtoju.wartractors.tractors.chassis;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.client.renderer.entity.model.BasicChassisModel;
import io.github.urtoju.wartractors.entities.TractorEntity;
import io.github.urtoju.wartractors.util.WheelSide;
import io.github.urtoju.wartractors.util.tractortypes.ITractorChassis;
import io.github.urtoju.wartractors.util.tractortypes.WeaponMountPoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

public class BasicChassis implements ITractorChassis {
    public static final BasicChassis INSTANCE = new BasicChassis();
    public static final Identifier IDENTIFIER = new Identifier(WarTractors.modid, "basic_chassis");
    @Environment(EnvType.CLIENT)
    public static final BasicChassisModel MODEL = new BasicChassisModel();
    private static final Identifier TEXTURE = new Identifier(WarTractors.modid, "textures/entities/tractor/tractor.png");

    public static final WeaponMountPoint[] MOUNT_POINTS = {
            new WeaponMountPoint(INSTANCE, 0)
    };

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public WeaponMountPoint[] getWeaponMountPoints() {
        return new WeaponMountPoint[0];
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int minWheelSize() {
        return 0;
    }

    @Override
    public int maxWheelSize() {
        return 0;
    }

    @Override
    public void translateForWheel(MatrixStack matrices, WheelSide wheelSide) {
        if (wheelSide.isFront()) {
            matrices.translate(0, -2.4f, -1.3f);
        } else {
            matrices.translate(0, -2.5f, 0.5f);
        }

        if (wheelSide.isLeft()) {
            matrices.translate(-0.59375f, 0, 0);
        } else {
            matrices.translate(0.71875f, 0, 0);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void render(TractorEntity entity, EntityRenderDispatcher dispatcher, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(MODEL.getLayer(TEXTURE));
        MODEL.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();
    }

    @Override
    public WeaponMountPoint[] getMountPoints() {
        return MOUNT_POINTS;
    }
}
