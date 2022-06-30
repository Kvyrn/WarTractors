package io.github.treesoid.wartractors;

import io.github.treesoid.wartractors.commands.TestCommand;
import io.github.treesoid.wartractors.commands.TractorCommand;
import io.github.treesoid.wartractors.registry.EntityRegistry;
import io.github.treesoid.wartractors.tractors.chassis.BasicChassis;
import io.github.treesoid.wartractors.tractors.wheels.BasicWheel;
import io.github.treesoid.wartractors.util.tractortypes.ITractorChassis;
import io.github.treesoid.wartractors.util.tractortypes.ITractorWeaponType;
import io.github.treesoid.wartractors.util.tractortypes.ITractorWheel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class WarTractors implements ModInitializer {
    public static final String modid = "war-tractors";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final Identifier DEFAULT_WEAPON = null;

    public static final HashMap<Identifier, ITractorChassis> CHASSIS = new HashMap<>();
    public static final HashMap<Identifier, ITractorWheel> WHEELS = new HashMap<>();
    public static final HashMap<Identifier, ITractorWeaponType> WEAPONS = new HashMap<>();

    @Override
    public void onInitialize() {
        EntityRegistry.register();

        registerChassis(BasicChassis.INSTANCE);
        registerWheel(BasicWheel.INSTANCE);

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            TestCommand.register(dispatcher);
            TractorCommand.register(dispatcher);
        });
    }

    public static void registerChassis(ITractorChassis chassis) {
        CHASSIS.put(chassis.getIdentifier(), chassis);
    }

    public static void registerWheel(ITractorWheel wheel) {
        WHEELS.put(wheel.getIdentifier(), wheel);
    }

    public static void registerWeapon(ITractorWeaponType weapon) {
        WEAPONS.put(weapon.getIdentifier(), weapon);
    }
}
