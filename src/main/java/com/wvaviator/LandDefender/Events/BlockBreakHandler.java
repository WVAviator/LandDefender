package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.google.common.eventbus.Subscribe;
import com.wvaviator.LandDefender.ChunkManager;
import com.wvaviator.LandDefender.Reference.Chat;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBreakHandler {

	@SubscribeEvent
	public void breakBlock(BreakEvent e) throws SQLException {
		
		//System.out.println("On break event succesfully called");
		EntityPlayerMP player = (EntityPlayerMP) e.getPlayer();
		BlockPos pos = e.pos;
		
		if (ChunkManager.canPlayerUse(pos, player) == false) {
			
			Chat.toChat(player, Chat.noBuild);
			e.setCanceled(true);
			
		}
		
	}
	
}
