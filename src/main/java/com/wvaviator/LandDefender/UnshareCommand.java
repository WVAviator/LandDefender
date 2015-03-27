package com.wvaviator.LandDefender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class UnshareCommand implements ICommand {

	private List aliases;
	public UnshareCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("unshare");
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "unshare";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "unshare <player/all>";
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
			Chat.toChat(sender, Chat.invalidArgs);
			return;
		}
		
		String trustee = args[0];
		
		if (trustee.equalsIgnoreCase("all")) {
			
			
			try {
				
				ChunkManager.removeAllShared(player);
				Chat.toChat(player, Chat.removedAll);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		
		try {
			
			ChunkManager.unShareChunk(player, trustee);
			
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
