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
import org.simiancage.bukkit.TheMonkeyPack.configs.*;
import org.simiancage.bukkit.TheMonkeyPack.listeners.*;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.HashMap;

public class TheMonkeyPack extends JavaPlugin {


    private MainLogger mainLogger;
    private MainConfig mainConfig;
    private JavaPlugin instance;
    private int registeredCommands = 0;
    private int registeredListeners = 0;
    private boolean vaultFound;
    private boolean economyEnabled;
    private KitConfig kitConfig;
    private GetPayedConfig getPayedConfig;
    private TNTControlConfig tntControlConfig;
    private AutoStopServerConfig autoStopServerConfig;
    private Economy economy = null;
    HashMap<String, Commands> registeredPlayerCommands = new HashMap<String, Commands>();

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

        for (Type event : mainConfig.getPlayerChatListeners()) {
            getServer().getPluginManager().registerEvent(event, new PlayerChatListener(this), Priority.Normal, this);
            addRegisteredListener();
        }

        for (Type event : mainConfig.getBlockListenerEvents()) {
            getServer().getPluginManager().registerEvent(event, new BlockListenerTMP(this), Priority.Monitor, this);
            addRegisteredListener();
        }

        for (Type event : mainConfig.getPlayerListenerEvents()) {
            getServer().getPluginManager().registerEvent(event, new PlayerListenerTMP(this), Priority.Monitor, this);
            addRegisteredListener();
        }

        for (Type event : mainConfig.getEntityListenerEvents()) {
            getServer().getPluginManager().registerEvent(event, new EntityListenerTMP(this), Priority.High, this);
            addRegisteredListener();
        }

        mainLogger.debug(registeredCommands + " Commands registered");
        mainLogger.debug(registeredListeners + " Listeners registered");
        if (mainConfig.isEnableKits()) {
            kitConfig = KitConfig.getInstance();
        }
        if (mainConfig.isEnableGetPayed()) {
            getPayedConfig = GetPayedConfig.getInstance();
            getPayedConfig.getGetPayedHelper().onEnable();
        }

        if (mainConfig.isEnableTNTControl()) {
            tntControlConfig = TNTControlConfig.getInstance();
            tntControlConfig.getTNTControlHelper().onEnable();
        }

        if (mainConfig.isEnableAutoStopServer()) {
            autoStopServerConfig = AutoStopServerConfig.getInstance();
            autoStopServerConfig.getAutoStopServerHelper().onEnable();
        }

        // ToDo add more configs for other Modules on Top
        mainLogger.enableMsg();

    }

    public void onDisable() {
        if (mainConfig.isEnableKits()) {
            kitConfig.resetKits();
        }
        if (mainConfig.isEnableGetPayed()) {
            getPayedConfig.getGetPayedHelper().onDisable();
        }

        if (mainConfig.isEnableTNTControl()) {
            tntControlConfig.getTNTControlHelper().onDisable();
        }

        if (mainConfig.isEnableAutoStopServer()) {
            autoStopServerConfig.getAutoStopServerHelper().onDisable();
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
        mainConfig.resetPlayerChatListeners();
        registeredPlayerCommands.clear();
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
        mainLogger.debug("Message to player: " + player.getName() + " = " + msg);
    }

    public void registerPlayerCommand(String command, Commands commands) {
        mainLogger.debug("chat command registered", command);
        registeredPlayerCommands.put(command, commands);
    }

    public HashMap<String, Commands> getRegisteredPlayerCommands() {
        return registeredPlayerCommands;
    }

    public boolean isValidPlayerCommand(String command) {
        return registeredPlayerCommands.containsKey(command);
    }

    public Commands getPlayerCommand(String command) {
        return registeredPlayerCommands.get(command);
    }
}

