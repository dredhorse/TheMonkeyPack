package org.simiancage.bukkit.TheMonkeyPack.configs; /**
 *
 * PluginName: TheMonkeyPack
 * Class: Configs
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 21:47
 *
 */

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

import java.io.PrintWriter;


/**
 * The Configs Class allows you to write a custom config file for craftbukkit plugins incl. comments.
 * It allows autoupdating config changes, checking for plugin updates and writing back the configuration.
 * Please note that writing to the config file will overwrite any manual changes.<p>
 * You NEED to fix all ToDos, otherwise the class will NOT work!<p>
 *
 * @author Don Redhorse
 */
@SuppressWarnings({"UnusedDeclaration"})
public abstract class Configs {

    /**
     * Instance of the Configuration Class
     */
    private static Configs instance = null;

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
    private Plugin plugin;
    /**
     * Configuration File Name
     */
    // private static String configFile = "config.yml";
    /**
     * Is the configuration available or did we have problems?
     */
    private boolean configAvailable = false;
// Default plugin configuration
    /**
     * Do we require a config update?
     */
    private boolean configRequiresUpdate = false;

    //the original config  and logger

    MainConfig mainConfig;
    private MainLogger mainLogger;

// <<<<=== here..


// and now the real stuff


    /**
     * Method to setup the config variables with default values
     */

    abstract void setupCustomDefaultVariables();


// And than we add the defaults

    /**
     * Method to add the config variables to the default configuration
     */

    abstract void customDefaultConfig();


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    abstract void loadCustomConfig();
// And than we write it....


    /**
     * Method to write the custom config variables into the config file
     *
     * @param stream will be handed over by  writeConfig
     */

    abstract void writeCustomConfig(PrintWriter stream);


// Last change coming up... choosing the right ClassName for the Logger..


    Configs() {
    }


// than the getters


    /**
     * Method to return the Config File Version
     *
     * @return configVer  Config File Version
     */
    abstract String configVer();

    /**
     * Method to return if we need to update the config
     *
     * @return configRequiresUpdate
     */
    abstract boolean isConfigRequiresUpdate();

// And the rest

// Setting up the config

    /**
     * Method to setup the configuration.
     * If the configuration file doesn't exist it will be created by
     * After that the configuration is loaded {@link #loadConfig()}
     * We than check if an configuration update is necessary {@link #updateNecessary()}
     *
     * @see #loadConfig()
     * @see #updateNecessary()
     * @see #updateConfig()
     */

    abstract void setupConfig();


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

    abstract void loadConfig();

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

    abstract boolean writeConfig();


// Checking if the configVersions differ

    /**
     * Method to check if the configuration version are different.
     * will set #configRequiresUpdate to true if versions are different
     */
    abstract void updateNecessary();

// Updating the config

    /**
     * Method to update the configuration if it is necessary.
     */
    abstract void updateConfig();

// Reloading the config

    /**
     * Method to reload the configuration.
     *
     * @return msg with the status of the reload
     */

    abstract String reloadConfig();
// Saving the config


    /**
     * Method to save the config to file.
     *
     * @return true if the save was successful
     */
    abstract boolean saveConfig();

}
