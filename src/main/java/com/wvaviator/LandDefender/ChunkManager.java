package com.wvaviator.LandDefender;

import java.sql.SQLException;
import java.util.UUID;

import com.wvaviator.LandDefender.ChunkPermissions.BlockPerms;
import com.wvaviator.LandDefender.ChunkPermissions.PermCheck;
import com.wvaviator.LandDefender.ChunkPermissions.PermModify;
import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.WorldManager.WorldsManager;

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
		int dimension = WorldsManager.getDimension(player);
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == false) {
			return true;
		}
		
		if (ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ, dimension) == true) {
			return true;
		}
		
		if (ChunkData.doesPlayerShareChunk(player, chunkX, chunkZ, dimension) == true) {
			return true;
		}
		
		if (BlockPerms.isAllowedBlock(pos, world) == true) {
			return true;
		}

		return false;

	}
	
	public static void claimChunk(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(player);
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == false) {		
			ChunkData.addChunk(player, chunkX, chunkZ, dimension);
			PermCheck.populateDefaultValues(chunkX, chunkZ, dimension);
			
			Chat.toChat(player, EnumChatFormatting.AQUA + "You claimed the chunk at " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
			
		} else {
			
			Chat.toChat(player, Chat.noClaim);
		}
	
	}
	
	public static void unClaimChunk(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(player);
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == true) {

			String uuid = player.getUniqueID().toString();
			
			if(ChunkData.whichPlayerOwnsChunk(chunkX, chunkZ, dimension).equals(uuid)) {
			
			ChunkData.removeChunk(player, chunkX, chunkZ, dimension);
			ChunkData.removeAllShared(chunkX, chunkZ, dimension);
			PermModify.removePerms(chunkX, chunkZ, dimension);
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
		int dimension = WorldsManager.getDimension(player);
		
		if (ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ, dimension) == true) {
			
			ChunkData.addShared(player, trustee, chunkX, chunkZ, dimension);

			
		} else {
			
			Chat.toChat(player, Chat.doNotOwn);
			Chat.toChat(player, Chat.onlyOwner);
			
		}
		
	}
	
	public static void unShareChunk(EntityPlayerMP player, String trustee) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(player);
		
		String uuid = player.getUniqueID().toString();
		
		
		
		if (uuid.equals(ChunkData.whichPlayerOwnsChunk(chunkX, chunkZ, dimension))) {
			
			ChunkData.removeShared(player, trustee, chunkX, chunkZ, dimension);
			
		} else {
			
			Chat.toChat(player, Chat.doNotOwn);
			
		}
		
	}

	public static void removeAllShared(EntityPlayerMP player) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(player);
		
		boolean doesOwn = ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ, dimension);
		
		if (doesOwn == false) {
			Chat.toChat(player, Chat.doNotOwn);
			Chat.toChat(player, Chat.onlyOwner);
			return;
		}
		
		ChunkData.removeAllShared(chunkX, chunkZ, dimension);
		
	}
	
	public static void protectChunk(EntityPlayerMP player, String name) throws SQLException {
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(player);
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == false) {		
			ChunkData.protectChunk(player, name, chunkX, chunkZ, dimension);
			
			Chat.toChat(player, EnumChatFormatting.AQUA + "You claimed the chunk at " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
			
		} else {
			
			if (LDConfiguration.canOverride == true) {
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
		int dimension = WorldsManager.getDimension(player);
		
		if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == false) {			
			Chat.toChat(player, Chat.nobodyOwns);
			return;
		}
		

		Chat.toChat(player, Chat.unprotect + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
		
		ChunkData.unprotectChunk(chunkX, chunkZ, dimension);
		PermModify.removePerms(chunkX, chunkZ, dimension);
		
	}
	
}
