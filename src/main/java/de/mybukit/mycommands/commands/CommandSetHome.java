package de.mybukit.mycommands.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.HomePoint;
import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommandSetHome {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("sethome");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
		}).executes(context -> execut(context))

		.then(CommandManager.argument("HomeName", StringArgumentType.word()).
				executes(context -> execute(context)));

		dispatcher.register(literal);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		String args = StringArgumentType.getString(context, "HomeName").toString();
		ServerPlayerEntity player = context.getSource().getPlayer();
		int homes=HomePoint.getHomecounts(player);	
		if (homes<5){
			HomePoint home = HomePoint.getHome(player, args);
			if(home ==null){
				HomePoint.setHome(player, args);
				player.addChatMessage(new TranslatableComponent("commands.sethome.done",HomePoint.getHome(player, args).homename).setStyle(MyStyle.Green), false);

			}else{
				player.addChatMessage(new TranslatableComponent("commands.sethome.failure",args).setStyle(MyStyle.Red), false);	

				player.addChatMessage(new TranslatableComponent("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);	
			}
		}else{
			player.addChatMessage(new TranslatableComponent("commands.sethome.maximum").setStyle(MyStyle.Red), false);
			player.addChatMessage(new TranslatableComponent("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);	

		}
		return 1;
	}

	public static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();
		int homes=HomePoint.getHomecounts(player);	
		if (homes<5){
			HomePoint home = HomePoint.getHome(player, "home");
			if(home ==null){
				HomePoint.setHome(player, "home");
				player.addChatMessage(new TranslatableComponent("commands.sethome.done",HomePoint.getHome(player, "home").homename).setStyle(MyStyle.Green), false);

			}else{
				player.addChatMessage(new TranslatableComponent("commands.sethome.failure","home").setStyle(MyStyle.Red), false);			
				player.addChatMessage(new TranslatableComponent("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);	

			}
		}else {
			player.addChatMessage(new TranslatableComponent("commands.sethome.maximum").setStyle(MyStyle.Red), false);
			player.addChatMessage(new TranslatableComponent("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);	
		}
		return 1;
	}
}


