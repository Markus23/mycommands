package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.commands.Command.TeleportRequests;
import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Location;
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

import java.util.List;


public class CommandTpAccept {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("tpaccept");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandTpAccept::execute);

        dispatcher.register(literal);
        LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("tpyes");
        literal1.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandTpAccept::execute);

        dispatcher.register(literal1);


    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        if (TeleportRequests.pending(player.getUuid())) {
            List<ServerPlayerEntity> playerlist = context.getSource().getMinecraftServer().getPlayerManager().getPlayerList();
            boolean playerFound = false;

            for (int i = 0; i < playerlist.size(); ++i) {
                if (playerlist.get(i).getUuid().equals(TeleportRequests.fromWho((player.getUuid())))) {
                    playerFound = true;
                    ServerPlayerEntity teleporter = playerlist.get(i);
                    ChatMessage chatTP = new ChatMessage(teleporter);
//                    teleporter.sendMessage(new TranslatableText("commands.tpa.gotaccepted"), false);
//                    player.sendMessage(new TranslatableText("commands.tpa.youaccepted"), false);
                    chat.send("You accepted " + teleporter.getName() + "'s request!");
                    chatTP.send("Your request was accepted!");

                    Teleport.warp(teleporter, new Location(player), true);
                }
            }
            if (!playerFound) {
//                player.sendMessage(new TranslatableText("commands.tpa.notonline"), false);
                chat.send("They are no longer online");
            }
            TeleportRequests.remove(player.getUuid());
        } else {
//            player.sendMessage(new TranslatableText("commands.tpa.nonetoaccept"), false);
            chat.send("No longer pending");
        }
        return 1;
    }
}