package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.WarpPoint;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


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
				//player.addChatMessage(new TranslatableComponent("commands.setwarp.done", warpPoint ,new Object[0]).setStyle(new Style().setColor(TextFormat.YELLOW)), false);
				player.addChatMessage(new TranslatableComponent("commands.setwarp.done",warpPointName).setStyle(MyStyle.Green), false);

			}else{
				
				//player.addChatMessage(new StringTextComponent(McColor.pink + "Warppoint already exists"), false);
				player.addChatMessage(new TranslatableComponent("commands.setwarp.failure",warpPointName).setStyle(MyStyle.Red), false);

			}
		}
		return 1;
    }
}