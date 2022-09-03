package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerAbilities;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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
			if (!playerEntity.getAbilities().allowFlying) {
				playerEntity.getAbilities().allowFlying = true;
				playerEntity.sendAbilitiesUpdate();

				playerEntity.sendMessage(Text.translatable("commands.fly.enabled").setStyle(MyStyle.Green), false);

			} else {

				playerEntity.getAbilities().allowFlying = false;
				playerEntity.getAbilities().flying = false;

				playerEntity.sendAbilitiesUpdate();


				playerEntity.sendMessage(Text.translatable("commands.fly.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.sendMessage(Text.translatable("commands.fly.error").setStyle(MyStyle.Red), false);

		}
		
		return 1;
	}
	private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity playerEntity = context.getSource().getPlayer();
		
		 ServerPlayerEntity requestedPlayer = mycomm.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));

		if (playerEntity.interactionManager.getGameMode()==GameMode.SURVIVAL||playerEntity.interactionManager.getGameMode()==GameMode.ADVENTURE) {
			if (!requestedPlayer.getAbilities().allowFlying) {
				requestedPlayer.getAbilities().allowFlying = true;
				requestedPlayer.sendAbilitiesUpdate();

				requestedPlayer.sendMessage(Text.translatable("commands.fly.enabled").setStyle(MyStyle.Green), false);

			} else {

				requestedPlayer.getAbilities().allowFlying = false;
				requestedPlayer.getAbilities().flying = false;

				requestedPlayer.sendAbilitiesUpdate();


				requestedPlayer.sendMessage(Text.translatable("commands.fly.disabled").setStyle(MyStyle.Green), false);
			}
		}else {
			playerEntity.sendMessage(Text.translatable("commands.fly.error").setStyle(MyStyle.Red), false);

		}

		return 1;
	}

}