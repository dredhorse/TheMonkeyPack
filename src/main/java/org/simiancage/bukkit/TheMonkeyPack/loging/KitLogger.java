package org.simiancage.bukkit.TheMonkeyPack.loging;

/**
 * PluginName: TheMonkeyPack
 * Class: KitLogger
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:07
 */

public class KitLogger extends MainLogger {
    public KitLogger kitLogger;


    public KitLogger(String moduleName) {
        super();
        this.pluginName = moduleName;
        this.kitLogger = this;
        debug("KitLogger enabled");
    }

    public KitLogger getLogger() {
        return kitLogger;
    }

    public void debug(String msg, Object object) {
        super.debug(msg, object);
    }

    public void debug(String msg) {
        super.debug(msg);
    }

    public void severe(String msg) {
        super.severe(msg);
    }


    public void severe(String msg, Throwable exception) {
        super.severe(msg, exception);
    }
}

