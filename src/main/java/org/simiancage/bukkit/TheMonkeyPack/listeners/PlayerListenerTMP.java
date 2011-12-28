package org.simiancage.bukkit.TheMonkeyPack.listeners;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.events.GPPlayerEvent;
import org.simiancage.bukkit.TheMonkeyPack.interfaces.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: PlayerListenerTMP
 * User: DonRedhorse
 * Date: 28.12.11
 * Time: 00:26
 */

public class PlayerListenerTMP extends PlayerListener implements Listeners {
    private static TheMonkeyPack main;
    private MainLogger mainLogger;
    private MainConfig mainConfig;
    private GPPlayerEvent gpPlayerEvent;


    public PlayerListenerTMP(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = plugin.getMainLogger();
        mainConfig = plugin.getMainConfig();
        mainLogger.debug("PlayerListenerTMP active");

    }

    @Override
    public void onEnable(TheMonkeyPack main) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDisable() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (mainConfig.isGetPayedActive()) {
            gpPlayerEvent = GPPlayerEvent.getInstance(main);
            gpPlayerEvent.getPayedPlayerInteract(event);
        }
    }

    public void onPlayerLogin(PlayerLoginEvent event) {
        if (mainConfig.isGetPayedActive()) {
            gpPlayerEvent = GPPlayerEvent.getInstance(main);
            gpPlayerEvent.getPayedPlayerLogin(event);
        }
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        if (mainConfig.isGetPayedActive()) {
            gpPlayerEvent = GPPlayerEvent.getInstance(main);
            gpPlayerEvent.getPayedPlayerQuit(event);
        }
    }

}

