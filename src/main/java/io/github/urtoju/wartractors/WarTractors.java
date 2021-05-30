package io.github.urtoju.wartractors;

import io.github.urtoju.wartractors.registry.EntityRegistry;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WarTractors implements ModInitializer {
    public static final String modid = "war-tractors";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        EntityRegistry.register();
    }
}
