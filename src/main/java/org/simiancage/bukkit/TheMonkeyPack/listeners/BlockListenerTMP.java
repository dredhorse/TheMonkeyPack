package org.simiancage.bukkit.TheMonkeyPack.listeners;

import org.bukkit.event.block.*;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.events.CSBlockEvent;
import org.simiancage.bukkit.TheMonkeyPack.events.GPBlockEvent;
import org.simiancage.bukkit.TheMonkeyPack.events.RARPEvent;
import org.simiancage.bukkit.TheMonkeyPack.events.TCBlockEvent;
import org.simiancage.bukkit.TheMonkeyPack.interfaces.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: BlockListenerTMP
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 14:46
 */

public class BlockListenerTMP extends BlockListener implements Listeners {
	private static TheMonkeyPack main;
	private MainLogger mainLogger;
	private MainConfig mainConfig;
	private GPBlockEvent gpBlockEvent;
	private TCBlockEvent tcBlockEvent;
	private RARPEvent rarpEvent;
	private CSBlockEvent csBlockEvent;


	public BlockListenerTMP(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = plugin.getMainLogger();
		mainConfig = plugin.getMainConfig();
		mainLogger.debug("BlockListenerTMP active");

	}

	@Override
	public void onEnable(TheMonkeyPack main) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onDisable() {

	}

	public void onBlockBreak(BlockBreakEvent event) {
		if (mainConfig.isGetPayedActive()) {
			gpBlockEvent = GPBlockEvent.getInstance(main);
			gpBlockEvent.getPayedBlockBreakEvent(event);
		}

		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpBlockBreak(event);
		}
		if (mainConfig.isEnableCreativeSwitch()) {
			csBlockEvent = CSBlockEvent.getInstance(main);
			csBlockEvent.csBlockBreakEvent(event);
		}
	}

	public void onBlockPlace(BlockPlaceEvent event) {
		if (mainConfig.isGetPayedActive()) {
			gpBlockEvent = GPBlockEvent.getInstance(main);
			gpBlockEvent.getPayedBlockPlaceEvent(event);
		}
		if (mainConfig.isEnableTNTControl()) {
			tcBlockEvent = TCBlockEvent.getInstance(main);
			tcBlockEvent.tntControlBlockPlace(event);
		}
		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpBlockPlace(event);
		}
	}

	public void onBlockDamage(BlockDamageEvent event) {
		if (mainConfig.isEnableTNTControl()) {
			tcBlockEvent = TCBlockEvent.getInstance(main);
			tcBlockEvent.tntControlBlockDamage(event);
		}
	}

	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpPistonExtend(event);

		}
	}

	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpPistonRetract(event);
		}
	}

	public void onBlockFromTo(BlockFromToEvent event) {
		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpBlockFromTo(event);
		}
	}

	public void onBlockPhysics(BlockPhysicsEvent event) {
		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpBlockPhysics(event);

		}

	}


}
