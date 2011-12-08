package org.simiancage.bukkit.TheMonkeyPack.configs; /**
 *
 * PluginName: TheMonkeyPack
 * Class: LampstoneConfig
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 22:20
 *
 */

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.loging.LampstoneLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * The LampstoneConfig Class allows you to write a custom config file for craftbukkit plugins incl. comments.
 * It allows autoupdating config changes, checking for plugin updates and writing back the configuration.
 * Please note that writing to the config file will overwrite any manual changes.<p>
 * You NEED to fix all ToDos, otherwise the class will NOT work!<p>
 *
 * @author Don Redhorse
 */
@SuppressWarnings({"UnusedDeclaration"})
public class LampstoneConfig extends Configs {

    private static String MODULE_NAME;

    /**
     * Instance of the Configuration Class
     */
    private static LampstoneConfig instance = null;

// Nothing to change from here to ==>>>
    /**
     * Object to handle the configuration
     *
     * @see org.bukkit.configuration.file.FileConfiguration
     */
    private FileConfiguration config;
    /**
     * Object to handle the plugin
     */
    private static Plugin main;
    /**
     * Configuration File Name
     */
    private static String configFile = "LampstoneConfig.yml";
    /**
     * Is the configuration available or did we have problems?
     */
    private boolean configAvailable = false;

    /**
     * Do we require a config update?
     */
    private boolean configRequiresUpdate = false;

// <<<<=== here..


    private static LampstoneLogger lampstoneLogger;

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
// ToDo Start here adding config variables
// You can declare defaults already here or later on in setupCustomDefaultVariables()

// Some examples:

/*    private boolean broadcastAll = true;
    private boolean broadcastGroups = false;
    private ArrayList<String> broadcastTargets = new ArrayList<String>();
    private boolean generalPermChanges = false;
    private Map<String, Object> aliasList;
    private Map<String, Object> defaultAliasList = new HashMap<String, Object>();
    private ArrayList<String> defaultBroadcastTargets = new ArrayList<String>();
    private boolean preferVault = true;
*/

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
// ToDo Start here setting up the config variables with default values

// Some examples

/*
        defaultBroadcastTargets.add("Admin");
        defaultBroadcastTargets.add("Moderators");
        defaultAliasList.put("mod", "Builder,Moderators");
        defaultAliasList.put("admin", "Builder,Admins");
        broadcastTargets = defaultBroadcastTargets;
        aliasList = defaultAliasList;
*/

    }

// And than we add the defaults

    /**
     * Method to add the config variables to the default configuration
     */

    void customDefaultConfig() {
// ToDo Start here adding the config variables to the default configuration

// Some examples


/*
        config.addDefault("preferVault", preferVault);
        config.addDefault("broadcastAll", broadcastAll);
        config.addDefault("broadcastGroups", broadcastGroups);
        config.addDefault("generalPermChanges", generalPermChanges);
*/


    }


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    void loadCustomConfig() {
// ToDo Start here loading the configuration into your config variables

// Some examples

/*
        preferVault = config.getBoolean("preferVault");
        broadcastAll = config.getBoolean("broadcastAll");
        broadcastGroups = config.getBoolean("broadcastGroups");
        generalPermChanges = config.getBoolean("generalPermChanges");
        broadcastTargets = (ArrayList<String>) config.getList("broadcastTargets", defaultBroadcastTargets);
        aliasList = config.getConfigurationSection("aliasList").getValues(true);
*/


        // ToDo Don't forget the debugging

/*
        log.debug("preferVault", preferVault);
        log.debug("broadcastAll", broadcastAll);
        log.debug("broadcastGroups", broadcastGroups);
        log.debug("generalPermChanges", generalPermChanges);
        log.debug("broadcastTargets", broadcastTargets);
        log.debug("aliasList", aliasList);
*/

    }

// And than we write it....


    /**
     * Method to write the custom config variables into the config file
     *
     * @param stream will be handed over by  writeConfig
     */

    void writeCustomConfig(PrintWriter stream) {
//Start here writing your config variables into the config file inkl. all comments


// Some examples

/*
        stream.println("#-------- Plugin Configuration");
        stream.println();
        stream.println("# Prefer Vault Plugin as permission handler");
        stream.println("preferVault: " + preferVault);
        stream.println();
        stream.println("# Broadcast to all players on the server when MakeMeMod is used");
        stream.println("broadcastAll: " + broadcastAll);
        stream.println();
        stream.println("# Broadcast to specific groups on the server when MakeMeMod is used");
        stream.println("broadcastGroups: " + broadcastGroups);
        stream.println();
        stream.println("# Groups to broadcast to");
        stream.println("broadcastTargets:");

        for (String groups : broadcastTargets) {

            stream.println("- '" + groups + "'");
        }
        stream.println();
        stream.println("# Make changes to GroupMembership general or world based?");
        stream.println("# NOTE: You need to have your permissions correctly set up for this,");
        stream.println("#       or you will see strange results.");
        stream.println("generalPermChanges: " + generalPermChanges);
        stream.println();
        stream.println("#--------- Group Change Commands");
        stream.println();
        stream.println("# Customize group change commands in form of");
        stream.println("# alias: GroupTheyAreIn,GroupTheyShouldBeIn");
        stream.println("aliasList:");
        List<String> aliasKeys = new ArrayList<String>(aliasList.keySet());
        for (String alias : aliasKeys) {
            stream.println("    " + alias + ": " + aliasList.get(alias));
        }
*/


    }


