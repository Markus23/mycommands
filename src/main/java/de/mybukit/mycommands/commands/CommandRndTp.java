package de.mybukit.mycommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.mybukit.mycommands.helper.Location;
import de.mybukit.mycommands.helper.Permission;
import de.mybukit.mycommands.helper.Teleport;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.Heightmap;



public class CommandRndTp 
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literal = CommandManager.literal("rndtp");
		literal.requires((source) -> {
			return Permission.hasperm(source, literal);})
		.executes(context -> { return execute(context);
		});

		dispatcher.register(literal);
		
		LiteralArgumentBuilder<ServerCommandSource> literal1 = CommandManager.literal("rtp");
		literal1.requires((source) -> {
			return Permission.hasperm(source, literal);})
		.executes(context -> { return execute(context);
		});

		dispatcher.register(literal1);
		
		LiteralArgumentBuilder<ServerCommandSource> literal2 = CommandManager.literal("wild");
		literal2.requires((source) -> {
			return Permission.hasperm(source, literal);})
		.executes(context -> { return execute(context);
		});

		dispatcher.register(literal2);
		
            
	}

	public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().getPlayer();
		BlockPos pos1 = findBlockPos(player.getEntityWorld()).getPosfrom();
	
		
		
		while (player.isInsideWall()||player.world.getBlockState(pos1).getBlock()==Blocks.WATER||player.world.getBlockState(pos1).getBlock()==Blocks.LAVA) 
		{ 
				pos1 = findBlockPos(player.getEntityWorld()).getPosfrom();
			
			pos1 = player.getEntityWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, pos1);
			
	       
		} 
		
		Teleport.warp(
				player,new Location(pos1,player.getWorld().getRegistryKey().getValue().toString()),false);
		return 1;
		 
	}
	public static Location findBlockPos(World world)
	{
		double dist = 150 + world.random.nextDouble() * (150 - 50);
		double angle = world.random.nextDouble() * Math.PI * 2D;

		int x = MathHelper.floor(Math.cos(angle) * dist);
		int y = 256;
		int z = MathHelper.floor(Math.sin(angle) * dist);



		//TODO: Find a better way to check for biome without loading the chunk
		RegistryEntry<Biome> biome = world.getBiome(new BlockPos(x, y, z));
		if (biome.getKey().get().getValue().toString().contains("ocean"))
		{
			return findBlockPos(world);
		}

		Chunk chunk = world.getChunk(x >> 4, z >> 4);

		while (y > 0)
		{
			y--;

			if (chunk.getBlockState(new BlockPos(x, y, z)).getMaterial() != Material.AIR)
			{
				return new Location(x + 0.5D, y + 2.5D, z + 0.5D, world.getRegistryKey().getValue().toString());
			}
		}

		return findBlockPos(world);
}

}