package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.Teleport;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommandBack
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("back");
				literal.requires((source) -> {
					return Permission.hasperm(source, literal);
				}).executes(context -> execute(context));
		dispatcher.register(literal);    
            
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();

		if (Teleport.goBack(player))
		{
			player.addChatMessage(new TranslatableComponent("commands.back.done").setStyle(MyStyle.Green), false);
			
			  //player.addChatMessage(new StringTextComponent(McColor.green + "Warped back to previous location."), false);
			
			} else
			{
			  //player.addChatMessage(new StringTextComponent(McColor.darkRed	+ "You have not warped anywhere!"), false);
				player.addChatMessage(new TranslatableComponent("commands.back.failure").setStyle(MyStyle.Red), false);
		}
		return 1;
	}
}