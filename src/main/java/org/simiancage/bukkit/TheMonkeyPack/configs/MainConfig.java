package org.simiancage.bukkit.TheMonkeyPack.configs; /**
 *
 * PluginName: TheMonkeyPack
 * Class: MainConfig
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 18:10
 *
 */

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.commands.AdminCommand;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.listeners.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * The MainConfig Class allows you to write a custom config file for craftbukkit plugins incl. comments.
 * It allows autoupdating config changes, checking for plugin updates and writing back the configuration.
 * Please note that writing to the config file will overwrite any manual changes.<p>
 * You NEED to fix all ToDos, otherwise the class will NOT work!<p>
 *
 * @author Don Redhorse
 */
@SuppressWarnings({"UnusedDeclaration"})
public class MainConfig {

    /**
     * Instance of the Configuration Class
     */
    private static MainConfig instance = null;

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
    private Plugin main;

    /**
     * Configuration File Name
     */
    private static String configFile = "TheMonkeyPackMainConfig.yml";
    /**
     * Is the configuration available or did we have problems?
     */
    private boolean configAvailable = false;
// Default plugin configuration
    /**
     * Enables logging to server console. Warning and Severe will still be logged.
     */
    private boolean errorLogEnabled = true;
    /**
     * Enable more logging.. could be messy!
     */
    boolean debugLogEnabled = true;
    /**
     * Check if there is a new version of the plugin out.
     */
    private boolean checkForUpdate = true;
    /**
     * AutoUpdate the config file if necessary. This will overwrite any changes outside the configuration parameters!
     */
    boolean autoUpdateConfig = false;
    /**
     * Enable saving of the config file
     */
    private boolean saveConfig = false;
    /**
     * Contains the plugin name
     */
    private String pluginName;
    /**
     * Contains the plugin version
     */
    private String pluginVersion;
    /**
     * Do we require a config update?
     */
    private boolean configRequiresUpdate = false;

// <<<<=== here..


// Stuff with minor changes

    // ToDo Change the pluginSlug, versionURL and the org.simiancage.bukkit.TheMonkeyPack.configs.LoggerClass  for the Plugin!
    /**
     * Link to the location of the plugin website
     */
    @SuppressWarnings({"FieldCanBeLocal"})
    private final String pluginSlug = "http://dev.bukkit.org/server-mods/monkey-pack/";
    /**
     * Link to the location of the recent version number, the file should be a text with just the number
     */
    @SuppressWarnings({"FieldCanBeLocal"})
    private final String versionURL = "https://raw.github.com/dredhorse/TheMonkeyPack/master/etc/themonkeypack.ver";
    // The org.simiancage.bukkit.TheMonkeyPack.configs.LoggerClass should be renamed to the name of the class you did change the original org.simiancage.bukkit.TheMonkeyPack.configs.LoggerClass too.
    /**
     * Reference of the org.simiancage.bukkit.TheMonkeyPack.configs.LoggerClass, needs to be renamed to correct name.
     *
     * @see org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger
     */
    private static MainLogger log;

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

// Default Config Variables start here!


// These are the modules which can be enabled.

    private final String ENABLE_LAMPSTONE = "enableLampstone";
    private final String MODULE_NAME_LAMPSTONE = "Lampstone";
    private boolean enableLampstone = false;
    public static LampstoneConfig lampstoneConfig;

    private final String ENABLE_AFKHANDLER = "enableAfkHandler";
    private final String MODULE_NAME_AFKHANDLER = "AFK Handler";
    private boolean enableAfkHandler = false;
    // ToDo after integrating the module make it available
    //public static AfkHandlerConfig afkHandlerConfig;

    private final String ENABLE_HELLOWORLD = "enableHelloWorld";
    private final String MODULE_NAME_HELLOWORLD = "HelloWorld";
    private boolean enableHelloWorld = false;
    // ToDo after integrating the module make it available
    //public static HelloWorldConfig helloWorldConfig;

