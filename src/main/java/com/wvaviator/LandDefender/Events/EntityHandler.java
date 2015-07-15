package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.wvaviator.LandDefender.ChunkManager;
import com.wvaviator.LandDefender.Data.ChunkData;

import net.minecraft.entity.Entity;
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
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {

	@SubscribeEvent
	public void onHitEntity(EntityInteractEvent e){
	
		
		Entity entity = e.entity;
		if (entity == null) return;
		if (!(entity instanceof EntityPlayerMP)) return;
		
		
		Entity target = e.entity;
		if (target == null) return;
		
		if (!(target instanceof EntityItemFrame) || !(target instanceof EntityPainting)
				|| !(target instanceof EntityArmorStand) || !(target instanceof EntityMinecart
				|| !(target instanceof EntityBoat))) {
			return;
		}
		
		EntityPlayerMP player = (EntityPlayerMP) entity;
		BlockPos pos = target.getPosition();
		
		try {
			
			if (!(ChunkManager.canPlayerUse(pos, player))) e.setCanceled(true);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
}
