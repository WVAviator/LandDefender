package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.wvaviator.LandDefender.Reference.UUIDManager;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerJoinHandler {
	
	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent e) throws SQLException {
		
		if (!(e.entity instanceof EntityPlayerMP)) {
			
			return;
			
		}
		
		EntityPlayerMP player = (EntityPlayerMP) e.entity;
		
		if (UUIDManager.isPlayerInDatabase(player) == true) {
			
			if (UUIDManager.didUsernameChange(player) == true) {
				UUIDManager.updateUsername(player);
			}
			
			return;
			
		}
		
		UUIDManager.addUsername(player);
	}

}
