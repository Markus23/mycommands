package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.HomePoint;
import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.Teleport;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


public class CommandHome  {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("home");
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

		HomePoint home = HomePoint.getHome(player,args);
		if (home != null)
		{
			Teleport.warp(player, home.location, false);
			player.addChatMessage(new TranslatableComponent("commands.home.done",home.homename).setStyle(MyStyle.Green), false);
			//player.addChatMessage(new StringTextComponent(McColor.green + "Warped home."), false);
		} else
		{	
			player.addChatMessage(new TranslatableComponent("commands.home.wrong").setStyle(MyStyle.Red), false);
			if(!HomePoint.gethomePoints(player).equals("")){
				player.addChatMessage(new TranslatableComponent("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);	
				//player.addChatMessage(new StringTextComponent(McColor.green + "Your HomePointNames: " + McColor.aqua + HomePoint.gethomePoints(player)), false);
			}else{
				player.addChatMessage(new TranslatableComponent("commands.home.failure").setStyle(MyStyle.Red), false);

				//player.addChatMessage(new StringTextComponent(McColor.darkRed +"You are homeless!"), false);
			}
		}
		return 1;

	}

	public static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();

		HomePoint home = HomePoint.getHome(player,"home");
		if(home!=null){
			Teleport.warp(player, home.location, false);
			player.addChatMessage(new TranslatableComponent("commands.home.done",home.homename).setStyle(MyStyle.Green), false);

		}else{

			if(!HomePoint.gethomePoints(player).equals("")) {
				player.addChatMessage(new TranslatableComponent("commands.home.list",HomePoint.gethomePoints(player)).setStyle(MyStyle.Aqua), false);	
			} else
			{
				player.addChatMessage(new TranslatableComponent("commands.home.failure").setStyle(MyStyle.Red), false);

			}
		}
		return 1;
	}

} 



