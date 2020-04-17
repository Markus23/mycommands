package ca.samueltaylor.taylor_commands.commands;


import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.Permission;
import ca.samueltaylor.taylor_commands.helper.WarpPoint;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.Objects;


public class CommandDelWarp {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("delwarp");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).then(CommandManager.argument("WarpPointName", StringArgumentType.word())
                .executes(CommandDelWarp::execute));
        dispatcher.register(literal);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String args = StringArgumentType.getString(context, "WarpPointName").toString();
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (Objects.equals(args, "")) {

        } else {
            WarpPoint warpPoint = WarpPoint.getWarpPoint(args);
            ChatMessage chat = new ChatMessage(player);

            if (warpPoint == null) {
//                player.sendMessage(new TranslatableText("commands.delwarp.failure", warpPointName), false);
                chat.send("Warp " + args + " does not exist!");
            } else {
                WarpPoint.delWarpPoint(args);
//                player.sendMessage(new TranslatableText("commands.delwarp.done", warpPointName), false);
                chat.send("Warp " + args + " is deleted!");
            }
        }
        return 1;
    }

}
