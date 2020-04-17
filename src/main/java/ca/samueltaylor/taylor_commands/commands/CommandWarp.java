package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.Objects;


public class CommandWarp {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("warp");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).then(CommandManager.argument("WarpPointName", StringArgumentType.word())
                .executes(CommandWarp::execute));
        dispatcher.register(literal);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String par2ArrayOfStr = StringArgumentType.getString(context, "WarpPointName").toString();
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        if (Objects.equals(par2ArrayOfStr, "")) {
//            player.sendMessage(new LiteralText("WarpPointNames: " + WarpPoint.getWarpPoints()), false);
            chat.send("Warp points: " + WarpPoint.getWarpPoints());
        } else {
            WarpPoint warpPoint = WarpPoint.getWarpPoint(par2ArrayOfStr);

            if (warpPoint != null) {
                Location loc = WarpPoint.getWarpPoint(par2ArrayOfStr).location;
                Teleport.warp(player, loc, false);
//                player.sendMessage(new TranslatableText("commands.warp.done", par2ArrayOfStr), false);
                chat.send("Warped to " + par2ArrayOfStr);
            } else {
//                player.sendMessage(new TranslatableText("commands.warp.failure", par2ArrayOfStr), false);
//                player.sendMessage(new LiteralText("WarpPointNames: " + WarpPoint.getWarpPoints()), false);
                chat.send("Warp " + par2ArrayOfStr + " does not exist!");
                chat.send("Warp points: " + WarpPoint.getWarpPoints());
            }

        }
        return 0;
    }
} 

