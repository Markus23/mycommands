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


public class CommandRain
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("rain");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
        }).executes(source -> execute(source));
            
            dispatcher.register(literal);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
	     ServerPlayerEntity player =context.getSource().getPlayer();
		  context.getSource().getWorld().getLevelProperties().setClearWeatherTime(0);
	      context.getSource().getWorld().getLevelProperties().setRainTime(6000);
	      //context.getSource().getWorld().getLevelProperties().setThunderTime(int_1);
	      context.getSource().getWorld().getLevelProperties().setRaining(true);
	      context.getSource().getWorld().getLevelProperties().setThundering(false);
		player.addChatMessage(new TranslatableComponent("commands.rain.done").setStyle(MyStyle.Green), false);
		return 1;

	}
}