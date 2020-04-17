package ca.samueltaylor.taylor_commands.commands;


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

						.executes(context -> execute(context)));

		dispatcher.register(literal);    
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		String args = StringArgumentType.getString(context, "HomeName").toString();
		ServerPlayerEntity player = context.getSource().getPlayer();

		String homePointName = args;
		HomePoint warpPoint = HomePoint.getHome(player, homePointName);

		if (warpPoint == null)
		{
			player.sendMessage(new TranslatableText("commands.delhome.failure",homePointName), false);
			player.sendMessage(new TranslatableText("commands.home.list", HomePoint.gethomePoints(player)), false);

		}
		else
		{
			HomePoint.delHomePoint(player, homePointName);
			player.sendMessage(new TranslatableText("commands.delhome.done",homePointName), false);

		}
		return 1;
	}


}
