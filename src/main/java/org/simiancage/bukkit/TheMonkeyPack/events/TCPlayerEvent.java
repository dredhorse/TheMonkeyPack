package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: TCPlayerEvent
 * User: DonRedhorse
 * Date: 28.12.11
 * Time: 00:21
 */

// contains code from http://forums.bukkit.org/threads/8145/

public class TCPlayerEvent implements Listener {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	protected TNTControlConfig tntControlConfig;
	private TNTControlLogger tntControlLogger;
	static TCPlayerEvent instance;
	private TNTControlHelper tntControlHelper;


	private TCPlayerEvent(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		tntControlConfig = TNTControlConfig.getInstance();
		tntControlLogger = tntControlConfig.getTNTControlLogger();
		tntControlHelper = tntControlConfig.getTNTControlHelper();
	}

	public static TCPlayerEvent getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new TCPlayerEvent(plugin);
		}
		return instance;
	}


	@EventHandler(priority = EventPriority.HIGHEST)
	public void tntControlPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (tntControlHelper.isOnReclaim(player)) {
			tntControlHelper.removePlayerFromReclaim(player);
		}

	}
}

