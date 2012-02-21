package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.commands.MemoryCommand;
import org.simiancage.bukkit.TheMonkeyPack.helpers.AutoStopServerHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.AutoStopServerLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * PluginName: TheMonkeyPack
 * Class: AutoStopServerConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class AutoStopServerConfig extends Configs {


	/**
	 * Messages for translation
	 */
	public enum Messages {
		WARNING_MESSAGE("Server will be stopped in %m minutes", "Warning Message displayed to announce server stop."),
		STOP_MESSAGE("Server is going down. Be back soon!", "Message displayed when the server goes down.");


		private String message;
		private String commentMessage;

		private Messages(String message, String comment) {
			this.message = message;
			this.commentMessage = comment;
		}

		public String getMessage() {
			return this.message;
		}

		public String getComment() {
			return this.commentMessage;
		}

		@Override
		public String toString() {
			String s = toCamelCase(super.toString());
			return s;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		String toCamelCase(String s) {
			String[] parts = s.split("_");
			String camelCaseString = "";
			for (String part : parts) {
				camelCaseString = camelCaseString + toProperCase(part);
			}
			return camelCaseString;
		}

		String toProperCase(String s) {
			return s.substring(0, 1).toUpperCase() +
					s.substring(1).toLowerCase();
		}

	}

	public enum MEMORY_COMMAND {
		MEMORY_CMD("memory", "The alias command for /tmpmemory WITHOUT the / !!!"),
		MEMORY_CMD_DESCRIPTION("Shows the Java Runtime memory usage", "The command description."),
		MEMORY_CMD_PERMISSION_DESCRIPTION("allows access to the memory command", "The command permissions description."),
		MEMORY_HELP_OPTION("help", "The alias for the help option"),
		MEMORY_HELP_MESSAGE("displays this help", "The help message displayed.");

		private String content;
		private String commentMessage;

		private MEMORY_COMMAND(String content, String comment) {
			this.content = content;
			this.commentMessage = comment;
		}

		public String getContent() {
			return this.content;
		}

		public String getComment() {
			return this.commentMessage;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			String s = toCamelCase(super.toString());
			return s;
		}

		String toCamelCase(String s) {
			String[] parts = s.split("_");
			String camelCaseString = "";
			for (String part : parts) {
				camelCaseString = camelCaseString + toProperCase(part);
			}
			return camelCaseString;
		}

		String toProperCase(String s) {
			return s.substring(0, 1).toUpperCase() +
					s.substring(1).toLowerCase();
		}

	}


	private static String MODULE_NAME;

	/**
	 * Instance of the Configuration Class
	 */
	private static AutoStopServerConfig instance = null;

	/**
	 * Object to handle the configuration
	 */
	private YamlConfiguration config = new YamlConfiguration();
	/**
	 * Object to handle the plugin
	 */
	private static TheMonkeyPack main;
	/**
	 * Configuration File Name
	 */
	private static String configFile = "AutoStopServerConfig.yml";
	/**
	 * Is the configuration available or did we have problems?
	 */
	private boolean configAvailable = false;

	/**
	 * Do we require a config update?
	 */
	private boolean configRequiresUpdate = false;

	private String pluginPath;
	private Commands MemoryCommand;


// <<<<=== here..


	private AutoStopServerLogger autoStopServerLogger;
	private AutoStopServerHelper autoStopServerHelper;


	// ToDo Change the configCurrent if the config changes!
	/**
	 * This is the internal config version
	 */
	private final String configCurrent = "1.0";
	/**
	 * This is the DEFAULT for the config file version, should be the same as configCurrent. Will afterwards be changed
	 */
	private String configVer = "1.0";


// and now the real stuff


// ********************************************************************************************************************

	// the internal command names

	private final String CMD_MEMORY_COMMAND = "tmpmemory";


	// permission for displaying java memory usage messages
	private final String PERM_MEMORY_COMMAND = "tmp.ass.memory";


// Default Config Variables start here!

	private boolean enableAutoStop = true;
	private final String ENABLE_AUTO_STOP = "enableAutoStop";
	private double autoStopInterval = 4.0;
	private final String AUTO_STOP_INTERVAL = "autoStopInterval";
	private List<Double> warningTimes = new ArrayList<Double>() {{
		add(10.0);
		add(5.0);
		add(2.0);
		add(1.0);
	}};
	private final String WARNING_TIMES = "warningTimes";


// Internal variables


// *******************************************************************************************************************


/*  Here comes the custom config, the default config is later onString in the class
Keep in mind that you need to create your config file in a way which is
afterwards parsable again from the configuration class of bukkit
*/

// First we have the default part..
// Which is devided in setting up some variables first


	/**
	 * Method to setup the config variables with default values
	 */

	void setupCustomDefaultVariables() {


	}

// And than we add the defaults

	/**
	 * Method to add the config variables to the default configuration
	 */

	void customDefaultConfig() {

		config.addDefault(ENABLE_AUTO_STOP, enableAutoStop);
		config.addDefault(AUTO_STOP_INTERVAL, autoStopInterval);
		config.addDefault(WARNING_TIMES, warningTimes);

		// now adding MEMORY_COMMAND

		for (MEMORY_COMMAND node : MEMORY_COMMAND.values()) {
			config.addDefault(node.toString(), node.getContent());
		}

		// now adding messages

		for (Messages node : Messages.values()) {
			config.addDefault(node.toString(), node.getMessage());
		}


	}


// Than we load it....

	/**
	 * Method to load the configuration into the config variables
	 */

	void loadCustomConfig() {


		enableAutoStop = config.getBoolean(ENABLE_AUTO_STOP);
		autoStopInterval = config.getDouble(AUTO_STOP_INTERVAL);
		warningTimes = config.getDoubleList(WARNING_TIMES);


		// now loading TNT_COMMAND

		for (MEMORY_COMMAND node : MEMORY_COMMAND.values()) {
			if (config.contains(node.toString())) {
				node.setContent(config.getString(node.toString()));
				autoStopServerLogger.debug(node + ": " + node.getContent());
			} else {
				autoStopServerLogger.warning(node + " doesn't exist in " + configFile);
				autoStopServerLogger.warning("Using internal defaults!");
			}
		}


		// now loading messages

		for (Messages node : Messages.values()) {
			if (config.contains(node.toString())) {
				node.setMessage(config.getString(node.toString()));
				autoStopServerLogger.debug(node + ": " + getMessage(node));
			} else {
				autoStopServerLogger.warning(node + " doesn't exist in " + configFile);
				autoStopServerLogger.warning("Using internal defaults!");
			}
		}


	}

// And than we write it....


	/**
	 * Method to write the custom config variables into the config file
	 *
	 * @param stream will be handed over by  writeConfig
	 */

	void writeCustomConfig(PrintWriter stream) {
//Start here writing your config variables into the config file inkl. all comments

		stream.println("#-------- Module Configuration");
		stream.println();
// first the options
		stream.println("# --- AutoStop Configuration");
		stream.println();

		stream.println("# Enable the AutoStop feature. ");
		stream.println(ENABLE_AUTO_STOP + ": " + enableAutoStop);
		stream.println("# Interval to stop the server, can be fractions of an hour, eg. 2.5 ");
		stream.println(AUTO_STOP_INTERVAL + ": " + autoStopInterval);
		stream.println("# Interval to stop the server, can be fractions of an hour, eg. 2.5 ");
		stream.println(WARNING_TIMES + ": " + warningTimes);
		stream.println();
		stream.println("# --- Translation Features");
		stream.println();
		stream.println("# Almost everything player visible can be translated!");
		stream.println("# Please change to your liking and use the following variables");
		stream.println("# %m = minutes");

		stream.println();

// than the commands translations


// than the messages

		stream.println();

		for (Messages node : Messages.values()) {
			stream.println("# " + node.getComment());
			stream.println(node + ": \"" + node.getMessage() + "\"");
		}
		stream.println();

	}


// *******************************************************************************************************

// And now you need to configure commands and listeners


	private void setupCommands() {
		main.registerCommand(CMD_MEMORY_COMMAND, new MemoryCommand(main));
	}

	private void setupListeners() {
	}


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!


// ToDO Add your getters and setters for your config variables here.


	public void setWarningTimes(List<Double> warningTimes) {
		this.warningTimes = warningTimes;
	}

	public void setEnableAutoStop(boolean enableAutoStop) {
		this.enableAutoStop = enableAutoStop;
	}

	public void setAutoStopInterval(double autoStopInterval) {
		this.autoStopInterval = autoStopInterval;
	}

	public String getPERM_MEMORY_COMMAND() {
		return PERM_MEMORY_COMMAND;
	}

	public String getMemoryCommandConfig(MEMORY_COMMAND node) {
		return node.getContent();
	}

	public double getWarningTime(int index) {
		return warningTimes.get(index);
	}


	public List<Double> getWarningTimesList() {
		return warningTimes;
	}

	public boolean isEnableAutoStop() {
		return enableAutoStop;
	}

	public AutoStopServerLogger getAutoStopServerLogger() {
		return autoStopServerLogger;
	}

	public double getAutoStopInterval() {
		return autoStopInterval;
	}

	public AutoStopServerHelper getAutoStopServerHelper() {
		return autoStopServerHelper;
	}

	public static String getMODULE_NAME() {
		return MODULE_NAME;
	}


	public String getConfigFile() {
		return configFile;
	}

	public String getPluginPath() {
		return pluginPath;
	}


	// Last change coming up... choosing the right ClassName for the Logger..

	/**
	 * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
	 *
	 * @return instance of class
	 */

	public static AutoStopServerConfig getInstance() {
		return instance;
	}


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


	AutoStopServerConfig(TheMonkeyPack plugin, String moduleName) {
		super();
		MODULE_NAME = moduleName;
		main = plugin;
		autoStopServerLogger = new AutoStopServerLogger(MODULE_NAME);
		mainConfig = main.getMainConfig();
		pluginPath = main.getDataFolder() + System.getProperty("file.separator");
		instance = this;
		setupConfig();
		autoStopServerHelper = AutoStopServerHelper.getInstance(main);
	}


// than the getters

	/**
	 * Method to return the Config File Version
	 *
	 * @return configVer  Config File Version
	 */
	public String configVer() {
		return configVer;
	}

	/**
	 * Method to return if we need to update the config
	 *
	 * @return configRequiresUpdate
	 */
	public boolean isConfigRequiresUpdate() {
		return configRequiresUpdate;
	}

// And the rest

// Setting up the config

	/**
	 * Method to setup the configuration.
	 * If the configuration file doesn't exist it will be created by {@link #defaultConfig()}
	 * After that the configuration is loaded {@link #loadConfig()}
	 * We than check if an configuration update is necessary {@link #updateNecessary()}
	 * and set {@link #configAvailable} to true
	 *
	 * @see #defaultConfig()
	 * @see #loadConfig()
	 * @see #updateNecessary()
	 * @see #updateConfig()
	 */

	void setupConfig() {


// Checking if config file exists, if not create it


		if (!(new File(main.getDataFolder(), configFile)).exists()) {
			autoStopServerLogger.info("Creating default configuration file");
			defaultConfig();
		}

// adding the default values

		customDefaultConfig();

		try {
			config.load(pluginPath + configFile);
		} catch (IOException e) {
			autoStopServerLogger.severe("Can't read the " + configFile + " File!", e);
		} catch (InvalidConfigurationException e) {
			autoStopServerLogger.severe("Problem with the configuration in " + configFile + "!", e);
		}
// Loading the config from file
		loadConfig();

// Checking internal configCurrent and config file configVer

		setupCommands();
		setupListeners();

		updateNecessary();
// If config file has new options update it if enabled
		if (mainConfig.autoUpdateConfig) {
			updateConfig();
		}
		configAvailable = true;
	}


// Creating the defaults

// Configuring the Default options..

	/**
	 * Method to write and create the default configuration.
	 * The custom configuration variables are added via #setupCustomDefaultVariables()
	 * Than we write the configuration to disk  #writeConfig()
	 * Than we get the config object from disk
	 * We are adding the default configuration for the variables and load the
	 * defaults for the custom variables  #customDefaultConfig()
	 *
	 * @see #setupCustomDefaultVariables()
	 * @see #customDefaultConfig()
	 */

	private void defaultConfig() {
		setupCustomDefaultVariables();
		if (!writeConfig()) {
			autoStopServerLogger.severe("Problems writing default config!");
			autoStopServerLogger.info("Using internal Defaults!");
		} else {
			autoStopServerLogger.debug("DefaultConfig written");
		}
		config.addDefault("configVer", configVer);

	}


// Loading the configuration

	/**
	 * Method for loading the configuration from disk.
	 * First we get the config object from disk, than we
	 * read in the standard configuration part.
	 * We also log a message if #debugLogEnabled
	 * and we produce some debug logs.
	 * After that we load the custom configuration part #loadCustomConfig()
	 *
	 * @see #loadCustomConfig()
	 */

	void loadConfig() {
		// Starting to update the standard configuration
		configVer = config.getString("configVer");
		// Debug OutPut NOW!
		autoStopServerLogger.debug("configCurrent", configCurrent);
		autoStopServerLogger.debug("configVer", configVer);
		loadCustomConfig();
		autoStopServerLogger.info("Configuration v." + configVer + " loaded.");
	}


//  Writing the config file

	/**
	 * Method for writing the configuration file.
	 * First we write the standard configuration part, than we
	 * write the custom configuration part via #writeCustomConfig()
	 *
	 * @return true if writing the config was successful
	 *
	 * @see #writeCustomConfig(java.io.PrintWriter)
	 */

	public boolean writeConfig() {
		autoStopServerLogger.debug("creating config");
		boolean success = false;
		try {
			PrintWriter stream;
			File folder = main.getDataFolder();
			if (folder != null) {
				folder.mkdirs();
			}
			PluginDescriptionFile pdfFile = main.getDescription();
			stream = new PrintWriter(pluginPath + configFile);
			autoStopServerLogger.debug("starting contents");
//Let's write our config ;)
			stream.println("# " + pdfFile.getName() + " " + pdfFile.getVersion() + " by " + pdfFile.getAuthors().toString());
			stream.println("#");
			stream.println("# Configuration File for module [" + MODULE_NAME + "]");
			stream.println("#");
			stream.println("# For detailed assistance please visit: " + mainConfig.getPluginSlug());
			stream.println();
			stream.println("# Configuration Version");
			stream.println("configVer: \"" + configVer + "\"");
			stream.println();
// Getting the custom config information from the top of the class
			autoStopServerLogger.debug("going for customConfig");
			writeCustomConfig(stream);

			stream.println();

			stream.close();

			success = true;

		} catch (FileNotFoundException e) {
			autoStopServerLogger.warning("Error saving the " + configFile + ".");
		}

		return success;
	}


// Checking if the configVersions differ

	/**
	 * Method to check if the configuration version are different.
	 * will set #configRequiresUpdate to true if versions are different
	 */
	void updateNecessary() {
		if (configVer.equalsIgnoreCase(configCurrent)) {
			autoStopServerLogger.info("Config is up to date");
		} else {
			autoStopServerLogger.warning("Config is not up to date!");
			autoStopServerLogger.warning("Config File Version: " + configVer);
			autoStopServerLogger.warning("Internal Config Version: " + configCurrent);
			autoStopServerLogger.warning("It is suggested to update the config.yml!");
			configRequiresUpdate = true;
		}
	}


// Updating the config

	/**
	 * Method to update the configuration if it is necessary.
	 */
	void updateConfig() {
		if (configRequiresUpdate) {
			configVer = configCurrent;
			if (writeConfig()) {
				autoStopServerLogger.info("Configuration was updated with new default values.");
				autoStopServerLogger.info("Please change them to your liking.");
			} else {
				autoStopServerLogger.warning("Configuration file could not be auto updated.");
				autoStopServerLogger.warning("Please rename " + configFile + " and try again.");
			}
		}
	}

// Reloading the config

	/**
	 * Method to reload the configuration.
	 *
	 * @return msg with the status of the reload
	 */

	public String reloadConfig() {
		configAvailable = false;
		try {
			config.load(pluginPath + configFile);
			configAvailable = true;
		} catch (IOException e) {
			autoStopServerLogger.severe("Can't read the " + configFile + " File!", e);
		} catch (InvalidConfigurationException e) {
			autoStopServerLogger.severe("Problem with the configuration in " + configFile + "!", e);
		}
		String msg;
		if (configAvailable) {
			loadConfig();
			autoStopServerLogger.info("Config reloaded");
			msg = MODULE_NAME + " Config was reloaded";
		} else {
			autoStopServerLogger.severe("Reloading Config before it exists.");
			autoStopServerLogger.severe("Flog the developer!");
			msg = "Something terrible terrible did go really really wrong, see console log!";
		}
		return msg;
	}
// Saving the config


	/**
	 * Method to save the config to file.
	 *
	 * @return true if the save was successful
	 */
	public boolean saveConfig() {
		boolean saved = false;
		if (mainConfig.isSaveConfig()) {
			saved = writeConfig();
		}
		return saved;
	}

	private void setMessage(Messages node, String contents) {
		node.setMessage(contents);
	}


	/**
	 * Methode to return the content of the MessagesNode
	 *
	 * @param node
	 *
	 * @return
	 */
	public String getMessage(Messages node) {
		return node.getMessage();
	}


}


