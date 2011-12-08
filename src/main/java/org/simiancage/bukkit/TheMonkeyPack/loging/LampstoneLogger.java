package org.simiancage.bukkit.TheMonkeyPack.loging; /**
 *
 * PluginName: ${plugin}
 * Class: LampstoneLogger
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 22:23
 *
 */

import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The LampstoneLogger Class allows you to log in an easy way to the craftbukkit console.
 * It supports different log levels:<p>
 * {@link #info(String) info} <p>
 * {@link #warning(String) warning} <p>
 * {@link #severe(String) severe} <p>
 * {@link #debug(String, Object) debug} <p>
 * {@link #error(String) error} <p>
 * {@link #log(java.util.logging.Level, String, Throwable) log}<p>
 * and checks with the {@link MainConfig} if debug and error logging is
 * enabled.<p>
 * It also supports a plugin {@link #enableMsg()} and {@link #disableMsg()}.<p>
 * Initialization of the LampstoneLogger is being done by {@link #getInstance(String, String)}  getInstance} .<p>
 * Based on the work of xZise.
 *
 * @author Don Redhorse
 * @author xZise
 */
@SuppressWarnings({"WeakerAccess", "UnusedDeclaration"})
public class LampstoneLogger {
    /**
     * Reference to the logger
     */
    private final Logger logger;
    /**
     * contains the plugin name
     */
    private final String moduleName;

    /**
     * Instance of the LampstoneLogger
     */
    private static LampstoneLogger instance = null;

    /**
     * Instance of the ConfigurationCalls
     */
    private final MainConfig config = MainConfig.getInstance();


    /**
     * Method to get the instance of the LampstoneLogger.
     * LampstoneLogger will be initialized when necessary.
     *
     * @param loggerName should be "Minecraft"
     * @param moduleName the pluginname as string
     *
     * @return instance of the LampstoneLogger
     */
    public static LampstoneLogger getInstance(String loggerName, String moduleName) {
        if (instance == null) {
            instance = new LampstoneLogger(loggerName, moduleName);
        }
        return instance;
    }


    /**
     * Method to get the instance of the LampstoneLogger.
     * LampstoneLogger will be initialized when necessary.
     *
     * @param moduleName pluginname as string
     *
     * @return instance of the LampstoneLogger
     */
    public static LampstoneLogger getInstance(String moduleName) {
        if (instance == null) {
            instance = new LampstoneLogger(moduleName);
        }
        return instance;
    }


    /**
     * Method to get the instance of the LampstoneLogger.
     *
     * @return instance of the LampstoneLogger, NOTE: This can be NULL
     */
    public static LampstoneLogger getLogger() {
        return instance;
    }

    /**
     * Constructor to initialize the LampstoneLogger via LoggerName and PluginName.
     * will hand over to {@link #LampstoneLogger(java.util.logging.Logger, String)}
     *
     * @param loggerName should be "Minecraft"
     * @param moduleName the name of the plugin
     */
    private LampstoneLogger(String loggerName, String moduleName) {
        this(Logger.getLogger(loggerName), moduleName);
    }


    /**
     * Constructor to initialize the LampstoneLogger via the PluginName.
     * will hand over to {@link #LampstoneLogger(String, String)}
     *
     * @param moduleName name of the plugin
     */
    private LampstoneLogger(String moduleName) {
        this("Minecraft", moduleName);
    }


    /**
     * Constructor which finally initializes the LampstoneLogger.
     *
     * @param logger     Logger object of Minecraft
     * @param moduleName the name of the plugin
     */

    private LampstoneLogger(Logger logger, String moduleName) {
        this.logger = logger;
        this.moduleName = moduleName;
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
        return "[" + moduleName + "] " + message;
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
        this.info(" enabled");
    }

    /**
     * will output [PluginName] v "VersionNumber" disabled to console
     */
    public void disableMsg() {
        this.info(" disabled");
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

