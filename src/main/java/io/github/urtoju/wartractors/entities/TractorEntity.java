package io.github.urtoju.wartractors.entities;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.client.WarTractorsClient;
import io.github.urtoju.wartractors.client.util.ITractorRenderer;
import io.github.urtoju.wartractors.registry.EntityRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TractorEntity extends Entity {
    public static final Identifier SPAWN_PACKET_IDENTIFIER = new Identifier(WarTractors.modid, "tractor_spawn");
    public static final Identifier DEFAULT_TYPE = new Identifier(WarTractors.modid, "default");

    @NotNull
    public Identifier type;
    @Environment(EnvType.CLIENT)
    private ITractorRenderer renderer;

    public TractorEntity(EntityType<?> type, World world) {
        super(type, world);
        this.type = DEFAULT_TYPE;
    }

    public TractorEntity(World world, @NotNull Identifier type) {
        super(EntityRegistry.TRACTOR, world);
        this.type = type;
    }

    public TractorEntity(World world) {
        this(world, DEFAULT_TYPE);
    }

    @Environment(EnvType.CLIENT)
    public TractorEntity(World world, double x, double y, double z, int id, UUID uuid, Identifier type) {
        this(world, type);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
        this.renderer = WarTractorsClient.TRACTOR_RENDERERS.get(type);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.type = tag.contains("type") ? new Identifier(tag.getString("type")) : DEFAULT_TYPE;
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putString("type", this.type.toString());
    }

    @Override
    public boolean collidesWith(Entity other) {
        return BoatEntity.method_30959(this, other);
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
    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.6D;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
    }

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
        //this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
    }

    @Environment(EnvType.CLIENT)
    public ITractorRenderer getRenderer() {
        return renderer;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());
        packet.writeInt(getEntityId());
        packet.writeUuid(getUuid());
        packet.writeIdentifier(this.type);
        return ServerSidePacketRegistryImpl.INSTANCE.toPacket(SPAWN_PACKET_IDENTIFIER, packet);
    }
}
