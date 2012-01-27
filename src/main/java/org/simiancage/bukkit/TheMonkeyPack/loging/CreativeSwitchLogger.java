package org.simiancage.bukkit.TheMonkeyPack.loging;

/**
 * PluginName: TheMonkeyPack
 * Class: AttackControlLogger
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:07
 */

public class CreativeSwitchLogger extends MainLogger {
	public CreativeSwitchLogger creativeSwitchLogger;


	public CreativeSwitchLogger(String moduleName) {
		super();
		this.pluginName = moduleName;
		this.creativeSwitchLogger = this;
		debug("CreativeSwitchLogger enabled");
	}

	public CreativeSwitchLogger getLogger() {
		return creativeSwitchLogger;
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

