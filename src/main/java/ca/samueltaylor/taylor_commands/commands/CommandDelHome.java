package ca.samueltaylor.taylor_commands.commands;


import ca.samueltaylor.taylor_commands.helper.ChatMessage;
import ca.samueltaylor.taylor_commands.helper.HomePoint;
import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;


public class CommandDelHome {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("delhome");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).then(CommandManager.argument("HomeName", StringArgumentType.word())
                .executes(CommandDelHome::execute));

        dispatcher.register(literal);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String args = StringArgumentType.getString(context, "HomeName").toString();
        ServerPlayerEntity player = context.getSource().getPlayer();
        HomePoint warpPoint = HomePoint.getHome(player, args);
        ChatMessage chat = new ChatMessage(player);

        if (warpPoint == null) {
//            player.sendMessage(new TranslatableText("commands.delhome.failure", args), false);
//            player.sendMessage(new TranslatableText("commands.home.list", HomePoint.gethomePoints(player)), false);
            chat.send("Home " + args + " does not exsits!");
            chat.send("Your homes: " + HomePoint.gethomePoints(player));

        } else {
            HomePoint.delHomePoint(player, args);
//            player.sendMessage(new TranslatableText("commands.delhome.done", args), false);
            chat.send("Home " + args + " is deleted!");
        }
        return 1;
    }
}
