package org.simiancage.bukkit.TheMonkeyPack.listeners;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.interfaces.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: ServerListenerTMP
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 14:46
 */

public class ServerListenerTMP extends ServerListener implements Listeners {
    private static TheMonkeyPack plugin;
    private MainLogger mainLogger;
    private MainConfig mainConfig;
    private boolean missingEconomyWarn = true;


    public ServerListenerTMP(TheMonkeyPack plugin) {
        this.plugin = plugin;
        mainLogger = plugin.getMainLogger();
        mainConfig = plugin.getMainConfig();
        mainLogger.debug("ServerListenerTMP active");

    }

    @Override
    public void onEnable(TheMonkeyPack main) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDisable() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        mainLogger.debug("onPluginDisable executing");
        PluginManager pm = plugin.getServer().getPluginManager();
        Plugin checkVault = pm.getPlugin("Vault");
        if ((checkVault == null) && plugin.isEconomyEnabled()) {
            RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider == null) {
                plugin.setVaultFound(false);
                plugin.setEconomyEnabled(false);
                mainLogger.info("un-hooked from Vault.");
                mainLogger.info("as Vault was unloaded / disabled.");
                if (mainConfig.isEnableGetPayed()) {
                    mainConfig.setGetPayedActive(false);
                    mainLogger.info("Disabling GetPayed!");
                }
            }
        }

    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        mainLogger.debug("onPluginEnable executing");
        PluginManager pm = plugin.getServer().getPluginManager();
        Plugin checkVault = pm.getPlugin("Vault");
        Plugin checkMobArena = pm.getPlugin("MobArena");
        if (checkVault != null && !plugin.isEconomyEnabled()) {
            plugin.setVaultFound(true);
            mainLogger.info("Vault detected");
            mainLogger.info("Checking economy providers now!");
        }
        if (plugin.isVaultFound() && !plugin.isEconomyEnabled()) {
            RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                plugin.setEconomy(economyProvider.getProvider());
                plugin.setEconomyEnabled(true);
                mainLogger.info("Economy provider found: " + economyProvider.getProvider().getName());
                if (mainConfig.isEnableGetPayed()) {
                    mainConfig.setGetPayedActive(true);
                    mainLogger.info("Activating GetPayed!");
                }

            } else {
                if (missingEconomyWarn) {
                    mainLogger.warning("No economy provider found.");
                    mainLogger.info("Still waiting for economy provider to show up.");
                    missingEconomyWarn = false;
                }
            }
        }


    }
}
