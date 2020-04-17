package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.commands.Command.TeleportRequests;
import ca.samueltaylor.taylor_commands.helper.Location;
import ca.samueltaylor.taylor_commands.helper.Permission;
import ca.samueltaylor.taylor_commands.helper.Teleport;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.List;


public class CommandTpAccept  {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("tpaccept");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);
        }).executes(context -> execute(context));

		dispatcher.register(literal);
		LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("tpyes");
		literal1.requires((source) -> {
			return Permission.hasperm(source, literal);
        }).executes(context -> execute(context));

		dispatcher.register(literal1);

            
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

					
						teleporter.sendMessage(new TranslatableText("commands.tpa.gotaccepted"), false);
						teleportTo.sendMessage(new TranslatableText("commands.tpa.youaccepted"), false);
						Teleport.warp(teleporter, new Location(teleportTo), true);
						//teleporter.setPositionAndUpdate(teleportTo.posX, teleportTo.posY, teleportTo.posZ);
					//}
				}
			}
			if (!playerFound) {
				player.sendMessage(new TranslatableText("commands.tpa.notonline"), false);
			}
			TeleportRequests.remove(player.getUuid());
		} else {
			player.sendMessage(new TranslatableText("commands.tpa.nonetoaccept"), false);
		}
		return 1;
	}
}