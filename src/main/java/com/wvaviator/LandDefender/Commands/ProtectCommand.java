package com.wvaviator.LandDefender.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wvaviator.LandDefender.ChunkManager;
import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.LandDefender;
import com.wvaviator.LandDefender.Reference.Chat;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class ProtectCommand implements ICommand {
	
	private List aliases;
	public ProtectCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("protect");
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "protect";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/protect <name>";
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
		
		if (args.length == 0 || args.length > 1) {
			Chat.toChat(sender, Chat.invalidArgs);
			return;
		}
		
		EntityPlayerMP player = (EntityPlayerMP) sender;
		String name = args[0];
		
		if (name.length() > 20) {
			Chat.toChat(sender, Chat.max20);
			return;
		}
		
		try {
			ChunkManager.protectChunk(player, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (sender.canUseCommand(LDConfiguration.useProtectPerm, getName())) {
			return true;
		}
		return false;
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
