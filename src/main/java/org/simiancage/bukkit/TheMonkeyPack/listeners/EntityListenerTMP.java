package org.simiancage.bukkit.TheMonkeyPack.listeners;

import org.bukkit.event.entity.*;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.events.ACEntityEvent;
import org.simiancage.bukkit.TheMonkeyPack.events.RARPEvent;
import org.simiancage.bukkit.TheMonkeyPack.events.TCEntityEvent;
import org.simiancage.bukkit.TheMonkeyPack.interfaces.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: EntityListenerTMP
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 14:46
 */

public class EntityListenerTMP extends EntityListener implements Listeners {
	private static TheMonkeyPack main;
	private MainLogger mainLogger;
	private MainConfig mainConfig;
	private TCEntityEvent tcEntityEvent;
	private ACEntityEvent acEntityEvent;
	private RARPEvent rarpEvent;


	public EntityListenerTMP(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = plugin.getMainLogger();
		mainConfig = plugin.getMainConfig();
		mainLogger.debug("EntityListenerTMP active");

	}

	@Override
	public void onEnable(TheMonkeyPack main) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onDisable() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void onExplosionPrime(ExplosionPrimeEvent event) {
		if (mainConfig.isEnableTNTControl()) {
			tcEntityEvent = TCEntityEvent.getInstance(main);
			tcEntityEvent.tntControlExplosionPrime(event);
		}
	}

	public void onEntityExplode(EntityExplodeEvent event) {
		if (mainConfig.isEnableTNTControl()) {
			tcEntityEvent = TCEntityEvent.getInstance(main);
			tcEntityEvent.tntControlEntityExplode(event);
		}
		if (mainConfig.isEnableRARP()) {
			rarpEvent = RARPEvent.getInstance(main);
			rarpEvent.rarpEntityExplodeEvent(event);
		}
	}

	public void onEntityTarget(EntityTargetEvent event) {
		if (mainConfig.isEnableAttackControl()) {
			acEntityEvent = ACEntityEvent.getInstance(main);
			acEntityEvent.playerTargetEvent(event);
		}
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (mainConfig.isEnableAttackControl()) {
			acEntityEvent = ACEntityEvent.getInstance(main);
			acEntityEvent.entityDamageEvent(event);
		}
	}

}
