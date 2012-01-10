package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.loging.RARPLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * PluginName: TheMonkeyPack
 * Class: RARPConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class RARPConfig extends Configs {


	public enum RARP_PERMISSIONS {
		RARP, RARP_REDSTONE_BREAK, RARP_RAILS_BREAK, RARP_REDSTONE_PLACE, RARP_RAILS_PLACE;

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

	/**
	 * Messages for translation
	 */
	public enum Messages {
		DONT_HAVE_PERMISSION_TO_PLACE_REDSTONE("You don't have the permission to place redstone!", "Message displayed when player tries to place redstone without having the permission."),
		DONT_HAVE_PERMISSION_TO_PLACE_RAILS("You don't have the permission to place rails!", "Message displayed when player tries to place rails without having the permission."),
		DONT_HAVE_PERMISSION_TO_BREAK_REDSTONE("You don't have the permission to break redstone!", "Message displayed when player tries to break redstone without having the permission."),
		DONT_HAVE_PERMISSION_TO_BREAK_RAILS("You don't have the permission to break rails!", "Message displayed when player tries to break rails without having the permission.");


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
	private static RARPConfig instance = null;

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
	private static String configFile = "RailAndRedstoneProtectionConfig.yml";
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


	private RARPLogger rarpLogger;

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


// Default Config Variables start here!

	private boolean disableTNTCreeperDamage = true;
	private final String DISABLE_TNT_CREEPER_DAMAGE = "disableTNTCreeperDamage";
	private boolean redstoneProtection = true;
	private final String REDSTONE_PROTECTION = "redstoneProtection";
	private boolean railProtection = true;
	private final String RAIL_PROTECTION = "railProtection";
	private boolean enableRedSeaEffekt = false;
	private final String ENABLE_RED_SEA_EFFEKT = "enableRedSeaEffekt";


	// Internal variables
	// Location and Counter of Rails after Piston Retract
	private HashMap<Location, Integer> blocksAtLocation = new HashMap<Location, Integer>();
	// Location and Type of Rails after Piston Rectract
	private HashMap<Location, Material> reAddRailsAtLocation = new HashMap<Location, Material>();

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
		config.addDefault(DISABLE_TNT_CREEPER_DAMAGE, disableTNTCreeperDamage);
		config.addDefault(REDSTONE_PROTECTION, redstoneProtection);
		config.addDefault(RAIL_PROTECTION, railProtection);
		config.addDefault(ENABLE_RED_SEA_EFFEKT, enableRedSeaEffekt);

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

		// now loading messages

		for (Messages node : Messages.values()) {
			if (config.contains(node.toString())) {
				node.setMessage(config.getString(node.toString()));
				rarpLogger.debug(node + ": " + getMessage(node));
			} else {
				rarpLogger.warning(node + " doesn't exist in " + configFile);
				rarpLogger.warning("Using internal defaults!");
			}
		}
		disableTNTCreeperDamage = config.getBoolean(DISABLE_TNT_CREEPER_DAMAGE);
		redstoneProtection = config.getBoolean(REDSTONE_PROTECTION);
		railProtection = config.getBoolean(RAIL_PROTECTION);
		enableRedSeaEffekt = config.getBoolean(ENABLE_RED_SEA_EFFEKT);
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
		stream.println("# --- Rail and Redstone Protection Configuration");
		stream.println();

		stream.println("# Disable TNT and Creeper explosion to damage redstone / rails");
		stream.println(DISABLE_TNT_CREEPER_DAMAGE + ": " + disableTNTCreeperDamage);
		stream.println();
		stream.println("# Allow Redstone to be protected");
		stream.println(REDSTONE_PROTECTION + ": " + redstoneProtection);
		stream.println();

		stream.println("# Allow Rails to be protected");
		stream.println(RAIL_PROTECTION + ": " + railProtection);

		stream.println("# Enable the effect that rails with 1 block water on top of them will disperse that water");
		stream.println(ENABLE_RED_SEA_EFFEKT + ": " + enableRedSeaEffekt);

		stream.println();
		stream.println();
		stream.println("# --- Translation Features");
		stream.println();
		stream.println("# Almost everything player visible can be translated!");
		stream.println("# Please change to your liking.");


		stream.println();


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

		for (RARP_PERMISSIONS perm : RARP_PERMISSIONS.values()) {
			main.getServer().getPluginManager().addPermission(perm.asPermission());
		}


	}

	private void setupListeners() {
		mainConfig.addBlockListeners(Type.BLOCK_PISTON_EXTEND);
		mainConfig.addBlockListeners(Type.BLOCK_PISTON_RETRACT);
		mainConfig.addBlockListeners(Type.BLOCK_FROMTO);
		mainConfig.addBlockListeners(Type.BLOCK_BREAK);
		mainConfig.addBlockListeners(Type.BLOCK_PLACE);
		mainConfig.addBlockListeners(Type.BLOCK_PHYSICS);
		mainConfig.addEntityListeners(Type.ENTITY_EXPLODE);
		mainConfig.addEntityListeners(Type.ENTITY_DAMAGE);
	}


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!


// ToDO Add your getters and setters for your config variables here.


	public boolean isDisableTNTCreeperDamage() {
		return disableTNTCreeperDamage;
	}

	public void setDisableTNTCreeperDamage(boolean disableTNTCreeperDamage) {
		this.disableTNTCreeperDamage = disableTNTCreeperDamage;
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

	public HashMap<Location, Material> getReAddRailsAtLocation() {
		return reAddRailsAtLocation;
	}

	public void addRailToReAdd(Location location, Material railType) {
		reAddRailsAtLocation.put(location, railType);
	}

	public void removeRailFromReAdd(Location location) {
		reAddRailsAtLocation.remove(location);
	}

	public int getBlocksAtLocation(Location location) {
		return blocksAtLocation.get(location);
	}

	public void removeOneBlockAtLocation(Location location) {
		blocksAtLocation.put(location, blocksAtLocation.get(location) - 1);
	}

	public void removeBlockAtLocation(Location location) {
		blocksAtLocation.remove(location);
	}

	public void initBlockAtLocation(Location location) {
		blocksAtLocation.put(location, 6);
	}


	public boolean isEnableRedSeaEffekt() {
		return enableRedSeaEffekt;
	}

	public void setEnableRedSeaEffekt(boolean enableRedSeaEffekt) {
		this.enableRedSeaEffekt = enableRedSeaEffekt;
	}

	public boolean isRailProtection() {
		return railProtection;
	}

	public void setRailProtection(boolean railProtection) {
		this.railProtection = railProtection;
	}

	public boolean isRedstoneProtection() {
		return redstoneProtection;
	}

	public void setRedstoneProtection(boolean redstoneProtection) {
		this.redstoneProtection = redstoneProtection;
	}

	public RARPLogger getRarpLogger() {
		return rarpLogger;
	}

	public void setRarpLogger(RARPLogger rarpLogger) {
		this.rarpLogger = rarpLogger;
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

	public static RARPConfig getInstance() {
		return instance;
	}


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


	RARPConfig(TheMonkeyPack plugin, String moduleName) {
		super();
		MODULE_NAME = moduleName;
		main = plugin;
		rarpLogger = new RARPLogger(MODULE_NAME);
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
			rarpLogger.info("Creating default configuration file");
			defaultConfig();
		}

// adding the default values

		customDefaultConfig();

		try {
			config.load(pluginPath + configFile);
		} catch (IOException e) {
			rarpLogger.severe("Can't read the " + configFile + " File!", e);
		} catch (InvalidConfigurationException e) {
			rarpLogger.severe("Problem with the configuration in " + configFile + "!", e);
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
			rarpLogger.severe("Problems writing default config!");
			rarpLogger.info("Using internal Defaults!");
		} else {
			rarpLogger.debug("DefaultConfig written");
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
		rarpLogger.debug("configCurrent", configCurrent);
		rarpLogger.debug("configVer", configVer);
		loadCustomConfig();
		rarpLogger.info("Configuration v." + configVer + " loaded.");
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
		rarpLogger.debug("creating config");
		boolean success = false;
		try {
			PrintWriter stream;
			File folder = main.getDataFolder();
			if (folder != null) {
				folder.mkdirs();
			}
			PluginDescriptionFile pdfFile = main.getDescription();
			stream = new PrintWriter(pluginPath + configFile);
			rarpLogger.debug("starting contents");
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
			rarpLogger.debug("going for customConfig");
			writeCustomConfig(stream);

			stream.println();

			stream.close();

			success = true;

		} catch (FileNotFoundException e) {
			rarpLogger.warning("Error saving the " + configFile + ".");
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
			rarpLogger.info("Config is up to date");
		} else {
			rarpLogger.warning("Config is not up to date!");
			rarpLogger.warning("Config File Version: " + configVer);
			rarpLogger.warning("Internal Config Version: " + configCurrent);
			rarpLogger.warning("It is suggested to update the config.yml!");
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
				rarpLogger.info("Configuration was updated with new default values.");
				rarpLogger.info("Please change them to your liking.");
			} else {
				rarpLogger.warning("Configuration file could not be auto updated.");
				rarpLogger.warning("Please rename " + configFile + " and try again.");
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
			rarpLogger.severe("Can't read the " + configFile + " File!", e);
		} catch (InvalidConfigurationException e) {
			rarpLogger.severe("Problem with the configuration in " + configFile + "!", e);
		}
		String msg;
		if (configAvailable) {
			loadConfig();
			rarpLogger.info("Config reloaded");
			msg = MODULE_NAME + " Config was reloaded";
		} else {
			rarpLogger.severe("Reloading Config before it exists.");
			rarpLogger.severe("Flog the developer!");
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


