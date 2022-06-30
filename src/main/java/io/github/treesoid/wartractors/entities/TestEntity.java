package io.github.treesoid.wartractors.entities;

import io.github.treesoid.wartractors.WarTractors;
import io.github.treesoid.wartractors.registry.EntityRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class TestEntity extends Entity {
    public static final Identifier SPAWN_PACKET_IDENTIFIER = new Identifier(WarTractors.modid, "test_entity_spawn");

    public TestEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public TestEntity(World world) {
        super(EntityRegistry.TEST_ENTITY, world);
    }

    @Environment(EnvType.CLIENT)
    public TestEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(EntityRegistry.TEST_ENTITY, world);
        WarTractors.LOGGER.error("constructing test entity on client");
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        System.out.println("asd");
        player.startRiding(this, true);
        return ActionResult.SUCCESS;
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

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());
        packet.writeInt(getEntityId());
        packet.writeUuid(getUuid());
        return ServerSidePacketRegistryImpl.INSTANCE.toPacket(SPAWN_PACKET_IDENTIFIER, packet);
    }
}
