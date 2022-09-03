package de.mybukit.mycommands.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.HomePoint;
import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


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
			player.sendMessage(Text.translatable("commands.delhome.failure",homePointName).setStyle(MyStyle.Red), false);
			player.sendMessage(Text.translatable("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);

		}
		else
		{
			HomePoint.delHomePoint(player, homePointName);
			player.sendMessage(Text.translatable("commands.delhome.done",homePointName).setStyle(MyStyle.Green), false);

		}
		return 1;
	}


}
