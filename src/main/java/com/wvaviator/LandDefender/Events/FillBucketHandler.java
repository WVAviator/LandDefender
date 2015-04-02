package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.wvaviator.LandDefender.ChunkManager;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FillBucketHandler {
	
	@SubscribeEvent
	public void onBucketUse(FillBucketEvent e) {
		
		if (!(e.entity instanceof EntityPlayerMP)) return;
		
		EntityPlayerMP player = (EntityPlayerMP) e.entity;
		MovingObjectPosition mop = e.target;
	    BlockPos pos = mop.getBlockPos();
	    
	    try {
			if (ChunkManager.canPlayerUse(pos, player)) {
				return;	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    
	    
	    e.setCanceled(true);
		
	}

}
