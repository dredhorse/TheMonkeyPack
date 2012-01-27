package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig.CREATIVE_SWITCH_PERMISSIONS;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.CreativeSwitchLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: GPPlayerEvent
 * User: DonRedhorse
 * Date: 28.12.11
 * Time: 00:21
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class CSPlayerEvent {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	protected CreativeSwitchConfig creativeSwitchConfig;
	protected CreativeSwitchLogger creativeSwitchLogger;
	static CSPlayerEvent instance;


	private CSPlayerEvent(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		creativeSwitchConfig = CreativeSwitchConfig.getInstance();
		creativeSwitchLogger = creativeSwitchConfig.getCreativeSwitchLogger();
	}

	public static CSPlayerEvent getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new CSPlayerEvent(plugin);
		}
		return instance;
	}


	public void getCreativeSwitchLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (CREATIVE_SWITCH_PERMISSIONS.CS.hasPermission(player)) {
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage(creativeSwitchConfig.getMessage(Messages.CREATIVE_MODE_MESSAGE));
		} else {
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(creativeSwitchConfig.getMessage(Messages.SURVIAL_MODE_MESSAGE));
		}

	}


}

