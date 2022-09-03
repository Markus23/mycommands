package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;

import net.minecraft.command.argument.EntityArgumentType;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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
			
			if (!playerEntity.getAbilities().invulnerable) {
				playerEntity.getAbilities().invulnerable = true;
				playerEntity.sendAbilitiesUpdate();
				playerEntity.sendMessage(Text.translatable("commands.god.enabled").setStyle(MyStyle.Green), false);

			} else {
				playerEntity.getAbilities().invulnerable = false;
				playerEntity.sendAbilitiesUpdate();
				playerEntity.sendMessage(Text.translatable("commands.god.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.sendMessage(Text.translatable("commands.god.error").setStyle(MyStyle.Red), false);

		}
	
		return 1;
	}
	private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();
		
		 ServerPlayerEntity requestedPlayer = mycomm.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			if (!requestedPlayer.getAbilities().invulnerable) {
				requestedPlayer.getAbilities().invulnerable = true;
				requestedPlayer.sendAbilitiesUpdate();

				requestedPlayer.sendMessage(Text.translatable("commands.god.enabled").setStyle(MyStyle.Green), false);

			} else {

				requestedPlayer.getAbilities().invulnerable = false;
				requestedPlayer.sendAbilitiesUpdate();


				requestedPlayer.sendMessage(Text.translatable("commands.god.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.sendMessage(Text.translatable("commands.god.error").setStyle(MyStyle.Red), false);

		}

		return 1;
	}
}