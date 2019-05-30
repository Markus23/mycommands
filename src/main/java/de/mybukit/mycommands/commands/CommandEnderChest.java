package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.Permission;
import net.minecraft.client.network.ClientDummyContainerProvider;
import net.minecraft.container.GenericContainer;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommandEnderChest
{


	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("enderchest");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
				}).executes(context ->  {
					return execute(context);
				});

		dispatcher.register(literal);
		LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("ec");
		literal1.requires((source) -> {
			return Permission.hasperm(source, literal);
				}).executes(context ->  {
					return execute(context);
				});

		dispatcher.register(literal1);
		

	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();

		EnderChestInventory inv = player.getEnderChestInventory();
		player.openContainer(new ClientDummyContainerProvider((int_1, playerInventory_1, playerEntity_1x) -> {
			return GenericContainer.createGeneric9x3(int_1, playerInventory_1, inv);
		}, new TranslatableComponent("container.enderchest", new Object[0])));

		return 1;
	}
}