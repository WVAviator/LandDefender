package com.wvaviator.LandDefender.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.Teleportation.Teleport;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class TPChunkCommand implements ICommand {
	
	private List aliases;
	public TPChunkCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("tpchunk");
		this.aliases.add("chunktp");
		this.aliases.add("ctp");
		this.aliases.add("tpc");
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "tpchunk";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/tpchunk <x> <z>";
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
		
		if (args.length != 2) {
			getCommandUsage(sender);
			return;
		}
		
		int chunkX;
		int chunkZ;
		
		try {
			chunkX = Integer.parseInt(args[0]);
			chunkZ = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			Chat.toChat(player, Chat.invalidArgs);
			return;
		}
		
		try {
			if (!(player.canUseCommand(LDConfiguration.useProtectPerm, "protect")) &&
					!(ChunkData.doesPlayerOwnChunk(player, chunkX, chunkZ))) {
				Chat.toChat(player, Chat.noTP);
				return;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		Teleport.teleportToChunk(player, chunkX, chunkZ);

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
