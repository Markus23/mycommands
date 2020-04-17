package ca.samueltaylor.taylor_commands.commands;

import ca.samueltaylor.taylor_commands.helper.Permission;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


public class CommandHeal {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("heal");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandHeal::execute)
                .then(CommandManager.argument("target", EntityArgumentType.players()).executes(CommandHeal::execut));
        dispatcher.register(literal);
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        playerEntity.setHealth(playerEntity.getMaximumHealth());
        playerEntity.getHungerManager().setFoodLevel(20);
        //playerEntity.getHungerManager().setSaturationLevelClient(5F);
        return 1;
    }

    private static int execut(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity requestedPlayer = Command.getPlayer(context.getSource(), EntityArgumentType.getPlayers(context, "target"));
        requestedPlayer.setHealth(requestedPlayer.getMaximumHealth());
        requestedPlayer.getHungerManager().setFoodLevel(20);
        //requestedPlayer.getHungerManager().setSaturationLevelClient(5F);
        return 1;
    }
}