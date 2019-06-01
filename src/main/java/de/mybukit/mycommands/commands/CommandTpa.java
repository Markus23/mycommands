package de.mybukit.mycommands.commands;

import java.util.Collection;
import java.util.Iterator;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.commands.mycomm.TeleportRequests;
import de.mybukit.mycommands.helper.MyStyle;
import de.mybukit.mycommands.helper.Permission;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;



public class CommandTpa 
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("tpa");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);})
		.then(CommandManager.argument("target", EntityArgumentType.players()).executes(context -> execute(context)));
            
		dispatcher.register(literal);
            
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();
		 ServerPlayerEntity requestedPlayer = getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));
		 	if(requestedPlayer.getUuid() != player.getUuid()) {
				TeleportRequests.add(requestedPlayer.getUuid(), player.getUuid());

				requestedPlayer.addChatMessage(new TranslatableComponent("commands.tpa.request",(new TextComponent(player.getEntityName()).setStyle(MyStyle.Gold))).setStyle(MyStyle.Green), false);

				requestedPlayer.addChatMessage(new TranslatableComponent("commands.tpa.info", false).setStyle(MyStyle.Aqua),false);
				
		 		}else {
					requestedPlayer.addChatMessage(new TranslatableComponent("commands.tpa.error", false).setStyle(MyStyle.Red),false);

		 		}
		 return 1;
	}
	   private static ServerPlayerEntity getPlayer(ServerCommandSource serverCommandSource_1, Collection<ServerPlayerEntity> collection_1) {
		      Iterator<ServerPlayerEntity> var3 = collection_1.iterator();
		      ServerPlayerEntity serverPlayerEntity_1 = null;
		      while(var3.hasNext()) {
		         serverPlayerEntity_1 = (ServerPlayerEntity)var3.next();
		      }

		     
			return serverPlayerEntity_1;
		   }
}