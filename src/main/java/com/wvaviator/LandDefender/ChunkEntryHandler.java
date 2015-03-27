package com.wvaviator.LandDefender;

import java.sql.SQLException;

import com.google.common.eventbus.Subscribe;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChunkEntryHandler {
	
	@SubscribeEvent
	public void onUpdate(EntityEvent.EnteringChunk e) throws SQLException {
		
		if (!(e.entity instanceof EntityPlayerMP)) {
			return;
		}
		
		EntityPlayerMP player = (EntityPlayerMP) e.entity;
		
		int oldChunkX = e.oldChunkX;
		int oldChunkZ = e.oldChunkZ;
		int chunkX = e.newChunkX;
		int chunkZ = e.newChunkZ;
			
		boolean oldChunkOwned = ChunkData.isChunkOwned(oldChunkX, oldChunkZ);
		boolean newChunkOwned = ChunkData.isChunkOwned(chunkX, chunkZ);
		
		if (oldChunkOwned == false && newChunkOwned == false) {
			return;
		}
		
		if (oldChunkOwned == true && newChunkOwned == true) {
			
			String oldOwner = ChunkData.whichPlayerOwnsChunkByName(oldChunkX, oldChunkZ);
			String newOwner = ChunkData.whichPlayerOwnsChunkByName(chunkX, chunkZ);
			
			if (oldOwner.equals(newOwner)) {
				return;
			}
			
			Chat.toChat(player, Chat.ownerDisplay + newOwner);
			
		}
		
		if (oldChunkOwned == false) {
			String owner = ChunkData.whichPlayerOwnsChunkByName(chunkX, chunkZ);
			Chat.toChat(player, Chat.ownerDisplay + owner);
		} else {
			
			Chat.toChat(player, Chat.unowned);
			
		}
		
		
	}

}
