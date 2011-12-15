package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.commands.KitCommand;
import org.simiancage.bukkit.TheMonkeyPack.loging.KitLogger;
import org.simiancage.bukkit.TheMonkeyPack.modules.KitObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PluginName: TheMonkeyPack
 * Class: KitConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class KitConfig extends Configs {

    private static String MODULE_NAME;

    /**
     * Instance of the Configuration Class
     */
    private static KitConfig instance = null;

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
    private static String configFile = "KitConfig.yml";
    /**
     * Is the configuration available or did we have problems?
     */
    private boolean configAvailable = false;

    /**
     * Do we require a config update?
     */
    private boolean configRequiresUpdate = false;

    private String pluginPath;
    private Commands kitCommand;

// <<<<=== here..


    private KitLogger kitLogger;

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

    private CopyOnWriteArrayList<KitObject> kits = new CopyOnWriteArrayList();
    private String kitExample1 = "268 1;269 1;-300;$10";
    private String nameExample1 = "Starter";
    private String kitExample2 = "256 1";
    private String nameExample2 = "Rock";

// *******************************************************************************************************************


/*  Here comes the custom config, the default config is later on in the class
Keep in mind that you need to create your config file in a way which is
afterwards parsable again from the configuration class of bukkit
*/

// First we have the default part..
// Which is devided in setting up some variables first

    /**
     * Method to setup the config variables with default values
     */

    void setupCustomDefaultVariables() {

        kits.add(new KitObject(nameExample1 + ";" + kitExample1));
        kits.add(new KitObject(nameExample2 + ";" + kitExample2));
    }

// And than we add the defaults

    /**
     * Method to add the config variables to the default configuration
     */

    void customDefaultConfig() {
    }


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    void loadCustomConfig() {

        if (!kits.isEmpty()) {
            kits.clear();
        }
        kitLogger.debug("Loading CustomConfig");
        for (String kitName : config.getConfigurationSection("kits").getKeys(false)) {
            kitLogger.debug("kitName", kitName);
            String kit = config.getString("kits." + kitName);

            kits.add(new KitObject(kitName + ";" + kit));
            kitLogger.debug("kit:", kitName + ";" + kit);


        }
        if (kits.isEmpty()) {
            kitLogger.debug("kits are empty using defaults");
            kits.add(new KitObject(nameExample1 + ";" + kitExample1));
            kits.add(new KitObject(nameExample2 + ";" + kitExample2));

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
        stream.println("# Add Kits here with the following style");
        stream.println("# Name: ID Amount;ID Amount;ID amount (etc)[;-cooldown][;$price]");
        stream.println();
        stream.println("# As an example:");
        stream.println("# " + nameExample1 + ": " + kitExample1);
        stream.println("# will give you 1 Wooden Sword (268) and 1 Wooden Door");
        stream.println("# with a cooldown of 300 seconds (5 minutes)");
        stream.println("# and for the amount of $10");
        stream.println();
        stream.println("kits:");
        kitLogger.debug("let's write the kits!");
        for (KitObject kit : kits) {
            kitLogger.debug("Kit ", kit.toString());
            stream.println("    " + kit);
        }

        /*Iterator iterator = kits.iterator();

        while(iterator.hasNext()){
            kitLogger.debug("hmm");
            String kit = iterator.next().toString();
            kitLogger.debug("Kit: ", kit);
            stream.println("    " + kit);
        }*/


    }


// *******************************************************************************************************

// And now you need to configure commands and listeners


    private void setupCommands() {
        main.registerCommand("kit", new KitCommand(main));
    }

    private void setupListeners() {
/*        addServerListenerEvents(Type.PLUGIN_ENABLE);
        addServerListenerEvents(Type.PLUGIN_DISABLE);*/
    }


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!

// ToDO Add your getters and setters for your config variables here.


    public void resetKits() {
        kits.clear();
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public CopyOnWriteArrayList<KitObject> getKits() {
        return kits;
    }

    public KitLogger getKitLogger() {
        return kitLogger;
    }

    // Last change coming up... choosing the right ClassName for the Logger..

    /**
     * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
     *
     * @return instance of class
     */

    public static KitConfig getInstance() {
        return instance;
    }


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


    KitConfig(TheMonkeyPack plugin, String moduleName) {
        super();
        MODULE_NAME = moduleName;
        main = plugin;
        kitLogger = new KitLogger(MODULE_NAME);
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
            kitLogger.info("Creating default configuration file");
            defaultConfig();
        }
        try {
            config.load(pluginPath + configFile);
        } catch (IOException e) {
            kitLogger.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            kitLogger.severe("Problem with the configuration in " + configFile + "!", e);
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
            kitLogger.info("Using internal Defaults!");
        }
        config.addDefault("configVer", configVer);
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

    void loadConfig() {
        // Starting to update the standard configuration
        configVer = config.getString("configVer");
        // Debug OutPut NOW!
        kitLogger.debug("configCurrent", configCurrent);
        kitLogger.debug("configVer", configVer);
        loadCustomConfig();
        kitLogger.info("Configuration v." + configVer + " loaded.");
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

    boolean writeConfig() {
        kitLogger.debug("creating config");
        boolean success = false;
        try {
            PrintWriter stream;
            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            PluginDescriptionFile pdfFile = main.getDescription();
            stream = new PrintWriter(pluginPath + configFile);
            kitLogger.debug("starting contents");
//Let's write our config ;)
            stream.println("# " + pdfFile.getName() + " " + pdfFile.getVersion() + " by " + pdfFile.getAuthors().toString());
            stream.println("#");
            stream.println("# Configuration File for module [" + MODULE_NAME + "]");
            stream.println("#");
            stream.println("# For detailed assistance please visit: " + mainConfig.getPluginSlug());
            stream.println();
            stream.println("# Configuration Version");
            stream.println("configVer: '" + configVer + "'");
            stream.println();
// Getting the custom config information from the top of the class
            kitLogger.debug("going for customConfig");
            writeCustomConfig(stream);

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            kitLogger.warning("Error saving the " + configFile + ".");
        }
        kitLogger.debug("DefaultConfig written", success);
        return success;
    }


// Checking if the configVersions differ

    /**
     * Method to check if the configuration version are different.
     * will set #configRequiresUpdate to true if versions are different
     */
    void updateNecessary() {
        if (configVer.equalsIgnoreCase(configCurrent)) {
            kitLogger.info("Config is up to date");
        } else {
            kitLogger.warning("Config is not up to date!");
            kitLogger.warning("Config File Version: " + configVer);
            kitLogger.warning("Internal Config Version: " + configCurrent);
            kitLogger.warning("It is suggested to update the config.yml!");
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
                kitLogger.info("Configuration was updated with new default values.");
                kitLogger.info("Please change them to your liking.");
            } else {
                kitLogger.warning("Configuration file could not be auto updated.");
                kitLogger.warning("Please rename " + configFile + " and try again.");
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
            kitLogger.info("Config reloaded");
            msg = "Config was reloaded";
        } else {
            kitLogger.severe("Reloading Config before it exists.");
            kitLogger.severe("Flog the developer!");
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


