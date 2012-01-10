package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.RARPConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.RARPConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.RARPConfig.RARP_PERMISSIONS;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.RARPLogger;

import java.util.List;

/**
 * PluginName: TheMonkeyPack
 * Class: RARPEvent
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 21:36
 */


public class RARPEvent {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	private RARPConfig rarpConfig;
	private RARPLogger rarpLogger;
	private TNTControlHelper tntControlHelper;
	static RARPEvent instance;


	private RARPEvent(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		rarpConfig = RARPConfig.getInstance();
		rarpLogger = rarpConfig.getRarpLogger();
		// tntControlHelper = tntControlConfig.getTNTControlHelper();
	}

	public static RARPEvent getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new RARPEvent(plugin);
		}
		return instance;
	}

	public void rarpBlockFromTo(BlockFromToEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Block blockFrom = event.getBlock();
		boolean isWater = blockFrom.getTypeId() == 8 || blockFrom.getTypeId() == 9;
		boolean isLava = blockFrom.getTypeId() == 10 || blockFrom.getTypeId() == 11;
		boolean isAir = blockFrom.getTypeId() == 0;
		if (isAir || isLava || isWater) {
			Block blockTo = event.getToBlock();
			if (isRailAndEnabled(blockTo)) {
				event.setCancelled(true);
				return;
			}
			if (isRedStoneAndEnabled(blockTo)) {
				event.setCancelled(true);
			}

		}
	}


	public void rarpPistonExtend(BlockPistonExtendEvent event) {
		rarpLogger.debug("pistonextend", event.getDirection());
		rarpLogger.debug("3 blocks up", event.getBlock().getRelative(0, 2, 0).getType());
		if (event.isCancelled()) {
			return;
		}
		if (redSeaEffectNeeded(event.getBlock().getRelative(0, 2, 0), true)) {
			rarpLogger.debug("placing one more air");
			event.getBlock().getRelative(0, 3, 0).setType(Material.AIR);
		}


	}

	public void rarpBlockPhysics(BlockPhysicsEvent event) {
/*		if (event.isCancelled()){
			return;
		}
		if (rarpConfig.isRailProtection()){
			Material changedType = event.getChangedType();
			rarpLogger.debug("Location", event.getBlock().getLocation());
			rarpLogger.debug("orgType", event.getBlock().getType());
			rarpLogger.debug("changedType", changedType);
			rarpLogger.debug("eventType",event.getType());
			if (event.getType() == Type.BLOCK_PHYSICS && checkIfRail(event.getBlock()) && changedType != Material.RAILS){
				if (rarpConfig.getReAddRailsAtLocation().containsKey(event.getBlock().getLocation())){
					rarpLogger.debug("cancelling blockphysics at " + event.getBlock().getLocation());
					//rarpLogger.debug("reAdding one rail "+ rarpConfig.getNumOfSpawnedRails() + " to go.");
					event.getBlock().setType(Material.AIR);
					event.getBlock().getRelative(0,-1,0).setType(rarpConfig.getReAddRailsAtLocation().get(event.getBlock().getLocation()));
					rarpConfig.removeRailFromReAdd(event.getBlock().getLocation());
					event.setCancelled(true);
					//rarpConfig.removeOneSpawnedRail();

				}

			}*/
		/*if (event.getType() == Type.BLOCK_PHYSICS && changedType==Material.RAILS){
						rarpLogger.debug("Cancelling All Block Physics events");
						rarpLogger.debug("Location",event.getBlock().getLocation());
						event.setCancelled(true);
					}*/
		/*if (changedType == Material.RAILS){

							// Cancelling placing of water
							if (event.getBlock().getType() == Material.STATIONARY_WATER || event.getBlock().getType() == Material.WATER){
								event.setCancelled(true);
								rarpLogger.debug("Changed Rails Cancelled");
							}
						}
						if (changedType == Material.WATER){
							if (event.getBlock().getType() == Material.RAILS){
								event.setCancelled(true);
								rarpLogger.debug("ChangedWater Cancelled");
							}
						}
		}*/


	}


	public void rarpPistonRetract(BlockPistonRetractEvent event) {
		/*if (event.isCancelled()){
			return;
		}
		if (rarpConfig.isRailProtection()){
			Block isRetracted = event.getRetractLocation().getBlock().getRelative(0,1,0);
			if (checkIfRail(isRetracted)){
				rarpConfig.addRailToReAdd(isRetracted.getLocation(), isRetracted.getType());
				rarpConfig.initBlockAtLocation(isRetracted.getLocation());
			}
		}
		rarpLogger.debug("pistonretract",event.getRetractLocation());
		rarpLogger.debug("BlockType",event.getRetractLocation().getBlock().getType());
		rarpLogger.debug("BlockType one up",event.getRetractLocation().getBlock().getRelative(0,1,0).getType());
		rarpLogger.debug("location", event.getRetractLocation().getBlock().getRelative(0,1,0));*/

	}

	public void rarpBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (isRailAndEnabled(event.getBlock()) && !RARP_PERMISSIONS.RARP_RAILS_PLACE.hasPermission(event.getPlayer())) {
			event.getPlayer().sendMessage(Commands.WARNING_MESSAGES + rarpConfig.getMessage(Messages.DONT_HAVE_PERMISSION_TO_PLACE_RAILS));
			event.setCancelled(true);
			return;
		}
		if (isRedStoneAndEnabled(event.getBlock()) && !RARP_PERMISSIONS.RARP_REDSTONE_PLACE.hasPermission(event.getPlayer())) {
			event.getPlayer().sendMessage(Commands.WARNING_MESSAGES + rarpConfig.getMessage(Messages.DONT_HAVE_PERMISSION_TO_PLACE_REDSTONE));
			event.setCancelled(true);
			return;
		}
		if (isRailAndEnabled(event.getBlock())) {
			if (redSeaEffectNeeded(event.getBlock(), true)) {
				event.getBlock().getRelative(0, 1, 0).setType(Material.AIR);
			}
		}

	}

	public void rarpBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (isRailAndEnabled(event.getBlock()) || isRedStoneAndEnabled(event.getBlock())) {
			Player player = event.getPlayer();
			rarpLogger.debug("pistonreaction", event.getBlock().getPistonMoveReaction());
			if (player != null) {
				if (checkIfRedstone(event.getBlock()) && !RARP_PERMISSIONS.RARP_REDSTONE_BREAK.hasPermission(player)) {
					player.sendMessage(Commands.WARNING_MESSAGES + rarpConfig.getMessage(Messages.DONT_HAVE_PERMISSION_TO_BREAK_REDSTONE));
					event.setCancelled(true);
					return;
				}
				if (checkIfRail(event.getBlock()) && !RARP_PERMISSIONS.RARP_RAILS_BREAK.hasPermission(player)) {
					player.sendMessage(Commands.WARNING_MESSAGES + rarpConfig.getMessage(Messages.DONT_HAVE_PERMISSION_TO_BREAK_RAILS));
					event.setCancelled(true);
					return;
				}
			}
			if (player == null) {
				event.setCancelled(true);
			}
			if (checkIfRail(event.getBlock()) && player == null) {
				if (event.getBlock().getPistonMoveReaction() == PistonMoveReaction.BREAK) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	private boolean isRedStoneAndEnabled(Block blockTo) {
		if (rarpConfig.isRedstoneProtection()) {
			if (checkIfRedstone(blockTo)) {
				return true;
			}
		}
		return false;
	}

	private boolean isRailAndEnabled(Block blockTo) {
		if (rarpConfig.isRailProtection()) {
			if (checkIfRail(blockTo)) {
				return true;
			}
			return redSeaEffectNeeded(blockTo, false);
		}
		return false;
	}

	private boolean redSeaEffectNeeded(Block block, boolean up) {
		if (rarpConfig.isRailProtection() && rarpConfig.isEnableRedSeaEffekt()) {
			if (up) {
				Material checkBlockType = block.getRelative(0, 1, 0).getType();
				rarpLogger.debug("RedSeaEffekt One up", checkBlockType);
				if (checkBlockType == Material.WATER || checkBlockType == Material.LAVA || checkBlockType == Material.STATIONARY_LAVA || checkBlockType == Material.STATIONARY_WATER) {
					return true;
				}
			} else {
				if (checkIfRail(block.getRelative(0, -1, 0))) {
					return true;
				}

			}
		}
		return false;
	}

	private boolean checkIfRail(Block checkBlock) {
		Material checkBlockType = checkBlock.getType();
		if (checkRailType(checkBlockType)) {
			return true;
		}
		return false;
	}

	private boolean checkRailType(Material checkBlockType) {
		if (checkBlockType == Material.RAILS || checkBlockType == Material.POWERED_RAIL || checkBlockType == Material.DETECTOR_RAIL) {
			return true;

		}
		return false;
	}

	private boolean checkIfRedstone(Block checkBlock) {
		Material checkBlockType = checkBlock.getType();
		if (checkRedStoneType(checkBlockType)) {
			return true;
		}
		return false;
	}

	private boolean checkRedStoneType(Material checkBlockType) {
		if (checkBlockType == Material.REDSTONE_WIRE || checkBlockType == Material.REDSTONE_TORCH_OFF || checkBlockType == Material.REDSTONE_TORCH_OFF) {
			return true;
		}
		return false;
	}


	public void rarpEntityExplodeEvent(EntityExplodeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (rarpConfig.isDisableTNTCreeperDamage()) {

			List<Block> blocks = event.blockList();
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i) == null) {
					continue;
				}
				if (checkIfRail(blocks.get(i)) || checkIfRedstone(blocks.get(i))) {
					rarpLogger.info("Explosion cancelled at: " + event.getLocation());
					rarpLogger.info("caused by: " + event.getEntity());
					rarpLogger.info("because of DamageProtection!");
					event.setCancelled(true);
					return;
				}
			}
		}
	}


}
