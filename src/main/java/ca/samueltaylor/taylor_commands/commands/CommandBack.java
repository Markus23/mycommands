package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Permission;
import ca.samueltaylor.taylor_commands.helper.Teleport;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class CommandBack {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("back");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandBack::execute);
        dispatcher.register(literal);

    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        if (Teleport.goBack(player)) {
//            player.sendMessage(new TranslatableText("commands.back.done"), false);
            chat.send("Warped to previous location!");
        } else {
//            player.sendMessage(new TranslatableText("commands.back.failure"), false);
            chat.send("You have not warped anywhere!");
        }
        return 1;
    }
}