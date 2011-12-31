package org.simiancage.bukkit.TheMonkeyPack.loging;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTControlLogger
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:07
 */

public class TNTControlLogger extends MainLogger {
    public TNTControlLogger tntControlLogger;


    public TNTControlLogger(String moduleName) {
        super();
        this.pluginName = moduleName;
        this.tntControlLogger = this;
        debug("TNTControlLogger enabled");
    }

    public TNTControlLogger getLogger() {
        return tntControlLogger;
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

