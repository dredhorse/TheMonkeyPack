package org.simiancage.bukkit.TheMonkeyPack.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * PluginName: TheMonkeyPack
 * Class: Commands
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 19:12
 */

// contains code taken from https://github.com/PneumatiCraft/CommandHandler
// and https://github.com/Multiverse/Multiverse-Core

public abstract class Commands {

    protected String permission;
    protected boolean opRequired;

    protected int minimumArgLength;
    protected int maximumArgLength;

    protected String commandName;
    protected String commandDesc;
    protected String commandExample;
    public String commandUsage;

    protected List<String> commandKeys;


    private Plugin main;
    private MainConfig mainConfig;
    private MainLogger mainLogger;


    public Commands(Plugin instance) {
        main = instance;
        mainLogger = MainLogger.getLogger();
        mainConfig = MainConfig.getInstance();
        this.commandKeys = new ArrayList<String>();
    }


    public abstract void runCommand(CommandSender sender, List<String> args);

    public boolean checkArgLength(List<String> args) {
        return (this.minimumArgLength == -1 || this.minimumArgLength <= args.size())
                && (args.size() <= this.maximumArgLength || this.maximumArgLength == -1);
    }

    private String getArgsString(List<String> args) {
        String returnString = "";
        for (String s : args) {
            returnString += s + " ";
        }
        return returnString.substring(0, returnString.length() - 1);
    }

    public String getKey(ArrayList<String> parsedArgs) {
        // Combines our args to a space separated string
        String argsString = this.getArgsString(parsedArgs);

        for (String s : this.commandKeys) {
            String identifier = s.toLowerCase();
            if (argsString.matches(identifier + "(\\s+.*|\\s*)")) {
                return identifier;
            }
        }
        return null;
    }

    // mutates!
    public List<String> removeKeyArgs(List<String> args, String key) {
        int identifierLength = key.split(" ").length;
        for (int i = 0; i < identifierLength; i++) {
            // Since we're pulling from the front, always remove the first element
            args.remove(0);
        }
        return args;
    }

    public String getPermission() {
        return this.permission;
    }

    public boolean isOpRequired() {
        return this.opRequired;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public String getCommandDesc() {
        return this.commandDesc;
    }

    public String getCommandExample() {
        return this.commandExample;
    }

    public String getCommandUsage() {
        return this.commandUsage;
    }

    /**
     * @return the plugin
     */
    public Plugin getPlugin() {
        return this.main;
    }
}

