package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;


public class CommandGod
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("god");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
		}).executes(source -> execute(source))
		.then(CommandManager.argument("target", EntityArgumentType.players()).executes(context -> execut(context)));

		dispatcher.register(literal);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			
			if (!playerEntity.abilities.invulnerable) {
				playerEntity.abilities.invulnerable = true;
				playerEntity.sendAbilitiesUpdate();
				playerEntity.addChatMessage(new TranslatableComponent("commands.god.enabled").setStyle(MyStyle.Green), false);

			} else {
				playerEntity.abilities.invulnerable = false;
				playerEntity.sendAbilitiesUpdate();
				playerEntity.addChatMessage(new TranslatableComponent("commands.god.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.addChatMessage(new TranslatableComponent("commands.god.error").setStyle(MyStyle.Red), false);

		}
	
		return 1;
	}
	private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();
		
		 ServerPlayerEntity requestedPlayer = mycomm.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			if (!requestedPlayer.abilities.invulnerable) {
				requestedPlayer.abilities.invulnerable = true;
				requestedPlayer.sendAbilitiesUpdate();

				requestedPlayer.addChatMessage(new TranslatableComponent("commands.god.enabled").setStyle(MyStyle.Green), false);

			} else {

				requestedPlayer.abilities.invulnerable = false;
				requestedPlayer.sendAbilitiesUpdate();


				requestedPlayer.addChatMessage(new TranslatableComponent("commands.god.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.addChatMessage(new TranslatableComponent("commands.god.error").setStyle(MyStyle.Red), false);

		}

		return 1;
	}
}