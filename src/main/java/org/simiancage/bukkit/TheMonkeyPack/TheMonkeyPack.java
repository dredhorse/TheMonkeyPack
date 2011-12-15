package org.simiancage.bukkit.TheMonkeyPack;

/**
 *
 * PluginName: TheMonkeyPack
 * Class: TheMonkeyPack
 * User: DonRedhorse
 * Date: 07.12.11
 * Time: 23:46
 *
 */


import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.KitConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.listeners.ServerListenerTMP;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

public class TheMonkeyPack extends JavaPlugin {


    private MainLogger mainLogger;
    private MainConfig mainConfig;
    private JavaPlugin instance;
    private int registeredCommands = 0;
    private int registeredListeners = 0;
    private boolean vaultFound;
    private boolean economyEnabled;
    private KitConfig kitConfig;
    private Economy economy = null;


/*    interface Listener {
        void onEnable(TheMonkeyPack main);

        void onDisable();
    }*/


    public void onEnable() {
        registeredCommands = 0;
        registeredListeners = 0;
        instance = this;
        mainLogger = new MainLogger(this);
        mainConfig = new MainConfig(this);
        mainLogger.setConfig(mainConfig);
        mainConfig.setupConfig(this);


        for (Type event : mainConfig.getServerListenerEvents()) {
            getServer().getPluginManager().registerEvent(event, new ServerListenerTMP(this), Priority.Monitor, this);
            addRegisteredListener();
        }

        mainLogger.debug(registeredCommands + " Commands registered");
        mainLogger.debug(registeredListeners + " Listeners registered");
        if (mainConfig.isEnableKits()) {
            kitConfig = KitConfig.getInstance();
        }
        mainLogger.enableMsg();

    }

    public void onDisable() {
        if (mainConfig.isEnableKits()) {
            kitConfig.resetKits();
        }
        registeredCommands = 0;
        registeredListeners = 0;
        mainConfig.resetBlockListeners();
        mainConfig.resetEntityListeners();
        mainConfig.resetEventListeners();
        mainConfig.resetInventoryListeners();
        mainConfig.resetPlayerListeners();
        mainConfig.resetVehicleListeners();
        mainConfig.resetWeatherListeners();
        mainConfig.resetWorldListeners();
        mainConfig.resetServerListeners();
        economy = null;
        economyEnabled = false;
    }

    public void registerCommand(String command, Commands commands) {
        mainLogger.debug("Command", command);
        mainLogger.debug("CommandObject", commands);
        this.getCommand(command).setExecutor(commands);
        Permission permission = new Permission(commands.getPermission(), commands.getPermissionDesc());
        this.getServer().getPluginManager().addPermission(permission);
        registeredCommands++;
    }

    private void addRegisteredListener() {
        registeredListeners++;
    }


    public boolean hasPermission(CommandSender sender, String node) {
        Player player = (Player) sender;
        mainLogger.debug("Checking to see if player [" + player.getName() + "] has permission [" + node + "]");
        boolean hasPermission = player.hasPermission(node);
        if (hasPermission) {
            mainLogger.debug("Player [" + player.getName() + "] HAS PERMISSION [" + node + "]!");
        }
        return hasPermission;
    }

    public boolean isVaultFound() {
        return vaultFound;
    }

    public void setVaultFound(boolean vaultFound) {
        this.vaultFound = vaultFound;
    }

    public boolean isEconomyEnabled() {
        return economyEnabled;
    }

    public void setEconomyEnabled(boolean economyEnabled) {
        this.economyEnabled = economyEnabled;
    }

    public Economy getEconomy() {
        return economy;
    }

    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    public MainLogger getMainLogger() {
        return mainLogger;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public void sendPlayerMessage(Player player, String msg) {
        player.sendRawMessage(msg);
    }
}

