package org.simiancage.bukkit.TheMonkeyPack.helpers;

import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.configs.KitConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.KitLogger;

import java.io.*;
import java.util.HashMap;

/**
 * PluginName: TheMonkeyPack
 * Class: KitHelper
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:46
 */

public class KitHelper {

    private static String path;
    private static KitConfig kitConfig;
    private static KitLogger kitLogger;


    public static HashMap<String, Long> loadHistory(Player player) {
        kitConfig = KitConfig.getInstance();
        path = kitConfig.getPluginPath();
        HashMap history = new HashMap();
        if (player != null) {
            String fname = path + "/kits-" + player.getName().toLowerCase() + ".txt";
            try {
                BufferedReader input = new BufferedReader(new FileReader(fname));
                String line = null;
                while ((line = input.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    history.put(parts[0], Long.valueOf(parts[1]));
                }
                input.close();
            } catch (FileNotFoundException localFileNotFoundException) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return history;
    }

    public static void saveHistory(Player player, HashMap<String, Long> history) {
        kitConfig = KitConfig.getInstance();
        path = kitConfig.getPluginPath();
        if (player != null) {
            String fname = path + "/kits-" + player.getName().toLowerCase() + ".txt";
            try {
                BufferedWriter output = new BufferedWriter(new FileWriter(fname));
                for (String key : history.keySet()) {
                    output.write(key + "=" + history.get(key) + "\n");
                }
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String timeUntil(Long sec) {
        if (sec < 120L) {
            Integer buf = Math.round((float) sec);
            return buf + " second" + (buf == 1 ? "" : "s");
        }
        if (sec < 7200L) {
            Integer buf = Math.round((float) (sec / 60L));
            return buf + " minute" + (buf == 1 ? "" : "s");
        }
        if (sec < 172800L) {
            Integer buf = Math.round((float) (sec / 3600L));
            return buf + " hour" + (buf == 1 ? "" : "s");
        }
        Integer buf = Math.round((float) (sec / 86400L));
        return buf + " day" + (buf == 1 ? "" : "s");
    }

}

