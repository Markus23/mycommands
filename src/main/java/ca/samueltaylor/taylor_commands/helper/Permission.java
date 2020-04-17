package ca.samueltaylor.taylor_commands.helper;

import ca.samueltaylor.taylor_commands.TaylorCommands;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

public class Permission {

	public static boolean hasperm(ServerCommandSource source,LiteralArgumentBuilder<ServerCommandSource> literal) {
			
		
		if(TaylorCommands.config.getBoolean(literal.getLiteral())){
			return 	source.hasPermissionLevel(4);

		}else {
			return true;
		}
	}

}
