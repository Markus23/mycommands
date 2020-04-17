package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.commands.Command.TeleportRequests;
import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


public class CommandTpa {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("tpa");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).then(CommandManager.argument("target", EntityArgumentType.player()).executes(CommandTpa::execute));
        dispatcher.register(literal);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ServerPlayerEntity requestedPlayer = EntityArgumentType.getPlayer(context, "target");
        ChatMessage chatSP = new ChatMessage(player);
        ChatMessage chatRP = new ChatMessage(requestedPlayer);

        if (requestedPlayer.getUuid() != player.getUuid()) {
            TeleportRequests.add(requestedPlayer.getUuid(), player.getUuid());
//            requestedPlayer.sendMessage(new TranslatableText("commands.tpa.request", (new LiteralText(player.getEntityName()))), false);
//            requestedPlayer.sendMessage(new TranslatableText("commands.tpa.info", false), false);
            chatSP.send("Request sent to " + requestedPlayer.getName());
            chatRP.send("Teleport request from " + player.getName());
            chatRP.send("/tpaccept /tpyes or /tpdeny /tpno");
        } else {
//            requestedPlayer.sendMessage(new TranslatableText("commands.tpa.error", false), false);
            chatRP.send("You can't request yourself!");
        }
        return 1;
    }

}