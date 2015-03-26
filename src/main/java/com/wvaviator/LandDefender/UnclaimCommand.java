package com.wvaviator.LandDefender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class UnclaimCommand implements ICommand {
	
	private List aliases;
	public UnclaimCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("unclaim");
		this.aliases.add("abandon");
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "unclaim";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		
		return "/unclaim";
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
			
			ChunkManager.unClaimChunk(player);
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
