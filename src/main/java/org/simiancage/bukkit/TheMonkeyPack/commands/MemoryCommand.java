package org.simiancage.bukkit.TheMonkeyPack.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.AutoStopServerConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.AutoStopServerConfig.MEMORY_COMMAND;
import org.simiancage.bukkit.TheMonkeyPack.helpers.AutoStopServerHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.AutoStopServerLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: MemoryCommand
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 21:42
 */

// contains code / features of http://forums.bukkit.org/threads/24652/

public class MemoryCommand extends Commands implements CommandExecutor {

    private AutoStopServerConfig autoStopServerConfig = AutoStopServerConfig.getInstance();
    private AutoStopServerLogger autoStopServerLogger;
    private AutoStopServerHelper autoStopServerHelper;
    private TheMonkeyPack main;
    private String cmd;
    private String helpOption;
    private String cmdDescription;
    private String cmdPermDescription;
    private String displayHelpMessage;


    public MemoryCommand(TheMonkeyPack instance) {
        super(instance);
        main = (TheMonkeyPack) instance;
        cmd = autoStopServerConfig.getMemoryCommandConfig(MEMORY_COMMAND.MEMORY_CMD);
        helpOption = autoStopServerConfig.getMemoryCommandConfig(MEMORY_COMMAND.MEMORY_HELP_OPTION);
        cmdDescription = autoStopServerConfig.getMemoryCommandConfig(MEMORY_COMMAND.MEMORY_CMD_DESCRIPTION);
        cmdPermDescription = autoStopServerConfig.getMemoryCommandConfig(MEMORY_COMMAND.MEMORY_CMD_DESCRIPTION);
        displayHelpMessage = autoStopServerConfig.getMemoryCommandConfig(MEMORY_COMMAND.MEMORY_HELP_MESSAGE);
        this.setCommandName(cmd);
        this.setCommandDesc(cmdDescription);
        this.setCommandUsage(COMMAND_COLOR + "/" + cmd);
        this.setCommandExample(COMMAND_COLOR + "/" + cmd);
        this.setPermission(autoStopServerConfig.getPERM_MEMORY_COMMAND(), cmdPermDescription);
        this.setHasSubCommands(false);
        autoStopServerLogger = autoStopServerConfig.getAutoStopServerLogger();
        main.registerPlayerCommand(this.getCommandName(), this);
        mainLogger.debug(cmd + " command registered");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            autoStopServerLogger.debug("memory command executing");
            if (!main.hasPermission(player, getPermission())) {
                permDenied(player, this);
                return true;
            } else {
                runCommand(commandSender, label, args);
                return true;
            }
        }

        return false;
    }

    public boolean onPlayerCommand(Player player, String[] args) {
        autoStopServerLogger.debug("memory command executing");
        if (!main.hasPermission(player, getPermission())) {
            permDenied(player, this);
            return true;
        } else {
            runCommand(player, this.getCommandName(), args);
            return true;
        }


    }

    @Override
    public void subCommands(CommandSender sender) {

    }


    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {
        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();

// show memory usage
            float freeMemory = (float) java.lang.Runtime.getRuntime().freeMemory() / (1024F * 1024F);
            float totalMemory = (float) java.lang.Runtime.getRuntime().totalMemory() / (1024F * 1024F);
            float maxMemory = (float) java.lang.Runtime.getRuntime().maxMemory() / (1024F * 1024F);

            main.sendPlayerMessage(player, INFO_MESSAGES + "Free memory: " + OPTIONAL_COLOR + String.format("%.1f", freeMemory) + "MB");
            main.sendPlayerMessage(player, INFO_MESSAGES + "Total memory: " + OPTIONAL_COLOR + String.format("%.1f", totalMemory) + "MB");
            main.sendPlayerMessage(player, INFO_MESSAGES + "Max memory: " + OPTIONAL_COLOR + String.format("%.1f", maxMemory) + "MB");
            return;
        }
        sender.sendMessage("This is not a console command!");
    }
}
