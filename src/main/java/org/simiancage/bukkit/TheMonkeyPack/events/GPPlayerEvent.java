package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * PluginName: TheMonkeyPack
 * Class: GPPlayerEvent
 * User: DonRedhorse
 * Date: 28.12.11
 * Time: 00:21
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class GPPlayerEvent {

    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected GetPayedConfig getPayedConfig;
    protected GetPayedLogger getPayedLogger;
    static GPPlayerEvent instance;
    protected GetPayedHelper getPayedHelper;


    private GPPlayerEvent(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        getPayedConfig = GetPayedConfig.getInstance();
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        getPayedHelper = getPayedConfig.getGetPayedHelper();
    }

    public static GPPlayerEvent getInstance(TheMonkeyPack plugin) {
        if (instance == null) {
            instance = new GPPlayerEvent(plugin);
        }
        return instance;
    }


    public void getPayedPlayerInteract(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            if (event.getAction().name().equalsIgnoreCase("Right_Click_Block")) {
                int blockID = event.getClickedBlock().getTypeId();
                Player player = event.getPlayer();
                String blockType = getPayedHelper.getItemViaIndex(blockID);
                // Only possible as admin, no translation
                if (getPayedHelper.waitingForBreak(player)) {
                    if (getPayedHelper.containsItem(blockID)) {

                        if (getPayedConfig.blockPricesContainsBlock(blockType)) {
                            getPayedConfig.setBreakBlockPrice(blockType, getPayedHelper.getNewPriceSet(player));
                        } else {
                            Map temp = new HashMap();
                            temp.put("break", getPayedHelper.getNewPriceSet(player));
                            temp.put("place", 0.0D);
                            getPayedConfig.setBlockPrices(blockType, temp);
                        }

                        getPayedConfig.writeConfig();
                        String msg = getPayedConfig.reloadConfig();
                        main.sendPlayerMessage(player, Commands.INFO_MESSAGES + "New break price set!");
                        main.sendPlayerMessage(player, Commands.INFO_MESSAGES + msg);
                    } else {
                        main.sendPlayerMessage(player, Commands.INFO_MESSAGES + " This block is not supported by " + getPayedConfig.getMODULE_NAME() + ", sorry");

                    }
                    getPayedHelper.removePlayerFromBreakPlace(player);
                    getPayedHelper.setNewPriceSet(player, 0.0D);

                }
                // Only possible as admin, no translation
                if (getPayedHelper.waitingForPlace(player)) {
                    if (getPayedHelper.containsItem(blockID)) {
                        if (getPayedConfig.blockPricesContainsBlock(blockType)) {
                            getPayedConfig.setPlaceBlockPrice(blockType, getPayedHelper.getNewPriceSet(player));
                        } else {
                            Map temp = new HashMap();
                            temp.put("break", 0.0D);
                            temp.put("place", getPayedHelper.getNewPriceSet(player));
                            getPayedConfig.setBlockPrices(blockType, temp);
                        }

                        getPayedConfig.writeConfig();
                        String msg = getPayedConfig.reloadConfig();
                        main.sendPlayerMessage(player, Commands.INFO_MESSAGES + "New place price set!");
                        main.sendPlayerMessage(player, Commands.INFO_MESSAGES + msg);
                    } else {
                        main.sendPlayerMessage(player, Commands.INFO_MESSAGES + " This block is not supported by " + getPayedConfig.getMODULE_NAME() + ", sorry");
                    }
                    getPayedHelper.removePlayerFromBreakPlace(player);
                    getPayedHelper.setNewPriceSet(player, 0.0D);
                }

                if (getPayedHelper.isPlayerPriceChecking(player)) {
                    if (getPayedConfig.blockPricesContainsBlock(blockType)) {
                        if (main.getEconomy() != null) {
                            double breakPrice = getPayedConfig.getBlockBreakPrice(blockType);
                            double placePrice = getPayedConfig.getBlockPlacePrice(blockType);
                            String msg = Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.YOU_WILL_BE_PAID_FOR_BREAKING).replace("%blocktype", blockType);
                            msg = msg.replace("$$", main.getEconomy().format(breakPrice));
                            main.sendPlayerMessage(player, msg);
                            msg = Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.YOU_WILL_BE_PAID_FOR_PLACING).replace("%blocktype", blockType);
                            msg = msg.replace("$$", main.getEconomy().format(placePrice));
                            main.sendPlayerMessage(player, msg);
                        }

                    } else {
                        if (main.getEconomy() != null) {
                            double breakPrice = getPayedConfig.getDefaultBreakAmount();
                            double placePrice = getPayedConfig.getDefaultPlaceAmount();
                            String msg = Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.YOU_WILL_BE_PAID_DEFAULT_FOR_BREAKING).replace("%blocktype", blockType);
                            msg = msg.replace("$$", main.getEconomy().format(breakPrice));
                            main.sendPlayerMessage(player, msg);
                            msg = Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.YOU_WILL_BE_PAID_DEFAULT_FOR_PLACING).replace("%blocktype", blockType);
                            msg = msg.replace("$$", main.getEconomy().format(placePrice));
                            main.sendPlayerMessage(player, msg);
                        }


                    }
                }


                if (player.getItemInHand().getTypeId() == getPayedConfig.getWorkPlaceTool()) {
                    if (main.hasPermission(player, getPayedConfig.getPERM_WORKPLACE_CONFIGURE())) {
                        Location blockLoc = event.getClickedBlock().getLocation();

                        if (getPayedHelper.isPositionInWorkPlace(blockLoc) == -1) {
                            if (getPayedHelper.isPlayerSettingWorkplaceTempPoints(player)) {
                                Map temp = new HashMap();
                                temp = getPayedHelper.getPlayersWorkplaceTempPoints(player);
                                getPayedHelper.removePlayersTempSelectionPoints(player);
                                temp.remove("x2");
                                temp.remove("z2");
                                temp.put("x2", (int) blockLoc.getX());
                                temp.put("z2", (int) blockLoc.getZ());
                                if ((Integer) temp.get("x1") != -999999) {
                                    temp.remove("pointsSelected");
                                    temp.put("pointsSelected", 1);
                                }
                                getPayedHelper.addSelectionToWorkplaceTempPoints(player, temp);
                                main.sendPlayerMessage(player, Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.BOTH_POINTS_SELECTED));

                            } else {
                                Map temp = new HashMap();
                                temp.put("x2", (int) blockLoc.getX());
                                temp.put("z2", (int) blockLoc.getZ());
                                temp.put("x1", -999999);
                                temp.put("z1", -999999);
                                temp.put("pointsSelected", 0);
                                getPayedHelper.addSelectionToWorkplaceTempPoints(player, temp);
                                main.sendPlayerMessage(player, Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.POINT_TWO_SELECTED));
                            }
                        } else {
                            main.sendPlayerMessage(player, Commands.WARNING_MESSAGES + getPayedConfig.getMessage(Messages.YOU_CANT_CREATE_A_WORKPLACE_IN_AN_EXISTING_ONE));
                        }

                    }

                }

            }

            if (event.getAction().name().equalsIgnoreCase("Left_Click_Block")) {
                Player player = event.getPlayer();
                if (player.getItemInHand().getTypeId() == getPayedConfig.getWorkPlaceTool()) {
                    if (main.hasPermission(player, getPayedConfig.getPERM_WORKPLACE_CONFIGURE())) {
                        Location blockLoc = event.getClickedBlock().getLocation();
                        if (getPayedHelper.isPositionInWorkPlace(blockLoc) == -1) {
                            if (getPayedHelper.isPlayerSettingWorkplaceTempPoints(player)) {
                                Map temp = new HashMap();
                                temp = getPayedHelper.getPlayersWorkplaceTempPoints(player);
                                getPayedHelper.removePlayersTempSelectionPoints(player);
                                temp.remove("x1");
                                temp.remove("z1");
                                temp.put("x1", (int) blockLoc.getX());
                                temp.put("z1", (int) blockLoc.getZ());
                                if ((Integer) temp.get("x2") != -999999) {
                                    temp.remove("pointsSelected");
                                    temp.put("pointsSelected", 1);
                                }
                                getPayedHelper.addSelectionToWorkplaceTempPoints(player, temp);
                                main.sendPlayerMessage(player, Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.BOTH_POINTS_SELECTED));
                            } else {
                                Map temp = new HashMap();
                                temp.put("x1", (int) blockLoc.getX());
                                temp.put("z1", (int) blockLoc.getZ());
                                temp.put("x2", -999999);
                                temp.put("z2", -999999);
                                temp.put("pointsSelected", Integer.valueOf(0));
                                getPayedHelper.addSelectionToWorkplaceTempPoints(player, temp);
                                main.sendPlayerMessage(player, Commands.INFO_MESSAGES + getPayedConfig.getMessage(Messages.POINT_ONE_SELECTED));
                            }
                        } else {
                            main.sendPlayerMessage(player, Commands.WARNING_MESSAGES + getPayedConfig.getMessage(Messages.YOU_CANT_CREATE_A_WORKPLACE_IN_AN_EXISTING_ONE));
                        }
                    }
                }
            }
        }

    }


    public void getPayedPlayerLogin(PlayerLoginEvent event) {
        getPayedHelper.addPlayerToPriceCheckOn(event.getPlayer(), false);
        getPayedHelper.addPlayerToPayDayMessageOn(event.getPlayer(), getPayedConfig.isPayDayMessageEnabled());

    }

    public void getPayedPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (getPayedHelper.isPlayerPriceChecking(player)) {
            getPayedHelper.removePlayerFromPriceCheckOn(player);
            getPayedHelper.removePlayerFromPayDayMessageOn(player);
        }

        if (getPayedHelper.isPlayerSettingWorkplaceTempPoints(player)) {
            getPayedHelper.removePlayersTempSelectionPoints(player);
        }

        if (getPayedHelper.isPlayerInWorksPlace(player)) {
            getPayedHelper.removePlayerFromWorkplace(player);
        }


    }
}

