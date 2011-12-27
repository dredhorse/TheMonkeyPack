package org.simiancage.bukkit.TheMonkeyPack.helpers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;
import org.simiancage.bukkit.TheMonkeyPack.threads.GetPayedPayDay;
import org.simiancage.bukkit.TheMonkeyPack.threads.GetPayedSaveWorkPlaces;
import org.simiancage.bukkit.TheMonkeyPack.threads.GetPayedWorkPlaceCheck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedHelper
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 15:36
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class GetPayedHelper {

    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected GetPayedConfig getPayedConfig;
    protected GetPayedLogger getPayedLogger;
    protected GetPayedHelper getPayedHelper;
    static GetPayedHelper instance;

    private List<Location> blackList;
    /*    protected ArrayList<Integer> blackListX = new ArrayList();
protected ArrayList<Integer> blackListY = new ArrayList();
protected ArrayList<Integer> blackListZ = new ArrayList();*/
    protected Map<String, Double> playerMap = new HashMap();
    protected Map<String, Collection> configArea;

    protected Map<String, Boolean> priceCheckOn = new HashMap();
    protected Map<String, Boolean> paydayMessageOn = new HashMap();

    protected Map<String, Map<String, Integer>> workPlaceTempPoints = new HashMap();
    protected ArrayList<Integer> workPlacesX1 = new ArrayList();
    protected ArrayList<Integer> workPlacesZ1 = new ArrayList();
    protected ArrayList<Integer> workPlacesX2 = new ArrayList();
    protected ArrayList<Integer> workPlacesZ2 = new ArrayList();
    protected Map<Integer, Map<String, Object>> workPlacesInfo = new HashMap();
    protected GetPayedWorkPlaceCheck workPlaceCheck = new GetPayedWorkPlaceCheck(main);
    protected Map<String, Integer> playersInWorkPlace = new HashMap();
    protected Map<String, Map<String, Double>> playerPayments = new HashMap();
    protected GetPayedSaveWorkPlaces workPlaceSaveRoutine = new GetPayedSaveWorkPlaces(main);
    protected GetPayedPayDay payDayTask = new GetPayedPayDay(main);
    protected Map<String, Integer> workPlaceNames = new HashMap();
    protected int currentWPTaskId;
    protected int currentWPSaveTaskId;
    protected int currentTaskId = -1;
    private final String GETPAYED_ITEMS_FILE = "GetPayedItems.txt";
    File itemsFile = new File(main.getDataFolder() + GETPAYED_ITEMS_FILE);
    private Map<String, String> waitForBreakPlace = new HashMap<String, String>();


    public Map<Integer, String> items = new HashMap();
    public ArrayList<String> orderingList = new ArrayList();


    private GetPayedHelper(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        getPayedConfig = GetPayedConfig.getInstance();
        getPayedLogger = getPayedConfig.getGetPayedLogger();
    }

    public static GetPayedHelper getInstance(TheMonkeyPack plugin) {
        if (instance == null) {
            instance = new GetPayedHelper(plugin);
        }
        return instance;
    }


    public void onDisable() {
        main.getServer().getScheduler().cancelTasks(main);
        main.getServer().getScheduler().scheduleAsyncDelayedTask(main, workPlaceSaveRoutine, 1L);
/*        blackListX = new ArrayList();
        blackListY = new ArrayList();
        blackListZ = new ArrayList();*/
        playerMap = new HashMap();
        getPayedConfig.clearBlockPrices();
        configArea = new HashMap();
        priceCheckOn = new HashMap();
        paydayMessageOn = new HashMap();
        workPlacesX1 = new ArrayList();
        workPlacesZ1 = new ArrayList();
        workPlacesX2 = new ArrayList();
        workPlacesZ2 = new ArrayList();
        workPlacesInfo = new HashMap();
        playerPayments = new HashMap();
        currentTaskId = -1;
        currentWPTaskId = -1;
        currentWPSaveTaskId = -1;
        items.clear();
        orderingList.clear();
    }

    public void onEnable() {

        for (Material material : Material.values()) {
            items.put(material.getId(), material.name());
            orderingList.add(material.name());
        }

        if (itemsFile.exists()) {
            itemsFile.delete();
        }

        try {
            itemsFile.createNewFile();
            FileOutputStream outItemsFile = new FileOutputStream(itemsFile);
            PrintWriter outItems = new PrintWriter(outItemsFile);
            for (int i = 0; i < orderingList.size(); i++) {
                outItems.println((String) orderingList.get(i));
            }
            outItems.flush();
            outItems.close();
            outItemsFile.flush();
            outItemsFile.close();
        } catch (IOException ex) {
            getPayedLogger.severe("Problems creating the " + GETPAYED_ITEMS_FILE + " file", ex);
        }

        currentTaskId = main.getServer().getScheduler().scheduleAsyncDelayedTask(main, payDayTask, getPayedConfig.getPayDayInterval() * 20);
        currentWPTaskId = main.getServer().getScheduler().scheduleAsyncDelayedTask(main, workPlaceCheck, getPayedConfig.getEntryExitRefreshRate());
        currentWPSaveTaskId = main.getServer().getScheduler().scheduleAsyncDelayedTask(main, workPlaceSaveRoutine, getPayedConfig.getWorkPlaceSaveInterval());


    }


    public int isPositionInWorkPlace(Location loc) {
        for (int j = 0; j < workPlacesX1.size(); j++) {
            if (loc.getX() < ((Integer) workPlacesX1.get(j)).intValue()) {
                continue;
            }
            if (loc.getX() > ((Integer) workPlacesX2.get(j)).intValue()) {
                continue;
            }
            if (loc.getZ() < ((Integer) workPlacesZ1.get(j)).intValue()) {
                continue;
            }
            if (loc.getZ() <= ((Integer) workPlacesZ2.get(j)).intValue()) {
                return j;
            }

        }

        return -1;
    }

// Getters & Setters

    public int getPlayerWorkPlaceIndex(Player player) {
        return playersInWorkPlace.get(player.getName());
    }

    public void addWorkPlaceNames(String workplacename, int index) {
        workPlaceNames.put(workplacename, index);
    }


    public void removeWorkPlaceNames(String workplacename) {
        workPlaceNames.remove(workplacename);
    }

    public void addWorkPlaceInfoRecord(int index, Map<String, Object> info) {
        workPlacesInfo.put(index, info);
    }

    public void removeWorkPlaceInfoRecord(int index) {
        workPlacesInfo.remove(index);
    }

    public Map<String, Object> getWorkPlaceInfoRecord(int index) {
        return workPlacesInfo.get(index);
    }

    public int getWorkPlaceNamesIndex(String workplace) {
        return workPlaceNames.get(workplace);
    }

    public void removePlayersTempSelectionPoints(Player player) {
        workPlaceTempPoints.remove(player.getName());
    }

    public int getNumberOfWorkplaces() {
        return workPlacesX1.size();
    }

    public int getNextWorkplace() {
        return workPlacesX1.size() - 1;
    }


    public void removeWorkplacesX1(int x1) {
        workPlacesX1.remove(x1);
    }

    public void removeWorkplacesX2(int x2) {
        workPlacesX2.remove(x2);
    }

    public void removeWorkplacesZ1(int z1) {
        workPlacesZ1.remove(z1);
    }

    public void removeWorkplacesZ2(int z2) {
        workPlacesZ2.remove(z2);
    }

    public void addWorkplacesX1(int x1) {
        workPlacesX1.add(x1);
    }

    public void addWorkplacesX2(int x2) {
        workPlacesX2.add(x2);
    }

    public void addWorkplacesZ1(int z1) {
        workPlacesZ1.add(z1);
    }

    public void addWorkplacesZ2(int z2) {
        workPlacesZ2.add(z2);
    }

    public Map<String, Integer> getPlayersWorkplaceTempPoints(Player player) {
        return workPlaceTempPoints.get(player.getName());
    }

    public boolean playerHasSelectedPoints(Player player) {
        return workPlaceTempPoints.containsKey(player.getName());
    }

    public boolean workplaceAlreadyExist(String workplace) {
        return workPlaceNames.containsKey(workplace);
    }

    public boolean waitingForBreak(Player player) {
        return waitForBreakPlace.get(player.getName()).equalsIgnoreCase("break");
    }

    public boolean waitingForPlace(Player player) {
        return waitForBreakPlace.get(player.getName()).equalsIgnoreCase("place");
    }

    public void setWaitForBreakPlace(Player player, String breakPlace) {
        waitForBreakPlace.put(player.getName(), breakPlace);
    }

    public void addPlayerPayments(int workplace, Map temp) {
        String owner = workPlacesInfo.get(workplace).get("OwnedBy").toString();
        playerPayments.put(owner, temp);
    }

    public void removePlayerPayments(int workplace) {
        String owner = workPlacesInfo.get(workplace).get("OwnedBy").toString();
        playerPayments.remove(owner);


    }

    public void addBlockToBlacklist(Location location) {
        blackList.add(location);
    }

    public void removeBlockFromBlacklist(int index) {
        blackList.remove(index);
    }

    public void removeBlockFromBlacklist(Location location) {
        int index = blackList.indexOf(location);
        blackList.remove(index);
    }

    public int sizeOfBlackList() {
        return blackList.size();
    }

    public boolean blockIsInBlackList(Location location) {
        return blackList.contains(location);
    }

    public void addPlayerToWorkplace(Player player, int workplace) {
        playersInWorkPlace.put(player.getName(), workplace);
    }

    public void removePlayerFromWorkplace(Player player) {
        playersInWorkPlace.remove(player.getName());
    }

    public void removePlayerFromPriceCheckOn(Player player) {
        priceCheckOn.remove(player.getName());
    }

    public void addPlayerToPriceCheckOn(Player player, boolean on) {
        priceCheckOn.put(player.getName(), on);

    }

    public void removePlayerFromPayDayMessageOn(Player player) {
        paydayMessageOn.remove(player.getName());
    }

    public void addPlayerToPayDayMessageOn(Player player, boolean on) {
        paydayMessageOn.put(player.getName(), on);
    }

    public int getWorkPlace(Player player) {
        return playersInWorkPlace.get(player.getName());
    }

    public boolean isPlayerInWorksPlace(Player player) {
        return playersInWorkPlace.containsKey(player.getName());
    }

    public GetPayedSaveWorkPlaces getWorkPlaceSaveRoutine() {
        return workPlaceSaveRoutine;
    }

    public GetPayedWorkPlaceCheck getWorkPlaceCheck() {
        return workPlaceCheck;
    }

    public Map<String, Integer> getWorkPlaceNames() {
        return workPlaceNames;
    }

    public Map<Integer, Map<String, Object>> getWorkPlacesInfo() {
        return workPlacesInfo;
    }

    public List<Location> getBlackList() {
        return blackList;
    }

    public ArrayList<Integer> getWorkPlacesX1() {
        return workPlacesX1;
    }

    public ArrayList<Integer> getWorkPlacesX2() {
        return workPlacesX2;
    }

    public ArrayList<Integer> getWorkPlacesZ1() {
        return workPlacesZ1;
    }

    public ArrayList<Integer> getWorkPlacesZ2() {
        return workPlacesZ2;
    }

    public Map<String, Double> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<String, Double> playerMap) {
        this.playerMap = playerMap;
    }

    public boolean isPaydayMessageOn(Player player) {
        return paydayMessageOn.get(player.getName());
    }

    public Map<String, Map<String, Double>> getPlayerPayments() {
        return playerPayments;
    }

    public void clearBlackList() {
        blackList.clear();
    }

    public void clearPlayerMap() {
        playerMap.clear();
    }

    public void clearPlayerPayments() {
        playerPayments.clear();
    }

    public int getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(int currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public int getCurrentWPSaveTaskId() {
        return currentWPSaveTaskId;
    }

    public void setCurrentWPSaveTaskId(int currentWPSaveTaskId) {
        this.currentWPSaveTaskId = currentWPSaveTaskId;
    }

    public int getCurrentWPTaskId() {
        return currentWPTaskId;
    }

    public void setCurrentWPTaskId(int currentWPTaskId) {
        this.currentWPTaskId = currentWPTaskId;
    }

    public GetPayedPayDay getPayDayTask() {
        return payDayTask;
    }

    public void setPayDayTask(GetPayedPayDay payDayTask) {
        this.payDayTask = payDayTask;
    }

    public void payPlayer(String name, Double aDouble) {
        playerMap.put(name, aDouble);
    }

    public boolean doesThisCrossOver(int x1, int x2, int z1, int z2) {
        for (int i = 0; i < workPlacesX1.size(); i++) {
            if (((workPlacesX1.get(i)).intValue() >= x1) &&
                    ((workPlacesX1.get(i)).intValue() <= x2) &&
                    ((workPlacesZ1.get(i)).intValue() >= z1) &&
                    ((workPlacesZ1.get(i)).intValue() <= z2)) {
                return true;
            }

            if (((workPlacesX2.get(i)).intValue() >= x1) &&
                    ((workPlacesX2.get(i)).intValue() <= x2) &&
                    ((workPlacesZ2.get(i)).intValue() >= z1) &&
                    ((workPlacesZ2.get(i)).intValue() <= z2)) {
                return true;
            }

            if (((workPlacesX1.get(i)).intValue() >= x1) &&
                    ((workPlacesX1.get(i)).intValue() <= x2) &&
                    ((workPlacesZ2.get(i)).intValue() >= z1) &&
                    ((workPlacesZ2.get(i)).intValue() <= z2)) {
                return true;
            }

            if (((workPlacesX2.get(i)).intValue() >= x1) &&
                    ((workPlacesX2.get(i)).intValue() <= x2) &&
                    ((workPlacesZ1.get(i)).intValue() >= z1) &&
                    ((workPlacesZ1.get(i)).intValue() <= z2)) {
                return true;
            }
        }

        return false;
    }

    public void shrinkData(int workplaceIndex) {

        //ToDo does this really work?

        Object[] keys = workPlacesInfo.keySet().toArray();
        Object[] infoObjects = workPlacesInfo.values().toArray();

        workPlacesInfo = new HashMap();

        for (int i = 0; i < keys.length; i++) {
            if ((Integer) keys[i] > workplaceIndex) {
                keys[i] = (Integer) keys[i] - 1;
            }

            workPlacesInfo.put((Integer) keys[i], (Map) infoObjects[i]);
        }

        keys = workPlaceNames.keySet().toArray();
        infoObjects = workPlaceNames.values().toArray();

        workPlaceNames = new HashMap();

        for (int i = 0; i < keys.length; i++) {
            if ((Integer) infoObjects[i] > workplaceIndex) {
                infoObjects[i] = (Integer) infoObjects[i] - 1;
            }

            workPlaceNames.put((String) keys[i], (Integer) infoObjects[i]);
        }

        keys = playersInWorkPlace.keySet().toArray();
        infoObjects = playersInWorkPlace.values().toArray();

        playersInWorkPlace = new HashMap();

        for (int i = 0; i < keys.length; i++) {
            if ((Integer) infoObjects[i] > workplaceIndex) {
                infoObjects[i] = (Integer) infoObjects[i] - 1;
            } else if ((Integer) infoObjects[i] == workplaceIndex) {
                continue;
            }
            playersInWorkPlace.put((String) keys[i], (Integer) infoObjects[i]);
        }

    }
}

