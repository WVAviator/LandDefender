package com.wvaviator.LandDefender.ChunkPermissions;

import java.sql.SQLException;

import com.wvaviator.LandDefender.WorldManager.WorldsManager;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureStrongholdPieces.Stronghold.Door;

public class BlockPerms {
	
	public static boolean isAllowedBlock(BlockPos pos, World world) throws SQLException {
	
		Block block = world.getBlockState(pos).getBlock();
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int dimension = WorldsManager.getDimension(world);
		
		if (block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.ender_chest){
				return PermCheck.canUseChests(chunkX, chunkZ, dimension);
		}
		
		
		if (block == Blocks.acacia_door || block == Blocks.birch_door || 
				block == Blocks.dark_oak_door || block == Blocks.jungle_door ||
				block == Blocks.iron_door || block == Blocks.spruce_door ||
				block == Blocks.oak_door) {
			
				return PermCheck.canUseDoors(chunkX, chunkZ, dimension);			
		}
	
	
		if (block == Blocks.furnace || block == Blocks.lit_furnace) {		
			return PermCheck.canUseFurnaces(chunkX, chunkZ, dimension);		
		}
		
		if (block == Blocks.brewing_stand) {
			return PermCheck.canUseBrew(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.dispenser) {
			return PermCheck.canUseDispensers(chunkX, chunkZ, dimension);
		}
	
		if (block == Blocks.dropper) {
			return PermCheck.canUseDroppers(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.hopper) {
			return PermCheck.canUseHoppers(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.jukebox) {
			return PermCheck.canUseJukeboxes(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.wall_sign) {
			return PermCheck.canUseSigns(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.acacia_fence_gate || block == Blocks.dark_oak_fence_gate ||
				block == Blocks.oak_fence_gate || block == Blocks.spruce_fence_gate ||
				block == Blocks.jungle_fence_gate || block == Blocks.birch_fence_gate) {
			return PermCheck.canUseGates(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.enchanting_table) {
			return PermCheck.canEnchant(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.anvil) {
			return PermCheck.canUseAnvils(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.wall_banner || block == Blocks.standing_banner) {
			return PermCheck.canUseBanners(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.bed) {
			return PermCheck.canUseBeds(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.trapdoor) {
			return PermCheck.canUseTrapdoors(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.stone_button || block == Blocks.wooden_button) {
			return PermCheck.canUseButtons(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.lever) {
			return PermCheck.canUseLevers(chunkX, chunkZ, dimension);
		}
		
		if (block == Blocks.beacon) {
			return PermCheck.canUseBeacons(chunkX, chunkZ, dimension);
		}
	
		return false;
	
	}
}
