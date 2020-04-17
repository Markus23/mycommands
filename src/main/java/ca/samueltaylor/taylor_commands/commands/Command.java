package ca.samueltaylor.taylor_commands.commands;

import net.minecraft.block.BlockState;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Command {


    public static class TeleportRequests {
        private static HashMap<UUID, UUID> requests = new HashMap<UUID, UUID>(); //Target, Requester

        public static void add(UUID target, UUID requester) {
            requests.put(target, requester);
        }

        public static boolean pending(UUID target) {
            return requests.containsKey(target);
        }

        public static void remove(UUID target) {
            if (pending(target)) {
                requests.remove(target);
            }
        }

        public static UUID fromWho(UUID target) {
            if (pending(target)) {
                return requests.get(target);
            }
            return null;
        }
    }

    public static BlockPos getTopPos(World world, BlockPos blockPos_1) {
        BlockPos blockPos_2;
        for (blockPos_2 = new BlockPos(blockPos_1.getX(), world.getSeaLevel(), blockPos_1.getZ()); !world.isAir(blockPos_2.up()); blockPos_2 = blockPos_2.up()) {
        }

        return blockPos_2;
    }

    public BlockState getTopNonAirState(World world, BlockPos blockPos_1) {
        BlockPos blockPos_2;
        for (blockPos_2 = new BlockPos(blockPos_1.getX(), world.getSeaLevel(), blockPos_1.getZ()); !world.isAir(blockPos_2.up()); blockPos_2 = blockPos_2.up()) {
        }

        return world.getBlockState(blockPos_2);
    }

    public static ServerPlayerEntity getPlayer(ServerCommandSource serverCommandSource_1, Collection<ServerPlayerEntity> collection_1) {
        Iterator<ServerPlayerEntity> var3 = collection_1.iterator();
        ServerPlayerEntity serverPlayerEntity_1 = null;
        while (var3.hasNext()) {
            serverPlayerEntity_1 = (ServerPlayerEntity) var3.next();
        }


        return serverPlayerEntity_1;
    }

}
