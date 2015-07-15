package com.wvaviator.LandDefender;

import java.io.File;
import java.sql.SQLException;

import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.google.common.eventbus.Subscribe;
import com.wvaviator.LandDefender.Commands.AllowCommand;
import com.wvaviator.LandDefender.Commands.ChunkInfoCommand;
import com.wvaviator.LandDefender.Commands.ClaimCommand;
import com.wvaviator.LandDefender.Commands.ClaimaddCommand;
import com.wvaviator.LandDefender.Commands.ClaimsetCommand;
import com.wvaviator.LandDefender.Commands.ClaimsubtractCommand;
import com.wvaviator.LandDefender.Commands.DenyCommand;
import com.wvaviator.LandDefender.Commands.ListChunksCommand;
import com.wvaviator.LandDefender.Commands.ProtectCommand;
import com.wvaviator.LandDefender.Commands.ShareCommand;
import com.wvaviator.LandDefender.Commands.TPChunkCommand;
import com.wvaviator.LandDefender.Commands.UnclaimCommand;
import com.wvaviator.LandDefender.Commands.UnprotectCommand;
import com.wvaviator.LandDefender.Commands.UnshareCommand;
import com.wvaviator.LandDefender.Data.Database;
import com.wvaviator.LandDefender.Events.BlockBreakHandler;
import com.wvaviator.LandDefender.Events.BlockBuildHandler;
import com.wvaviator.LandDefender.Events.ChunkEntryHandler;
import com.wvaviator.LandDefender.Events.EntityHandler;
import com.wvaviator.LandDefender.Events.ExplosionHandler;
import com.wvaviator.LandDefender.Events.FillBucketHandler;
import com.wvaviator.LandDefender.Events.PlayerJoinHandler;
import com.wvaviator.LandDefender.Reference.Reference;
import com.wvaviator.LandDefender.WorldManager.WorldsManager;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions="*")
public class LandDefender {
	
	public static File directory = null;
	public static String dataDirectory = null;
	public static boolean databaseExists = false;
	public Configuration config;
	public static boolean addedWorldColumn = false;
	public static int primaryWorld;
	
	
	
	@EventHandler
	public void onPreInitialization(FMLPreInitializationEvent e) {
		
		this.config = new Configuration(e.getSuggestedConfigurationFile());
		LDConfiguration.setupConfig(this.config);

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
		
		try {
			Database.databaseUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		MinecraftForge.EVENT_BUS.register(new ChunkEntryHandler());
		MinecraftForge.EVENT_BUS.register(new BlockBreakHandler());
		MinecraftForge.EVENT_BUS.register(new BlockBuildHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerJoinHandler());
		MinecraftForge.EVENT_BUS.register(new FillBucketHandler());
		MinecraftForge.EVENT_BUS.register(new ExplosionHandler());
		//MinecraftForge.EVENT_BUS.register(new EntityHandler());
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent e) {
		
		e.registerServerCommand(new ClaimCommand());
		e.registerServerCommand(new UnclaimCommand());
		e.registerServerCommand(new ShareCommand());
		e.registerServerCommand(new UnshareCommand());
		e.registerServerCommand(new ChunkInfoCommand());
		e.registerServerCommand(new ListChunksCommand());
		e.registerServerCommand(new ProtectCommand());
		e.registerServerCommand(new UnprotectCommand());
		e.registerServerCommand(new AllowCommand());
		e.registerServerCommand(new DenyCommand());
		e.registerServerCommand(new TPChunkCommand());
		e.registerServerCommand(new ClaimsetCommand());
		e.registerServerCommand(new ClaimaddCommand());
		e.registerServerCommand(new ClaimsubtractCommand());
		
		primaryWorld = 0;
		
		if (addedWorldColumn) {
			
			System.out.println("Dimension ID not found in database! Adding columns in Chunks Table and Permissions Table");
			
			try {
				Database.addDimensionColumn();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	private static void createNewDirectory(File directory) {
		
		directory.mkdir();
		
	}
	
}
