package com.wvaviator.LandDefender.ChunkPermissions;

import java.sql.SQLException;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Blocks {
	
	public static boolean isAllowedBlock(BlockPos pos, World world) {
	
		Block block = world.getBlockState(pos).getBlock();
		int blockID = block.getIdFromBlock(block);		
		TileEntity te = world.getTileEntity(pos);
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (te instanceof TileEntityChest){
			
			try {
				
				return PermData.canUseChests(chunkX, chunkZ);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
	
	
	
	
	
		return false;
	
	}
}
