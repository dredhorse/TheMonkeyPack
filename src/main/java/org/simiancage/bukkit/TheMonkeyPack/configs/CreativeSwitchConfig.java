package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.helpers.CreativeSwitchHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.CreativeSwitchLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * PluginName: TheMonkeyPack
 * Class: CreativeSwitchConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class CreativeSwitchConfig extends Configs {


	public enum CREATIVE_SWITCH_PERMISSIONS {
		CS;

		@Override
		public String toString() {
			String s = toPermission(super.toString());
			s = "tmp." + s;
			return s;
		}

		String toPermission(String s) {
			s = s.replace("_", ".");
			s = s.toLowerCase();
			return s;
		}

		public Permission asPermission() {
			Permission permission = new Permission("tmp." + toPermission(super.toString()));
			return permission;
		}

		public boolean hasPermission(Player player) {
			return main.hasPermission(player, toPermission("tmp." + super.toString()));

		}

	}

	public enum Messages {
		CREATIVE_MODE_MESSAGE("You are now in creative mode", "Message displayed to tell player that he is in creative mode."),
		SURVIAL_MODE_MESSAGE("You are now in survival mode", "Message displayed to tell player that he is in survival mode.");


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


	private static String MODULE_NAME;

	/**
	 * Instance of the Configuration Class
	 */
	private static CreativeSwitchConfig instance = null;

	/**
	 * Instance of the CreativeSwitch Helper
	 */
	private static CreativeSwitchHelper creativeSwitchHelper;

// Nothing to change from here to ==>>>
	/**
	 * Object to handle the configuration
	 *
	 * @see org.bukkit.configuration.file.FileConfiguration
	 */
	private YamlConfiguration config = new YamlConfiguration();
	/**
	 * Object to handle the plugin
	 */
	private static TheMonkeyPack main;
	/**
	 * Configuration File Name
	 */
	private static String configFile = "CreativeSwitchConfig.yml";
	/**
	 * Is the configuration available or did we have problems?
	 */
	private boolean configAvailable = false;

	/**
	 * Do we require a config update?
	 */
	private boolean configRequiresUpdate = false;

	private String pluginPath;

// <<<<=== here..

	private int checkIntervall = 10;
	private String CHECK_INTERFALL = "checkIntervall";


	private CreativeSwitchLogger creativeSwitchLogger;


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

		config.addDefault(CHECK_INTERFALL, checkIntervall);

		for (Messages node : Messages.values()) {
			config.addDefault(node.toString(), node.getMessage());
		}

	}


// Than we load it....

	/**
	 * Method to load the configuration into the config variables
	 */

	void loadCustomConfig() {

		checkIntervall = config.getInt(CHECK_INTERFALL);
		for (Messages node : Messages.values()) {
			if (config.contains(node.toString())) {
				node.setMessage(config.getString(node.toString()));
				creativeSwitchLogger.debug(node + ": " + getMessage(node));
			} else {
				creativeSwitchLogger.warning(node + " doesn't exist in " + configFile);
				creativeSwitchLogger.warning("Using internal defaults!");
			}
		}

// And than we write it....

	}

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
		stream.println("# --- Creative Switch Configuration");
		stream.println();
		stream.println("# Amount of time in seconds we check if player changed permissions");
		stream.println(CHECK_INTERFALL + ": " + checkIntervall);


		stream.println("# --- Translation Features");
		stream.println();
		stream.println("# Almost everything player visible can be translated!");

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
		// registering the additional permissions

		for (CREATIVE_SWITCH_PERMISSIONS perm : CREATIVE_SWITCH_PERMISSIONS.values()) {
			main.getServer().getPluginManager().addPermission(perm.asPermission());
		}


	}

	private void setupListeners() {
		mainConfig.addPlayerListeners(Type.PLAYER_JOIN);
	}


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!


// ToDO Add your getters and setters for your config variables here.

	public String getMessage(Messages messages) {
		return messages.getMessage();
	}

	public int getCheckIntervall() {
		return checkIntervall;
	}

	public static CreativeSwitchHelper getCreativeSwitchHelper() {
		return creativeSwitchHelper;
	}

	public CreativeSwitchLogger getCreativeSwitchLogger() {
		return creativeSwitchLogger;
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

	public static CreativeSwitchConfig getInstance() {
		return instance;
	}


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


	CreativeSwitchConfig(TheMonkeyPack plugin, String moduleName) {
		super();
		MODULE_NAME = moduleName;
		main = plugin;
		creativeSwitchLogger = new CreativeSwitchLogger(MODULE_NAME);
		mainConfig = main.getMainConfig();
		pluginPath = main.getDataFolder() + System.getProperty("file.separator");
		instance = this;
		setupConfig();

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
			creativeSwitchLogger.info("Creating default configuration file");
			defaultConfig();
		}

// adding the default values

		customDefaultConfig();

		try {
			config.load(pluginPath + configFile);
		} catch (IOException e) {
			creativeSwitchLogger.severe("Can't read the " + configFile + " File!", e);
		} catch (InvalidConfigurationException e) {
			creativeSwitchLogger.severe("Problem with the configuration in " + configFile + "!", e);
		}
// Loading the config from file
		loadConfig();

// Checking internal configCurrent and config file configVer

		setupCommands();
		setupListeners();

		creativeSwitchHelper = CreativeSwitchHelper.getInstance(main);

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
			creativeSwitchLogger.severe("Problems writing default config!");
			creativeSwitchLogger.info("Using internal Defaults!");
		} else {
			creativeSwitchLogger.debug("DefaultConfig written");
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
		creativeSwitchLogger.debug("configCurrent", configCurrent);
		creativeSwitchLogger.debug("configVer", configVer);
		loadCustomConfig();
		creativeSwitchLogger.info("Configuration v." + configVer + " loaded.");
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
		creativeSwitchLogger.debug("creating config");
		boolean success = false;
		try {
			PrintWriter stream;
			File folder = main.getDataFolder();
			if (folder != null) {
				folder.mkdirs();
			}
			PluginDescriptionFile pdfFile = main.getDescription();
			stream = new PrintWriter(pluginPath + configFile);
			creativeSwitchLogger.debug("starting contents");
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
			creativeSwitchLogger.debug("going for customConfig");
			writeCustomConfig(stream);

			stream.println();

			stream.close();

			success = true;

		} catch (FileNotFoundException e) {
			creativeSwitchLogger.warning("Error saving the " + configFile + ".");
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
			creativeSwitchLogger.info("Config is up to date");
		} else {
			creativeSwitchLogger.warning("Config is not up to date!");
			creativeSwitchLogger.warning("Config File Version: " + configVer);
			creativeSwitchLogger.warning("Internal Config Version: " + configCurrent);
			creativeSwitchLogger.warning("It is suggested to update the config.yml!");
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
				creativeSwitchLogger.info("Configuration was updated with new default values.");
				creativeSwitchLogger.info("Please change them to your liking.");
			} else {
				creativeSwitchLogger.warning("Configuration file could not be auto updated.");
				creativeSwitchLogger.warning("Please rename " + configFile + " and try again.");
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
			creativeSwitchLogger.severe("Can't read the " + configFile + " File!", e);
		} catch (InvalidConfigurationException e) {
			creativeSwitchLogger.severe("Problem with the configuration in " + configFile + "!", e);
		}
		String msg;
		if (configAvailable) {
			loadConfig();
			creativeSwitchLogger.info("Config reloaded");
			msg = MODULE_NAME + " Config was reloaded";
		} else {
			creativeSwitchLogger.severe("Reloading Config before it exists.");
			creativeSwitchLogger.severe("Flog the developer!");
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


}


