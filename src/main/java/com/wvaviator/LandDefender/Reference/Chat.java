package com.wvaviator.LandDefender.Reference;

import com.wvaviator.LandDefender.LandDefender;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Chat {
	
	public static String noClaim = EnumChatFormatting.AQUA + "You cannot claim this land!";
	public static String doNotOwn = EnumChatFormatting.AQUA + "You do not own this land!";
	public static String playerNotFound = EnumChatFormatting.AQUA + "Player not found!";
	public static String noConsole = "You must be a player to perform this command!";
	public static String nobodyOwns = EnumChatFormatting.AQUA + "Nobody owns this land...";
	public static String ownedBy = EnumChatFormatting.AQUA + "This chunk is owned by: ";
	public static String noBuild = EnumChatFormatting.AQUA + "You cannot do that here!";
	public static String claimedLand = EnumChatFormatting.AQUA + "You have succesfully claimed this land!";
	public static String followingChunks = EnumChatFormatting.AQUA + "The following chunks are owned by " + EnumChatFormatting.GOLD;
	public static String followingSharedChunks = EnumChatFormatting.AQUA + "The following chunks are shared with " + EnumChatFormatting.GOLD;
	public static String tooManyChunks = EnumChatFormatting.AQUA + "You have reached your maximum claim amount!\n" + EnumChatFormatting.AQUA + "You are limited to a maximum of " + EnumChatFormatting.GOLD + LandDefender.allowedChunks + EnumChatFormatting.AQUA + " chunks.";
	public static String ownerDisplay = EnumChatFormatting.AQUA + "# " + EnumChatFormatting.GOLD;
	public static String unowned = EnumChatFormatting.AQUA + "# Unowned";
	public static String sharedWith = EnumChatFormatting.AQUA + "Shared with: ";
	public static String onlyOwner = EnumChatFormatting.AQUA + "Only the chunk owner can add shared players!";
	public static String invalidArgs = EnumChatFormatting.AQUA + "Invalid arguments!";
	public static String addedShared = EnumChatFormatting.AQUA + "Added shared player: " + EnumChatFormatting.GOLD;
	public static String removeShared = EnumChatFormatting.AQUA + "Removed shared player: " + EnumChatFormatting.GOLD;
	public static String removedAll = EnumChatFormatting.AQUA + "Removed all shared players!";
	public static String useUnprotect = EnumChatFormatting.AQUA + "This chunk is already owned! Use /unprotect first!";
	public static String max20 = EnumChatFormatting.AQUA + "You are limited to 20 characters for the name!";
	public static String unprotect = EnumChatFormatting.AQUA + "Removed all protections from chunk " + EnumChatFormatting.GOLD;
	
	
	public static void toChat(EntityPlayerMP player, String message) {
		
		ChatComponentText text = new ChatComponentText(message);
		player.addChatMessage(text);
		
	}
	
	public static void toChat(ICommandSender sender, String message) {
		
		ChatComponentText text = new ChatComponentText(message);
		sender.addChatMessage(text);
		
	}
	
	
}
