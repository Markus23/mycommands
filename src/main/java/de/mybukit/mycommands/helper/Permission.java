package de.mybukit.mycommands.helper;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import de.mybukit.mycommands.MyCommands;
import net.minecraft.server.command.ServerCommandSource;

public class Permission {

	public static boolean hasperm(ServerCommandSource source,LiteralArgumentBuilder<ServerCommandSource> literal) {
			
		
		if(MyCommands.config.getBoolean(literal.getLiteral())){
			return 	source.hasPermissionLevel(4);

		}else {
			return true;
		}
	}

}
