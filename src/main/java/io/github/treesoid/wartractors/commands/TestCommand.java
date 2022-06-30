package io.github.treesoid.wartractors.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TestCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("test")
                .then(literal("mount").then(argument("entity", EntityArgumentType.entity()).executes(context -> {
                    Entity entity = EntityArgumentType.getEntity(context, "entity");
                    PlayerEntity player = context.getSource().getPlayer();
                    player.startRiding(entity, true);
                    return 1;
                })))
        );
    }
}
