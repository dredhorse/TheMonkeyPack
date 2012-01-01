package org.simiancage.bukkit.TheMonkeyPack.helpers;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.AutoStopServerConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.AutoStopServerConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.AutoStopServerLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * PluginName: TheMonkeyPack
 * Class: AutoStopServerHelper
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */

// contains code / features of http://forums.bukkit.org/threads/24652/


public class AutoStopServerHelper {

    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected AutoStopServerConfig autoStopServerConfig;
    protected AutoStopServerLogger autoStopServerLogger;
    protected AutoStopServerHelper autoStopServerHelper;
    static AutoStopServerHelper instance;
    private int autoStopServerSchedulerID;
    private List<Double> warnTimes;
    private Timer stopTimer;
    private ArrayList<Timer> warningTimers = new ArrayList<Timer>();
    private long startTimestamp;


    private AutoStopServerHelper(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        autoStopServerConfig = AutoStopServerConfig.getInstance();
        autoStopServerLogger = autoStopServerConfig.getAutoStopServerLogger();
        warnTimes = autoStopServerConfig.getWarningTimesList();

    }

    public static AutoStopServerHelper getInstance(TheMonkeyPack plugin) {
        if (instance == null) {
            instance = new AutoStopServerHelper(plugin);
        }
        return instance;
    }


    public void onDisable() {
        cancelTasks();

    }

    public void onEnable() {
        if (autoStopServerConfig.isEnableAutoStop()) {
            scheduleTasks();
        }
    }


// Methods

    public void enableAutoStop() {
        autoStopServerConfig.setEnableAutoStop(true);
        autoStopServerLogger.info("Enabling AutoStop function");
    }

    public void disableAutoStop() {
        autoStopServerConfig.setEnableAutoStop(false);
        autoStopServerLogger.info("Disabling AutoStop function");
    }

    public void cancelTasks() {
//plugin.getServer().getScheduler().cancelTasks(plugin);
        for (int i = 0; i < warningTimers.size(); i++) {
            warningTimers.get(i).cancel();
        }
        warningTimers.clear();
        if (stopTimer != null) {
            stopTimer.cancel();
        }
        stopTimer = new Timer();
        disableAutoStop();
    }

    public void scheduleTasks() {
        cancelTasks();
// start the warning tasks
        for (int i = 0; i < warnTimes.size(); i++) {
            if (autoStopServerConfig.getAutoStopInterval() * 60 - warnTimes.get(i) > 0) {
// only do "positive" warning times
// start the warning task
                final double warnTime = warnTimes.get(i);
                Timer warnTimer = new Timer();
                warningTimers.add(warnTimer);
                warnTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String warningMessage = autoStopServerConfig.getMessage(Messages.WARNING_MESSAGE).replaceAll("%m", "" + warnTime);
                        main.getServer().broadcastMessage(Commands.INFO_MESSAGES + warningMessage);
                        autoStopServerLogger.info(warningMessage);
                    }
                }, (long) ((autoStopServerConfig.getAutoStopInterval() * 60 - warnTimes.get(i)) * 60000.0));
                autoStopServerLogger.info("Warning message scheduled for " + (long) ((autoStopServerConfig.getAutoStopInterval() * 60 - warnTimes.get(i)) * 60.0) + " seconds from now!");
            }
        }

        stopTimer = new Timer();
        stopTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopServer();
            }
        }, (long) (autoStopServerConfig.getAutoStopInterval() * 3600000.0));

        autoStopServerLogger.info("Stop scheduled for " + (long) (autoStopServerConfig.getAutoStopInterval() * 3600.0) + " seconds from now!");
        enableAutoStop();
        startTimestamp = System.currentTimeMillis();
    }

    // kick all players from the server
// with a friendly message!
    void clearServer() {
        String stopMessage = autoStopServerConfig.getMessage(Messages.STOP_MESSAGE);
        main.getServer().broadcastMessage(Commands.WARNING_MESSAGES + stopMessage);
        Player[] players = main.getServer().getOnlinePlayers();
        for (Player player : players) {
            player.kickPlayer(stopMessage);
        }
    }

    // shut the server down!
    public boolean stopServer() {
// log it and empty out the server first
        autoStopServerLogger.warning("Restarting...");
        clearServer();
        try {

            ConsoleCommandSender sender = main.getServer().getConsoleSender();
            ;
            main.getServer().dispatchCommand(sender, "save-all");
            main.getServer().dispatchCommand(sender, "stop");

        } catch (Exception e) {
            autoStopServerLogger.severe("Something went wrong!", e);
            return false;
        }
        return true;
    }


// Getters & Setters


    public long getStartTimestamp() {
        return startTimestamp;
    }
}

