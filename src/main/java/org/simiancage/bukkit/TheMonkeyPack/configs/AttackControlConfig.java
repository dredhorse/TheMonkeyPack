package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.loging.AttackControlLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * PluginName: TheMonkeyPack
 * Class: AttackControlConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class AttackControlConfig extends Configs {


    public enum ATTACK_CONTROL_PERMISSIONS {
        AC_ALL, AC_MONSTER, AC_BLAZE, AC_CAVESPIDER, AC_CREEPER, AC_ENDERDRAGON, AC_ENDERMAN, AC_GHAST, AC_GIANT,
        AC_MAGMACUBE, AC_PIGZOMBIE, AC_SILVERFISH, AC_SKELETON, AC_SLIME, AC_SNOWMAN, AC_SPIDER, AC_WOLF, AC_ZOMBIE;

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


    private static String MODULE_NAME;

    /**
     * Instance of the Configuration Class
     */
    private static AttackControlConfig instance = null;

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
    private static String configFile = "AttackControlConfig.yml";
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


    private AttackControlLogger attackControlLogger;


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

    private boolean playerCanAttack = false;                   // if player can Attack / Kill if he is not targeted
    private final String PLAYER_CAN_ATTACK = "playerCanAttack";

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

        config.addDefault(PLAYER_CAN_ATTACK, playerCanAttack);


    }


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    void loadCustomConfig() {


        playerCanAttack = config.getBoolean(PLAYER_CAN_ATTACK);
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
        stream.println("# --- Attack Control Configuration");
        stream.println();

        stream.println("# Allow the player to attack those tamed beasts (Cheater!) ");
        stream.println(PLAYER_CAN_ATTACK + ": " + playerCanAttack);


    }


// *******************************************************************************************************

// And now you need to configure commands and listeners


    private void setupCommands() {
        // registering the additional permissions

        for (ATTACK_CONTROL_PERMISSIONS perm : ATTACK_CONTROL_PERMISSIONS.values()) {
            main.getServer().getPluginManager().addPermission(perm.asPermission());
        }


    }

    private void setupListeners() {
        mainConfig.addEntityListeners(Type.ENTITY_TARGET);
        mainConfig.addEntityListeners(Type.ENTITY_DAMAGE);

    }


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!


// ToDO Add your getters and setters for your config variables here.


    public AttackControlLogger getAttackControlLogger() {
        return attackControlLogger;
    }


    public boolean isPlayerCanAttack() {
        return playerCanAttack;
    }

    public void setPlayerCanAttack(boolean playerCanAttack) {
        this.playerCanAttack = playerCanAttack;
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

    public static AttackControlConfig getInstance() {
        return instance;
    }


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


    AttackControlConfig(TheMonkeyPack plugin, String moduleName) {
        super();
        MODULE_NAME = moduleName;
        main = plugin;
        attackControlLogger = new AttackControlLogger(MODULE_NAME);
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
            attackControlLogger.info("Creating default configuration file");
            defaultConfig();
        }

// adding the default values

        customDefaultConfig();

        try {
            config.load(pluginPath + configFile);
        } catch (IOException e) {
            attackControlLogger.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            attackControlLogger.severe("Problem with the configuration in " + configFile + "!", e);
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
            attackControlLogger.severe("Problems writing default config!");
            attackControlLogger.info("Using internal Defaults!");
        } else {
            attackControlLogger.debug("DefaultConfig written");
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
        attackControlLogger.debug("configCurrent", configCurrent);
        attackControlLogger.debug("configVer", configVer);
        loadCustomConfig();
        attackControlLogger.info("Configuration v." + configVer + " loaded.");
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
        attackControlLogger.debug("creating config");
        boolean success = false;
        try {
            PrintWriter stream;
            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            PluginDescriptionFile pdfFile = main.getDescription();
            stream = new PrintWriter(pluginPath + configFile);
            attackControlLogger.debug("starting contents");
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
            attackControlLogger.debug("going for customConfig");
            writeCustomConfig(stream);

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            attackControlLogger.warning("Error saving the " + configFile + ".");
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
            attackControlLogger.info("Config is up to date");
        } else {
            attackControlLogger.warning("Config is not up to date!");
            attackControlLogger.warning("Config File Version: " + configVer);
            attackControlLogger.warning("Internal Config Version: " + configCurrent);
            attackControlLogger.warning("It is suggested to update the config.yml!");
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
                attackControlLogger.info("Configuration was updated with new default values.");
                attackControlLogger.info("Please change them to your liking.");
            } else {
                attackControlLogger.warning("Configuration file could not be auto updated.");
                attackControlLogger.warning("Please rename " + configFile + " and try again.");
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
            attackControlLogger.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            attackControlLogger.severe("Problem with the configuration in " + configFile + "!", e);
        }
        String msg;
        if (configAvailable) {
            loadConfig();
            attackControlLogger.info("Config reloaded");
            msg = MODULE_NAME + " Config was reloaded";
        } else {
            attackControlLogger.severe("Reloading Config before it exists.");
            attackControlLogger.severe("Flog the developer!");
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


