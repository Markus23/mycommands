package ca.samueltaylor.taylor_commands.helper;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;

public class Teleport
{ 
	public static HashMap<ServerPlayerEntity, Location> playerBackMap = new HashMap<ServerPlayerEntity, Location>();

	/**
	 * Send player to location.
	 * @param exact: use doubles when it's true;
	 */
	public static void warp(ServerPlayerEntity player, Location loc, boolean exact)
	{
		if(playerBackMap.containsKey(player)){
			Teleport.playerBackMap.remove(player);
		}
		playerBackMap.put(player, new Location(player));
		
		int dimension = loc.dimension;

		if (dimension != player.dimension.getRawId())
			//MyCommandBase.transDimension(player, loc);
			player.changeDimension(DimensionType.byRawId(dimension));
		if (!exact)
		{
			player.networkHandler.requestTeleport(loc.x + 0.5, loc.y, loc.z + 0.5,player.yaw, player.pitch);
			//player.setPositionAndAngles(loc.x + 0.5, loc.y, loc.z + 0.5,player.pitch,player.yaw);
			//player.setPosition(loc.x + 0.5, loc.y, loc.z + 0.5);

		} else
		{
			player.networkHandler.requestTeleport(loc.posX, loc.posY, loc.posZ,player.yaw,player.pitch);
		}
	}

/*	*//**
	 * @param name: the name of target warp point. player to warp point
	 *//*
	public static void warp(PlayerEntity player, PlayerEntity target)
	{
		if (target.dimension != player.dimension)
			MyCommandBase.transDimension(player, new(target.playerLocation);
		Location loc = new Location(target);
		warp(player, loc, false);
	}*/

	/**
	 * Send player to location he is looking at.
	 * @param player
	 */
	public static void jump(ServerPlayerEntity player)
	{
		Location loc = new Location(player, "jump");
		warp(player, loc, false);
	}

	public static boolean goBack(ServerPlayerEntity player)
	{
		Location loc;
		if (playerBackMap.containsKey(player))
		{
			loc = playerBackMap.get(player);
			warp(player, loc, true);
			return true;
		} 
		else
		{
			return false;
		}
	}
}

