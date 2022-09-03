package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.WarpPoint;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


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
				player.sendMessage(Text.translatable("commands.setwarp.done",warpPointName).setStyle(MyStyle.Green), false);

			}else{
				player.sendMessage(Text.translatable("commands.setwarp.failure",warpPointName).setStyle(MyStyle.Red), false);

			}
		}
		return 1;
    }
}