    private final String ENABLE_WURKIT = "enableWurkit";
    private final String MODULE_NAME_WURKIT = "Wurkit";
    private boolean enableWurkit = false;
    // ToDo after integrating the module make it available
    //public static WurkitConfig wurkitConfig;

    private final String ENABLE_KITS = "enableKits";
    private final String MODULE_NAME_KITS = "Kits";
    private boolean enableKits = false;
    // ToDo after integrating the module make it available
    //public static KitsConfig kitsConfig;

// *******************************************************************************************************************

    // These are helper variables

    private List<Listeners> listeners = new ArrayList<Listeners>();

    private List<Commands> commands = new ArrayList<Commands>();


// *******************************************************************************************************************


/*  Here comes the custom config, the default config is later on in the class
Keep in mind that you need to create your config file in a way which is
afterwards parsable again from the configuration class of bukkit
*/

// First we have the default part..
// Which is devided in setting up some variables first


    /**
     * Method to add the config variables to the default configuration
     */

    private void customDefaultConfig() {
// ToDo Start here adding the config variables to the default configuration

        config.addDefault(ENABLE_AFKHANDLER, enableAfkHandler);
        config.addDefault(ENABLE_HELLOWORLD, enableHelloWorld);
        config.addDefault(ENABLE_KITS, enableKits);
        config.addDefault(ENABLE_LAMPSTONE, enableLampstone);
        config.addDefault(ENABLE_WURKIT, enableWurkit);

    }


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    private void loadCustomConfig() {
// ToDo Start here loading the configuration into your config variables

        commands.add(new AdminCommand(main));
        enableAfkHandler = config.getBoolean(ENABLE_AFKHANDLER);
        enableHelloWorld = config.getBoolean(ENABLE_HELLOWORLD);
        enableKits = config.getBoolean(ENABLE_KITS);
        enableLampstone = config.getBoolean(ENABLE_LAMPSTONE);
        enableWurkit = config.getBoolean(ENABLE_WURKIT);


        // ToDo Don't forget the debugging

        log.debug(ENABLE_LAMPSTONE, enableLampstone);
        log.debug(ENABLE_WURKIT, enableWurkit);
        log.debug(ENABLE_AFKHANDLER, enableAfkHandler);
        log.debug(ENABLE_HELLOWORLD, enableHelloWorld);
        log.debug(ENABLE_KITS, enableKits);
    }

// And than we write it....


    /**
     * Method to write the custom config variables into the config file
     *
     * @param stream will be handed over by  writeConfig
     */

    private void writeCustomConfig(PrintWriter stream) {
//Start here writing your config variables into the config file inkl. all comments


        stream.println("#-------- Module Configuration");
        stream.println();
        stream.println("# Enable the different modules here or via");
        stream.println("# /mtp enable [MODULENAME]");
        stream.println();
        stream.println("# After that you need to reload the server or use");
        stream.println("# /mtp reload");
        stream.println("# to create the config files for the different modules");
        stream.println();
        stream.println("# So you still need to configure THEM!");
        stream.println();
        stream.println("#-------- Module [" + MODULE_NAME_AFKHANDLER + "] --------");
        stream.println();
        stream.println("# Kick, Announce, GOD mode when AFK");
        stream.println(ENABLE_AFKHANDLER + ": " + enableAfkHandler);
        stream.println();
        stream.println("#-------- Module [" + MODULE_NAME_HELLOWORLD + "] --------");
        stream.println();
        stream.println("# Announce which world you entered");
        stream.println(ENABLE_HELLOWORLD + ": " + enableHelloWorld);
        stream.println();
        stream.println("#-------- Module [" + MODULE_NAME_LAMPSTONE + "] --------");
        stream.println();
        stream.println("# Switch between different blocks between day and night");
        stream.println(ENABLE_LAMPSTONE + ": " + enableLampstone);
        stream.println();
        stream.println("#-------- Module [" + MODULE_NAME_WURKIT + "] --------");
        stream.println();
        stream.println("# Earn Money when you build or destroy stuff");
        stream.println(ENABLE_WURKIT + ": " + enableWurkit);
        stream.println();
        stream.println("#-------- Module [" + MODULE_NAME_KITS + "] --------");
        stream.println();
        stream.println("# Give out kits to your players");
        stream.println(ENABLE_KITS + ": " + enableKits);


    }


// *******************************************************************************************************

