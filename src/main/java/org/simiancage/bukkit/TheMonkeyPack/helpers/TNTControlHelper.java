package org.simiancage.bukkit.TheMonkeyPack.helpers;

import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTControlHelper
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */


public class TNTControlHelper {

    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected TNTControlConfig tntControlConfig;
    protected TNTControlLogger tntControlLogger;
    protected TNTControlHelper tntControlHelper;
    static TNTControlHelper instance;
    private List<String> playerIsReclaiming = new ArrayList<String>();


    private TNTControlHelper(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        tntControlConfig = TNTControlConfig.getInstance();
        tntControlLogger = tntControlConfig.getTNTControlLogger();

    }

    public static TNTControlHelper getInstance(TheMonkeyPack plugin) {
        if (instance == null) {
            instance = new TNTControlHelper(plugin);
        }
        return instance;
    }


    public void onDisable() {

    }

    public void onEnable() {
    }


// Methods


// Getters & Setters

    public boolean isOnReclaim(Player player) {
        boolean isOnReclaim = false;
        if (playerIsReclaiming.contains(player.getName())) {
            isOnReclaim = true;
        }
        return isOnReclaim;
    }

    public void removePlayerFromReclaim(Player player) {
        playerIsReclaiming.remove(player.getName());
    }

    public void addPlayerToReclaim(Player player) {
        playerIsReclaiming.add(player.getName());
    }

}

