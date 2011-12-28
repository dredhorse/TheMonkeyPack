package org.simiancage.bukkit.TheMonkeyPack.commands.GetPayed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: PriceSetCommand
 * User: DonRedhorse
 * Date: 23.12.11
 * Time: 23:56
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class PriceSetCommand extends Commands implements CommandExecutor {

    private GetPayedConfig getPayedConfig = GetPayedConfig.getInstance();
    private GetPayedLogger getPayedLogger;
    private GetPayedHelper getPayedHelper;
    private TheMonkeyPack main;
    private String priceSetCmd;
    private String displayPriceSetMessage;
    private String newPrice;
    private String breakString;
    private String placeString;
    private String priceSetPerm;
    private String helpOption;
    private String displayHelpMessage;
    private String cmdDescription;
    private String cmdPermDescription;
    private String on;
    private String off;


    public PriceSetCommand(TheMonkeyPack instance) {
        super(instance);
        main = (TheMonkeyPack) instance;
        getPayedHelper = getPayedConfig.getGetPayedHelper();
        helpOption = getPayedConfig.getHelpOption();
        cmdDescription = getPayedConfig.getPriceSetCmdDescription();
        cmdPermDescription = getPayedConfig.getPriceSetCmdPermDescription();
        displayHelpMessage = getPayedConfig.getDisplayHelpMessage();
        on = getPayedConfig.getOnString();
        off = getPayedConfig.getOffString();
        priceSetCmd = getPayedConfig.getPriceSetCmd();
        displayPriceSetMessage = getPayedConfig.getDisplayPriceSetMessage();
        newPrice = getPayedConfig.getNewPrice();
        breakString = getPayedConfig.getBreakString();
        placeString = getPayedConfig.getPlaceString();
        priceSetPerm = getPayedConfig.getPERM_PRICESET();
        this.setCommandName(priceSetCmd);
        this.setCommandDesc(cmdDescription);
        this.setCommandUsage(COMMAND_COLOR + "/" + priceSetCmd + OPTIONAL_COLOR + " <" + breakString + "|" + placeString + "> " + newPrice);
        this.setCommandExample(COMMAND_COLOR + "/" + priceSetCmd + SUB_COLOR + " " + breakString + " 10.5");
        this.setPermission(priceSetPerm, cmdPermDescription);
        this.setHasSubCommands(true);
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        main.registerPlayerCommand(this.getCommandName(), this);
        mainLogger.debug(priceSetCmd + " command registered");
    }


    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {
        getPayedHelper = getPayedConfig.getGetPayedHelper();
        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();
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


        if (args.length < 1) {
            displayHelp(player, this);
            return;
        }
        double newBlockPrice;
        String breakPlace = args[0];
        try {
            newBlockPrice = Double.parseDouble(args[1]);
        } catch (NumberFormatException ex) {
            displayHelp(player, this);
            return;
        }


        String msg;
        if (breakPlace.equalsIgnoreCase(breakString)) {
            if (getPayedHelper.waitingForPlace(player)) {
                msg = WARNING_MESSAGES + getPayedConfig.getMessage(Messages.CANT_BREAK_PLACE_AT_SAME_TIME);
                msg = msg.replace("%breakPlace", placeString);
                msg = msg.replace("%onOff", off);
                main.sendPlayerMessage(player, msg);
            }
            getPayedHelper.setWaitForBreakPlace(player, "break");
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.RIGHT_CLICK_BLOCK_TO_PRICESET);
            msg = msg.replace("%breakPlace", breakString);
            main.sendPlayerMessage(player, msg);
            getPayedHelper.setNewPriceSet(player, newBlockPrice);
            return;
        }

        if (breakPlace.equalsIgnoreCase(placeString)) {
            if (getPayedHelper.waitingForBreak(player)) {
                msg = WARNING_MESSAGES + getPayedConfig.getMessage(Messages.CANT_BREAK_PLACE_AT_SAME_TIME);
                msg = msg.replace("%breakPlace", breakString);
                msg = msg.replace("%onOff", off);
                main.sendPlayerMessage(player, msg);
            }
            getPayedHelper.setWaitForBreakPlace(player, "place");
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.RIGHT_CLICK_BLOCK_TO_PRICESET);
            msg = msg.replace("%breakPlace", placeString);
            getPayedHelper.setNewPriceSet(player, newBlockPrice);
            main.sendPlayerMessage(player, msg);
            return;
        }
        displayHelp(player, this);

    }


    @Override
    public void subCommands(CommandSender sender) {
        String msg = "/" + COMMAND_COLOR + priceSetCmd + SUB_COLOR + " ";
        sender.sendMessage(msg + helpOption + DEFAULT_COLOR + " " + displayHelpMessage);
        sender.sendMessage(msg + breakString + DEFAULT_COLOR + " " + newPrice);
        sender.sendMessage(msg + placeString + DEFAULT_COLOR + " " + newPrice);
    }


    @Override
    public boolean onPlayerCommand(Player player, String[] args) {
        getPayedLogger.debug(priceSetCmd + " command executing");
        if (!main.hasPermission(player, getPermission())) {
            permDenied(player, this);
            return true;
        } else {
            runCommand(player, this.getCommandName(), args);
            return true;
        }


    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            getPayedLogger.debug(priceSetCmd + " command executing");
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


}

