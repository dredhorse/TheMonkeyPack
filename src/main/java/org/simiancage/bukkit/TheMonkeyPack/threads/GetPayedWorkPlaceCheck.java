package org.simiancage.bukkit.TheMonkeyPack.threads;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedWorkPlaceCheck
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */

// contains code from http://forums.bukkit.org/threads/20984/


public class GetPayedWorkPlaceCheck
        implements Runnable {
    private TheMonkeyPack main = null;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected GetPayedConfig getPayedConfig;
    protected GetPayedLogger getPayedLogger;
    private GetPayedHelper getPayedHelper;
    private final ChatColor MODULE = ChatColor.GREEN;
    private final ChatColor DEFAULT = ChatColor.WHITE;
    private String moduleName;

    public GetPayedWorkPlaceCheck(TheMonkeyPack instance) {
        main = instance;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        getPayedConfig = GetPayedConfig.getInstance();
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        getPayedHelper = GetPayedHelper.getInstance(main);
        moduleName = getPayedConfig.getMODULE_NAME();
    }

    public void run() {
        Player[] onlinePlayers = main.getServer().getOnlinePlayers();
        for (int i = 0; i < onlinePlayers.length; i++)

        {
            Player player = onlinePlayers[i];
            int inWorkPlace = getPayedHelper.isPositionInWorkPlace(player.getLocation());
            if (inWorkPlace != -1) {
                if (getPayedHelper.isPlayerInWorksPlace(player)) {
                    if (getPayedHelper.getWorkPlace(player) != inWorkPlace) {
                        getPayedHelper.removePlayerFromWorkplace(player);
                        getPayedHelper.addPlayerToWorkplace(player, inWorkPlace);
                        player.sendMessage(getPayedConfig.getWorkPlaceGreeting());
                        break;
                    }
                } else {
                    getPayedHelper.addPlayerToWorkplace(player, inWorkPlace);
                    player.sendMessage(getPayedConfig.getWorkPlaceGreeting());
                    break;
                }
            }

            if ((inWorkPlace != -1) || (!getPayedHelper.isPlayerInWorksPlace(player))) {
                continue;
            }
            player.sendMessage(getPayedConfig.getWorkPlaceFarewell());
            getPayedHelper.removePlayerFromWorkplace(player);
        }

        getPayedHelper.setCurrentWPTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, this, getPayedConfig.getEntryExitRefreshRate()));
    }
}
