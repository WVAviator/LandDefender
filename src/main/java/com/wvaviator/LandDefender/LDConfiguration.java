package com.wvaviator.LandDefender;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class LDConfiguration {
	
	public static int allowedChunks;
	public static int useProtectPerm;
	public static boolean canOverride;
	public static String chunkInfoPrefix;
	public static boolean displayInfo;

	public static void setupConfig(Configuration config) {
		
		config.load();
		
		Property totalAllowedClaims = config.get(Configuration.CATEGORY_GENERAL, "Maximum Allowed Claims Per Player", 6);
		allowedChunks = totalAllowedClaims.getInt();
		totalAllowedClaims.comment = "This number will limit the number of chunks they can claim.";
		
		Property useProtect = config.get(Configuration.CATEGORY_GENERAL, "Required Permission Level to use Protect Command", 4);
		useProtectPerm = useProtect.getInt();
		useProtect.comment = "Use \'1\' to allow all players, \'2\' to allow players in creative mode, \'4\' to allow only operators to use /protect";
		
		Property override = config.get(Configuration.CATEGORY_GENERAL, "Using /unprotect Can Override and Remove any Claimed Chunk", true);
		canOverride = override.getBoolean();
		override.comment = "Setting this to false will not permit the /unprotect command from unprotecting any claim, it will only unprotect chunks protected with /protect";
		
		config.addCustomCategoryComment("Information Display", "Change settings regarding what information is displayed to users");
		
		Property infoPrefix = config.get("Information Display", "Prefix displayed before player names when entering chunks", "#");
		chunkInfoPrefix = infoPrefix.getString();
		infoPrefix.comment = "This is the symbol you wish to display to players before their names when entering owned chunks";
		
		Property displayChunkInfo = config.get("Information Display", "Show Messages when Entering Chunks", true);
		displayInfo = displayChunkInfo.getBoolean();
		displayChunkInfo.comment = "Change this to false if you wish to disable player names displaying when entering claimed chunks. Then only admins will be able to see the messages.";
		
		config.save();
		
		
		
	}

}
