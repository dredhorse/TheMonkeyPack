package org.simiancage.bukkit.TheMonkeyPack.threads;

import org.bukkit.ChatColor;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedSaveWorkPlaces
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */

// contains code from http://forums.bukkit.org/threads/20984/


public class GetPayedSaveWorkPlaces
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


    public GetPayedSaveWorkPlaces(TheMonkeyPack instance) {
        main = instance;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        getPayedConfig = GetPayedConfig.getInstance();
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        getPayedHelper = GetPayedHelper.getInstance(main);
        moduleName = getPayedConfig.getMODULE_NAME();
    }

    public void run() {
        File workPlaceSaveFile = new File(main.getDataFolder() + System.getProperty("file.separator") + getPayedHelper.getWORKPLACES_FILE());
        try {
            workPlaceSaveFile.createNewFile();
            FileOutputStream outFile = new FileOutputStream(workPlaceSaveFile);
            ObjectOutputStream out = new ObjectOutputStream(outFile);
            out.writeObject(getPayedHelper.getWorkPlacesX1());
            out.writeObject(getPayedHelper.getWorkPlacesX2());
            out.writeObject(getPayedHelper.getWorkPlacesZ1());
            out.writeObject(getPayedHelper.getWorkPlacesZ2());
            out.writeObject(getPayedHelper.getWorkPlacesInfo());
            out.writeObject(getPayedHelper.getWorkPlaceNames());
            out.flush();
            out.close();
        } catch (IOException ex) {
            getPayedLogger.severe("Problems saving the workplace file", ex);
        }

        getPayedLogger.debug("Workplaces saved");

        getPayedHelper.setCurrentWPSaveTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, getPayedHelper.getWorkPlaceSaveRoutine(), getPayedConfig.getWorkPlaceSaveInterval()));
    }
}
