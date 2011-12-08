package org.simiancage.bukkit.TheMonkeyPack.listeners;

import org.bukkit.event.Listener;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: ${plugin}
 * Class: Listeners
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 21:41
 */

public abstract class Listeners implements Listener {

    private TheMonkeyPack main;
    private MainConfig mainConfig;
    private MainLogger mainLogger;

    public Listeners(TheMonkeyPack instance) {
        main = instance;
        mainLogger = MainLogger.getLogger();
        mainConfig = MainConfig.getInstance();

    }

    public abstract void onEnable(TheMonkeyPack main);

    public abstract void onDisable();
}

