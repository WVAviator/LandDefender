package com.wvaviator.LandDefender.WorldManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class WorldsManager {
	
	public static int getDimension(EntityPlayer player) {
		
		World world = player.getEntityWorld();
		int dimension = world.provider.getDimensionId();
		
		return dimension;
		
	}
	
	public static World getWorld(int dimension) {
		
		WorldServer ws = MinecraftServer.getServer().worldServerForDimension(dimension);
		World world = (World)ws;
		
		return world;
	}

	public static int getDimension(World world) {
		int dimension = world.provider.getDimensionId();
		return dimension;
	}

}
