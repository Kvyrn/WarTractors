package io.github.urtoju.wartractors;

import io.github.urtoju.wartractors.registry.EntityRegistry;
import net.fabricmc.api.ModInitializer;

public class WarTractors implements ModInitializer {
    public static final String modid = "war-tractors";

    @Override
    public void onInitialize() {
        EntityRegistry.register();
    }
}
