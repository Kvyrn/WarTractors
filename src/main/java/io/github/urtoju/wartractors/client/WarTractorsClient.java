package io.github.urtoju.wartractors.client;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.client.renderer.entity.TestEntityRenderer;
import io.github.urtoju.wartractors.client.renderer.entity.TractorRenderer;
import io.github.urtoju.wartractors.entities.TestEntity;
import io.github.urtoju.wartractors.entities.TractorEntity;
import io.github.urtoju.wartractors.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

import java.util.UUID;

@Environment(net.fabricmc.api.EnvType.CLIENT)
public class WarTractorsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.TRACTOR, (manager, context) -> new TractorRenderer(manager));
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.TEST_ENTITY, (manager, context) -> new TestEntityRenderer(manager));

        ClientSidePacketRegistryImpl.INSTANCE.register(TestEntity.SPAWN_PACKET_IDENTIFIER, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();
            int id = packet.readInt();
            UUID uuid = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null) return;
                TestEntity entity = new TestEntity(world, x, y, z, id, uuid);
                world.addEntity(id, entity);
            });
        });

        ClientSidePacketRegistryImpl.INSTANCE.register(TractorEntity.SPAWN_PACKET_IDENTIFIER, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();
            int id = packet.readInt();
            UUID uuid = packet.readUuid();
            Identifier chassisId = packet.readIdentifier();
            Identifier wheelId = packet.readIdentifier();

            context.getTaskQueue().execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null) return;
                TractorEntity entity = new TractorEntity(world, x, y, z, id, uuid, WarTractors.CHASSIS.get(chassisId), WarTractors.WHEELS.get(wheelId));
                world.addEntity(id, entity);
            });
        });
    }
}
