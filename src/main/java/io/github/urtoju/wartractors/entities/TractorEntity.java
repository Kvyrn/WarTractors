package io.github.urtoju.wartractors.entities;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.registry.EntityRegistry;
import io.github.urtoju.wartractors.tractors.chassis.BasicChassis;
import io.github.urtoju.wartractors.tractors.wheels.BasicWheel;
import io.github.urtoju.wartractors.util.tractortypes.ITractorChassis;
import io.github.urtoju.wartractors.util.tractortypes.ITractorWeaponType;
import io.github.urtoju.wartractors.util.tractortypes.ITractorWheel;
import io.github.urtoju.wartractors.util.tractortypes.WeaponMountPoint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class TractorEntity extends Entity {
    public static final Identifier SPAWN_PACKET_IDENTIFIER = new Identifier(WarTractors.modid, "tractor_spawn");

    private ITractorChassis chassis;
    private ITractorWheel wheel;
    private final HashMap<WeaponMountPoint, ITractorWeaponType> weapons = new HashMap<>();
    private final HashMap<WeaponMountPoint, ItemStack> weaponInventories = new HashMap<>();

    public TractorEntity(EntityType<?> type, World world) {
        super(type, world);
        this.chassis = BasicChassis.INSTANCE;
        this.wheel = BasicWheel.INSTANCE;
    }

    public TractorEntity(World world, @NotNull ITractorChassis chassis, @NotNull ITractorWheel wheel) {
        super(EntityRegistry.TRACTOR, world);
        this.chassis = chassis;
        this.wheel = wheel;
    }

    @SuppressWarnings("unused")
    public TractorEntity(World world) {
        this(world, BasicChassis.INSTANCE, BasicWheel.INSTANCE);
    }

    @Environment(EnvType.CLIENT)
    public TractorEntity(World world, double x, double y, double z, int id, UUID uuid, ITractorChassis chassis, ITractorWheel wheel) {
        this(world, chassis, wheel);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        try {
            System.out.println(tag.getString("chassis"));
            System.out.println(tag.getString("wheel"));
            this.chassis = WarTractors.CHASSIS.getOrDefault(new Identifier(tag.getString("chassis")), BasicChassis.INSTANCE);
            this.wheel = WarTractors.WHEELS.getOrDefault(new Identifier(tag.getString("wheel")), BasicWheel.INSTANCE);
            weapons.clear();
            ListTag tags = tag.getList("Weapons", 10);
            for (Tag tag1 : tags) {
                CompoundTag compound = (CompoundTag) tag1;
                weapons.put(WeaponMountPoint.fromTag(compound.getCompound("MountPoint")), WarTractors.WEAPONS.get(new Identifier(compound.getString("WeaponType"))));
            }
            weaponInventories.clear();
            tags = tag.getList("Weapons", 10);
            for (Tag tag1 : tags) {
                CompoundTag compound = (CompoundTag) tag1;
                weaponInventories.put(WeaponMountPoint.fromTag(compound.getCompound("MountPoint")), ItemStack.fromTag(compound.getCompound("WeaponInv")));
            }
        } catch (Exception e) {
            WarTractors.LOGGER.warn(this.world.isClient ? "(Client)" : "(Server)" + " Error reading tractor data for entity " + this + "!", e);
        }
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        try {
            tag.putString("chassis", chassis.getIdentifier().toString());
            tag.putString("wheel", wheel.getIdentifier().toString());
            ListTag listTag = new ListTag();
            for (WeaponMountPoint mountPoint : weapons.keySet()) {
                CompoundTag tag1 = new CompoundTag();
                tag1.put("MountPoint", mountPoint.toTag());
                tag1.putString("WeaponType", weapons.get(mountPoint).getIdentifier().toString());
                listTag.add(tag1);
            }
            tag.put("Weapons", listTag);
            ListTag listTag1 = new ListTag();
            for (WeaponMountPoint mountPoint : weaponInventories.keySet()) {
                CompoundTag tag1 = new CompoundTag();
                tag1.put("MountPoint", mountPoint.toTag());
                tag1.put("WeaponInv", weaponInventories.get(mountPoint).toTag(new CompoundTag()));
                listTag1.add(tag1);
            }
            tag.put("WeaponInvs", listTag1);
        } catch (Exception e) {
            WarTractors.LOGGER.warn(this.world.isClient ? "(Client)" : "(Server)" + " Failed saving tractor data for entity " + this + "!", e);
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    //TODO: find right height
    //  kinda done (verify)
    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.6D;
    }

    //TODO: passenger position
    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
    }

    //TODO: physics
    @Override
    public void tick() {
        super.tick();
        if (this.isLogicalSideForUpdatingMovement()) {
            Vec3d prevVelocity = this.getVelocity();
            double y = prevVelocity.y;
            if (!this.hasNoGravity()) {
                y -= 0.03999999910593033D;
            }
            this.setVelocity(prevVelocity.x * 0.2d, y, prevVelocity.z * 0.2d);
            this.move(MovementType.SELF, this.getVelocity());
        }
        this.checkBlockCollision();
        this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
    }

    public ITractorChassis getChassis() {
        return chassis;
    }

    public ITractorWheel getWheel() {
        return wheel;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());
        packet.writeInt(getEntityId());
        packet.writeUuid(getUuid());
        packet.writeIdentifier(chassis == null ? BasicChassis.IDENTIFIER : chassis.getIdentifier());
        packet.writeIdentifier(wheel == null ? BasicWheel.IDENTIFIER :wheel.getIdentifier());
        return ServerSidePacketRegistryImpl.INSTANCE.toPacket(SPAWN_PACKET_IDENTIFIER, packet);
    }
}
