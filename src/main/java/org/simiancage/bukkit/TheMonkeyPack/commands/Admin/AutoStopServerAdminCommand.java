package org.simiancage.bukkit.TheMonkeyPack.commands.Admin;

import org.bukkit.command.CommandSender;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.AutoStopServerConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.AutoStopServerHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.AutoStopServerLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: AutoStartServerAdminCommand
 * User: DonRedhorse
 * Date: 31.12.11
 * Time: 20:49
 */

// contains code / features of http://forums.bukkit.org/threads/24652/

public class AutoStopServerAdminCommand {
    private TheMonkeyPack main;
    private AutoStopServerConfig autoStopServerConfig;
    private AutoStopServerLogger autoStopServerLogger;
    public AutoStopServerHelper autoStopServerHelper;

    public AutoStopServerAdminCommand(TheMonkeyPack main) {
        this.main = main;
        autoStopServerConfig = AutoStopServerConfig.getInstance();
        autoStopServerLogger = autoStopServerConfig.getAutoStopServerLogger();
        autoStopServerHelper = autoStopServerConfig.getAutoStopServerHelper();
    }

    public boolean autostopSubCommand(CommandSender sender, String[] args) {
        autoStopServerHelper = autoStopServerConfig.getAutoStopServerHelper();
        boolean cmdProcessed = false;
        String subCommand = args[1].toLowerCase();

        if (subCommand.equalsIgnoreCase("on")) {
            onOption(sender, args);
            cmdProcessed = true;
        }

        if (subCommand.equalsIgnoreCase("off")) {
            offOption(sender, args);
            cmdProcessed = true;
        }

        if ((subCommand.equalsIgnoreCase("h") || subCommand.equalsIgnoreCase("m") || subCommand.equalsIgnoreCase("s"))) {
            specifyTimeOption(sender, args);
            cmdProcessed = true;
        }

        if (subCommand.equalsIgnoreCase("now")) {
            nowOption(sender, args);
            cmdProcessed = true;
        }

        if (subCommand.equalsIgnoreCase("time")) {
            timeOption(sender, args);
            cmdProcessed = true;
        }

        if (subCommand.equalsIgnoreCase("status")) {
            statusOption(sender, args);
            cmdProcessed = true;
        }

        if (subCommand.equalsIgnoreCase("reload")) {
            reloadOption(sender, args);
            cmdProcessed = true;
        }


        return cmdProcessed;
    }

    private void statusOption(CommandSender sender, String[] args) {
        infoMessage(sender, autoStopServerConfig.getMODULE_NAME() + " configuration");
        String msg = "Autostop is ";
        if (autoStopServerConfig.isEnableAutoStop()) {
            msg = msg + "enabled";

        } else {
            msg = msg + "disabled";
        }
        infoMessage(sender, msg);
        timeToServerStop(sender);
        double interval = (autoStopServerConfig.getAutoStopInterval() * 3600);
        int hours = (int) (interval / 3600);
        int minutes = (int) ((interval - hours * 3600) / 60);
        int seconds = (int) interval % 60;
        infoMessage(sender, "The server will be stopped every " + hours + "h" + minutes + "m" + seconds + "s");
        infoMessage(sender, "Warning Interval is: " + autoStopServerConfig.getWarningTimesList().toString());
    }

    private void infoMessage(CommandSender sender, String msg) {
        sender.sendMessage(Commands.INFO_MESSAGES + msg);
    }

    private void nowOption(CommandSender sender, String[] args) {
        sender.sendMessage(Commands.WARNING_MESSAGES + "Ok, you asked for it!");
        autoStopServerHelper.stopServer();
    }

    private void specifyTimeOption(CommandSender sender, String[] args) {
        String timeFormat = args[1];
        double timeAmount = 0;
        try {
            timeAmount = Double.parseDouble(args[2]);
        } catch (Exception e) {
            sender.sendMessage(Commands.WARNING_MESSAGES + "Bad time!");
            return;
        }

// "parse" the restart time
        double restartTime = 0; // in seconds
        if (timeFormat.equalsIgnoreCase("h")) {
            restartTime = timeAmount * 3600;
        } else if (timeFormat.equalsIgnoreCase("m")) {
            restartTime = timeAmount * 60;
        } else if (timeFormat.equalsIgnoreCase("s")) {
            restartTime = timeAmount;
        }

// log to console
        autoStopServerLogger.info(sender.getName() + " is setting a new restart time...");

// ok, we have the proper time
// if the scheduler is already going, cancel it!
        if (autoStopServerConfig.isEnableAutoStop()) {
// ok, cancel all the tasks associated with this plugin!
            autoStopServerHelper.cancelTasks();
        }

// and set the restart interval for /restart time
        autoStopServerConfig.setAutoStopInterval(restartTime / 3600.0);

// now, start it up again!
        autoStopServerLogger.info("scheduling restart tasks...");
        autoStopServerHelper.scheduleTasks();

// and inform!

        timeToServerStop(sender);
    }

    private void timeOption(CommandSender sender, String[] args) {
        timeToServerStop(sender);
    }

    private void offOption(CommandSender sender, String[] args) {
        if (!autoStopServerConfig.isEnableAutoStop()) {
            sender.sendMessage(Commands.INFO_MESSAGES + "AutoStop function is already disabled.");
            return;
        }

        autoStopServerHelper.cancelTasks();
// and inform!
        sender.sendMessage(Commands.INFO_MESSAGES + "Automatic stop have been turned off!");
        autoStopServerLogger.info(sender.getName() + " turned automatic restarts off!");
        autoStopServerHelper.disableAutoStop();
    }

    private void onOption(CommandSender sender, String[] args) {
        if (autoStopServerConfig.isEnableAutoStop()) {
            sender.sendMessage(Commands.INFO_MESSAGES + "The server is already configured to AutoStop.");
            return;
        }

        autoStopServerLogger.info("reloading configuration..");
        reloadOption(sender, args);
        autoStopServerLogger.info("scheduling stop tasks...");
        autoStopServerHelper.scheduleTasks();
        autoStopServerHelper.enableAutoStop();
// and inform!
        sender.sendMessage(Commands.INFO_MESSAGES + "Automatic stop have been turned on!");
        autoStopServerLogger.info(sender.getName() + " turned automatic stops on!");

// ok, now see how long is left!
// (in seconds)
        timeToServerStop(sender);

        autoStopServerHelper.enableAutoStop();

    }

    private void timeToServerStop(CommandSender sender) {
        double timeLeft = (autoStopServerConfig.getAutoStopInterval() * 3600) - ((double) (System.currentTimeMillis() - autoStopServerHelper.getStartTimestamp()) / 1000);
        int hours = (int) (timeLeft / 3600);
        int minutes = (int) ((timeLeft - hours * 3600) / 60);
        int seconds = (int) timeLeft % 60;

        sender.sendMessage(Commands.INFO_MESSAGES + "The server will be stopped in " + hours + "h" + minutes + "m" + seconds + "s");
        autoStopServerLogger.info("The server will be stopped in " + hours + "h" + minutes + "m" + seconds + "s");
    }

    private void reloadOption(CommandSender sender, String[] args) {
        autoStopServerConfig.writeConfig();
        String msg = autoStopServerConfig.reloadConfig();
        sender.sendMessage(Commands.INFO_MESSAGES + msg);
    }


}

