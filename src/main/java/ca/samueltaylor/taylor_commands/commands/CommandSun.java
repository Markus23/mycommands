package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;


public class CommandSun {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("sun");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandSun::execute);
        dispatcher.register(literal);

    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        context.getSource().getWorld().getLevelProperties().setClearWeatherTime(10000);
        context.getSource().getWorld().getLevelProperties().setRainTime(0);
        context.getSource().getWorld().getLevelProperties().setRaining(false);
        context.getSource().getWorld().getLevelProperties().setThundering(false);
//        player.sendMessage(new TranslatableText("commands.sun.done"), false);
        chat.send("It's sunny!");
        return 1;

    }
}
