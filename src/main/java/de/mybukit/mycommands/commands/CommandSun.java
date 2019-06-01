package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


public class CommandSun
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("sun");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
        }).executes(context -> execute(context));
         
		dispatcher.register(literal);
            
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
	     ServerPlayerEntity player =context.getSource().getPlayer();

		context.getSource().getWorld().getLevelProperties().setClearWeatherTime(10000);
	      context.getSource().getWorld().getLevelProperties().setRainTime(0);
	      context.getSource().getWorld().getLevelProperties().setRaining(false);
	      context.getSource().getWorld().getLevelProperties().setThundering(false);
		player.addChatMessage(new TranslatableComponent("commands.sun.done").setStyle(MyStyle.Green), false);

		return 1;

	}
}
