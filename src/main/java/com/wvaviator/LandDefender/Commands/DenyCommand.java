package com.wvaviator.LandDefender.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.LandDefender;
import com.wvaviator.LandDefender.ChunkPermissions.PermManager;
import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Reference.Chat;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class DenyCommand implements ICommand {
	
	private List aliases;
	public DenyCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("deny");
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "deny";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/deny <block>";
	}

	@Override
	public List getAliases() {
		// TODO Auto-generated method stub
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
		
		if (args.length == 0 || args.length > 1) {
			getCommandUsage(sender);
			return;
		}
		
		String allowed = args[0];
		
		Chunk chunk = player.getEntityWorld().getChunkFromBlockCoords(player.getPosition());
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		
		
		try {
			if (ChunkData.isChunkOwned(chunkX, chunkZ) == false) {
				Chat.toChat(player, Chat.doNotOwn);
				return;
			}
			
			if (ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ) == false && (!(sender.canUseCommand(LDConfiguration.useProtectPerm, "protect")))) {
				Chat.toChat(player, Chat.doNotOwn);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			
			PermManager.setPerm(player, allowed, false, chunkX, chunkZ);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,
			BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
