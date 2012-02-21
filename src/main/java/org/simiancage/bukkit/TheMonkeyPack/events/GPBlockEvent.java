package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * PluginName: TheMonkeyPack
 * Class: GPBlockEvent
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 21:36
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class GPBlockEvent implements Listener {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	protected GetPayedConfig getPayedConfig;
	protected GetPayedLogger getPayedLogger;
	static GPBlockEvent instance;
	protected GetPayedHelper getPayedHelper;
	private final String PLAYER_ALLOW_PAY = "tmp.getpayed.allowpay";


	private GPBlockEvent(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		getPayedConfig = GetPayedConfig.getInstance();
		getPayedLogger = getPayedConfig.getGetPayedLogger();
		getPayedHelper = getPayedConfig.getGetPayedHelper();
	}

	public static GPBlockEvent getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new GPBlockEvent(plugin);
		}
		return instance;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void getPayedBlockBreakEvent(BlockBreakEvent event) {
		if (!mainConfig.isGetPayedActive()) {
			return;
		}
		Player player = event.getPlayer();
		Block block = event.getBlock();
		blockEvent(block, player, false);


	}

	public void payPlayer(Player player, Double toPay) {
		double pay = getPayedHelper.getPlayerMap().containsKey(player.getName()) ? getPayedHelper.getPlayerMap().get(player.getName()) : 0.0D;
		getPayedHelper.payPlayer(player.getName(), pay + toPay);
	}

	public Double getPrice(Block block, boolean broken) {
		if (!isInBlackList(block.getLocation())) {
			if (broken) {
				if (getPayedConfig.isNotDefaultPrice(block)) {
					return getPayedConfig.getBlockBreakPrice(block);
				} else {
					return getPayedConfig.getBlockBreakPrice("default");
				}
			}

			if (getPayedConfig.isNotDefaultPrice(block)) {
				return getPayedConfig.getBlockPlacePrice(block);
			} else {
				return getPayedConfig.getBlockPlacePrice("default");
			}
		}

		return Double.valueOf(0.0D);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void getPayedBlockPlaceEvent(BlockPlaceEvent event) {
		if (!mainConfig.isGetPayedActive()) {
			return;
		}
		Player player = event.getPlayer();
		Block block = event.getBlock();
		blockEvent(block, player, true);


	}

	public boolean isInBlackList(Location location) {
		if (!getPayedHelper.blockIsInBlackList(location)) {
			return false;
		}
		return true;
	}

	private void recordPlayerPayments(Player player, double amountPaid, int workPlace) {
		Map<String, Object> workPlaceInfo = getPayedHelper.getWorkPlacesInfo().get(workPlace);
		if (getPayedHelper.getPlayerPayments().containsKey(workPlaceInfo.get("OwnedBy"))) {
			Map temp = getPayedHelper.getPlayerPayments().get((workPlaceInfo).get("OwnedBy"));

			if (temp.containsKey(player.getName())) {
				double current = ((Double) temp.get(player.getName())).doubleValue();
				temp.remove(player.getName());
				temp.put(player.getName(), Double.valueOf(current + amountPaid));
			} else {
				temp.put(player.getName(), Double.valueOf(amountPaid));
			}

			getPayedHelper.removePlayerPayments(workPlace);
			getPayedHelper.addPlayerPayments(workPlace, temp);
		} else {
			Map temp = new HashMap();

			temp.put(player.getName(), Double.valueOf(amountPaid));
			getPayedHelper.addPlayerPayments(workPlace, temp);

		}
	}

	private void blockEvent(Block block, Player player, boolean place) {

		// if the event is not place it it break
		String eventType;
		boolean payBreak;
		if (place) {
			eventType = "place";
			payBreak = false;
		} else {
			eventType = "break";
			payBreak = true;
		}


		if ((!main.getServer().getScheduler().isQueued(getPayedHelper.getCurrentTaskId()) && (!main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentTaskId())))) {
			getPayedHelper.setCurrentTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, getPayedHelper.getPayDayTask(), getPayedConfig.getPayDayInterval()));
		}

		if (!isInBlackList(block.getLocation())) {
			if ((main.hasPermission(player, PLAYER_ALLOW_PAY))) {
				int workPlace = getPayedHelper.isPositionInWorkPlace(block.getLocation());
				if (workPlace != -1) {
					Map<String, Object> workPlaceInfo = getPayedHelper.getWorkPlacesInfo().get(workPlace);
					getPayedLogger.debug("workplaceInfo", workPlaceInfo.toString());
					if ((workPlaceInfo.get(eventType + "Type").toString().equalsIgnoreCase("percent"))) {
						double amountToAdd = getPrice(block, true).doubleValue() * (Double.parseDouble(workPlaceInfo.get(eventType + "Amount").toString()) / 100.0D);
						recordPlayerPayments(player, amountToAdd, workPlace);
						payPlayer(player, getPrice(block, payBreak));
					} else if (workPlaceInfo.get(eventType + "Type").toString().equalsIgnoreCase("flat")) {
						double amountToAdd = Double.parseDouble(workPlaceInfo.get(eventType + "Amount").toString());
						recordPlayerPayments(player, amountToAdd, workPlace);
						payPlayer(player, getPrice(block, payBreak));
					}
				} else {
					payPlayer(player, getPrice(block, payBreak));
				}
			}
		}

		getPayedHelper.addBlockToBlacklist(block.getLocation());
		if (getPayedHelper.sizeOfBlackList() > getPayedConfig.getBufferSize()) {
			getPayedHelper.removeBlockFromBlacklist(0);

		}

	}
}

