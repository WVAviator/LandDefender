package com.wvaviator.LandDefender.Teleportation;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Teleport {
	
	public static void teleportToChunk(EntityPlayerMP player, int chunkX, int chunkZ) {
		
		World world = player.getEntityWorld();

		BlockPos unsafePos = convertChunkToPos(chunkX, chunkZ);
		BlockPos pos = getSafePos(unsafePos, world);
		
		updateLocation(player, pos);
		
	}
	
	private static BlockPos convertChunkToPos(int chunkX, int chunkZ) {

		int x = chunkX*16 + 8;
		int z = chunkZ*16 + 8;
		int y = 70;
		
		BlockPos pos = new BlockPos(x, y, z);
		return pos;
		
	}

	private static void updateLocation(EntityPlayerMP player, BlockPos pos) {
		
		player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		
	}
	
	private static BlockPos getSafePos(BlockPos pos, World world) {
		
		BlockPos newPos = world.getTopSolidOrLiquidBlock(pos);
		
		while (!(world.getBlockState(newPos).getBlock() == Blocks.air)) {
		newPos = newPos.up();
		}
		return newPos;
	}

}
