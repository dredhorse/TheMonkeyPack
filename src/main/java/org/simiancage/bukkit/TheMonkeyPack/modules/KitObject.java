package org.simiancage.bukkit.TheMonkeyPack.modules;

import org.simiancage.bukkit.TheMonkeyPack.configs.KitConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.KitLogger;

import java.util.HashMap;

/**
 * PluginName: TheMonkeyPack
 * Class: KitObject
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:28
 */

// http://forums.bukkit.org/threads/38557/

public class KitObject {
    String raw = "";
    String name = "";
    Integer cooldown = 0;
    Integer cost = 0;
    HashMap<String, Integer> components = new HashMap();
    KitConfig kitConfig = KitConfig.getInstance();
    KitLogger kitLogger = kitConfig.getKitLogger();

    public KitObject(String newraw) {
        kitLogger.debug("raw", newraw);
        raw = newraw;
        String[] parts = raw.split(";");
        name = parts[0];
        try {
            for (Integer index = 1; index < parts.length; index = index + 1) {
                String[] pair = parts[index].split(" ", 2);
                if (pair[0].startsWith("-")) {
                    cooldown = Integer.parseInt(pair[0]) * -1;
                } else if (pair[0].startsWith("$")) {
                    cost = Integer.parseInt(pair[0].substring(1));
                } else if (pair.length == 1) {
                    components.put(pair[0], 1);
                } else {
                    components.put(pair[0], Integer.valueOf(pair[1]));
                }
            }
        } catch (Exception e) {
            kitLogger.severe("The definition of kit '" + name + "' contains an error, please fix " + kitConfig.getConfigFile(), e);

        }
    }

    public String Name() {
        return name;
    }

    public Integer Cooldown() {
        return cooldown;
    }

    public Integer Cost() {
        return cost;
    }

    public HashMap<String, Integer> Components() {
        return components;
    }

    public Integer ComponentId(String item) {
        String[] parts = item.split(":");
        return Integer.parseInt(parts[0]);
    }

    public Byte ComponentData(String item) {
        String[] parts = item.split(":");
        if ((parts.length > 1) && (!parts[1].equals(""))) {
            return Byte.parseByte(parts[1]);
        }
        return (byte) 0;
    }

    public Short ComponentDurability(String item) {
        String[] parts = item.split(":");
        if ((parts.length > 2) && (!parts[2].equals(""))) {
            return Short.parseShort(parts[2]);
        }
        return (short) 0;
    }

    public String toString() {
        String kitDefinition;
        kitDefinition = name + ": ";
        for (String item : components.keySet()) {
            Integer count = (Integer) components.get(item);
            item = item + " " + count;
            kitDefinition = kitDefinition + item + ";";
        }
        if (cooldown > 0) {
            kitDefinition = kitDefinition + "-" + cooldown;
        }
        if (cost > 0) {
            kitDefinition = kitDefinition + ";$" + cost;
        }
        if (kitDefinition.endsWith(";")) {
            int length = kitDefinition.length();
            kitDefinition = kitDefinition.substring(0, length - 1);
        }
        return kitDefinition;
    }
}

