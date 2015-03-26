package com.wvaviator.LandDefender;

import java.io.File;
import java.sql.SQLException;

import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.google.common.eventbus.Subscribe;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions="*")
public class LandDefender {
	
	public static File directory = null;
	public static String dataDirectory = null;
	public static boolean databaseExists = false;
	public Configuration config;
	
	@EventHandler
	public void onPreInitialization(FMLPreInitializationEvent e) {
		
		this.config = new Configuration(e.getSuggestedConfigurationFile());
		this.config.load();

		directory = new File(e.getModConfigurationDirectory() + "/LandDefender/");
		dataDirectory = directory.getPath() + "/";
		if (!(directory.exists())) {
			createNewDirectory(directory);	
		}
		
		File database = new File(dataDirectory + "chunks.db");
		if (database.exists()) {
			databaseExists = true;
		}
		
	}

	@EventHandler
	public void onInitialization(FMLInitializationEvent e) {
		
		try {
			Database.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (databaseExists == false) {
			
			try {
				Database.establishTables();
				databaseExists = true;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
		//MinecraftForge.EVENT_BUS.register(new ChunkEntryHandler());
		//MinecraftForge.EVENT_BUS.register(new ChunkExitHandler());
		MinecraftForge.EVENT_BUS.register(new BlockBreakHandler());
		MinecraftForge.EVENT_BUS.register(new BlockBuildHandler());
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent e) {
		
		e.registerServerCommand(new ClaimCommand());
		e.registerServerCommand(new UnclaimCommand());
		//e.registerServerCommand(new ShareCommand());
		//e.registerServerCommand(new UnshareCommand());
		e.registerServerCommand(new ChunkInfoCommand());
		
	}
	
	private static void createNewDirectory(File directory) {
		
		directory.mkdir();
		
	}
	
}
