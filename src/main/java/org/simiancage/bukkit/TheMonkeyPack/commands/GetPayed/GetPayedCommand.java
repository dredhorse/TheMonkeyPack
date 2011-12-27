package org.simiancage.bukkit.TheMonkeyPack.commands.GetPayed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedCommand
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 21:42
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class GetPayedCommand extends Commands implements CommandExecutor {

    private GetPayedConfig getPayedConfig = GetPayedConfig.getInstance();
    private GetPayedLogger getPayedLogger;
    private GetPayedHelper getPayedHelper;
    private TheMonkeyPack main;
    private String getPayedCmd;
    private String helpOption;
    private String cmdDescription;
    private String cmdPermDescription;
    private String displayHelpMessage;
    private String priceCheck;
    private String displayPriceCheckMessage;
    private String displayPayDayMessage;
    private String displayDisplayPayDayMessageMessage;
    private String on;
    private String off;
    private String priceCheckMessagesOn;
    private String priceCheckMessagesOff;
    private String payDayMessagesOnOff;
    private String getPayedPerm;


    public GetPayedCommand(TheMonkeyPack instance) {
        super(instance);
        main = (TheMonkeyPack) instance;
        getPayedHelper = getPayedConfig.getGetPayedHelper();
        getPayedCmd = getPayedConfig.getGetPayedCmd();
        helpOption = getPayedConfig.getHelpOption();
        cmdDescription = getPayedConfig.getGetPayedCmdDescription();
        cmdPermDescription = getPayedConfig.getGetPayedCmdPermDescription();
        displayHelpMessage = getPayedConfig.getDisplayHelpMessage();
        priceCheck = getPayedConfig.getPriceCheck();
        displayPriceCheckMessage = getPayedConfig.getDisplayPriceCheckMessage();
        displayPayDayMessage = getPayedConfig.getDisplayPayDayMessage();
        displayDisplayPayDayMessageMessage = getPayedConfig.getDisplayDisplayPayDayMessageMessage();
        on = getPayedConfig.getOn();
        off = getPayedConfig.getOff();
        priceCheckMessagesOn = getPayedConfig.getPriceCheckMessagesOn();
        priceCheckMessagesOff = getPayedConfig.getPriceCheckMessagesOff();
        payDayMessagesOnOff = getPayedConfig.getPayDayMessagesOnOff();
        getPayedPerm = getPayedConfig.getPERM_GETPAYED();
        this.setCommandName(getPayedCmd);
        this.setCommandDesc(cmdDescription);
        this.setCommandUsage(COMMAND_COLOR + "/" + getPayedCmd + OPTIONAL_COLOR + " [option] [option]");
        this.setCommandExample(COMMAND_COLOR + "/" + getPayedCmd + SUB_COLOR + " " + priceCheck + " " + on);
        this.setPermission(getPayedPerm, cmdPermDescription);
        this.setHasSubCommands(true);
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        main.registerPlayerCommand(this.getCommandName(), this);
        mainLogger.debug(getPayedCmd + " command registered");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            getPayedLogger.debug(getPayedCmd + " command executing");
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
        getPayedLogger.debug(getPayedCmd + " command executing");
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
        String msg = "/" + COMMAND_COLOR + getPayedCmd + SUB_COLOR + " ";
        sender.sendMessage(msg + helpOption + DEFAULT_COLOR + " " + displayHelpMessage);
        sender.sendMessage(msg + priceCheck + DEFAULT_COLOR + " " + displayPriceCheckMessage);
        sender.sendMessage(msg + displayPayDayMessage + DEFAULT_COLOR + " " + displayDisplayPayDayMessageMessage);
    }


    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {

        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();
            if (args.length == 0) {
                displayHelp(player, this);
            }
        }

        if (player == null) {
            sender.sendMessage("Please look up the command syntax! This is not a console command");
            return;
        }

        // not enough arguments as we need always at least 2
        if (args.length == 0) {
            displayHelp(player, this);
            return;
        }

        String command = args[0];
// somebody did use the help option
        if (command.equalsIgnoreCase(helpOption)) {
            displayHelp(player, this);
            return;
        }

        String msg;

// somebody is using the pricecheck option
        if (command.equalsIgnoreCase(priceCheck)) {

            if (args.length == 0) {
                displayHelp(player, this);
                return;
            }

            String onOff = args[1];
            if (onOff.equalsIgnoreCase(on)) {
                getPayedHelper.removePlayerFromPriceCheckOn(player);
                getPayedHelper.addPlayerToPriceCheckOn(player, true);
                msg = INFO_MESSAGES + priceCheckMessagesOn;
                main.sendPlayerMessage(player, msg);
                return;
            }
            if (onOff.equalsIgnoreCase(off)) {
                getPayedHelper.removePlayerFromPriceCheckOn(player);
                getPayedHelper.addPlayerToPriceCheckOn(player, false);
                msg = INFO_MESSAGES + priceCheckMessagesOff;
                main.sendPlayerMessage(player, msg);
                return;
            }
            displayHelp(player, this);
            return;

        }

// somebody is using the paydaymessage option

        if (command.equalsIgnoreCase(displayPayDayMessage)) {
            if (args.length == 0) {
                displayHelp(player, this);
                return;
            }
            String onOff = args[1];
            if (onOff.equalsIgnoreCase(on)) {
                getPayedHelper.removePlayerFromPayDayMessageOn(player);
                getPayedHelper.addPlayerToPayDayMessageOn(player, true);
                msg = INFO_MESSAGES + payDayMessagesOnOff;
                msg = msg.replace("%onOff", on);
                main.sendPlayerMessage(player, msg);
                return;
            }
            if (onOff.equalsIgnoreCase(off)) {
                getPayedHelper.removePlayerFromPayDayMessageOn(player);
                getPayedHelper.addPlayerToPayDayMessageOn(player, false);
                msg = INFO_MESSAGES + payDayMessagesOnOff;
                msg = msg.replace("%onOff", off);
                main.sendPlayerMessage(player, msg);
                return;
            }
            displayHelp(player, this);
            return;
        }

    }

}
