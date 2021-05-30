package io.github.urtoju.wartractors.entities;

import io.github.urtoju.wartractors.registry.EntityRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class AbstractTractorEntity extends Entity {
    public AbstractTractorEntity(World world, EntityType<?> type) {
        super(type, world);
    }

    public AbstractTractorEntity(World world) {
        super(EntityRegistry.SIMPLE_TRACTOR, world);
    }

    @Environment(EnvType.CLIENT)
    public AbstractTractorEntity(World world, double x, double y, double z, int id, UUID uuid) {
        this(world);
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
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
    }

    public abstract Identifier spawnPacketIdentifier();

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());
        packet.writeInt(getEntityId());
        packet.writeUuid(getUuid());
        return ServerSidePacketRegistryImpl.INSTANCE.toPacket(spawnPacketIdentifier(), packet);
    }
}
