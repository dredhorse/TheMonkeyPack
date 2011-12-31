package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.commands.TNTCommand;
import org.simiancage.bukkit.TheMonkeyPack.commands.TNTReclaimCommand;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTControlConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class TNTControlConfig extends Configs {


    /**
     * Messages for translation
     */
    public enum Messages {
        YOU_CANNOT_PLACE_TNT_ABOVE("You cannot place TNT above level %abovelevel", "Message displayed when player tries to place TNT above the limit"),
        YOU_CANNOT_PLACE_TNT_ONSURFACE("You cannot place TNT on the surface!", "Message displayed when player tries to place TNT on the surface"),
        YOU_DONOT_HAVE_THE_PERMISSION_TO_USE_TNT("You do not have the permission to use TNT", "Message displayed when player tries to place TNT without permission"),
        YOU_CANNOT_ACTIVATE_TNT_ABOVE("You cannot activate TNT above level %abovelevel", "Message displayed when player tries to activate TNT above the limit"),
        YOU_CANNOT_ACTIVATE_TNT_ONSURFACE("You cannot activate TNT on the surface!", "Message displayed when player tries to activate TNT on the surface"),
        INFO_ELEVATION_LIMIT("Elevation Limit : %abovelevel blocks", "Info Messages to display Elevation Limit"),
        INFO_BLAST_RADIUS("Radius : %radius", "Info Messages to display Blast Radius"),
        INFO_BLAST_YIELD("Yield : %yield", "Info Messages to display Blast Yield"),
        INFO_BLAST_FIRE("TNT Blast causes fire: %onOff", "Info Messages to display if TNT explosion causes fire"),
        INFO_ON_SURFACE("TNT is allowed on surface : %onOff", "Info Messages to display if TNT is allowed on the surface"),
        INFO_SLACK("including %blocks Feet under.", "Info Messages to display if Slack is allowed."),
        INFO_ARE_ALLOWED("You are allowed to use TNT!", "Info Messages to display if somebody is allowed to use TNT"),
        INFO_ARE_NOT_ALLOWED("You are NOT allowed to use TNT!", "Info Messages to display if somebody is NOT allowed to use TNT"),
        INFO_YOU_ARE_LIMITED("You can only %placeActivate TNT at level %abovelevel and below", "Info Messages to display if player is restricted to below tntBlastLimit"),
        INFO_ALLOWED_RECLAIM_TOOL("You are allowed to use the reclaim tool %tool", "Info Messages to display the reclaim tool"),
        INFO_ALLOWED_RECLAIM_COMMAND("You are allowed to use the reclaim command", "Info Message to display if player is allowed the reclaim command"),
        RECLAIMING_IS_ONOFF("Reclaiming TNT blocks is %onOff", "Message displayed when player issues the reclaim command without options"),
        ON_STRING("On", "String for On"),
        OFF_STRING("Off", "String for Off"),
        PLACE_STRING("place", "String for place"),
        ACTIVATE_STRING("activate", "String for activate"),
        RECLAIM_COMMAND_REQUIRES("The %cmd command requires [on|off] to be useful", "Message displayed when reclaim command is being executed without an option."),
        SETTING_RECLAIM_TO("Setting reclaiming TNT blocks to: %onOff", "Message displayed when the reclaim option is changed.");


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

    public enum TNT_COMMAND {
        TNT_CMD("tnt", "The alias command for /tmptnt WITHOUT the / !!!"),
        TNT_CMD_DESCRIPTION("Shows the status of TNT Control", "The command description."),
        TNT_CMD_PERMISSION_DESCRIPTION("allows access to the status command", "The command permissions description."),
        TNT_HELP_OPTION("help", "The alias for the help option"),
        TNT_HELP_MESSAGE("displays this help", "The help message displayed.");

        private String content;
        private String commentMessage;

        private TNT_COMMAND(String content, String comment) {
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


    public enum TNT_RECLAIM_COMMAND {
        TNT_RECLAIM_CMD("reclaim", "The alias command for /tmptntreclaim WITHOUT the / !!!"),
        TNT_RECLAIM_CMD_DESCRIPTION("Allows you to reclaim TNT.", "The command description."),
        TNT_RECLAIM_CMD_PERMISSION_DESCRIPTION("allows access to the reclaim command", "The command permissions description."),
        TNT_RECLAIM_HELP_OPTION("help", "The alias for the help option"),
        TNT_RECLAIM_HELP_MESSAGE("displays this help", "The help message displayed.");

        private String content;
        private String commentMessage;

        private TNT_RECLAIM_COMMAND(String content, String comment) {
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


    public enum TNT_CONTROL_PERMISSIONS {
        TNT_ALLOWED, TNT_ABOVELIMIT_PLACE, TNT_ABOVELIMIT_ACTIVATE, TNT_ON_SURFACE_PLACE, TNT_ON_SURFACE_ACTIVATE;

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
    private static TNTControlConfig instance = null;

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
    private static String configFile = "TNTControlConfig.yml";
    /**
     * Is the configuration available or did we have problems?
     */
    private boolean configAvailable = false;

    /**
     * Do we require a config update?
     */
    private boolean configRequiresUpdate = false;

    private String pluginPath;
    private Commands TNTCommand;
    private Commands TNTReclaimCommand;

// <<<<=== here..


    private TNTControlLogger tntControlLogger;
    private TNTControlHelper tntControlHelper;


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

    private final String CMD_TNT_COMMAND = "tmptnt";
    private final String CMD_TNT_RECLAIM_COMMAND = "tmptntreclaim";

    // permission for displaying status messages
    private final String PERM_TNT_COMMAND = "tmp.tnt.command";
    // permission for reclaiming tnt
    private final String PERM_TNT_RECLAIM_COMMAND = "tmp.tnt.command.reclaim";

    /* // EnumMap for messages

private EnumMap<Messages, String> messageTranslations = new EnumMap<Messages, String>(Messages.class);

// EnumMap for TNT_COMMAND

private EnumMap<TNT_COMMAND, String> tntCommandConfig = new EnumMap<TNT_COMMAND, String>(TNT_COMMAND.class);

// EnumMap for TNT_RECLAIM_COMMAND

private  EnumMap<TNT_RECLAIM_COMMAND, String> tntReclaimCommandConfig = new EnumMap<TNT_RECLAIM_COMMAND, String>(TNT_RECLAIM_COMMAND.class);*/


// Default Config Variables start here!

    private int tntBlastLimit = 54;                   // 54th level
    private final String TNT_BLAST_LIMIT = "tntBlastLimit";
    private int tntBlastYield = 30;                  // 30%
    private final String TNT_BLAST_YIELD = "tntBlastYield";
    private int tntBlastRadius = 4;                 // 4 blocks
    private final String TNT_BLAST_RADIUS = "tntBlastRadius";
    private boolean tntBlastCauseFire = false;      // TNT Blast will cause fire
    private final String TNT_BLAST_CAUSE_FIRE = "tntBlastCauseFire";
    private boolean tntOnSurface = false;           // Allow TNT placement on surface
    private final String TNT_ON_SURFACE = "tntOnSurface";
    private int tntBelowSurfaceLevel = 5;           // IF tntOnSurface is true allow some slack
    private final String TNT_BELOW_SURFACE_LEVEL = "tntBelowSurfaceLevel";
    private boolean tntAllowReclaimTool = true;     // Allow using reclaim tool to get TNT
    private final String TNT_ALLOW_RECLAIM_TOOL = "tntAllowReclaimTool";
    private int tntReclaimTool = 359;               // Shears
    private final String TNT_RECLAIM_TOOL = "tntReclaimTool";


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

        config.addDefault(TNT_BLAST_LIMIT, tntBlastLimit);
        config.addDefault(TNT_BLAST_YIELD, tntBlastYield);
        config.addDefault(TNT_BLAST_RADIUS, tntBlastRadius);
        config.addDefault(TNT_ON_SURFACE, tntOnSurface);
        config.addDefault(TNT_BELOW_SURFACE_LEVEL, tntBelowSurfaceLevel);
        config.addDefault(TNT_ALLOW_RECLAIM_TOOL, tntAllowReclaimTool);
        config.addDefault(TNT_RECLAIM_TOOL, tntReclaimTool);
        config.addDefault(TNT_BLAST_CAUSE_FIRE, tntBlastCauseFire);


        // now adding TNT_COMMAND

        for (TNT_COMMAND node : TNT_COMMAND.values()) {
            config.addDefault(node.toString(), node.getContent());
        }

        // now adding TNT_RECLAIM_COMMAND

        for (TNT_RECLAIM_COMMAND node : TNT_RECLAIM_COMMAND.values()) {
            config.addDefault(node.toString(), node.getContent());
        }

        // now adding messages

        for (Messages node : Messages.values()) {
            config.addDefault(node.toString(), node.getMessage());
        }

        // now loading TNT_COMMAND

        for (TNT_COMMAND node : TNT_COMMAND.values()) {
            config.addDefault(node.toString(), node.getContent());

        }

        // now loading TNT_RECLAIM_COMMAND

        for (TNT_RECLAIM_COMMAND node : TNT_RECLAIM_COMMAND.values()) {
            config.addDefault(node.toString(), node.getContent());
        }

    }


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    void loadCustomConfig() {


        tntBlastLimit = config.getInt(TNT_BLAST_LIMIT);
        tntBlastYield = config.getInt(TNT_BLAST_YIELD);
        tntBlastRadius = config.getInt(TNT_BLAST_RADIUS);
        tntOnSurface = config.getBoolean(TNT_ON_SURFACE);
        tntBelowSurfaceLevel = config.getInt(TNT_BELOW_SURFACE_LEVEL);
        tntAllowReclaimTool = config.getBoolean(TNT_ALLOW_RECLAIM_TOOL);
        tntReclaimTool = config.getInt(TNT_RECLAIM_TOOL);
        tntBlastCauseFire = config.getBoolean(TNT_BLAST_CAUSE_FIRE);


        tntControlLogger.debug(TNT_BLAST_LIMIT, tntBlastLimit);
        tntControlLogger.debug(TNT_BLAST_YIELD, tntBlastYield);
        tntControlLogger.debug(TNT_BLAST_RADIUS, tntBlastRadius);
        tntControlLogger.debug(TNT_BLAST_CAUSE_FIRE, tntBlastCauseFire);
        tntControlLogger.debug(TNT_ON_SURFACE, tntOnSurface);
        tntControlLogger.debug(TNT_BELOW_SURFACE_LEVEL, tntBelowSurfaceLevel);
        tntControlLogger.debug(TNT_ALLOW_RECLAIM_TOOL, tntAllowReclaimTool);
        tntControlLogger.debug(TNT_RECLAIM_TOOL, tntReclaimTool);

        // now loading TNT_COMMAND

        for (TNT_COMMAND node : TNT_COMMAND.values()) {
            if (config.contains(node.toString())) {
                node.setContent(config.getString(node.toString()));
                tntControlLogger.debug(node + ": " + node.getContent());
            } else {
                tntControlLogger.warning(node + " doesn't exist in " + configFile);
                tntControlLogger.warning("Using internal defaults!");
            }
        }

        // now loading TNT_RECLAIM_COMMAND

        for (TNT_RECLAIM_COMMAND node : TNT_RECLAIM_COMMAND.values()) {
            if (config.contains(node.toString())) {
                node.setContent(config.getString(node.toString()));
                tntControlLogger.debug(node + ": " + node.getContent());
            } else {
                tntControlLogger.warning(node + " doesn't exist in " + configFile);
                tntControlLogger.warning("Using internal defaults!");
            }
        }


        // now loading messages

        for (Messages node : Messages.values()) {
            if (config.contains(node.toString())) {
                node.setMessage(config.getString(node.toString()));
                tntControlLogger.debug(node + ": " + getMessage(node));
            } else {
                tntControlLogger.warning(node + " doesn't exist in " + configFile);
                tntControlLogger.warning("Using internal defaults!");
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
        stream.println("# --- TNT Configuration");
        stream.println();

        stream.println("# Level up to which players with the " + TNT_CONTROL_PERMISSIONS.TNT_ALLOWED + " permission ");
        stream.println("# are allowed to place and activate TNT. ");
        stream.println(TNT_BLAST_LIMIT + ": " + tntBlastLimit);
        stream.println("# Yield of the TNT Blast in percent (WITHOUT % !!) ");
        stream.println(TNT_BLAST_YIELD + ": " + tntBlastYield);
        stream.println("# Radius of the TNT Blast in blocks");
        stream.println(TNT_BLAST_RADIUS + ": " + tntBlastRadius);
        stream.println("# Will TNT Blast cause a fire?");
        stream.println(TNT_BLAST_CAUSE_FIRE + ": " + tntBlastCauseFire);
        stream.println("# Allow placement / activation of TNT on the surface. Note: This will prohibit placing / activating TNT even if the surface");
        stream.println("# is below Level " + tntBlastLimit + " and even if the player has the " + TNT_CONTROL_PERMISSIONS.TNT_ABOVELIMIT_PLACE + " permission.");
        stream.println("# NOTE: This means the highest block atm, so this means ON a tree for example");
        stream.println(TNT_ON_SURFACE + ": " + tntOnSurface);
        stream.println("# Give the TNT placement / activation some slack and also allow TNT to be placed / activated x blocks below surface.");
        stream.println(TNT_BELOW_SURFACE_LEVEL + ": " + tntBelowSurfaceLevel);
        stream.println("# Allow a tool to be used to reclaim TNT");
        stream.println(TNT_ALLOW_RECLAIM_TOOL + ": " + tntAllowReclaimTool);
        stream.println("# Tool to be used for reclaiming");
        stream.println(TNT_RECLAIM_TOOL + ": " + tntReclaimTool);


        stream.println();
        stream.println("# --- Translation Features");
        stream.println();
        stream.println("# Almost everything player visible can be translated!");
        stream.println("# Please change to your liking and use the following variables");
        stream.println("# %player = playername, %cmd = command, %help = help option");
        stream.println("# %abovelevel = blastlimit level, %radius = radius of blast");
        stream.println("# %yield = yield of blast, %blocks = replaced with " + TNT_BELOW_SURFACE_LEVEL);
        stream.println("# %placeActivate = replaced by place or activate, %onOff = replaced by On or Off");

        stream.println();

// than the commands translations

        for (TNT_COMMAND node : TNT_COMMAND.values()) {
            stream.println("# " + node.getComment());
            stream.println(node + ": \"" + node.getContent() + "\"");
        }
        stream.println();
        for (TNT_RECLAIM_COMMAND node : TNT_RECLAIM_COMMAND.values()) {
            stream.println("# " + node.getComment());
            stream.println(node + ": \"" + node.getContent() + "\"");
        }

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
        main.registerCommand(CMD_TNT_COMMAND, new TNTCommand(main));
        main.registerCommand(CMD_TNT_RECLAIM_COMMAND, new TNTReclaimCommand(main));


        // registering the additional permissions

        for (TNT_CONTROL_PERMISSIONS perm : TNT_CONTROL_PERMISSIONS.values()) {
            main.getServer().getPluginManager().addPermission(perm.asPermission());
        }


    }

    private void setupListeners() {
        mainConfig.addBlockListeners(Type.BLOCK_DAMAGE);
        mainConfig.addBlockListeners(Type.BLOCK_PLACE);
        mainConfig.addEntityListeners(Type.EXPLOSION_PRIME);
        mainConfig.addEntityListeners(Type.ENTITY_DAMAGE);
        mainConfig.addEntityListeners(Type.ENTITY_EXPLODE);
        mainConfig.addPlayerListeners(Type.PLAYER_QUIT);
    }


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!


// ToDO Add your getters and setters for your config variables here.


    public String getPERM_TNT_COMMAND() {
        return PERM_TNT_COMMAND;
    }

    public String getPERM_TNT_RECLAIM_COMMAND() {
        return PERM_TNT_RECLAIM_COMMAND;
    }


    public boolean isTntBlastCauseFire() {
        return tntBlastCauseFire;
    }

    public void setTntBlastCauseFire(boolean tntBlastCauseFire) {
        this.tntBlastCauseFire = tntBlastCauseFire;
    }

    public boolean isReclaimTool(ItemStack itemStack) {
        boolean isTool = false;
        if (tntReclaimTool == itemStack.getTypeId()) {
            isTool = true;
            tntControlLogger.debug("isReclaimTool", isTool);
        }
        return isTool;
    }

    public int getTntReclaimTool() {
        return tntReclaimTool;
    }

    public void setTntReclaimTool(int tntReclaimTool) {
        this.tntReclaimTool = tntReclaimTool;
    }

    public boolean isTntAllowReclaimTool() {
        return tntAllowReclaimTool;
    }

    public void setTntAllowReclaimTool(boolean tntAllowReclaimTool) {
        this.tntAllowReclaimTool = tntAllowReclaimTool;
    }

    public int getTntBelowSurfaceLevel() {
        return tntBelowSurfaceLevel;
    }

    public void setTntBelowSurfaceLevel(int tntBelowSurfaceLevel) {
        this.tntBelowSurfaceLevel = tntBelowSurfaceLevel;
    }

    public int getTntBlastLimit() {
        return tntBlastLimit;
    }

    public int getTntBlastRadius() {
        return tntBlastRadius;
    }

    public float getTntBlastYield() {
        return (float) tntBlastYield / 100;
    }

    public boolean isTntOnSurfaceEnabled() {
        return tntOnSurface;
    }

    public void setTntBlastLimit(int tntBlastLimit) {
        this.tntBlastLimit = tntBlastLimit;
    }

    public void setTntBlastRadius(int tntBlastRadius) {
        this.tntBlastRadius = tntBlastRadius;
    }

    public void setTntBlastYield(int tntBlastYield) {
        this.tntBlastYield = tntBlastYield;
    }

    public void setTntOnSurface(boolean tntOnSurface) {
        this.tntOnSurface = tntOnSurface;
    }

    public TNTControlHelper getTNTControlHelper() {
        return tntControlHelper;
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


    public TNTControlLogger getTNTControlLogger() {
        return tntControlLogger;
    }


    // Last change coming up... choosing the right ClassName for the Logger..

    /**
     * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
     *
     * @return instance of class
     */

    public static TNTControlConfig getInstance() {
        return instance;
    }


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


    TNTControlConfig(TheMonkeyPack plugin, String moduleName) {
        super();
        MODULE_NAME = moduleName;
        main = plugin;
        tntControlLogger = new TNTControlLogger(MODULE_NAME);
        mainConfig = main.getMainConfig();
        pluginPath = main.getDataFolder() + System.getProperty("file.separator");
        instance = this;
        setupConfig();
        tntControlHelper = TNTControlHelper.getInstance(main);
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
            tntControlLogger.info("Creating default configuration file");
            defaultConfig();
        }

// adding the default values

        customDefaultConfig();

        try {
            config.load(pluginPath + configFile);
        } catch (IOException e) {
            tntControlLogger.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            tntControlLogger.severe("Problem with the configuration in " + configFile + "!", e);
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
            tntControlLogger.severe("Problems writing default config!");
            tntControlLogger.info("Using internal Defaults!");
        } else {
            tntControlLogger.debug("DefaultConfig written");
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
        tntControlLogger.debug("configCurrent", configCurrent);
        tntControlLogger.debug("configVer", configVer);
        loadCustomConfig();
        tntControlLogger.info("Configuration v." + configVer + " loaded.");
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
        tntControlLogger.debug("creating config");
        boolean success = false;
        try {
            PrintWriter stream;
            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            PluginDescriptionFile pdfFile = main.getDescription();
            stream = new PrintWriter(pluginPath + configFile);
            tntControlLogger.debug("starting contents");
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
            tntControlLogger.debug("going for customConfig");
            writeCustomConfig(stream);

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            tntControlLogger.warning("Error saving the " + configFile + ".");
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
            tntControlLogger.info("Config is up to date");
        } else {
            tntControlLogger.warning("Config is not up to date!");
            tntControlLogger.warning("Config File Version: " + configVer);
            tntControlLogger.warning("Internal Config Version: " + configCurrent);
            tntControlLogger.warning("It is suggested to update the config.yml!");
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
                tntControlLogger.info("Configuration was updated with new default values.");
                tntControlLogger.info("Please change them to your liking.");
            } else {
                tntControlLogger.warning("Configuration file could not be auto updated.");
                tntControlLogger.warning("Please rename " + configFile + " and try again.");
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
            tntControlLogger.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            tntControlLogger.severe("Problem with the configuration in " + configFile + "!", e);
        }
        String msg;
        if (configAvailable) {
            loadConfig();
            tntControlLogger.info("Config reloaded");
            msg = MODULE_NAME + " Config was reloaded";
        } else {
            tntControlLogger.severe("Reloading Config before it exists.");
            tntControlLogger.severe("Flog the developer!");
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

    /**
     * Methode to return the content of the TNT_COMMAND
     *
     * @param node
     *
     * @return
     */
    public String getTNTCommandConfig(TNT_COMMAND node) {
        return node.getContent();
    }

    public String getTNTReclaimCommandConfg(TNT_RECLAIM_COMMAND node) {
        return node.getContent();
    }

}


