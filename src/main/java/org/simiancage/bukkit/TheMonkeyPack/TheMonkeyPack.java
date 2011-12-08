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


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.CommandHelper;
import org.simiancage.bukkit.TheMonkeyPack.listeners.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.ArrayList;
import java.util.Arrays;

public class TheMonkeyPack extends JavaPlugin {

    private CommandHelper commandHelper;
    private MainLogger mainLogger;
    private MainConfig mainConfig;
    private Plugin instance;

/*    interface Listener {
        void onEnable(TheMonkeyPack main);

        void onDisable();
    }*/


    public void onEnable() {
        instance = this;
        mainLogger = MainLogger.getInstance(this);
        mainConfig = MainConfig.getInstance();
        mainConfig.setupConfig(this);

        this.commandHelper = new CommandHelper(this);


        registerCommands();
        mainLogger.enableMsg();

    }

    public void onDisable() {

    }

    /**
     * Registering the commands we have
     */


    private void registerCommands() {
        int total = 0;

        for (Commands command : mainConfig.getCommands()) {
            total++;
            this.commandHelper.registerCommand(command);
        }
        mainLogger.debug(total + " Commands registered");
    }


    /**
     * Registering the Listeners we have
     */

    private void registerListeners() {
        int total = 0;
        for (Listeners listener : mainConfig.getListeners()) {
            total++;
            listener.onEnable(this);
        }
    }

    /**
     * onCommand
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!this.isEnabled()) {
            sender.sendMessage("This plugin is Disabled!");
            return true;
        }
        ArrayList<String> allArgs = new ArrayList<String>(Arrays.asList(args));
        allArgs.add(0, command.getName());
        return this.commandHelper.locateAndRunCommand(sender, allArgs);
    }

    public CommandHelper getCommandHandler() {
        return this.commandHelper;
    }

    public boolean hasPermission(CommandSender sender, String node) {
        Player player = (Player) sender;
        mainLogger.debug("Checking to see if player [" + player.getName() + "] has permission [" + node + "]");
        boolean hasPermission = sender.hasPermission(node);
        if (hasPermission) {
            mainLogger.debug("Player [" + player.getName() + "] HAS PERMISSION [" + node + "]!");
        }
        return hasPermission;
    }

}

