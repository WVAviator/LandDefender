package com.wvaviator.LandDefender.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Data.PlayerData;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.WorldManager.WorldsManager;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.chunk.Chunk;

public class ChunkInfoCommand implements ICommand {

	private List aliases;
	public ChunkInfoCommand(){
		
		this.aliases = new ArrayList();
		this.aliases.add("chunkinfo");
		this.aliases.add("landinfo");
		this.aliases.add("cinfo");
		
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		String name = "chunkinfo";
		return name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		String usage = "/chunkinfo";
		return usage;
	}

	@Override
	public List getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
	
		if (!(sender instanceof EntityPlayerMP)) {
			Chat.toChat(sender, Chat.noConsole);
			return;
		}
		
		EntityPlayerMP player = (EntityPlayerMP) sender;
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(player);
		String name = null;
		
		try {
			if (ChunkData.isChunkOwned(chunkX, chunkZ, dimension) == false) {
				
				Chat.toChat(sender, Chat.nobodyOwns);
				return;	
			}

			name = ChunkData.whichPlayerOwnsChunkByName(chunkX, chunkZ, dimension);
			Chat.toChat(player,  Chat.ownedBy + EnumChatFormatting.GOLD + name);
			
			if (PlayerData.getTotalSharedForChunk(chunkX, chunkZ) > 0) {
			
				Chat.toChat(player, Chat.sharedWith);
				PlayerData.listSharedForChunk(player, chunkX, chunkZ);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,
			BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
