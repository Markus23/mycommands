package de.mybukit.mycommands.commands;

import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.commands.mycomm.TeleportRequests;
import de.mybukit.mycommands.helper.Location;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.Teleport;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;



public class CommandTpAccept  {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("tpaccept");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
        }).executes(context -> execute(context));

		dispatcher.register(literal);
		LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("tpyes");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
        }).executes(context -> execute(context));

		dispatcher.register(literal1);
/*		dispatcher.register(CommandManager.literal("tpyes").redirect(literal.build()));
		dispatcher.register(CommandManager.literal("yes").redirect(literal.build()));
		dispatcher.register(CommandManager.literal("y").redirect(literal.build()));
*/
            
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();

		if (TeleportRequests.pending(player.getUuid())) {
			List<ServerPlayerEntity> playerlist = context.getSource().getMinecraftServer().getPlayerManager().getPlayerList();
			Boolean playerFound = false;
			for (int i = 0; i < playerlist.size(); ++ i) {
				if (playerlist.get(i).getUuid().equals(TeleportRequests.fromWho((player.getUuid())))) {
					playerFound = true;
					ServerPlayerEntity teleporter = playerlist.get(i);
					ServerPlayerEntity teleportTo =  player;

					
						teleporter.addChatMessage(new TextComponent("gotaccepted"), false);
						teleportTo.addChatMessage(new TextComponent("youaccepted"), false);
						Teleport.warp(teleporter, new Location(teleportTo), true);
						//teleporter.setPositionAndUpdate(teleportTo.posX, teleportTo.posY, teleportTo.posZ);
					//}
				}
			}
			if (!playerFound) {
				player.addChatMessage(new TextComponent("notonline"), playerFound);
			}
			TeleportRequests.remove(player.getUuid());
		} else {
			player.addChatMessage(new TextComponent("nonetoaccept"), false);
		}
		return 1;
	}
}