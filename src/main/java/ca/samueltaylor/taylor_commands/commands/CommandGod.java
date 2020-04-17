package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
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
				playerEntity.sendMessage(new TranslatableText("commands.god.enabled"), false);

			} else {
				playerEntity.abilities.invulnerable = false;
				playerEntity.sendAbilitiesUpdate();
				playerEntity.sendMessage(new TranslatableText("commands.god.disabled"), false);
			}
		}else {
			playerEntity.sendMessage(new TranslatableText("commands.god.error"), false);

		}
	
		return 1;
	}
	private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();
		
		 ServerPlayerEntity requestedPlayer = Command.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			if (!requestedPlayer.abilities.invulnerable) {
				requestedPlayer.abilities.invulnerable = true;
				requestedPlayer.sendAbilitiesUpdate();

				requestedPlayer.sendMessage(new TranslatableText("commands.god.enabled"), false);

			} else {

				requestedPlayer.abilities.invulnerable = false;
				requestedPlayer.sendAbilitiesUpdate();


				requestedPlayer.sendMessage(new TranslatableText("commands.god.disabled"), false);
			}
		}else {
			playerEntity.sendMessage(new TranslatableText("commands.god.error"), false);

		}

		return 1;
	}
}