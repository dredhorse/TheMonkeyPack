package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.CreativeSwitchLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.HashMap;

/**
 * PluginName: TheMonkeyPack
 * Class: CSBlockEvent
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 21:36
 */


public class CSBlockEvent {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	protected CreativeSwitchConfig creativeSwitchConfig;
	protected CreativeSwitchLogger creativeSwitchLogger;
	static CSBlockEvent instance;
	private HashMap<Location, Long> blockList = new HashMap<Location, Long>();


	private CSBlockEvent(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		creativeSwitchConfig = CreativeSwitchConfig.getInstance();
		creativeSwitchLogger = creativeSwitchConfig.getCreativeSwitchLogger();

	}

	public static CSBlockEvent getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new CSBlockEvent(plugin);
		}
		return instance;
	}

	public void csBlockBreakEvent(BlockBreakEvent event) {
		if (event.isCancelled() || !creativeSwitchConfig.isDisableInstantBreak()) {
			return;
		}
		Block block = event.getBlock();
		Material blockType = block.getType();
		creativeSwitchLogger.debug("BlockType: " + blockType);
		if (blockType == Material.LEVER || blockType == Material.WOODEN_DOOR || blockType == Material.IRON_DOOR_BLOCK || blockType == Material.STONE_BUTTON
				|| blockType == Material.TRAP_DOOR || blockType == Material.SIGN || blockType == Material.WALL_SIGN || blockType == Material.FENCE_GATE
				|| blockType == Material.SIGN_POST || blockType == Material.FURNACE || blockType == Material.CHEST || blockType == Material.ENCHANTMENT_TABLE
				|| blockType == Material.CAULDRON || blockType == Material.BED_BLOCK || blockType == Material.MINECART || blockType == Material.POWERED_MINECART
				|| blockType == Material.STORAGE_MINECART || blockType == Material.BOOKSHELF) {
			Location loc = block.getLocation();
			if (!blockList.containsKey(loc)) {
				// This is the first time
				blockList.put(loc, System.currentTimeMillis());
				event.setCancelled(true);
				return;
			}
			// now let's check if there is some time gone before first hit and second
			long timenow = System.currentTimeMillis();
			long timethan = blockList.get(loc);
			if (timenow - timethan > 1500) {
				// if bigger than 1500 millisecs let's break it
				blockList.remove(loc);
				// on the other side... if it is too long ago... don't break it
				if (timenow - timethan > 4000) {
					event.setCancelled(true);
				}
			} else {
				// nah... we don't break it yet
				event.setCancelled(true);
			}
		}


		Player player = event.getPlayer();
	}


}

