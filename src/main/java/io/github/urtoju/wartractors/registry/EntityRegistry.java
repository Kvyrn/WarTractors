package io.github.urtoju.wartractors.registry;

import io.github.urtoju.wartractors.WarTractors;
import io.github.urtoju.wartractors.entities.SimpleTractorEntity;
import io.github.urtoju.wartractors.entities.TestEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static final EntityType<TestEntity> TEST_ENTITY = FabricEntityTypeBuilder.<TestEntity>create(SpawnGroup.MISC, TestEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build();
    public static final EntityType<SimpleTractorEntity> SIMPLE_TRACTOR = FabricEntityTypeBuilder.<SimpleTractorEntity>create(SpawnGroup.MISC, SimpleTractorEntity::new).dimensions(EntityDimensions.fixed(2, 2)).build();

    public static void register() {
        registerEntityType("test", TEST_ENTITY);
        registerEntityType("simple_tractor", SIMPLE_TRACTOR);
    }

    private static void registerEntityType(String name, EntityType<?> entityType) {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(WarTractors.modid, name), entityType);
    }
}
