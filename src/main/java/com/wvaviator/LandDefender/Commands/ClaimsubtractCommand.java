package com.wvaviator.LandDefender.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wvaviator.LandDefender.Data.PlayerData;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.Reference.UUIDManager;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

public class ClaimsubtractCommand implements ICommand {

	private List aliases;
	public ClaimsubtractCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("claimsubtract");
		this.aliases.add("subtractclaim");
		this.aliases.add("subtractclaims");
		
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "claimsubtract";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/claimsubtract <player> <claims>";
	}

	@Override
	public List getAliases() {
		// TODO Auto-generated method stub
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		
		if (args.length > 2 || args.length < 2) {
			Chat.toChat(sender, Chat.invalidArgs);
			return;
		}
		
		int claims;
		
		try {
			claims = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			Chat.toChat(sender, Chat.invalidArgs);
			return;
		}
		
		if (claims < 0) {
			Chat.toChat(sender, Chat.noNegative);
			return;
		}
		
		String username = args[0];
		
		try {
			if (UUIDManager.getStringUUIDFromName(username) == null) {
				Chat.toChat(sender, Chat.playerNotFound);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int newTotal = 0;
		
		try {
		String uuid = UUIDManager.getStringUUIDFromName(username);
		username = UUIDManager.getUsernameFromStringUUID(uuid);
		
		int current = PlayerData.getAllowedChunks(uuid);
		newTotal = current - claims;
		
		if (newTotal < 0) {
			Chat.toChat(sender, Chat.notEnoughClaims);
			newTotal = 0;
		}
		
		PlayerData.updateAllowedChunks(uuid, newTotal);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Chat.toChat(sender, EnumChatFormatting.AQUA + "Updated allowed claims to " + newTotal + " for " + username + ".");
		

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (sender.canUseCommand(4, "claimset")) {
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