// *******************************************************************************************************

// And now you need to create the getters and setters if needed for your config variables    


// The plugin specific getters start here!

// ToDO Add your getters and setters for your config variables here.


// Some examples..


/*
    public boolean isPreferVault() {
        return preferVault;
    }

    public boolean isBroadcastAll() {
        return broadcastAll;
    }

    public boolean isBroadcastGroups() {
        return broadcastGroups;
    }

    public boolean isGeneralPermChanges() {
        return generalPermChanges;
    }

    public ArrayList<String> broadcastTargets() {
        return broadcastTargets;
    }

    public Map<String, Object> aliasList() {
        return aliasList;
    }

    public boolean isAlias(String alias) {
        return aliasList.containsKey(alias);
    }

    public Boolean isValid(String alias) {
        return aliasList.containsKey(alias);
    }

    public String getOldGroup(String alias) {
        String[] groups = aliasList.get(alias).toString().split(",");
        String oldGroup = "";
        if (groups.length < 2) {
            log.warning("There is no correct configuration for command: " + alias);
        } else {
            oldGroup = groups[0];
        }
        log.debug("OldGroup", oldGroup);
        return oldGroup;
    }

    public String getNewGroup(String alias) {
        String[] groups = aliasList.get(alias).toString().split(",");
        String newGroup = "";
        if (groups.length < 2) {
            log.warning("There is no correct configuration for command: " + alias);
        } else {
            newGroup = groups[1];
        }
        log.debug("NewGroup", newGroup);
        return newGroup;
    }

*/

// Last change coming up... choosing the right ClassName for the Logger..

    /**
     * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
     *
     * @return instance of class
     */

    public LampstoneConfig getInstance() {
        return instance;
    }


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!    


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


    LampstoneConfig(Plugin plugin, String moduleName) {
        super();
        MODULE_NAME = moduleName;
        main = plugin;
        lampstoneLogger = LampstoneLogger.getInstance(MODULE_NAME);
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
            lampstoneLogger.info("Creating default configuration file");
            defaultConfig();
        }
// Loading the config from file
        loadConfig();

// Checking internal configCurrent and config file configVer

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
            lampstoneLogger.info("Using internal Defaults!");
        }
        config = main.getConfig();
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
        config = main.getConfig();
        // Starting to update the standard configuration
        configVer = config.getString("configVer");
        // Debug OutPut NOW!
        lampstoneLogger.debug("configCurrent", configCurrent);
        lampstoneLogger.debug("configVer", configVer);
        loadCustomConfig();
        lampstoneLogger.info("Configuration v." + configVer + " loaded.");
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
        boolean success = false;
        try {
            PrintWriter stream;
            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            String pluginPath = main.getDataFolder() + System.getProperty("file.separator");
            PluginDescriptionFile pdfFile = this.main.getDescription();
            stream = new PrintWriter(pluginPath + configFile);
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
            writeCustomConfig(stream);

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            lampstoneLogger.warning("Error saving the " + configFile + ".");
        }
        lampstoneLogger.debug("DefaultConfig written", success);
        return success;
    }


// Checking if the configVersions differ

    /**
     * Method to check if the configuration version are different.
     * will set #configRequiresUpdate to true if versions are different
     */
    void updateNecessary() {
        if (configVer.equalsIgnoreCase(configCurrent)) {
            lampstoneLogger.info("Config is up to date");
        } else {
            lampstoneLogger.warning("Config is not up to date!");
            lampstoneLogger.warning("Config File Version: " + configVer);
            lampstoneLogger.warning("Internal Config Version: " + configCurrent);
            lampstoneLogger.warning("It is suggested to update the config.yml!");
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
                lampstoneLogger.info("Configuration was updated with new default values.");
                lampstoneLogger.info("Please change them to your liking.");
            } else {
                lampstoneLogger.warning("Configuration file could not be auto updated.");
                lampstoneLogger.warning("Please rename " + configFile + " and try again.");
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
            lampstoneLogger.info("Config reloaded");
            msg = "Config was reloaded";
        } else {
            lampstoneLogger.severe("Reloading Config before it exists.");
            lampstoneLogger.severe("Flog the developer!");
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
