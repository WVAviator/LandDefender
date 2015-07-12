package com.wvaviator.LandDefender.ChunkPermissions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Data.Database;

public class PermCheck {
	
	/* Reference Purposes: Tables are
	 * chests, doors, furnaces, brewingstands,
	 * dispensers, droppers, hoppers, jukeboxes,
	 * signs, gates, enchantingtables, anvils, banners,
	 * beds, trapdoors, buttons, levers, beacons
	 */

	public static boolean canUseChests(int chunkX, int chunkZ, int dimension) throws SQLException {	
		return checkData(chunkX, chunkZ, "chests", dimension);
	}

	public static boolean canUseDoors(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "doors", dimension);
	}
	
	public static boolean canUseFurnaces(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "furnaces", dimension);
	}
	
	public static boolean canUseBrew(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "brewingstands", dimension);
	}
	
	public static boolean canUseDispensers(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "dispensers", dimension);
	}
	
	public static boolean canUseDroppers(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "droppers", dimension);
	}
	
	public static boolean canUseHoppers(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "hoppers", dimension);
	}
	
	public static boolean canUseJukeboxes(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "jukeboxes", dimension);
	}
	
	public static boolean canUseSigns(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "signs", dimension);
	}
	
	public static boolean canUseGates(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "gates", dimension);
	}
	
	public static boolean canEnchant(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "enchantingtables", dimension);
	}
	
	public static boolean canUseAnvils(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "anvils", dimension);
	}
	
	public static boolean canUseBanners(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "banners", dimension);
	}
	
	public static boolean canUseBeds(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "beds", dimension);
	}
	
	public static boolean canUseTrapdoors(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "trapdoors", dimension);
	}
	
	public static boolean canUseButtons(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "buttons", dimension);
	}
	
	public static boolean canUseLevers(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "levers", dimension);
	}
	
	public static boolean canUseBeacons(int chunkX, int chunkZ, int dimension) {
		return checkData(chunkX, chunkZ, "beacons", dimension);
	}
	
	public static void populateDefaultValues(int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String update = "INSERT INTO permissions (chunkx, chunkz, dimension) VALUES (" + chunkX + ", " + chunkZ + ", " + dimension + ")";
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(update);
		} finally {
			stmt.close();
			c.close();
		}
	}
	
	public static boolean checkData(int chunkX, int chunkZ, String column, int dimension) {
		
		String query = "SELECT * FROM permissions WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND dimension = " + dimension;
		Connection c;
		try {
			c = Database.getConnection();
			Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if ((!rs.next()) && (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == true)) {
				populateDefaultValues(chunkX, chunkZ, dimension);
			}

			String result = rs.getString(column);
			
			if (result.equalsIgnoreCase("true")) return true;
			if (result.equalsIgnoreCase("false")) return false;
			
			return false;
			
		} finally {
				stmt.close();
				c.close();
			}
		} catch (SQLException exception){
			exception.printStackTrace();
			return false;

		}
		
	}
	
public static void ensureData(int chunkX, int chunkZ, int dimension) {
		
		String query = "SELECT * FROM permissions WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND dimension = " + dimension;
		Connection c;
		try {
			c = Database.getConnection();
			Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if ((!rs.next()) && (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == true)) {
				populateDefaultValues(chunkX, chunkZ, dimension);
				return;
			}
			
		} finally {
				stmt.close();
				c.close();
			}
		} catch (SQLException exception){
			exception.printStackTrace();
			return;

		}
		
	}
}
