package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.Teleport;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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
			player.sendMessage(Text.translatable("commands.back.done").setStyle(MyStyle.Green), false);
			} else
			{
				player.sendMessage(Text.translatable("commands.back.failure").setStyle(MyStyle.Red), false);
		}
		return 1;
	}
}