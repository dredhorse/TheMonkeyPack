package org.simiancage.bukkit.TheMonkeyPack.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.TNT_RECLAIM_COMMAND;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTReclaimCommand
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 21:42
 */

// contains code from http://forums.bukkit.org/threads/8145/

public class TNTReclaimCommand extends Commands implements CommandExecutor {

    private TNTControlConfig tntControlConfig = TNTControlConfig.getInstance();
    private TNTControlLogger tntControlLogger;
    private TNTControlHelper tntControlHelper;
    private TheMonkeyPack main;
    private String cmd;
    private String helpOption;
    private String cmdDescription;
    private String cmdPermDescription;
    private String displayHelpMessage;


    public TNTReclaimCommand(TheMonkeyPack instance) {
        super(instance);
        main = (TheMonkeyPack) instance;
        cmd = tntControlConfig.getTNTReclaimCommandConfg(TNT_RECLAIM_COMMAND.TNT_RECLAIM_CMD);
        helpOption = tntControlConfig.getTNTReclaimCommandConfg(TNT_RECLAIM_COMMAND.TNT_RECLAIM_HELP_OPTION);
        cmdDescription = tntControlConfig.getTNTReclaimCommandConfg(TNT_RECLAIM_COMMAND.TNT_RECLAIM_CMD_DESCRIPTION);
        cmdPermDescription = tntControlConfig.getTNTReclaimCommandConfg(TNT_RECLAIM_COMMAND.TNT_RECLAIM_CMD_PERMISSION_DESCRIPTION);
        displayHelpMessage = tntControlConfig.getTNTReclaimCommandConfg(TNT_RECLAIM_COMMAND.TNT_RECLAIM_HELP_MESSAGE);
        this.setCommandName(cmd);
        this.setCommandDesc(cmdDescription);
        this.setCommandUsage(COMMAND_COLOR + "/" + cmd + " [" + tntControlConfig.getMessage(Messages.ON_STRING) + "|" + tntControlConfig.getMessage(Messages.OFF_STRING) + "]");
        this.setCommandExample(COMMAND_COLOR + "/" + cmd + " " + tntControlConfig.getMessage(Messages.ON_STRING));
        this.setPermission(tntControlConfig.getPERM_TNT_RECLAIM_COMMAND(), cmdPermDescription);
        this.setHasSubCommands(false);
        tntControlLogger = tntControlConfig.getTNTControlLogger();
        main.registerPlayerCommand(this.getCommandName(), this);
        mainLogger.debug(cmd + " command registered");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            tntControlLogger.debug("reclaim command executing");
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
        tntControlLogger.debug("reclaim command executing");
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
        tntControlHelper = tntControlConfig.getTNTControlHelper();
        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();

            if (main.hasPermission(player, tntControlConfig.getPERM_TNT_RECLAIM_COMMAND())) {

                if (args.length == 0) {
                    main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_ALLOWED_RECLAIM_COMMAND));
                    if (tntControlHelper.isOnReclaim(player)) {
                        main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.RECLAIMING_IS_ONOFF).replace("%onOff", tntControlConfig.getMessage(Messages.ON_STRING)));
                    } else {
                        main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.RECLAIMING_IS_ONOFF).replace("%onOff", tntControlConfig.getMessage(Messages.OFF_STRING)));
                    }
                    main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.RECLAIM_COMMAND_REQUIRES).replace("%cmd", getCommandName()));
                    return;

                }
                if (args.length >= 1) {
                    tntControlLogger.debug("option", args[0]);
                    if (args[0].equalsIgnoreCase(helpOption)) {
                        tntControlLogger.debug("helpOption", helpOption);
                        displayHelp(player, this);
                        return;
                    }
                    if (args[0].equalsIgnoreCase(tntControlConfig.getMessage(Messages.ON_STRING))) {
                        tntControlHelper.addPlayerToReclaim(player);
                        main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.SETTING_RECLAIM_TO).replace("%onOff", tntControlConfig.getMessage(Messages.ON_STRING)));
                        return;
                    }
                    if (args[0].equalsIgnoreCase(tntControlConfig.getMessage(Messages.OFF_STRING))) {
                        tntControlHelper.removePlayerFromReclaim(player);
                        main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.SETTING_RECLAIM_TO).replace("%onOff", tntControlConfig.getMessage(Messages.OFF_STRING)));
                        return;
                    }
                }
                displayHelp(player, this);
                return;
            } else {
                permDenied(player, this);
            }
            sender.sendMessage("This is not a console command!");
        }
    }
}
