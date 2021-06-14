package io.github.urtoju.wartractors.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

public interface ITractorChassis {
    int getWeight();
    WeaponMountPoint[] getWeapons();
    int getWidth();
    int getHeight();
    int minWheelSize();
    int maxWheelSize();
    @Environment(EnvType.CLIENT)
    void translateForWheel(MatrixStack matrices, WheelSide wheelSide);
}