    // Creating the necessary hooks into the different config classes

    private void loadModuleConfigs() {
        if (enableLampstone) {
            lampstoneConfig = new LampstoneConfig(main, MODULE_NAME_LAMPSTONE);
        }
        // ToDo after enabling the module make it available

    }


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!

// ToDO Add your getters and setters for your config variables here.


    public String getMODULE_NAME_LAMPSTONE() {
        return MODULE_NAME_LAMPSTONE;
    }

    public String getMODULE_NAME_AFKHANDLER() {
        return MODULE_NAME_AFKHANDLER;
    }

    public String getMODULE_NAME_HELLOWORLD() {
        return MODULE_NAME_HELLOWORLD;
    }

    public String getMODULE_NAME_WURKIT() {
        return MODULE_NAME_WURKIT;
    }

    public String getMODULE_NAME_KITS() {
        return MODULE_NAME_KITS;
    }

    public boolean isEnableLampstone() {
        return enableLampstone;
    }

    public void setEnableLampstone(boolean enableLampstone) {
        this.enableLampstone = enableLampstone;
    }

    public boolean isEnableAfkHandler() {
        return enableAfkHandler;
    }

    public void setEnableAfkHandler(boolean enableAfkHandler) {
        this.enableAfkHandler = enableAfkHandler;
    }

    public boolean isEnableHelloWorld() {
        return enableHelloWorld;
    }

    public void setEnableHelloWorld(boolean enableHelloWorld) {
        this.enableHelloWorld = enableHelloWorld;
    }

    public boolean isEnableWurkit() {
        return enableWurkit;
    }

    public void setEnableWurkit(boolean enableWurkit) {
        this.enableWurkit = enableWurkit;
    }

    public boolean isEnableKits() {
        return enableKits;
    }

    public void setEnableKits(boolean enableKits) {
        this.enableKits = enableKits;
    }

    // Last change coming up... choosing the right ClassName for the Logger..

    /**
     * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
     *
     * @return instance of class
     */

    public static MainConfig getInstance() {
        if (instance == null) {
            instance = new MainConfig();
        }
        log = MainLogger.getLogger();
        return instance;
    }

    /**
     * Method to get the Instance of the Class and pass over a different name for the config file, if the class
     * hasn't been initialized yet it will.
     *
     * @param configuratonFile name of the config file
     *
     * @return instance of class
     */
    public static MainConfig getInstance(String configuratonFile) {
        if (instance == null) {
            instance = new MainConfig();
        }
        log = MainLogger.getLogger();
        configFile = configuratonFile;
        return instance;
    }


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


    private MainConfig() {

    }


// than the getters

    /**
     * Method to return the PluginName
     *
     * @return PluginName
     */

    public String pluginName() {
        return pluginName;
    }

    /**
     * Method to return the PluginVersion
     *
     * @return PluginVersion
     */
    public String pluginVersion() {
        return pluginVersion;
    }

    /**
     * Method to return the Config File Version
     *
     * @return configVer  Config File Version
     */
    public String configVer() {
        return configVer;
    }

    public String getPluginSlug() {
        return pluginSlug;
    }

    /**
     * Method to return if Error Logging is enabled
     *
     * @return errorLogEnabled
     */

    public boolean isErrorLogEnabled() {
        return errorLogEnabled;
    }

    /**
     * Method to return if Debug Loggin is enabled
     *
     * @return debugLogEnabled
     */
    public boolean isDebugLogEnabled() {
        return debugLogEnabled;
    }

