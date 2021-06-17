package io.github.urtoju.wartractors.util.tractortypes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class WeaponMountPoint {
    public final Identifier chasis;
    public final int index;

    public WeaponMountPoint(ITractorChassis chasis, int index) {
        this.chasis = chasis.getIdentifier();
        this.index = index;
    }

    private WeaponMountPoint(Identifier chasis, int index) {
        this.chasis = chasis;
        this.index = index;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("identifier", chasis.toString());
        tag.putInt("index", index);
        return tag;
    }

    public static WeaponMountPoint fromTag(CompoundTag tag) {
        return new WeaponMountPoint(new Identifier(tag.getString("identifier")), tag.getInt("index"));
    }
}
