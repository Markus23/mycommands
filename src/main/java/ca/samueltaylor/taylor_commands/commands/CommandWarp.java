package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.Location;
import ca.samueltaylor.taylor_commands.helper.Permission;
import ca.samueltaylor.taylor_commands.helper.Teleport;
import ca.samueltaylor.taylor_commands.helper.WarpPoint;
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
			player.sendMessage(new LiteralText("WarpPointNames: " + WarpPoint.getWarpPoints()),false);

		} 
		else 
		{
			final String warpPointName =  par2ArrayOfStr;
			WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

			if (warpPoint != null)
			{
				Location loc = WarpPoint.getWarpPoint(warpPointName).location;
			Teleport.warp(player, loc, false);
				player.sendMessage(new TranslatableText("commands.warp.done",warpPointName), false);
			}else{
				player.sendMessage(new TranslatableText("commands.warp.failure",warpPointName), false);

				player.sendMessage(new LiteralText("WarpPointNames: "+ WarpPoint.getWarpPoints()), false);

			}

		}
		return 0;
	}
} 

