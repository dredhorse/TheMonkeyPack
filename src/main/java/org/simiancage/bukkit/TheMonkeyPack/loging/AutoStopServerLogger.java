package org.simiancage.bukkit.TheMonkeyPack.loging;

/**
 * PluginName: TheMonkeyPack
 * Class: AutoStopServerLogger
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:07
 */

public class AutoStopServerLogger extends MainLogger {
    public AutoStopServerLogger autoStopServerLogger;


    public AutoStopServerLogger(String moduleName) {
        super();
        this.pluginName = moduleName;
        this.autoStopServerLogger = this;
        debug("AutoStopServerLogger enabled");
    }

    public AutoStopServerLogger getLogger() {
        return autoStopServerLogger;
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