    /**
     * Method to return if we are checking for updates
     *
     * @return checkForUpdate
     */
    public boolean isCheckForUpdate() {
        return checkForUpdate;
    }

    /**
     * Method to return if we are AutoUpdating the Config File
     *
     * @return autoUpdateConfig
     */
    public boolean isAutoUpdateConfig() {
        return autoUpdateConfig;
    }

    /**
     * Method to return if we are saving the config automatically
     *
     * @return saveConfig
     */
    public boolean isSaveConfig() {
        return saveConfig;
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
     * and if {@link #autoUpdateConfig} is true we update the configuration {@link #updateConfig()}
     * If {@link #checkForUpdate} is true we check if there is a new version of the plugin {@link #versionCheck()}
     * and set {@link #configAvailable} to true
     *
     * @param plugin references the plugin for this configuration
     *
     * @see #defaultConfig()
     * @see #loadConfig()
     * @see #updateNecessary()
     * @see #updateConfig()
     * @see #versionCheck()
     */

    public void setupConfig(Plugin plugin) {
        main = plugin;
        String pluginPath = main.getDataFolder() + System.getProperty("file.separator");
// Checking if config file exists, if not create it
        if (!(new File(plugin.getDataFolder(), configFile)).exists()) {
            log.info("Creating default configuration file");
            defaultConfig();
        }
        try {
            config.load(pluginPath + configFile);
        } catch (IOException e) {
            log.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            log.severe("Problem with the configuration in " + configFile + "!", e);
        }
// Loading the config from file
        loadConfig();

// Checking internal configCurrent and config file configVer

        updateNecessary();
// If config file has new options update it if enabled
        if (autoUpdateConfig) {
            updateConfig();
        }
// Also check for New Version of the plugin
        if (checkForUpdate) {
            versionCheck();
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
     * @see #customDefaultConfig()
     */

    private void defaultConfig() {
        if (!writeConfig()) {
            log.info("Using internal Defaults!");
        }
        config.addDefault("configVer", configVer);
        config.addDefault("errorLogEnabled", errorLogEnabled);
        config.addDefault("DebugLogEnabled", debugLogEnabled);
        config.addDefault("checkForUpdate", checkForUpdate);
        config.addDefault("autoUpdateConfig", autoUpdateConfig);
        config.addDefault("saveConfig", saveConfig);
        customDefaultConfig();
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

    private void loadConfig() {
        // Starting to update the standard configuration
        configVer = config.getString("configVer");
        errorLogEnabled = config.getBoolean("errorLogEnabled");
        debugLogEnabled = config.getBoolean("DebugLogEnabled");
        checkForUpdate = config.getBoolean("checkForUpdate");
        autoUpdateConfig = config.getBoolean("autoUpdateConfig");
        saveConfig = config.getBoolean("saveConfig");
        // Debug OutPut NOW!
        if (debugLogEnabled) {
            log.info("Debug Logging is enabled!");
        }
        log.debug("configCurrent", configCurrent);
        log.debug("configVer", configVer);
        log.debug("errorLogEnabled", errorLogEnabled);
        log.debug("checkForUpdate", checkForUpdate);
        log.debug("autoUpdateConfig", autoUpdateConfig);
        log.debug("saveConfig", saveConfig);

        loadCustomConfig();

        log.info("Configuration v." + configVer + " loaded.");
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

    private boolean writeConfig() {
        boolean success = false;
        try {
            PrintWriter stream;
            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            String pluginPath = main.getDataFolder() + System.getProperty("file.separator");
            PluginDescriptionFile pdfFile = main.getDescription();
            pluginName = pdfFile.getName();
            pluginVersion = pdfFile.getVersion();
            stream = new PrintWriter(pluginPath + configFile);
//Let's write our config ;)
            stream.println("# " + pluginName + " " + pdfFile.getVersion() + " by " + pdfFile.getAuthors().toString());
            stream.println("#");
            stream.println("# Configuration File for " + pluginName + ".");
            stream.println("#");
            stream.println("# For detailed assistance please visit: " + pluginSlug);
            stream.println();
            stream.println("#------- Default Configuration");
            stream.println();
            stream.println("# Configuration Version");
            stream.println("configVer: '" + configVer + "'");
            stream.println();
            stream.println("# Error Log Enabled");
            stream.println("# Enable logging to server console");
            stream.println("# Warning and Severe will still be logged.");
            stream.println("errorLogEnabled: " + errorLogEnabled);
            stream.println();
            stream.println("# Debug Log Enabled");
            stream.println("# Enable more logging.. could be messy!");
            stream.println("DebugLogEnabled: " + debugLogEnabled);
            stream.println();
            stream.println("# Check for Update");
            stream.println("# Will check if there is a new version of the plugin out.");
            stream.println("checkForUpdate: " + checkForUpdate);
            stream.println();
            stream.println("# Auto Update Config");
            stream.println("# This will overwrite any changes outside the configuration parameters!");
            stream.println("autoUpdateConfig: " + autoUpdateConfig);
            stream.println();
            stream.println("# Save Config");
            stream.println("# This will overwrite any changes outside the configuration parameters!");
            stream.println("# Only needed if you use ingame commands to change the configuration.");
            stream.println("saveConfig: " + saveConfig);
            stream.println();

// Getting the custom config information from the top of the class
            writeCustomConfig(stream);

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            log.warning("Error saving the " + configFile + ".");
        }
        log.debug("DefaultConfig written", success);
        return success;
    }


// Checking if the configVersions differ

    /**
     * Method to check if the configuration version are different.
     * will set #configRequiresUpdate to true if versions are different
     */
    private void updateNecessary() {
        if (configVer.equalsIgnoreCase(configCurrent)) {
            log.info("Config is up to date");
        } else {
            log.warning("Config is not up to date!");
            log.warning("Config File Version: " + configVer);
            log.warning("Internal Config Version: " + configCurrent);
            log.warning("It is suggested to update the config.yml!");
            configRequiresUpdate = true;
        }
    }

// Checking the Current Version via the Web

    /**
     * Method to check if there is a newer version of the plugin available.
     */
    private void versionCheck() {
        String thisVersion = main.getDescription().getVersion();
        URL url;
        try {
            url = new URL(versionURL);
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String newVersion = "";
            String line;
            while ((line = in.readLine()) != null) {
                newVersion += line;
            }
            in.close();
            if (newVersion.equals(thisVersion)) {
                log.info("is up to date at version "
                        + thisVersion + ".");

            } else {
                log.warning("is out of date!");
                log.warning("This version: " + thisVersion + "; latest version: " + newVersion + ".");
            }
        } catch (MalformedURLException ex) {
            log.warning("Error accessing update URL.", ex);
        } catch (IOException ex) {
            log.warning("Error checking for update.", ex);
        }
    }

// Updating the config

    /**
     * Method to update the configuration if it is necessary.
     */
    private void updateConfig() {
        if (configRequiresUpdate) {
            configVer = configCurrent;
            if (writeConfig()) {
                log.info("Configuration was updated with new default values.");
                log.info("Please change them to your liking.");
            } else {
                log.warning("Configuration file could not be auto updated.");
                log.warning("Please rename " + configFile + " and try again.");
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
        String msg;
        if (configAvailable) {
            loadConfig();
            log.info("Config reloaded");
            msg = "Config was reloaded";
        } else {
            log.severe("Reloading Config before it exists.");
            log.severe("Flog the developer!");
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
        if (saveConfig) {
            saved = writeConfig();
        }
        return saved;
    }


    public List<Listeners> getListeners() {
        return listeners;
    }

    public List<Commands> getCommands() {
        return commands;
    }

    public void addListener(Listeners listener) {
        listeners.add(listener);
    }

    public void addCommand(Commands command) {
        commands.add(command);
    }

}