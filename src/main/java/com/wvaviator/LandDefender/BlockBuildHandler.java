package com.wvaviator.LandDefender;

import java.sql.SQLException;

import com.google.common.eventbus.Subscribe;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBuildHandler {

	@SubscribeEvent
	public void onBuild(PlayerInteractEvent e) throws SQLException {
		
		//System.out.println("On build event succesfully called");
		EntityPlayerMP player = (EntityPlayerMP) e.entityPlayer;
		BlockPos pos = e.pos;
		
		if (ChunkManager.canPlayerUse(pos, player) == false) {
			
			Chat.toChat(player, Chat.noBuild);
			e.setCanceled(true);
		}
		
	}
	
}
