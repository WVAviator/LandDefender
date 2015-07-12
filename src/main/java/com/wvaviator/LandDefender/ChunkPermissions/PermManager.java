package com.wvaviator.LandDefender.ChunkPermissions;

import java.sql.SQLException;

import com.wvaviator.LandDefender.Reference.Chat;

import net.minecraft.entity.player.EntityPlayerMP;

public class PermManager {

	
	public static void setPerm(EntityPlayerMP player, String perm, boolean value, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		PermCheck.ensureData(chunkX, chunkZ, dimension);	
		
		if (perm.equalsIgnoreCase("chests")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("doors")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("furnaces")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("brewingstands")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("dispensers")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("droppers")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("hoppers")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("jukeboxes")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("signs")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("gates")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("enchantingtables")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("anvils")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("banners")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("beds")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("trapdoors")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("buttons")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("levers")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		if (perm.equalsIgnoreCase("beacons")) {
			String newPerm = perm.toLowerCase();
			PermModify.permUpdate(newPerm, value, chunkX, chunkZ, dimension);
			Chat.toChat(player, Chat.updatedFor + perm.toLowerCase());
			return;
		}
		
		
		
	}
	
}
