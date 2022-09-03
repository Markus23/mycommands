package de.mybukit.mycommands.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class CommandDay{



	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("day");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
				}).executes(context -> execute(context));
		dispatcher.register(literal);   

            
	}
	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException{
		ServerWorld world = context.getSource().getWorld();
		ServerPlayerEntity player = context.getSource().getPlayer();
		world.setTimeOfDay(2000);

		player.sendMessage(Text.translatable("commands.day.done").setStyle(MyStyle.Green), false);
		return Command.SINGLE_SUCCESS;
	}
}
