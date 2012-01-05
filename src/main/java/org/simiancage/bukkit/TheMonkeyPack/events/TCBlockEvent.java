package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.TNT_CONTROL_PERMISSIONS;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: TCBlockEvent
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 21:36
 */

// contains code from http://forums.bukkit.org/threads/8145/


public class TCBlockEvent {

    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    private TNTControlConfig tntControlConfig;
    private TNTControlLogger tntControlLogger;
    private TNTControlHelper tntControlHelper;
    static TCBlockEvent instance;


    private TCBlockEvent(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        tntControlConfig = TNTControlConfig.getInstance();
        tntControlLogger = tntControlConfig.getTNTControlLogger();
        tntControlHelper = tntControlConfig.getTNTControlHelper();
    }

    public static TCBlockEvent getInstance(TheMonkeyPack plugin) {
        if (instance == null) {
            instance = new TCBlockEvent(plugin);
        }
        return instance;
    }

    public void tntControlBlockPlace(BlockPlaceEvent event) {
        if ((!event.isCancelled()) && event.getBlock().getType() == Material.TNT) {
            String msg = "You should not see this, if yes, tell the plugin developer!";
            boolean bCancel = true;
            Player player = event.getPlayer();
            Block block = event.getBlock();
            Location location = block.getLocation();
            int blockY = block.getY();
            if (main.hasPermission(player, TNT_CONTROL_PERMISSIONS.TNT_ALLOWED.toString())) {
                boolean onSurface = false;
                boolean aboveLimit = false;
                if (blockY >= (block.getWorld().getHighestBlockYAt(location) - tntControlConfig.getTntBelowSurfaceLevel())) {
                    onSurface = true;
                    tntControlLogger.debug("onSurface", onSurface);
                }
                if (blockY > tntControlConfig.getTntBlastLimit()) {
                    aboveLimit = true;
                } else {
                    bCancel = false;
                }
                if (aboveLimit && !onSurface) {
                    if (TNT_CONTROL_PERMISSIONS.TNT_ABOVELIMIT_PLACE.hasPermission(player)) {
                        bCancel = false;
                    } else {
                        msg = Commands.WARNING_MESSAGES + tntControlConfig.getMessage(Messages.YOU_CANNOT_PLACE_TNT_ABOVE);
                        msg = msg.replace("%abovelevel", "" + Commands.OPTIONAL_COLOR + tntControlConfig.getTntBlastLimit() + Commands.INFO_MESSAGES);
                    }
                } else {

                    if (onSurface && (TNT_CONTROL_PERMISSIONS.TNT_ON_SURFACE_PLACE.hasPermission(player) || tntControlConfig.isTntOnSurfaceEnabled())) {
                        bCancel = false;
                    } else {
                        msg = Commands.WARNING_MESSAGES + tntControlConfig.getMessage(Messages.YOU_CANNOT_PLACE_TNT_ONSURFACE);
                    }
                }

            } else {
                msg = Commands.WARNING_MESSAGES + tntControlConfig.getMessage(Messages.YOU_DONOT_HAVE_THE_PERMISSION_TO_USE_TNT);
            }

            if (bCancel) {
                main.sendPlayerMessage(player, msg);
            }
            event.setCancelled(bCancel);
        }
    }


    public void tntControlBlockDamage(BlockDamageEvent event) {

        if (!event.isCancelled() && event.getBlock().getType() == Material.TNT) {
            Block block = event.getBlock();
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getItemInHand();
            if (tntControlHelper.isOnReclaim(player)) {
                block.setType(Material.AIR);
                event.setCancelled(true);
                player.getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.TNT, 1));
                return;
            }

            if (tntControlConfig.isTntAllowReclaimTool() && tntControlConfig.isReclaimTool(itemInHand)) {
                block.setType(Material.AIR);
                player.getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.TNT, 1));
                event.setCancelled(true);
                return;
            }

            String msg = "You should not see this, if yes, tell the plugin developer!";
            boolean bCancel = true;
            Location location = block.getLocation();
            int blockY = block.getY();
            if (main.hasPermission(player, TNT_CONTROL_PERMISSIONS.TNT_ALLOWED.toString())) {
                boolean onSurface = false;
                boolean aboveLimit = false;
                if (blockY >= (block.getWorld().getHighestBlockYAt(location) - tntControlConfig.getTntBelowSurfaceLevel())) {
                    onSurface = true;
                }
                if (blockY > tntControlConfig.getTntBlastLimit()) {
                    aboveLimit = true;
                } else {
                    bCancel = false;
                }
                if (aboveLimit && !onSurface) {
                    if (TNT_CONTROL_PERMISSIONS.TNT_ABOVELIMIT_ACTIVATE.hasPermission(player)) {
                        bCancel = false;
                    } else {
                        msg = Commands.WARNING_MESSAGES + tntControlConfig.getMessage(Messages.YOU_CANNOT_ACTIVATE_TNT_ABOVE);
                        msg = msg.replace("%abovelevel", "" + Commands.OPTIONAL_COLOR + tntControlConfig.getTntBlastLimit() + Commands.INFO_MESSAGES);
                    }
                } else {

                    if (onSurface && (TNT_CONTROL_PERMISSIONS.TNT_ON_SURFACE_ACTIVATE.hasPermission(player) || tntControlConfig.isTntOnSurfaceEnabled())) {
                        bCancel = false;
                    } else {
                        msg = Commands.WARNING_MESSAGES + tntControlConfig.getMessage(Messages.YOU_CANNOT_ACTIVATE_TNT_ONSURFACE);
                    }
                }
            } else {
                main.sendPlayerMessage(player, Commands.WARNING_MESSAGES + tntControlConfig.getMessage(Messages.YOU_DONOT_HAVE_THE_PERMISSION_TO_USE_TNT));
            }
            if (bCancel) {
                main.sendPlayerMessage(player, msg);
                event.setCancelled(true);
                return;
            }
            // We cancel the orginal event, delete the block and spawn a primed tnt
            event.setCancelled(true);
            block.setType(Material.AIR);
            Location primedLocation = new Location(block.getWorld(), block.getX() + 0.5D, block.getY() + 0.5D, block.getZ() + 0.5D);
            block.getWorld().spawn(primedLocation, TNTPrimed.class);
        }


    }

}

