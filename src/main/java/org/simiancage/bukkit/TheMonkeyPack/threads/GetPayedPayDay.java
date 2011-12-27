package org.simiancage.bukkit.TheMonkeyPack.threads;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.util.Iterator;
import java.util.Map;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedPayDay
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */

// contains code from http://forums.bukkit.org/threads/20984/


public class GetPayedPayDay
        implements Runnable {
    private TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected GetPayedConfig getPayedConfig;
    protected GetPayedLogger getPayedLogger;
    private GetPayedHelper getPayedHelper;
    private final ChatColor MODULE = ChatColor.GREEN;
    private final ChatColor DEFAULT = ChatColor.WHITE;
    private String moduleName;


    public GetPayedPayDay(TheMonkeyPack instance) {
        main = instance;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        getPayedConfig = GetPayedConfig.getInstance();
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        getPayedHelper = GetPayedHelper.getInstance(main);
        moduleName = getPayedConfig.getMODULE_NAME();

    }

    public void run() {
        if (!mainConfig.isGetPayedActive()) {
            return;
        }

        getPayedLogger.debug("PayDay Fired!");
        if (getPayedHelper.getPlayerMap().size() == 0) {
            getPayedLogger.debug("No players earned anything this payday");
        }


        Iterator itr = getPayedHelper.getPlayerMap().entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry playerPay = (Map.Entry) itr.next();
            Player player = main.getServer().getPlayer(playerPay.getKey().toString());
            if (player == null) {
                continue;
            }

            main.getEconomy().depositPlayer(player.getName(), (Double) playerPay.getValue());

            if (!getPayedConfig.isPayDayMessageEnabled()) {
                continue;
            }
            if (!(getPayedHelper.isPaydayMessageOn(player))) {
                continue;
            }
            String userMessage = getPayedConfig.getPayDayMessage();

            if (userMessage.indexOf("$") > -1) {
                String firstPart = userMessage.substring(0, userMessage.indexOf("$"));
                String secondPart;
                if (userMessage.indexOf("$") + 2 < userMessage.length()) {
                    secondPart = userMessage.substring(userMessage.indexOf("$") + 2, userMessage.length());
                } else {
                    secondPart = "";
                }

                userMessage = firstPart + main.getEconomy().format((Double) playerPay.getValue()) + secondPart;
            }

            player.sendMessage(MODULE + "[" + moduleName + "] " + DEFAULT + userMessage);

            getPayedLogger.debug("Paid " + playerPay.getKey().toString() + " " + playerPay.getValue() + " on this Payday");

        }

        itr = getPayedHelper.getPlayerPayments().entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry employerPay = (Map.Entry) itr.next();
            Map employees = (Map) employerPay.getValue();
            Iterator employeeItr = employees.entrySet().iterator();
            Player employer = main.getServer().getPlayer(employerPay.getKey().toString());
            double employerPaidOut = 0.0D;

            while (employeeItr.hasNext()) {
                Map.Entry employeePay = (Map.Entry) employeeItr.next();
                Player employee = main.getServer().getPlayer(employeePay.getKey().toString());


                main.getEconomy().depositPlayer(employeePay.getKey().toString(), (Double) employeePay.getValue());
                if (employee.isOnline()) {
                    String employerMessage = getPayedConfig.getPayDayEmployedMessage();
                    employerMessage = MODULE + "[" + moduleName + "] " + DEFAULT + employerMessage.replace("$$", main.getEconomy().format((Double) employeePay.getValue()));
                    employerMessage = employerMessage.replace("%employer", employer.getDisplayName());
                    employee.sendMessage(employerMessage);
                }


                employerPaidOut += ((Double) employeePay.getValue()).doubleValue();
            }

            main.getEconomy().withdrawPlayer(employer.getName(), employerPaidOut);

            if (employer.isOnline()) {
                String employeeMessage = getPayedConfig.getPayDayEmployerMessage();
                employeeMessage = MODULE + "[" + moduleName + "] " + DEFAULT + employeeMessage.replace("$$", main.getEconomy().format(employerPaidOut));
                employer.sendMessage(employeeMessage);
            }


        }

        if (getPayedConfig.isClearBufferOnPayday()) {
            getPayedHelper.clearBlackList();
        }

        getPayedHelper.clearPlayerMap();
        getPayedHelper.clearPlayerPayments();
        getPayedHelper.setCurrentTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, getPayedHelper.getPayDayTask(), getPayedConfig.getPayDayInterval() * 20));
    }
}
