package io.github.treesoid.wartractors.entities;

import io.github.treesoid.wartractors.WarTractors;
import io.github.treesoid.wartractors.registry.EntityRegistry;
import io.github.treesoid.wartractors.tractors.chassis.BasicChassis;
import io.github.treesoid.wartractors.tractors.wheels.BasicWheel;
import io.github.treesoid.wartractors.util.tractortypes.ITractorChassis;
import io.github.treesoid.wartractors.util.tractortypes.ITractorWeaponType;
import io.github.treesoid.wartractors.util.tractortypes.WeaponMountPoint;
import io.github.treesoid.wartractors.util.tractortypes.ITractorWheel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class TractorEntity extends Entity {
    public static final Identifier SPAWN_PACKET_IDENTIFIER = new Identifier(WarTractors.modid, "tractor_spawn");

    public static final TrackedData<Float> RED = DataTracker.registerData(TractorEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> GREEN = DataTracker.registerData(TractorEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> BLUE = DataTracker.registerData(TractorEntity.class, TrackedDataHandlerRegistry.FLOAT);

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
        this.dataTracker.startTracking(RED, 1f);
        this.dataTracker.startTracking(GREEN, 1f);
        this.dataTracker.startTracking(BLUE, 1f);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        try {
            this.dataTracker.set(RED, tag.getFloat("red"));
            this.dataTracker.set(GREEN, tag.getFloat("green"));
            this.dataTracker.set(BLUE, tag.getFloat("blue"));
            this.chassis = WarTractors.CHASSIS.getOrDefault(new Identifier(tag.getString("chassis")), BasicChassis.INSTANCE);
            this.wheel = WarTractors.WHEELS.getOrDefault(new Identifier(tag.getString("wheel")), BasicWheel.INSTANCE);
            weapons.clear();
            NbtList tags = tag.getList("Weapons", 10);
            for (NbtElement tag1 : tags) {
                NbtCompound compound = (NbtCompound) tag1;
                weapons.put(WeaponMountPoint.fromTag(compound.getCompound("MountPoint")), WarTractors.WEAPONS.get(new Identifier(compound.getString("WeaponType"))));
            }
            weaponInventories.clear();
            tags = tag.getList("Weapons", 10);
            for (NbtElement tag1 : tags) {
                NbtCompound compound = (NbtCompound) tag1;
                weaponInventories.put(WeaponMountPoint.fromTag(compound.getCompound("MountPoint")), ItemStack.fromNbt(compound.getCompound("WeaponInv")));
            }
        } catch (Exception e) {
            WarTractors.LOGGER.warn(this.world.isClient ? "(Client)" : "(Server)" + " Error reading tractor data for entity " + this + "!", e);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        try {
            tag.putFloat("red", dataTracker.get(RED));
            tag.putFloat("green", dataTracker.get(GREEN));
            tag.putFloat("blue", dataTracker.get(BLUE));
            tag.putString("chassis", chassis.getIdentifier().toString());
            tag.putString("wheel", wheel.getIdentifier().toString());
            NbtList NbtList = new NbtList();
            for (WeaponMountPoint mountPoint : weapons.keySet()) {
                NbtCompound tag1 = new NbtCompound();
                tag1.put("MountPoint", mountPoint.toTag());
                tag1.putString("WeaponType", weapons.get(mountPoint).getIdentifier().toString());
                NbtList.add(tag1);
            }
            tag.put("Weapons", NbtList);
            NbtList NbtList1 = new NbtList();
            for (WeaponMountPoint mountPoint : weaponInventories.keySet()) {
                NbtCompound tag1 = new NbtCompound();
                tag1.put("MountPoint", mountPoint.toTag());
                tag1.put("WeaponInv", weaponInventories.get(mountPoint).writeNbt(new NbtCompound()));
                NbtList1.add(tag1);
            }
            tag.put("WeaponInvs", NbtList1);
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

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height;
    }

    protected Vec3d method_30633(Direction.Axis axis, PortalUtil.Rectangle rectangle) {
        return LivingEntity.method_31079(super.method_30633(axis, rectangle));
    }

    @Override
    public boolean collides() {
        return !this.isRemoved();
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

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < 1;
    }

    //TODO: physics
    //  look at BoatEntity#method_7555
    @Override
    public void tick() {
        super.tick();
        if (this.isLogicalSideForUpdatingMovement()) {
            Vec3d prevVelocity = this.getVelocity();
            double y = prevVelocity.y;
            if (!this.hasNoGravity()) {
                y -= 0.03999999910593033D;
            }
            this.setVelocity(prevVelocity.x * 0.9d, y, prevVelocity.z * 0.9d);
            this.move(MovementType.PLAYER, this.getVelocity());
        }
        this.checkBlockCollision();
        this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) return ActionResult.PASS;
        player.startRiding(this);
        return ActionResult.SUCCESS;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return this.getPassengerList().size() < 1;
    }

    public ITractorChassis getChassis() {
        return chassis;
    }

    public ITractorWheel getWheel() {
        return wheel;
    }

    @SuppressWarnings("unused")
    public void color(int colorInt) {
        color(new Color(colorInt));
    }

    public void color(Color color) {
        dataTracker.set(RED, color.getRed() / 255f);
        dataTracker.set(GREEN, color.getGreen() / 255f);
        dataTracker.set(BLUE, color.getBlue() / 255f);
    }

    public float getRed() {
        return dataTracker.get(RED);
    }

    public float getBlue() {
        return dataTracker.get(BLUE);
    }

    public float getGreen() {
        return dataTracker.get(GREEN);
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
