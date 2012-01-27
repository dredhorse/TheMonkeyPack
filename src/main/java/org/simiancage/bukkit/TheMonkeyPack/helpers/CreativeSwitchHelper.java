package org.simiancage.bukkit.TheMonkeyPack.helpers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig.CREATIVE_SWITCH_PERMISSIONS;
import org.simiancage.bukkit.TheMonkeyPack.configs.CreativeSwitchConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.CreativeSwitchLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTControlHelper
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */


public class CreativeSwitchHelper {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	protected CreativeSwitchConfig creativeSwitchConfig;
	protected CreativeSwitchLogger creativeSwitchLogger;
	protected CreativeSwitchHelper tntControlHelper;
	static CreativeSwitchHelper instance;
	private List<Player> playerIsCreative;


	private CreativeSwitchHelper(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		creativeSwitchConfig = CreativeSwitchConfig.getInstance();
		creativeSwitchLogger = creativeSwitchConfig.getCreativeSwitchLogger();
		playerIsCreative = new ArrayList<Player>();
	}

	public static CreativeSwitchHelper getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new CreativeSwitchHelper(plugin);
		}
		return instance;
	}


	public void onDisable() {

	}

	public void onEnable() {

		main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

			public void run() {
				switchGameMode();
			}
		}, creativeSwitchConfig.getCheckIntervall() * 20, creativeSwitchConfig.getCheckIntervall() * 20);

	}


// Methods

	void switchGameMode() {
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();

		for (Player player : onlinePlayers) {
			if (!playerIsCreative.contains(player) && CREATIVE_SWITCH_PERMISSIONS.CS.hasPermission(player)) {
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage(creativeSwitchConfig.getMessage(Messages.CREATIVE_MODE_MESSAGE));
				playerIsCreative.add(player);
			}
			if (playerIsCreative.contains(player) && !CREATIVE_SWITCH_PERMISSIONS.CS.hasPermission(player)) {
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(creativeSwitchConfig.getMessage(Messages.SURVIAL_MODE_MESSAGE));
				playerIsCreative.remove(player);
			}
		}
	}


// Getters & Setters


}

