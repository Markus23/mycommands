package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class CommandRepair {

	

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("repair");
				literal.requires(source ->  { return Permission.hasperm(source, literal);
        }).executes(context -> execute(context));
          
            dispatcher.register(literal);
        	LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("rp").
    				requires(source ->  { return Permission.hasperm(source, literal);
            }).executes(context -> execute(context));
                
                dispatcher.register(literal1);
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();
		ItemStack item = player.getMainHandStack();
		if(item.isDamaged()) {
		item.setDamage(0);
		player.sendMessage(new TranslatableText("commands.repair.done"), false);

		}else {
			player.sendMessage(new TranslatableText("commands.repair.failure"), false);

		}
		return Command.SINGLE_SUCCESS;
	}
}
