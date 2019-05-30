package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;


public class CommandFly 
{
	public static PlayerAbilities pla= new PlayerAbilities();
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("fly");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
		}).executes(source -> execute(source))
		.then(CommandManager.argument("target", EntityArgumentType.players()).executes(context -> execut(context)));

		dispatcher.register(literal);
	}

	private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();
	

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			if (!playerEntity.abilities.allowFlying) {
				playerEntity.abilities.allowFlying = true;
				playerEntity.sendAbilitiesUpdate();

				playerEntity.addChatMessage(new TranslatableComponent("commands.fly.enabled").setStyle(MyStyle.Green), false);

			} else {

				playerEntity.abilities.allowFlying = false;
				playerEntity.abilities.flying = false;

				playerEntity.sendAbilitiesUpdate();


				playerEntity.addChatMessage(new TranslatableComponent("commands.fly.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.addChatMessage(new TranslatableComponent("commands.fly.error").setStyle(MyStyle.Red), false);

		}
		
		return 1;
	}
	private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();
		
		 ServerPlayerEntity requestedPlayer = mycomm.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			if (!requestedPlayer.abilities.allowFlying) {
				requestedPlayer.abilities.allowFlying = true;
				requestedPlayer.sendAbilitiesUpdate();

				requestedPlayer.addChatMessage(new TranslatableComponent("commands.fly.enabled").setStyle(MyStyle.Green), false);

			} else {

				requestedPlayer.abilities.allowFlying = false;
				requestedPlayer.abilities.flying = false;

				requestedPlayer.sendAbilitiesUpdate();


				requestedPlayer.addChatMessage(new TranslatableComponent("commands.fly.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.addChatMessage(new TranslatableComponent("commands.fly.error").setStyle(MyStyle.Red), false);

		}

		return 1;
	}

}