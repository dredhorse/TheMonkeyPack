package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.AttackControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.AttackControlConfig.ATTACK_CONTROL_PERMISSIONS;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.AttackControlLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: ACBlockEvent
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 21:36
 */


public class ACEntityEvent implements Listener {

	protected TheMonkeyPack main;
	private MainConfig mainConfig;
	protected MainLogger mainLogger;
	protected AttackControlConfig attackControlConfig;
	protected AttackControlLogger attackControlLogger;
	static ACEntityEvent instance;


	private ACEntityEvent(TheMonkeyPack plugin) {
		main = plugin;
		mainLogger = main.getMainLogger();
		mainConfig = main.getMainConfig();
		attackControlConfig = AttackControlConfig.getInstance();
		attackControlLogger = attackControlConfig.getAttackControlLogger();

	}

	public static ACEntityEvent getInstance(TheMonkeyPack plugin) {
		if (instance == null) {
			instance = new ACEntityEvent(plugin);
		}
		return instance;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void playerTargetEvent(EntityTargetEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity targetEntity = event.getTarget();
		if (targetEntity == null) {
			return;
		}
		Entity attackerEntity = event.getEntity();
		if ((attackerEntity instanceof Monster) || (attackerEntity instanceof Slime) || (attackerEntity instanceof Snowman)) {
			if (targetEntity instanceof Player) {
				Player player = (Player) targetEntity;
				if (player.hasPermission("tmp.ac.all")) {
					event.setCancelled(true);
					return;
				}
				if ((attackerEntity instanceof Snowman) && player.hasPermission("tmp.ac.snowman")) {
					event.setCancelled(true);
					return;
				}
				if (player.hasPermission("tmp.ac.monster")) {
					event.setCancelled(true);
					return;
				}
				event.setCancelled(isImmune(attackerEntity, player));

			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void entityDamageEvent(EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (attackControlConfig.isPlayerCanAttack()) {
			return;
		}
		if (event.getEntity() instanceof Player) {
			return;
		}
		if (event instanceof EntityDamageByEntityEvent) {
			Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
			if (damager instanceof Player) {
				Player player = (Player) damager;
				Entity target = event.getEntity();
				if (((target instanceof Monster) || (target instanceof Snowman)) && (player.hasPermission("tmp.ac.all"))) {
					event.setCancelled(true);
					return;
				}
				if ((target instanceof Monster) && ATTACK_CONTROL_PERMISSIONS.AC_MONSTER.hasPermission(player)) {
					event.setCancelled(true);
				}
				event.setCancelled(isImmune(target, player));
			}
		}


	}

	private boolean isImmune(Entity entity, Player player) {
		if (entity instanceof Blaze) {
			return ATTACK_CONTROL_PERMISSIONS.AC_BLAZE.hasPermission(player);
		}
		if (entity instanceof CaveSpider) {
			return ATTACK_CONTROL_PERMISSIONS.AC_CAVESPIDER.hasPermission(player);
		}
		if (entity instanceof Creeper) {
			return ATTACK_CONTROL_PERMISSIONS.AC_CREEPER.hasPermission(player);
		}
		if (entity instanceof EnderDragon) {
			return ATTACK_CONTROL_PERMISSIONS.AC_ENDERDRAGON.hasPermission(player);
		}
		if (entity instanceof Enderman) {
			return ATTACK_CONTROL_PERMISSIONS.AC_ENDERMAN.hasPermission(player);
		}
		if (entity instanceof Ghast) {
			return ATTACK_CONTROL_PERMISSIONS.AC_GHAST.hasPermission(player);
		}
		if (entity instanceof Giant) {
			return ATTACK_CONTROL_PERMISSIONS.AC_GIANT.hasPermission(player);
		}
		if (entity instanceof MagmaCube) {
			return ATTACK_CONTROL_PERMISSIONS.AC_MAGMACUBE.hasPermission(player);
		}
		if (entity instanceof PigZombie) {
			return ATTACK_CONTROL_PERMISSIONS.AC_PIGZOMBIE.hasPermission(player);
		}
		if (entity instanceof Skeleton) {
			return ATTACK_CONTROL_PERMISSIONS.AC_SKELETON.hasPermission(player);
		}
		if (entity instanceof Snowman) {
			return ATTACK_CONTROL_PERMISSIONS.AC_SNOWMAN.hasPermission(player);
		}
		if (entity instanceof Slime) {
			return ATTACK_CONTROL_PERMISSIONS.AC_SLIME.hasPermission(player);
		}
		if (entity instanceof Silverfish) {
			return ATTACK_CONTROL_PERMISSIONS.AC_SILVERFISH.hasPermission(player);
		}
		if (entity instanceof Spider) {
			return ATTACK_CONTROL_PERMISSIONS.AC_SPIDER.hasPermission(player);
		}
		if (entity instanceof Zombie) {
			return ATTACK_CONTROL_PERMISSIONS.AC_ZOMBIE.hasPermission(player);
		}
		if (entity instanceof Wolf) {
			return ATTACK_CONTROL_PERMISSIONS.AC_WOLF.hasPermission(player);
		}


		return false;
	}
}

