package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.WorldManager.WorldsManager;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExplosionHandler {

	@SubscribeEvent
	public void onExplode(ExplosionEvent e) {
		Explosion explosion = e.explosion;
		Vec3 sourceLocation = explosion.getPosition();
		double x = sourceLocation.xCoord;
		double y = sourceLocation.yCoord;
		double z = sourceLocation.zCoord;
		World world = e.world;
		
		if (!canExplodeHere(x, y, z, world)) {
			e.setCanceled(true);
		}
		
	}
	
	public static boolean canExplodeHere(double x, double y, double z, World world) {
		
		BlockPos pos = new BlockPos(x, y, z);
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(world);
		
		try {
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX - 1, chunkZ, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX + 1, chunkZ, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ - 1, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ + 1, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX - 1, chunkZ - 1, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX + 1, chunkZ - 1, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX - 1, chunkZ + 1, dimension)) {
			return false;
		}
		
		if (ChunkData.isChunkOwned(chunkX + 1, chunkZ + 1, dimension)) {
			return false;
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
}
