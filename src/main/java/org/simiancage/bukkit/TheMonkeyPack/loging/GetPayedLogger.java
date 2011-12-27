package org.simiancage.bukkit.TheMonkeyPack.loging;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedLogger
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:07
 */

public class GetPayedLogger extends MainLogger {
    public GetPayedLogger getPayedLogger;


    public GetPayedLogger(String moduleName) {
        super();
        this.pluginName = moduleName;
        this.getPayedLogger = this;
        debug("GetPayedLogger enabled");
    }

    public GetPayedLogger getLogger() {
        return getPayedLogger;
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

