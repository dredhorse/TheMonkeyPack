package org.simiancage.bukkit.TheMonkeyPack.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: Commands
 * User: DonRedhorse
 * Date: 15.12.11
 * Time: 19:19
 */

public abstract class Commands implements CommandExecutor {

    protected String permission;
    protected String permissionDesc;
    protected String commandDesc;
    protected String commandExample;
    protected String commandUsage;
    protected String commandName;
    protected boolean hasSubCommands;

    protected final ChatColor DEFAULT_COLOR = ChatColor.WHITE;
    protected final ChatColor SUB_COLOR = ChatColor.AQUA;
    protected final ChatColor COMMAND_COLOR = ChatColor.RED;
    protected final ChatColor OPTIONAL_COLOR = ChatColor.GREEN;
    protected final ChatColor PERMISSION_COLOR = ChatColor.LIGHT_PURPLE;
    protected final ChatColor INFO_MESSAGES = ChatColor.AQUA;
    protected final ChatColor WARNING_MESSAGES = ChatColor.GOLD;
    protected String PERM_DENIED = "You need the permission: %perm to use the %cmd command which %Description !";
    protected String WRONG_SYNTAX = "You used the wrong syntax for the command: %cmd";
    protected String ALLOWS_YOU_TO = "which allows you to ";
    protected String RIGHT_SYNTAX = "The right syntax for this command is: ";
    protected String EXAMPLE = "An example for the command is: ";


    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;


    public Commands(TheMonkeyPack instance) {
        main = instance;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        PERM_DENIED = mainConfig.getPERM_DENIED();
        WRONG_SYNTAX = mainConfig.getWRONG_SYNTAX();
        ALLOWS_YOU_TO = mainConfig.getALLOWS_YOU_TO();
        RIGHT_SYNTAX = mainConfig.getRIGHT_SYNTAX();
        EXAMPLE = mainConfig.getEXAMPLE();
    }

    public void permDenied(Player player, Commands command) {
        String commandName = command.getCommandName();
        String commandPerm = command.getPermission();
        String permDesc = command.getPermissionDesc();
        String msg = PERM_DENIED.replace("%perm", PERMISSION_COLOR + commandPerm + DEFAULT_COLOR);
        msg = msg.replace("%cmd", COMMAND_COLOR + commandName + DEFAULT_COLOR);
        msg = msg.replace("%Description", SUB_COLOR + permDesc + DEFAULT_COLOR);
        main.sendPlayerMessage(player, msg);
    }

    public void permDenied(Player player, Commands command, String option, String permNode) {
        String commandName = command.getCommandName();
        String commandPerm = command.getPermission();
        String permDesc = command.getPermissionDesc();
        String msg = PERM_DENIED.replace("%perm", PERMISSION_COLOR + commandPerm + DEFAULT_COLOR);
        msg = msg.replace("%cmd", COMMAND_COLOR + commandName + DEFAULT_COLOR);
        msg = msg.replace("%Description", SUB_COLOR + permDesc + DEFAULT_COLOR);
        main.sendPlayerMessage(player, msg);
    }


    public void displayHelp(Player player, Commands commands) {
        String msg = WRONG_SYNTAX.replace("%cmd", COMMAND_COLOR + commands.getCommandName() + DEFAULT_COLOR);
        main.sendPlayerMessage(player, msg);
        msg = ALLOWS_YOU_TO.replace("%Description", SUB_COLOR + commands.getCommandDesc() + DEFAULT_COLOR);
        main.sendPlayerMessage(player, msg);
        main.sendPlayerMessage(player, RIGHT_SYNTAX + " " + commands.getCommandUsage());
        main.sendPlayerMessage(player, EXAMPLE + " " + commands.getCommandExample());
        if (commands.hasSubCommands) {
            commands.subCommands(player);
        }
    }

    public void permDenied(Player player, Commands command, String commandOption, String optionPermission, String optionDescription) {
        String commandName = command.getCommandName() + " " + commandOption;
        String commandPerm = optionPermission;
        String permDesc = optionDescription;
        String msg = PERM_DENIED.replace("%perm", PERMISSION_COLOR + commandPerm + DEFAULT_COLOR);
        msg = msg.replace("%cmd", COMMAND_COLOR + commandName + DEFAULT_COLOR);
        msg = msg.replace("%Description", SUB_COLOR + permDesc + DEFAULT_COLOR);
        main.sendPlayerMessage(player, msg);
    }


    abstract public void subCommands(CommandSender sender);

    abstract public void runCommand(CommandSender sender, String label, String[] args);


    public String getPermission() {
        return permission;
    }

    public String getCommandDesc() {
        return commandDesc;
    }

    public String getCommandExample() {
        return commandExample;
    }

    public String getCommandUsage() {
        return commandUsage;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermission(String permission, String permDesc) {
        this.permission = permission;
        this.permissionDesc = permDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }


    public void setCommandDesc(String commandDesc) {
        this.commandDesc = commandDesc;
    }

    public void setCommandExample(String commandExample) {
        this.commandExample = commandExample;
    }

    public void setCommandUsage(String commandUsage) {
        this.commandUsage = commandUsage;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public boolean isHasSubCommands() {
        return hasSubCommands;
    }

    public void setHasSubCommands(boolean hasSubCommands) {
        this.hasSubCommands = hasSubCommands;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }


    abstract public boolean onPlayerCommand(Player player, String[] args);


}

