package org.simiancage.bukkit.TheMonkeyPack.loging; /**
 *
 * PluginName: ${plugin}
 * Class: MainLogger
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 18:11
 *
 */

import org.bukkit.plugin.Plugin;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The MainLogger Class allows you to log in an easy way to the craftbukkit console.
 * It supports different log levels:<p>
 * {@link #info(String) info} <p>
 * {@link #warning(String) warning} <p>
 * {@link #severe(String) severe} <p>
 * {@link #debug(String, Object) debug} <p>
 * {@link #error(String) error} <p>
 * {@link #log(java.util.logging.Level, String, Throwable) log}<p>
 * and checks with the {@link org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig
 * It also supports a plugin {@link #enableMsg()} and {@link #disableMsg()}.<p>
 * Initialization of the MainLogger is being done by {@link #getInstance(String, String)}  getInstance} .<p>
 * Based on the work of xZise.
 *
 * @author Don Redhorse
 * @author xZise
 */
@SuppressWarnings({"WeakerAccess", "UnusedDeclaration"})
public class MainLogger {
    /**
     * Reference to the logger
     */
    private final Logger logger;
    /**
     * contains the plugin name
     */
    private final String pluginName;
    /**
     * contains the plugin pluginVersion
     */
    private final String pluginVersion;
    /**
     * Instance of the MainLogger
     */
    private static MainLogger instance = null;

// ToDo Change the org.simiancage.bukkit.TheMonkeyPack.loging.ConfigurationClass to the correct name of the Class
    /**
     * Instance of the ConfigurationCalls
     */
    private final MainConfig config = MainConfig.getInstance();


    /**
     * Method to get the instance of the MainLogger.
     * MainLogger will be initialized when necessary.
     *
     * @param loggerName should be "Minecraft"
     * @param pluginName the pluginname as string
     *
     * @return instance of the MainLogger
     */
    public static MainLogger getInstance(String loggerName, String pluginName) {
        if (instance == null) {
            instance = new MainLogger(loggerName, pluginName);
        }
        return instance;
    }


    /**
     * Method to get the instance of the MainLogger.
     * MainLogger will be initialized when necessary.
     *
     * @param pluginName the pluginname as string
     *
     * @return instance of the MainLogger
     */
    public static MainLogger getInstance(String pluginName) {
        if (instance == null) {
            instance = new MainLogger(pluginName);
        }
        return instance;
    }


    /**
     * Method to get the instance of the MainLogger.
     * MainLogger will be initialized when necessary.
     * Use this to initialize the MainLogger.
     *
     * @param pluginName the pluginname as Plugin
     *
     * @return instance of the MainLogger
     */
    public static MainLogger getInstance(Plugin pluginName) {
        if (instance == null) {
            instance = new MainLogger(pluginName);
        }
        return instance;
    }


    /**
     * Method to get the instance of the MainLogger.
     *
     * @return instance of the MainLogger, NOTE: This can be NULL
     */
    public static MainLogger getLogger() {
        return instance;
    }

    /**
     * Constructor to initialize the MainLogger via LoggerName and PluginName.
     * will hand over to {@link #MainLogger(java.util.logging.Logger, String, String)}
     *
     * @param loggerName should be "Minecraft"
     * @param pluginName the name of the plugin
     */
    private MainLogger(String loggerName, String pluginName) {
        this(Logger.getLogger(loggerName), pluginName, "");
    }


    /**
     * Constructor to initialize the MainLogger via the PluginName.
     * will hand over to {@link #MainLogger(String, String)}
     *
     * @param pluginName the name of the plugin
     */
    private MainLogger(String pluginName) {
        this("Minecraft", pluginName);
    }


    /**
     * Constructor which finally initializes the MainLogger.
     *
     * @param logger     Logger object of Minecraft
     * @param pluginName the name of the plugin
     * @param version    the version of the plugin
     */

    private MainLogger(Logger logger, String pluginName, String version) {
        this.logger = logger;
        this.pluginName = pluginName;
        this.pluginVersion = version;
    }


    /**
     * Constructor to initialize the MainLogger via a Plugin Object.
     * will hand over to {@link #MainLogger(java.util.logging.Logger, String, String)}
     *
     * @param plugin the plugin object
     */
    private MainLogger(Plugin plugin) {
        this(plugin.getServer().getLogger(), plugin.getDescription().getName(), plugin.getDescription().getVersion());
    }

// Nothing to change from here on....
// ***************************************************************************************************************************    

    /**
     * will output with INFO level to console if debugging is enabled and also prints out the object contents.
     *
     * @param msg    message to output
     * @param object object to output, will use .toString()
     */
    public void debug(String msg, Object object) {
        if (config.isDebugLogEnabled()) {
            this.logger.info(this.formatMessage(msg + "= [" + object.toString() + "]"));
        }
    }

    /**
     * will output with INFO level to console if debugging is enabled.
     *
     * @param msg message to output
     */
    public void debug(String msg) {
        if (config.isDebugLogEnabled()) {
            this.logger.info(msg);
        }
    }

    /**
     * formats the message by adding the [PluginName] in front.
     *
     * @param message to format, e.g. this is a test
     *
     * @return formated message, e.g. [PluginName] this is a test
     */
    private String formatMessage(String message) {
        return "[" + pluginName + "] " + message;
    }

    /**
     * will output with INFO level to console if error logging is enabled
     *
     * @param msg message to output
     */
    public void info(String msg) {
        if (config.isErrorLogEnabled()) {
            this.logger.info(this.formatMessage(msg));
        }
    }

    /**
     * will output with WARN level to console
     *
     * @param msg message to output
     */
    public void warning(String msg) {
        this.logger.warning(this.formatMessage(msg));
    }

    /**
     * will output with SEVERE level to console
     *
     * @param msg message to output
     */
    public void severe(String msg) {
        this.logger.severe(this.formatMessage(msg));
    }

    /**
     * will output with SEVERE level to console and also prints the exception
     *
     * @param msg       message to output
     * @param exception exception to output
     */
    public void severe(String msg, Throwable exception) {
        this.log(Level.SEVERE, msg, exception);
    }

    /**
     * will output with specified level to console
     *
     * @param level     LoggerLevel = INFO, WARNING, SEVERE
     * @param msg       message to output
     * @param exception exception to output
     */
    public void log(Level level, String msg, Throwable exception) {
        this.logger.log(level, this.formatMessage(msg), exception);
    }

    /**
     * will output with WARNING level to console and also prints exception
     *
     * @param msg       message to output
     * @param exception exception to output
     */
    public void warning(String msg, Throwable exception) {
        this.log(Level.WARNING, msg, exception);
    }

    /**
     * will output [PluginName] v "VersionNumber" enabled to console
     */
    public void enableMsg() {
        this.info("v" + this.pluginVersion + " enabled");
    }

    /**
     * will output [PluginName] v "VersionNumber" disabled to console
     */
    public void disableMsg() {
        this.info("v" + this.pluginVersion + " disabled");
    }

    /**
     * will output with INFO level to console if error logging is enabled
     *
     * @param msg message to output
     */
    public void error(String msg) {
        if (config.isErrorLogEnabled()) {
            this.logger.info(msg);
        }
    }


}

