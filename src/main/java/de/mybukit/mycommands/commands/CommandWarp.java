package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.Location;
import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.Teleport;
import de.mybukit.mycommands.helper.WarpPoint;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class CommandWarp  {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("warp");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
			}).then(CommandManager.argument("WarpPointName", StringArgumentType.word())
				
				.executes(context -> execute(context)));
            
		dispatcher.register(literal);
		
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		String par2ArrayOfStr = StringArgumentType.getString(context, "WarpPointName").toString();
		ServerPlayerEntity player = context.getSource().getPlayer();

		
		if ( par2ArrayOfStr == ""||  par2ArrayOfStr ==null)
		{
			player.sendMessage(Text.of("WarpPointNames: " + WarpPoint.getWarpPoints()).copy().setStyle(MyStyle.Aqua),false);

		} 
		else 
		{
			final String warpPointName =  par2ArrayOfStr;
			WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

			if (warpPoint != null)
			{
				Location loc = WarpPoint.getWarpPoint(warpPointName).location;
			Teleport.warp(player, loc, false);
				player.sendMessage(Text.translatable("commands.warp.done",warpPointName).setStyle(MyStyle.Green), false);
			}else{
				player.sendMessage(Text.translatable("commands.warp.failure",warpPointName).setStyle(MyStyle.Red), false);

				player.sendMessage(Text.of("WarpPointNames: "+WarpPoint.getWarpPoints()).copy().setStyle(MyStyle.Aqua), false);

			}

		}
		return 0;
	}
} 

