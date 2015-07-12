package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.google.common.eventbus.Subscribe;
import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.WorldManager.WorldsManager;

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

		if (!(LDConfiguration.displayInfo) && !(player.canUseCommand(LDConfiguration.useProtectPerm, "protect"))) {
			return;
		}
		
		int oldChunkX = e.oldChunkX;
		int oldChunkZ = e.oldChunkZ;
		
		int chunkX = e.newChunkX;
		int chunkZ = e.newChunkZ;
		
		int dimension = WorldsManager.getDimension(player);
			
		boolean oldChunkOwned = ChunkData.isChunkOwned(oldChunkX, oldChunkZ, dimension);
		boolean newChunkOwned = ChunkData.isChunkOwned(chunkX, chunkZ, dimension);
		
		if (oldChunkOwned == false && newChunkOwned == false) {
			return;
		}
		
		if (oldChunkOwned == true && newChunkOwned == true) {
			
			String oldOwner = ChunkData.whichPlayerOwnsChunkByName(oldChunkX, oldChunkZ, dimension);
			String newOwner = ChunkData.whichPlayerOwnsChunkByName(chunkX, chunkZ, dimension);
			
			if (oldOwner.equals(newOwner)) {
				return;
			}
			
			Chat.toChat(player, Chat.ownerDisplay + newOwner);
			
		}
		
		if (oldChunkOwned == false) {
			String owner = ChunkData.whichPlayerOwnsChunkByName(chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.ownerDisplay + owner);
		} else {
			
			Chat.toChat(player, Chat.unowned);
			
		}
		
		
	}

}
