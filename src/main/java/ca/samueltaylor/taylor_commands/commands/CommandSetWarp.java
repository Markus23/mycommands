package ca.samueltaylor.taylor_commands.commands;

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


public class CommandSetWarp   {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("setwarp");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);}).then(CommandManager.argument("WarpPointName", StringArgumentType.word()).
				executes(context -> execute(context)));
         	dispatcher.register(literal);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		String args =StringArgumentType.getString(context, "WarpPointName").toString();
		ServerPlayerEntity player = context.getSource().getPlayer();
		if ( args==null ||  args=="")
		{
			
		} 
		else 
		{
			final String warpPointName =  args;
			WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

			if (warpPoint == null)
			{
				WarpPoint.setWarpPoint(player,warpPointName);
				player.sendMessage(new TranslatableText("commands.setwarp.done",warpPointName), false);

			}else{
				player.sendMessage(new TranslatableText("commands.setwarp.failure",warpPointName), false);

			}
		}
		return 1;
    }
}