package com.wvaviator.LandDefender;

import java.sql.SQLException;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkManager {

	public static boolean canPlayerUse(BlockPos pos, EntityPlayerMP player) throws SQLException {
		
		World world = player.getEntityWorld();
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ) == false) {
			return true;
		}
		
		if (ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ) == true) {
			return true;
		}
		
		if (ChunkData.doesPlayerShareChunk(player, chunkX, chunkZ) == true) {
			return true;
		}

		return false;

	}
	
	public static void claimChunk(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ) == false) {		
			ChunkData.addChunk(player, chunkX, chunkZ);
			
			Chat.toChat(player, EnumChatFormatting.AQUA + "You claimed the chunk at " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
			
		} else {
			
			Chat.toChat(player, Chat.noClaim);
		}
	
	}
	
	public static void unClaimChunk(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ) == true) {

			String uuid = player.getUniqueID().toString();
			
			if(ChunkData.whichPlayerOwnsChunk(chunkX, chunkZ).equals(uuid)) {
			
			ChunkData.removeChunk(player, chunkX, chunkZ);
			ChunkData.removeAllShared(chunkX, chunkZ);
			Chat.toChat(player, EnumChatFormatting.AQUA + "You unclaimed the chunk at " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
			return;
			}
		}
			
		Chat.toChat(player, Chat.doNotOwn);
			
		
	}
	
	public static void shareChunk(EntityPlayerMP player, String trustee) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ) == true) {
			
			ChunkData.addShared(player, trustee, chunkX, chunkZ);

			
		} else {
			
			Chat.toChat(player, Chat.doNotOwn);
			Chat.toChat(player, Chat.onlyOwner);
			
		}
		
	}
	
	public static void unShareChunk(EntityPlayerMP player, String trustee) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		String uuid = player.getUniqueID().toString();
		
		
		
		if (uuid.equals(ChunkData.whichPlayerOwnsChunk(chunkX, chunkZ))) {
			
			ChunkData.removeShared(player, trustee, chunkX, chunkZ);
			
		} else {
			
			Chat.toChat(player, Chat.doNotOwn);
			
		}
		
	}

	public static void removeAllShared(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		boolean doesOwn = ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ);
		
		if (doesOwn == false) {
			Chat.toChat(player, Chat.doNotOwn);
			Chat.toChat(player, Chat.onlyOwner);
			return;
		}
		
		ChunkData.removeAllShared(chunkX, chunkZ);
		
	}
	
	public static void protectChunk(EntityPlayerMP player, String name) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ) == false) {		
			ChunkData.protectChunk(player, name, chunkX, chunkZ);
			
			Chat.toChat(player, EnumChatFormatting.AQUA + "You claimed the chunk at " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
			
		} else {
			
			if (LandDefender.canOverride == true) {
				Chat.toChat(player, Chat.useUnprotect);
				return;
			}
			
			Chat.toChat(player, Chat.noClaim);
		}
		
	}
	
	public static void unprotectChunk(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ) == false) {			
			Chat.toChat(player, Chat.nobodyOwns);
			return;
		}
		

		Chat.toChat(player, Chat.unprotect + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
		
		ChunkData.unprotectChunk(chunkX, chunkZ);
		
	}
	
}
