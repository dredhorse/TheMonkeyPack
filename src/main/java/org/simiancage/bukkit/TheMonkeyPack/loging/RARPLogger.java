package org.simiancage.bukkit.TheMonkeyPack.loging;

/**
 * PluginName: TheMonkeyPack
 * Class: RARPLogger
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:07
 */

public class RARPLogger extends MainLogger {
	public RARPLogger RARPLogger;


	public RARPLogger(String moduleName) {
		super();
		this.pluginName = moduleName;
		this.RARPLogger = this;
		debug("RARPLogger enabled");
	}

	public RARPLogger getLogger() {
		return RARPLogger;
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

