package io.github.urtoju.wartractors.entities;

import io.github.urtoju.wartractors.WarTractors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class SimpleTractorEntity extends AbstractTractorEntity {
    public static final Identifier SPAWN_PACKET_IDENTIFIER = new Identifier(WarTractors.modid, "simple_tractor_spawn");

    public SimpleTractorEntity(EntityType<?> type, World world) {
        super(world, type);
    }

    public SimpleTractorEntity(World world) {
        super(world);
    }

    @Environment(EnvType.CLIENT)
    public SimpleTractorEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(world, x, y, z, id, uuid);
        WarTractors.LOGGER.error("simple tractor arrived on client");
    }

    @Override
    public Identifier spawnPacketIdentifier() {
        return SPAWN_PACKET_IDENTIFIER;
    }
}
