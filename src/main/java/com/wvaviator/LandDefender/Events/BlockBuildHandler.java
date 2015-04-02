package com.wvaviator.LandDefender.Events;

import java.sql.SQLException;

import com.google.common.eventbus.Subscribe;
import com.wvaviator.LandDefender.ChunkManager;
import com.wvaviator.LandDefender.LandDefender;
import com.wvaviator.LandDefender.Reference.Chat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBuildHandler {

	@SubscribeEvent
	public void onBuild(PlayerInteractEvent e) throws SQLException {
		
		EntityPlayerMP player = (EntityPlayerMP) e.entity;
		BlockPos pos = e.pos;
		World world = e.world;
		Block block = world.getBlockState(pos).getBlock();
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityChest) {
		}
		
		if (block == Blocks.crafting_table) {	
		}
		
		
		if (ChunkManager.canPlayerUse(pos, player) == false) {
	
			Chat.toChat(player, Chat.noBuild);
			e.setCanceled(true);

			
		}
		
	}
	
}
