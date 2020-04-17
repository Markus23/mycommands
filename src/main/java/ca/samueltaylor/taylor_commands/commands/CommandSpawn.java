package ca.samueltaylor.taylor_commands.commands;

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
import net.minecraft.world.World;


public class CommandSpawn {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("spawn");
        literal.requires((source) -> {
            return Permission.hasperm(source, literal);
        }).executes(CommandSpawn::execute);
        dispatcher.register(literal);
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        World world = context.getSource().getWorld();
        int x = world.getLevelProperties().getSpawnX();
        int y = world.getLevelProperties().getSpawnY();
        int z = world.getLevelProperties().getSpawnZ();
        int dim = 0;
        Teleport.warp(player, new Location(x, y, z, dim), true);
        return 1;
    }
}
