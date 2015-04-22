package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.wvaviator.LandDefender.ChunkManager;
import com.wvaviator.LandDefender.Data.ChunkData;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {

	@SubscribeEvent
	public void onHitEntity(EntityInteractEvent e){
		
		if (!(e.entityPlayer instanceof EntityPlayerMP)) return;
		if (!(e.target instanceof EntityItemFrame) || !(e.target instanceof EntityPainting)
				|| !(e.target instanceof EntityArmorStand) || !(e.target instanceof EntityMinecart
				|| !(e.target instanceof EntityBoat))) {
			return;
		}
		
		EntityPlayerMP player = (EntityPlayerMP) e.entityPlayer;
		BlockPos pos = player.getPosition();
		
		try {
			
			if (!(ChunkManager.canPlayerUse(pos, player))) e.setCanceled(true);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
}
