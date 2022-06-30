package io.github.treesoid.wartractors.util.tractortypes;

import io.github.treesoid.wartractors.entities.TractorEntity;
import io.github.treesoid.wartractors.util.IIdentified;
import io.github.treesoid.wartractors.util.WheelSide;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;

public interface ITractorChassis extends IIdentified {
    int getWeight();
    WeaponMountPoint[] getWeaponMountPoints();
    int getWidth();
    int getHeight();
    int minWheelSize();
    int maxWheelSize();
    @Environment(EnvType.CLIENT)
    void translateForWheel(MatrixStack matrices, WheelSide wheelSide);
    @Environment(EnvType.CLIENT)
    void render(TractorEntity entity, EntityRenderDispatcher dispatcher, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
    WeaponMountPoint[] getMountPoints();
    default boolean verifyMountpoint(WeaponMountPoint mountPoint) {
        return Arrays.asList(getMountPoints()).contains(mountPoint);
    }
}
