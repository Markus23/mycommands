package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.commands.Command.TeleportRequests;
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

import java.util.List;

public class CommandTpDeny {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("tpdeny");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandTpDeny::execute);
        dispatcher.register(literal);

        LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("tpno");
        literal1.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandTpDeny::execute);
        dispatcher.register(literal1);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        if (TeleportRequests.pending(player.getUuid())) {
//            player.sendMessage(new TranslatableText("commands.tpa.youdenied"), false);
            chat.send("You denied the request");

            List<ServerPlayerEntity> playerlist = context.getSource().getMinecraftServer().getPlayerManager().getPlayerList();
            for (int i = 0; i < playerlist.size(); ++i) {
                if (playerlist.get(i).getUuid().equals(TeleportRequests.fromWho(player.getUuid()))) {
                    new ChatMessage(playerlist.get(i)).send("Your request was denied");
                }
            }
            TeleportRequests.remove(player.getUuid());

        } else {
//            player.sendMessage(new TranslatableText("commands.tpa.nonetodeny"), false);
            chat.send("No longer pending");
        }
        return 1;
    }
}