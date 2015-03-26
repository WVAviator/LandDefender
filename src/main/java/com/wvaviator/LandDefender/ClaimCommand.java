package com.wvaviator.LandDefender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class ClaimCommand implements ICommand {
	
	private List aliases;
	public ClaimCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("claim");
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "claim";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/claim";
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
		try {
			
			ChunkManager.claimChunk(player);
			
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
