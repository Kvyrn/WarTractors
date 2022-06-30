package io.github.treesoid.wartractors.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.treesoid.wartractors.entities.TractorEntity;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import java.awt.*;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TractorCommand {
    public static final DynamicCommandExceptionType INVALID_COLOR = new DynamicCommandExceptionType(o -> new TranslatableText("war-tractors.command.tractor.invalid-color", o));
    public static final SimpleCommandExceptionType NOT_A_TRATOR = new SimpleCommandExceptionType(new TranslatableText("war-tractors.command.tractor.non-tractor"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tractor").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(literal("summon"))
                .then(literal("modify")
                        .then(literal("color").then(argument("target", EntityArgumentType.entity()).then(argument("color", StringArgumentType.word()).executes(TractorCommand::color)))))
        );
    }

    private static int color(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        Entity entity = EntityArgumentType.getEntity(ctx, "target");
        String rawColor = StringArgumentType.getString(ctx, "color");
        if (!(entity instanceof TractorEntity)) throw NOT_A_TRATOR.create();
        TractorEntity tractorEntity = (TractorEntity) entity;
        Color color;
        try {
            color = Color.decode(rawColor);
        } catch (NumberFormatException e) {
            throw INVALID_COLOR.create(rawColor);
        }
        tractorEntity.color(color);
        return 1;
    }
}
