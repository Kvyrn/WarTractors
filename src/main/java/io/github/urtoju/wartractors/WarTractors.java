package io.github.urtoju.wartractors;

import io.github.urtoju.wartractors.registry.EntityRegistry;
import io.github.urtoju.wartractors.util.ATractorProperties;
import io.github.urtoju.wartractors.util.TestCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class WarTractors implements ModInitializer {
    public static final String modid = "war-tractors";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final HashMap<Identifier, ATractorProperties> TRACTORS = new HashMap<>();

    @Override
    public void onInitialize() {
        EntityRegistry.register();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            TestCommand.register(dispatcher);
        });
    }
}
