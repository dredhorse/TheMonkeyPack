package org.simiancage.bukkit.TheMonkeyPack.configs;

import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event.Type;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.commands.GetPayed.GetPayedCommand;
import org.simiancage.bukkit.TheMonkeyPack.commands.GetPayed.PriceSetCommand;
import org.simiancage.bukkit.TheMonkeyPack.commands.GetPayed.WorkPlaceCommand;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * PluginName: TheMonkeyPack
 * Class: GetPayedConfig
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 22:06
 */

public class GetPayedConfig extends Configs {


    /**
     * Messages for translation
     */
    public enum Messages {
        PAYDAY_EMPLOYER_MESSAGE, PAYDAY_EMPLOYED_MESSAGE, PRICECHECK_MESSAGES_ON, PRICECHECK_MESSAGES_OFF, PAYDAY_MESSAGES_ONOFF,
        DISPLAY_PRICE_SET_MESSAGE, CANT_BREAK_PLACE_AT_SAME_TIME, RIGHT_CLICK_BLOCK_TO_PRICESET, WORKPLACE_CREATE_HELP_MESSAGE,
        WORKPLACE_RENAME_HELP_MESSAGE, WORKPLACE_SET_HELP_MESSAGE, WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE,
        WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE, WORKPLACE_ALREADY_EXIST, WORKPLACE_CREATED_MESSAGE, WORKPLACE_DELETED_MESSAGE,
        YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE, NO_WORKPLACE_WITH_THIS_NAME_MESSAGE, WORKPLACE_RENAMED_TO_MESSAGE,
        WORKPLACE_NAME_MESSAGE, WORKPLACE_OWNER_MESSAGE, WORKPLACE_BREAK_TYPE_MESSAGE, WORKPLACE_BREAK_AMOUNT_MESSAGE,
        WORKPLACE_PLACE_TYPE_MESSAGE, WORKPLACE_PLACE_AMOUNT_MESSAGE, WRONG_BREAK_PLACE_TYPE_MESSAGE, NOT_A_VALID_VARIABLE_MESSAGE,
        VAR_SET_TO_NEW_VALUE_MESSAGE, YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE;

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

    private static String MODULE_NAME;

    /**
     * Instance of the Configuration Class
     */
    private static GetPayedConfig instance = null;

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
    private static String configFile = "GetPayedConfig.yml";
    /**
     * Is the configuration available or did we have problems?
     */
    private boolean configAvailable = false;

    /**
     * Do we require a config update?
     */
    private boolean configRequiresUpdate = false;

    private String pluginPath;
    private Commands GetPayedCommand;

// <<<<=== here..


    private GetPayedLogger getPayedLogger;
    private GetPayedHelper getPayedHelper;


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


// commands

    private final String TMP = "tmp";
    private final String CMD_GETPAYED = "getpayed";
    private final String CMD_WORKPLACE = "workplace";
    private final String CMD_PRICESET = "priceset";

    private String helpOption = "help";
    private final String HELP_OPTION = "helpOption";
    private String displayHelpMessage = "displays this help.";
    private final String DISPLAY_HELP_MESSAGE = "displayHelpMessage";
    private String getPayedCmd = "getpayed";
    private final String GETPAYED_CMD = "getPayedCmd";
    private String getPayedCmdDescription = "get price infos and configure some aspects of getpayed.";
    private final String GETPAYED_CMD_DESCRIPTION = "getPayedCmdDescription";
    private String getPayedCmdPermDescription = "allows access of the getpayed command";
    private final String GETPAYED_CMD_PERM_DESCRIPTION = "getPayedCmdPermDescription";
    private String workPlaceCmd = "workplace";
    private final String WORKPLACE_CMD = "workPlaceCmd";
    private String workPlaceCmdDescription = "configure the workplace";
    private final String WORKPLACE_CMD_DESCRIPTION = "workPlaceCmdDescription";
    private String workPlaceCmdPermDescription = "allows access to the workplace command";
    private final String WORKPLACE_CMD_PERM_DESCRIPTION = "workPlaceCmdPermDescription";
    private String priceSetCmd = "priceset";
    private final String PRICESET_CMD = "priceSetCmd";
    private String priceSetCmdDescription = "set the break or place price of the next block that you right click.";
    private final String PRICESET_CMD_DESCRIPTION = "priceSetCmdDescription";
    private String priceSetCmdPermDescription = "allows acces to the priceset command";
    private final String PRICESET_CMD_PERM_DESCRIPTION = "priceSetCmdPermDescription";


//

    private int workPlaceTool = 284;
    private final String WORKPLACE_TOOL = "workPlaceTool";
    private String defaultBreakType = "percent";
    private final String DEFAULT_BREAK_TYPE = "defaultBreakType";
    private double defaultBreakAmount = 10.0D;
    private final String DEFAULT_BREAK_AMOUNT = "defaultBreakAmount";
    private String defaultPlaceType = "percent";
    private final String DEFAULT_PLACE_TYPE = "defaultPlaceType";
    private double defaultPlaceAmount = 10.0D;
    private final String DEFAULT_PLACE_AMOUNT = "defaultPlaceAmount";
    private String workPlaceGreeting = "You just entered a workplace.";
    private final String WORKPLACE_GREETING = "workPlaceGreeting";
    private String workPlaceFarewell = "You just left a workplace.";
    private final String WORKPLACE_FAREWELL = "workPlaceFarewell";
    private int workPlaceSaveInterval = 360;
    private final String WORKPLACE_SAVE_INTERVAL = "workPlaceSaveInterval";
    private int entryExitRefreshRate = 10;
    private final String ENTRY_EXIT_REFRESH_RATE = "entryExitRefreshRate";
    private int payDayInterval = 60;
    private final String PAYDAY_INTERVAL = "payDayInterval";
    private boolean clearBufferOnPayday = true;
    private final String CLEAR_BUFFER_ON_PAYDAY = "clearBufferOnPayday";
    private int bufferSize = 1000;
    private final String BUFFER_SIZE = "bufferSize";
    private String payDayMessage = "It's payday! You earned $$";
    private final String PAYDAY_MESSAGE = "payDayMessage";
    private boolean payDayMessageEnabled = true;
    private final String PAYDAY_MESSAGE_ENABLED = "payDayMessageEnabled";
    private String priceCheck = "pricecheck";
    private final String PRICE_CHECK = "priceCheck";
    private String displayPriceCheckMessage = "Turns on and off the price check feature.";
    private final String DISPLAY_PRICE_CHECK_MESSAGE = "displayPriceCheckMessage";
    private String displayPayDayMessage = "paydaymessage";
    private final String DISPLAY_PAY_DAY_MESSAGE = "displayPayDayMessage";
    private String displayDisplayPayDayMessageMessage = "Turns on and off the payday message for yourself.";
    private final String DISPLAY_DISPLAY_PAY_DAY_MESSAGE_MESSAGE = "displayDisplayPayDayMessageMessage";
    private String newPrice = "<newPrice>";
    private final String NEW_PRICE = "newPrice";
    private String on = "on";
    private final String ON = "on";
    private String off = "off";
    private final String OFF = "off";
    private String breakString = "break";
    private final String BREAK_STRING = "break";
    private String placeString = "place";
    private final String PLACE_STRING = "place";
    private String workplaceCreateOption = "create";
    private final String WORKPLACE_CREATE_OPTION = "workplaceCreateOption";
    private String workplaceRenameOption = "rename";
    private final String WORKPLACE_RENAME_OPTION = "workplaceRenameOption";
    private String workplaceInfoOption = "info";
    private final String WORKPLACE_INFO_OPTION = "workplaceInfoOption";
    private String workplaceDeleteOption = "delete";
    private final String WORKPLACE_DELETE_OPTION = "workplaceDeleteOption";
    private String workplaceSetOption = "set";
    private final String WORKPLACE_SET_OPTION = "workplaceSetOption";
    private String workplaceCreateDescription = "creates a new workplace after you selected the 2 points with your workplace selection tool";
    private final String WORKPLACE_CREATE_DESCRIPTION = "workplaceCreateDescription";
    private String workplaceRenameDescription = "renames a workplace";
    private final String WORKPLACE_RENAME_DESCRIPTION = "workplaceRenameDescription";
    private String workplaceDeleteDescription = "deletes a workplace";
    private final String WORKPLACE_DELETE_DESCRIPTION = "workplaceDeleteDescription";
    private String workplaceInfoDescription = "display information about the workplace you are standing in";
    private final String WORKPLACE_INFO_DESCRIPTION = "workplaceInfoDescription";
    private String workplaceSetDescription = "set one of the workplace setting of the workplace your are standing in";
    private final String WORKPLACE_SET_DESCRIPTION = "workplaceSetDescription";


    // Messages from here onward
    private String payDayEmployerMessage = "You have paid $$ for services rendered on your Work Places";
    private final String PAYDAY_EMPLOYER_MESSAGE = "payDayEmployerMessage";
    private String payDayEmployedMessage = "You have been paid $$ for services rendered to %employer";
    private final String PAYDAY_EMPLOYED_MESSAGE = "payDayEmployedMessage";
    private String priceCheckMessagesOn = "Right click any block to check its price.";
    private final String PRICECHECK_MESSAGES_ON = "priceCheckMessagesOn";
    private String priceCheckMessagesOff = "Price check off";
    private final String PRICECHECK_MESSAGES_OFF = "priceCheckMessagesOff";
    private String payDayMessagesOnOff = "Payday Message %onOff";
    private final String PAYDAY_MESSAGES_ONOFF = "payDayMessagesOnOff";
    private String displayPriceSetMessage = "Set the break or place price of the next block that you right click to <newPrice>.";
    private final String DISPLAY_PRICE_SET_MESSAGE = "displayPriceSetMessage";
    private String cantBreakPlaceAtSameTime = "You cannot use break and place price setting at the same time. %breakPlace price setting turned %onOff";
    private final String CANT_BREAK_PLACE_AT_SAME_TIME = "cantBreakPlaceAtSameTime";
    private String rightClickBlockToPriceSet = "Now right click the block you want to set the %breakPlace price for.";
    private final String RIGHT_CLICK_BLOCK_TO_PRICESET = "rightClickBlockToPriceSet";
    private String workplaceCreateHelpMessage = "You must enter a Work Place Name!";
    private final String WORKPLACE_CREATE_HELP_MESSAGE = "workplaceCreateHelpMessage";
    private String workplaceRenameHelpMessage = "You need to supply <oldname> <newname> for this command!";
    private final String WORKPLACE_RENAME_HELP_MESSAGE = "workplaceRenameHelpMessage";
    private String workplaceSetHelpMessage = "You need to supply variable and option to set!";
    private final String WORKPLACE_SET_HELP_MESSAGE = "workplaceSetHelpMessage";
    private String workplaceSelectionOverlapHelpMessage = "Your selection overlaps another Work place please select your points again.";
    private final String WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE = "workplaceSelectionOverlapHelpMessage";
    private String workplaceSelect2PointsHelpMessage = "You must select two points before your Work place can be created!";
    private final String WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE = "workplaceSelect2PointsHelpMessage";
    private String workplaceAlreadyExist = "There is already a Work place with that name, please choose another name";
    private final String WORKPLACE_ALREADY_EXIST = "workplaceAlreadyExist";
    private String workplaceCreatedMessage = "Workplace %workplace created";
    private final String WORKPLACE_CREATED_MESSAGE = "workplaceCreatedMessage";
    private String workplaceDeletedMessage = "Workplace %workplace deleted";
    private final String WORKPLACE_DELETED_MESSAGE = "workplaceDeletedMessage";
    private String youNeedToOwnTheWorkplaceMessage = "You need to own the workplace to manage it";
    private final String YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE = "youNeedToOwnTheWorkplaceMessage";
    private String noWorkplaceWithThisNameMessage = "There is no workplace by this name";
    private final String NO_WORKPLACE_WITH_THIS_NAME_MESSAGE = "noWorkplaceWithThisNameMessage";
    private String workplaceRenamedToMessage = "Workplace %oldname renamed to %newname";
    private final String WORKPLACE_RENAMED_TO_MESSAGE = "workplaceRenamedToMessage";
    private String workplaceNameMessage = "Name of the Workplace";
    private final String WORKPLACE_NAME_MESSAGE = "workplaceNameMessage";
    private String workplaceOwnerMessage = "Owner of the Workplace";
    private final String WORKPLACE_OWNER_MESSAGE = "workplaceOwnerMessage";
    private String workplaceBreakTypeMessage = "Break payment type";
    private final String WORKPLACE_BREAK_TYPE_MESSAGE = "workplaceBreakTypeMessage";
    private String workplaceBreakAmountMessage = "Break payment amount";
    private final String WORKPLACE_BREAK_AMOUNT_MESSAGE = "workplaceBreakAmountMessage";
    private String workplacePlaceTypeMessage = "Place payment type";
    private final String WORKPLACE_PLACE_TYPE_MESSAGE = "workplacePlaceTypeMessage";
    private String workplacePlaceAmountMessage = "Place payment amount";
    private final String WORKPLACE_PLACE_AMOUNT_MESSAGE = "workplacePlaceAmountMessage";
    private String wrongBreakPlaceTypeMessage = "When setting break or place types you must choose either percent or flat!";
    private final String WRONG_BREAK_PLACE_TYPE_MESSAGE = "wrongBreakPlaceTypeMessage";
    private String notAValidVariableMessage = "%varname is not a valid workplace variable";
    private final String NOT_A_VALID_VARIABLE_MESSAGE = "notAValidVariableMessage";
    private String varSetToNewValueMessage = "%varname set to %newvalue";
    private final String VAR_SET_TO_NEW_VALUE_MESSAGE = "varSetToNewValueMessage";
    private String youAreNotInAWorkplaceMessage = "You are not currently in a workplace";
    private final String YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE = "youAreNotInAWorkplaceMessage";


    // Permissions from here onward

    private final String PERM_GETPAYED = "tmp.getpayed.getpayed";
    private final String PERM_WORKPLACE = "tmp.getpayed.workplace";
    private final String PERM_PRICESET = "tmp.getpayed.priceset";
    private final String PERM_WORKPLACE_CONFIGURE = "tmp.getpayed.workplace.configure";

// Internal variables

    protected Map<String, Map<String, Double>> blockPrices = new HashMap<String, Map<String, Double>>();


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

        Map<String, Double> priceList = new HashMap<String, Double>(2);
        priceList.put("break", defaultBreakAmount);
        priceList.put("place", defaultPlaceAmount);
        blockPrices.put("default", priceList);
    }

// And than we add the defaults

    /**
     * Method to add the config variables to the default configuration
     */

    void customDefaultConfig() {
        config.addDefault(GETPAYED_CMD, getPayedCmd);
        config.addDefault(HELP_OPTION, helpOption);
        config.addDefault(GETPAYED_CMD_DESCRIPTION, getPayedCmdDescription);
        config.addDefault(GETPAYED_CMD_PERM_DESCRIPTION, getPayedCmdPermDescription);
        config.addDefault(DISPLAY_HELP_MESSAGE, displayHelpMessage);
        config.addDefault(WORKPLACE_CMD, workPlaceCmd);
        config.addDefault(WORKPLACE_CMD_DESCRIPTION, WORKPLACE_CMD_DESCRIPTION);
        config.addDefault(WORKPLACE_CMD_PERM_DESCRIPTION, workPlaceCmdPermDescription);
        config.addDefault(WORKPLACE_TOOL, workPlaceTool);
        config.addDefault(DEFAULT_BREAK_TYPE, defaultBreakType);
        config.addDefault(DEFAULT_BREAK_AMOUNT, defaultBreakAmount);
        config.addDefault(DEFAULT_PLACE_TYPE, defaultPlaceType);
        config.addDefault(DEFAULT_PLACE_AMOUNT, defaultPlaceAmount);
        config.addDefault(WORKPLACE_GREETING, workPlaceGreeting);
        config.addDefault(WORKPLACE_FAREWELL, workPlaceFarewell);
        config.addDefault(WORKPLACE_SAVE_INTERVAL, workPlaceSaveInterval);
        config.addDefault(ENTRY_EXIT_REFRESH_RATE, entryExitRefreshRate);
        config.addDefault(PAYDAY_INTERVAL, payDayInterval);
        config.addDefault(CLEAR_BUFFER_ON_PAYDAY, clearBufferOnPayday);
        config.addDefault(BUFFER_SIZE, bufferSize);
        config.addDefault(PAYDAY_MESSAGE, payDayMessage);
        config.addDefault(PAYDAY_MESSAGE_ENABLED, payDayMessageEnabled);
        config.addDefault(PAYDAY_EMPLOYER_MESSAGE, payDayEmployerMessage);
        config.addDefault(PAYDAY_EMPLOYED_MESSAGE, payDayEmployedMessage);
        config.addDefault(PRICE_CHECK, priceCheck);
        config.addDefault(DISPLAY_PRICE_CHECK_MESSAGE, displayPriceCheckMessage);
        config.addDefault(DISPLAY_PAY_DAY_MESSAGE, displayPayDayMessage);
        config.addDefault(DISPLAY_DISPLAY_PAY_DAY_MESSAGE_MESSAGE, displayDisplayPayDayMessageMessage);
        config.addDefault(PRICESET_CMD, priceSetCmd);
        config.addDefault(DISPLAY_PRICE_SET_MESSAGE, displayPriceSetMessage);
        config.addDefault(ON, on);
        config.addDefault(OFF, off);
        config.addDefault(NEW_PRICE, newPrice);
        config.addDefault(BREAK_STRING, breakString);
        config.addDefault(PLACE_STRING, placeString);
        config.addDefault(PRICECHECK_MESSAGES_ON, priceCheckMessagesOn);
        config.addDefault(PRICECHECK_MESSAGES_OFF, priceCheckMessagesOff);
        config.addDefault(PAYDAY_MESSAGES_ONOFF, payDayMessagesOnOff);
        config.addDefault(PRICESET_CMD, priceSetCmd);
        config.addDefault(PRICESET_CMD_DESCRIPTION, priceSetCmdDescription);
        config.addDefault(PRICESET_CMD_PERM_DESCRIPTION, priceSetCmdPermDescription);
        config.addDefault(CANT_BREAK_PLACE_AT_SAME_TIME, cantBreakPlaceAtSameTime);
        config.addDefault(RIGHT_CLICK_BLOCK_TO_PRICESET, rightClickBlockToPriceSet);
        config.addDefault(WORKPLACE_CREATE_OPTION, workplaceCreateOption);
        config.addDefault(WORKPLACE_RENAME_OPTION, workplaceRenameOption);
        config.addDefault(WORKPLACE_INFO_OPTION, workplaceInfoOption);
        config.addDefault(WORKPLACE_DELETE_OPTION, workplaceDeleteOption);
        config.addDefault(WORKPLACE_SET_OPTION, workplaceSetOption);
        config.addDefault(WORKPLACE_CREATE_DESCRIPTION, workplaceCreateDescription);
        config.addDefault(WORKPLACE_RENAME_DESCRIPTION, workplaceRenameDescription);
        config.addDefault(WORKPLACE_INFO_DESCRIPTION, workplaceInfoDescription);
        config.addDefault(WORKPLACE_DELETE_DESCRIPTION, workplaceDeleteDescription);
        config.addDefault(WORKPLACE_SET_DESCRIPTION, workplaceSetDescription);
        config.addDefault(WORKPLACE_CREATE_HELP_MESSAGE, workplaceCreateHelpMessage);
        config.addDefault(WORKPLACE_RENAME_HELP_MESSAGE, workplaceRenameHelpMessage);
        config.addDefault(WORKPLACE_SET_HELP_MESSAGE, workplaceSetHelpMessage);
        config.addDefault(WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE, workplaceSelectionOverlapHelpMessage);
        config.addDefault(WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE, workplaceSelect2PointsHelpMessage);
        config.addDefault(WORKPLACE_ALREADY_EXIST, workplaceAlreadyExist);
        config.addDefault(WORKPLACE_CREATED_MESSAGE, workplaceCreatedMessage);
        config.addDefault(WORKPLACE_DELETED_MESSAGE, workplaceDeletedMessage);
        config.addDefault(YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE, youNeedToOwnTheWorkplaceMessage);
        config.addDefault(NO_WORKPLACE_WITH_THIS_NAME_MESSAGE, noWorkplaceWithThisNameMessage);
        config.addDefault(WORKPLACE_RENAMED_TO_MESSAGE, workplaceRenamedToMessage);
        config.addDefault(WORKPLACE_NAME_MESSAGE, workplaceNameMessage);
        config.addDefault(WORKPLACE_OWNER_MESSAGE, workplaceOwnerMessage);
        config.addDefault(WORKPLACE_BREAK_TYPE_MESSAGE, workplaceBreakTypeMessage);
        config.addDefault(WORKPLACE_BREAK_AMOUNT_MESSAGE, workplaceBreakAmountMessage);
        config.addDefault(WORKPLACE_PLACE_TYPE_MESSAGE, workplacePlaceTypeMessage);
        config.addDefault(WORKPLACE_PLACE_AMOUNT_MESSAGE, workplacePlaceAmountMessage);
        config.addDefault(WRONG_BREAK_PLACE_TYPE_MESSAGE, wrongBreakPlaceTypeMessage);
        config.addDefault(NOT_A_VALID_VARIABLE_MESSAGE, notAValidVariableMessage);
        config.addDefault(VAR_SET_TO_NEW_VALUE_MESSAGE, varSetToNewValueMessage);
        config.addDefault(YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE, youAreNotInAWorkplaceMessage);


    }


// Than we load it....

    /**
     * Method to load the configuration into the config variables
     */

    void loadCustomConfig() {

        getPayedCmd = config.getString(GETPAYED_CMD);
        helpOption = config.getString(HELP_OPTION);
        getPayedCmdDescription = config.getString(GETPAYED_CMD_DESCRIPTION);
        getPayedCmdPermDescription = config.getString(GETPAYED_CMD_PERM_DESCRIPTION);
        displayHelpMessage = config.getString(DISPLAY_HELP_MESSAGE);
        workPlaceCmd = config.getString(WORKPLACE_CMD);
        workPlaceCmdDescription = config.getString(WORKPLACE_CMD_DESCRIPTION);
        workPlaceCmdPermDescription = config.getString(WORKPLACE_CMD_PERM_DESCRIPTION);
        workPlaceTool = config.getInt(WORKPLACE_TOOL);
        defaultBreakType = config.getString(DEFAULT_BREAK_TYPE);
        defaultBreakAmount = config.getDouble(DEFAULT_BREAK_AMOUNT);
        defaultPlaceType = config.getString(DEFAULT_BREAK_TYPE);
        defaultPlaceAmount = config.getDouble(DEFAULT_PLACE_AMOUNT);
        workPlaceGreeting = config.getString(WORKPLACE_GREETING);
        workPlaceFarewell = config.getString(WORKPLACE_FAREWELL);
        workPlaceSaveInterval = config.getInt(WORKPLACE_SAVE_INTERVAL);
        entryExitRefreshRate = config.getInt(ENTRY_EXIT_REFRESH_RATE);
        payDayInterval = config.getInt(PAYDAY_INTERVAL);
        clearBufferOnPayday = config.getBoolean(CLEAR_BUFFER_ON_PAYDAY);
        bufferSize = config.getInt(BUFFER_SIZE);
        payDayMessage = config.getString(PAYDAY_MESSAGE);
        payDayMessageEnabled = config.getBoolean(PAYDAY_MESSAGE_ENABLED);
        payDayEmployedMessage = config.getString(PAYDAY_EMPLOYED_MESSAGE);
        payDayEmployerMessage = config.getString(PAYDAY_EMPLOYER_MESSAGE);
        priceCheck = config.getString(PRICE_CHECK);
        displayPriceCheckMessage = config.getString(DISPLAY_PRICE_CHECK_MESSAGE);
        displayPayDayMessage = config.getString(DISPLAY_PAY_DAY_MESSAGE);
        displayDisplayPayDayMessageMessage = config.getString(DISPLAY_DISPLAY_PAY_DAY_MESSAGE_MESSAGE);
        priceSetCmd = config.getString(PRICESET_CMD);
        displayPriceSetMessage = config.getString(DISPLAY_PRICE_SET_MESSAGE);
        on = config.getString(ON);
        off = config.getString(OFF);
        newPrice = config.getString(NEW_PRICE);
        breakString = config.getString(BREAK_STRING);
        placeString = config.getString(PLACE_STRING);
        priceCheckMessagesOn = config.getString(PRICECHECK_MESSAGES_ON);
        priceCheckMessagesOff = config.getString(PRICECHECK_MESSAGES_OFF);
        payDayMessagesOnOff = config.getString(PAYDAY_MESSAGES_ONOFF);
        priceSetCmdDescription = config.getString(PRICESET_CMD_DESCRIPTION);
        priceSetCmdPermDescription = config.getString(PRICESET_CMD_PERM_DESCRIPTION);
        cantBreakPlaceAtSameTime = config.getString(CANT_BREAK_PLACE_AT_SAME_TIME);
        rightClickBlockToPriceSet = config.getString(RIGHT_CLICK_BLOCK_TO_PRICESET);
        workplaceCreateOption = config.getString(WORKPLACE_CREATE_OPTION);
        workplaceRenameOption = config.getString(WORKPLACE_RENAME_OPTION);
        workplaceInfoOption = config.getString(WORKPLACE_INFO_OPTION);
        workplaceDeleteOption = config.getString(WORKPLACE_DELETE_OPTION);
        workplaceSetOption = config.getString(WORKPLACE_SET_OPTION);
        workplaceCreateDescription = config.getString(WORKPLACE_CREATE_DESCRIPTION);
        workplaceRenameDescription = config.getString(WORKPLACE_RENAME_DESCRIPTION);
        workplaceInfoDescription = config.getString(WORKPLACE_INFO_DESCRIPTION);
        workplaceDeleteDescription = config.getString(WORKPLACE_DELETE_DESCRIPTION);
        workplaceSetDescription = config.getString(WORKPLACE_SET_DESCRIPTION);
        workplaceCreateHelpMessage = config.getString(WORKPLACE_CREATE_HELP_MESSAGE);
        workplaceRenameHelpMessage = config.getString(WORKPLACE_RENAME_HELP_MESSAGE);
        workplaceSetHelpMessage = config.getString(WORKPLACE_SET_HELP_MESSAGE);
        workplaceSelectionOverlapHelpMessage = config.getString(WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE);
        workplaceSelect2PointsHelpMessage = config.getString(WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE);
        workplaceAlreadyExist = config.getString(WORKPLACE_ALREADY_EXIST);
        workplaceCreatedMessage = config.getString(WORKPLACE_CREATED_MESSAGE);
        workplaceDeletedMessage = config.getString(WORKPLACE_DELETED_MESSAGE);
        youNeedToOwnTheWorkplaceMessage = config.getString(YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE);
        noWorkplaceWithThisNameMessage = config.getString(NO_WORKPLACE_WITH_THIS_NAME_MESSAGE);
        workplaceRenamedToMessage = config.getString(WORKPLACE_RENAMED_TO_MESSAGE);
        workplaceNameMessage = config.getString(WORKPLACE_NAME_MESSAGE);
        workplaceOwnerMessage = config.getString(WORKPLACE_OWNER_MESSAGE);
        workplaceBreakTypeMessage = config.getString(WORKPLACE_BREAK_TYPE_MESSAGE);
        workplaceBreakAmountMessage = config.getString(WORKPLACE_BREAK_AMOUNT_MESSAGE);
        workplacePlaceTypeMessage = config.getString(WORKPLACE_PLACE_TYPE_MESSAGE);
        workplacePlaceAmountMessage = config.getString(WORKPLACE_PLACE_AMOUNT_MESSAGE);
        wrongBreakPlaceTypeMessage = config.getString(WRONG_BREAK_PLACE_TYPE_MESSAGE);
        notAValidVariableMessage = config.getString(NOT_A_VALID_VARIABLE_MESSAGE);
        varSetToNewValueMessage = config.getString(VAR_SET_TO_NEW_VALUE_MESSAGE);
        youAreNotInAWorkplaceMessage = config.getString(YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE);

        getPayedLogger.debug("getPayedCmd", getPayedCmd);
        getPayedLogger.debug("helpOption", helpOption);
        getPayedLogger.debug("getPayedCmdDescription", getPayedCmdDescription);
        getPayedLogger.debug("getPayedCmdPermDescription", getPayedCmdPermDescription);
        getPayedLogger.debug("displayHelpMessage", displayHelpMessage);
        getPayedLogger.debug("workPlaceCmd", workPlaceCmd);
        getPayedLogger.debug("workPlaceCmdDescription", workPlaceCmdDescription);
        getPayedLogger.debug("workPlaceCmdPermDescription", workPlaceCmdPermDescription);
        getPayedLogger.debug("workPlaceTool", workPlaceTool);
        getPayedLogger.debug("defaultBreakType", defaultBreakType);
        getPayedLogger.debug("defaultBreakAmount", defaultBreakAmount);
        getPayedLogger.debug("defaultPlaceType", defaultPlaceType);
        getPayedLogger.debug("defaultPlaceAmount", defaultPlaceAmount);
        getPayedLogger.debug("workPlaceGreeting", workPlaceGreeting);
        getPayedLogger.debug("workPlaceFarewell", workPlaceFarewell);
        getPayedLogger.debug("workPlaceSaveInterval", workPlaceSaveInterval);
        getPayedLogger.debug("entryExitRefreshRate", entryExitRefreshRate);
        getPayedLogger.debug("paydayInterval", payDayInterval);
        getPayedLogger.debug("clearBufferOnPayday", clearBufferOnPayday);
        getPayedLogger.debug("bufferSize", bufferSize);
        getPayedLogger.debug("payDayEmployedMessage", payDayEmployedMessage);
        getPayedLogger.debug("payDayEmployerMessage", payDayEmployerMessage);
        getPayedLogger.debug(PRICE_CHECK, priceCheck);
        getPayedLogger.debug(DISPLAY_PRICE_CHECK_MESSAGE, displayPriceCheckMessage);
        getPayedLogger.debug(DISPLAY_PAY_DAY_MESSAGE, displayPayDayMessage);
        getPayedLogger.debug(DISPLAY_DISPLAY_PAY_DAY_MESSAGE_MESSAGE, displayDisplayPayDayMessageMessage);
        getPayedLogger.debug(PRICESET_CMD, priceSetCmd);
        getPayedLogger.debug(DISPLAY_PRICE_SET_MESSAGE, displayPriceSetMessage);
        getPayedLogger.debug(ON, on);
        getPayedLogger.debug(OFF, off);
        getPayedLogger.debug(NEW_PRICE, newPrice);
        getPayedLogger.debug(BREAK_STRING, breakString);
        getPayedLogger.debug(PLACE_STRING, placeString);
        getPayedLogger.debug(PRICECHECK_MESSAGES_ON, priceCheckMessagesOn);
        getPayedLogger.debug(PRICECHECK_MESSAGES_OFF, priceCheckMessagesOff);
        getPayedLogger.debug(PAYDAY_MESSAGES_ONOFF, payDayMessagesOnOff);
        getPayedLogger.debug(CANT_BREAK_PLACE_AT_SAME_TIME, cantBreakPlaceAtSameTime);
        getPayedLogger.debug(RIGHT_CLICK_BLOCK_TO_PRICESET, rightClickBlockToPriceSet);
        getPayedLogger.debug(WORKPLACE_CREATE_OPTION, workplaceCreateOption);
        getPayedLogger.debug(WORKPLACE_RENAME_OPTION, workplaceRenameOption);
        getPayedLogger.debug(WORKPLACE_INFO_OPTION, workplaceInfoOption);
        getPayedLogger.debug(WORKPLACE_DELETE_OPTION, workplaceDeleteOption);
        getPayedLogger.debug(WORKPLACE_SET_OPTION, workplaceSetOption);
        getPayedLogger.debug(WORKPLACE_CREATE_DESCRIPTION, workplaceCreateDescription);
        getPayedLogger.debug(WORKPLACE_RENAME_DESCRIPTION, workplaceRenameDescription);
        getPayedLogger.debug(WORKPLACE_INFO_DESCRIPTION, workplaceInfoDescription);
        getPayedLogger.debug(WORKPLACE_DELETE_DESCRIPTION, workplaceDeleteDescription);
        getPayedLogger.debug(WORKPLACE_SET_DESCRIPTION, workplaceSetDescription);
        getPayedLogger.debug(WORKPLACE_CREATE_HELP_MESSAGE, workplaceCreateHelpMessage);
        getPayedLogger.debug(WORKPLACE_RENAME_HELP_MESSAGE, workplaceRenameHelpMessage);
        getPayedLogger.debug(WORKPLACE_SET_HELP_MESSAGE, workplaceSetHelpMessage);
        getPayedLogger.debug(WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE, workplaceSelectionOverlapHelpMessage);
        getPayedLogger.debug(WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE, workplaceSelect2PointsHelpMessage);
        getPayedLogger.debug(WORKPLACE_ALREADY_EXIST, workplaceAlreadyExist);
        getPayedLogger.debug(WORKPLACE_CREATED_MESSAGE, workplaceCreatedMessage);
        getPayedLogger.debug(WORKPLACE_DELETED_MESSAGE, workplaceDeletedMessage);
        getPayedLogger.debug(YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE, youNeedToOwnTheWorkplaceMessage);
        getPayedLogger.debug(NO_WORKPLACE_WITH_THIS_NAME_MESSAGE, noWorkplaceWithThisNameMessage);
        getPayedLogger.debug(WORKPLACE_RENAMED_TO_MESSAGE, workplaceRenamedToMessage);
        getPayedLogger.debug(NO_WORKPLACE_WITH_THIS_NAME_MESSAGE, noWorkplaceWithThisNameMessage);
        getPayedLogger.debug(WORKPLACE_NAME_MESSAGE, workplaceNameMessage);
        getPayedLogger.debug(WORKPLACE_OWNER_MESSAGE, workplaceOwnerMessage);
        getPayedLogger.debug(WORKPLACE_BREAK_TYPE_MESSAGE, workplaceBreakTypeMessage);
        getPayedLogger.debug(WORKPLACE_BREAK_AMOUNT_MESSAGE, workplaceBreakAmountMessage);
        getPayedLogger.debug(WORKPLACE_PLACE_TYPE_MESSAGE, workplacePlaceTypeMessage);
        getPayedLogger.debug(WORKPLACE_PLACE_AMOUNT_MESSAGE, workplacePlaceAmountMessage);
        getPayedLogger.debug(WRONG_BREAK_PLACE_TYPE_MESSAGE, wrongBreakPlaceTypeMessage);
        getPayedLogger.debug(NOT_A_VALID_VARIABLE_MESSAGE, notAValidVariableMessage);
        getPayedLogger.debug(YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE, youAreNotInAWorkplaceMessage);


        for (String blockID : config.getConfigurationSection("PriceList").getKeys(false)) {
            getPayedLogger.debug("blockID", blockID);
            double breakPrice;
            if (config.contains("PriceList." + blockID + ".break")) {
                breakPrice = config.getDouble("PriceList." + blockID + ".break");
            } else {
                getPayedLogger.warning("Break value missing for " + blockID);
                getPayedLogger.warning("Setting value to 0.0");
                breakPrice = 0.0;
            }
            double placePrice;
            if (config.contains("PricList." + blockID + ".place")) {
                placePrice = config.getDouble("PriceList." + blockID + ".place");
            } else {
                getPayedLogger.warning("Place value missing for " + blockID);
                getPayedLogger.warning("Setting value to 0.0");
                placePrice = 0.0;
            }
            Map<String, Double> priceList = new HashMap<String, Double>(2);
            priceList.put("break", breakPrice);
            priceList.put("place", placePrice);
            blockPrices.put(blockID, priceList);
            getPayedLogger.debug("PriceList:", blockID + ":" + breakPrice + ":" + placePrice);
        }

        if (!defaultBreakType.equalsIgnoreCase("percent") || !defaultBreakType.equalsIgnoreCase("flat")) {
            getPayedLogger.warning(DEFAULT_BREAK_TYPE + " is not flat or percent, using percent!");
            defaultBreakType = "percent";
        }
        if (!defaultPlaceType.equalsIgnoreCase("percent") || !defaultPlaceType.equalsIgnoreCase("flat")) {
            getPayedLogger.warning(DEFAULT_PLACE_TYPE + " is not flat or percent, using percent!");
            defaultPlaceType = "percent";
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
        stream.println("# Please note: due to the ammount of translations for this module you find the");
        stream.println("#              translation feature at the end of the file.");
        stream.println();

        stream.println();
        stream.println("# --- Workplace Configuration");
        stream.println();


        stream.println("# ID of the tool to mark a workplace (Default 284, Gold Shovel.");
        stream.println(WORKPLACE_TOOL + ": '" + workPlaceTool + "'");
        stream.println("# Calculation method for break payment (flat / percent).");
        stream.println(DEFAULT_BREAK_TYPE + ": '" + defaultBreakType + "'");
        stream.println("# Amount to pay for breaking blocks");
        stream.println(DEFAULT_BREAK_AMOUNT + ": " + defaultBreakAmount);
        stream.println("# Calculation method for place payment (flat / percent).");
        stream.println(DEFAULT_PLACE_TYPE + ": '" + defaultPlaceType + "'");
        stream.println("# Amount to pay for placing blocks.");
        stream.println(DEFAULT_PLACE_AMOUNT + ": " + defaultPlaceAmount);
        stream.println("# The message displayed when the players enter a workplace.");
        stream.println(WORKPLACE_GREETING + ": '" + workPlaceGreeting + "'");
        stream.println("# The message displayed when the players leaves a workplace.");
        stream.println(WORKPLACE_FAREWELL + ": '" + workPlaceGreeting + "'");
        stream.println("# Time in seconds changes are save to disk.");
        stream.println(WORKPLACE_SAVE_INTERVAL + ": " + workPlaceSaveInterval);
        stream.println("# Ticks (20 Ticks = 1 Seconds to check if player entered / left a workplace.");
        stream.println("# If you notice lag being caused by this increase this value to check less often");
        stream.println(ENTRY_EXIT_REFRESH_RATE + ": " + entryExitRefreshRate);

        stream.println();
        stream.println("# --- PayDay Configuration");
        stream.println();
        stream.println("# Set your message in the message field. Use the text $$ where you want to put your amount.");
        stream.println("# The currency units are automatically picked up");
        stream.println(PAYDAY_MESSAGE + ": '" + payDayMessage + "'");
        stream.println("# Broadcast the PayDay message to the players?");
        stream.println(PAYDAY_MESSAGE_ENABLED + ": " + payDayMessageEnabled);
        stream.println("# Time in Seconds for Payment");
        stream.println(PAYDAY_INTERVAL + ": " + payDayInterval);
        stream.println("# Anti-Place-Break-Exploit, stores blocks inside the buffer");
        stream.println("# 1000 is a good value. The buffer uses a sliding window.");
        stream.println(BUFFER_SIZE + ": " + bufferSize);
        stream.println("# Clear the buffer every time on payday (saves some memory from time to time");
        stream.println(CLEAR_BUFFER_ON_PAYDAY + ": " + clearBufferOnPayday);


        stream.println();
        stream.println("# --- PriceList Configuration");
        stream.println();


        stream.println("# The pricelist is what you pay the player for breaking or placing certain blocks");
        stream.println("# default is what the player is paid if you don't specify how much a certain block is");
        stream.println("# For example if you only have Stone and default setup in your price list then your players");
        stream.println("# will get your set break price every time they break stone and if they break any other block they will get your default price");
        stream.println("# For a list of block names take a look in the GetPayedItems.txt file.");
        stream.println();
        stream.println("# The format is:");
        stream.println("#    <block_name>:");
        stream.println("#        break: 0.0");
        stream.println("#        place: 0.0");
        stream.println();
        stream.println("PriceList:");
        getPayedLogger.debug("let's write the priceList!");
        for (String blockID : blockPrices.keySet()) {
            getPayedLogger.debug("blockID", blockID);
            stream.println("    " + blockID + ":");
            double breakPrice = blockPrices.get(blockID).get("break");
            double placePrice = blockPrices.get(blockID).get("place");
            getPayedLogger.debug("breakPrice", breakPrice);
            getPayedLogger.debug("placePrice", placePrice);
            stream.println("        break: " + breakPrice);
            stream.println("        place: " + placePrice);

        }
        stream.println();
        stream.println("# --- Translation Features");
        stream.println();
        stream.println("# Almost everything player visible can be translated!");
        stream.println("# Please change to your liking and use the following variables");
        stream.println("# %player = playername, %cmd = command, %help = help option");
        stream.println("# $$ = amount and currency , %onOff = will become on or off");
        stream.println("# %employer = owner of the workplace");
        stream.println("# %breakPlace = will become break or place");
        stream.println("# %workplace = will become workplace name");
        stream.println("# %oldname / %newname = old / new name of the workplace");
        stream.println("# %varname = variable name, %newvalue = new value of variable");
        stream.println();
        stream.println("# NOTE: You need to use '' if you want to use ' in this text!");
        stream.println();
        stream.println("# The alias for the help option.");
        stream.println(HELP_OPTION + ": '" + helpOption + "'");
        stream.println("# The help message displayed.");
        stream.println(DISPLAY_HELP_MESSAGE + ": '" + displayHelpMessage + "'");
        stream.println();
        stream.println("# The alias command for /" + TMP + CMD_GETPAYED + " WITHOUT the / !!!");
        stream.println(GETPAYED_CMD + "d: '" + getPayedCmd + "'");
        stream.println("# The command description.");
        stream.println(GETPAYED_CMD_DESCRIPTION + ": '" + getPayedCmdDescription + "'");
        stream.println("# The command permissions description.");
        stream.println(GETPAYED_CMD_PERM_DESCRIPTION + ": '" + getPayedCmdPermDescription + "'");
        stream.println("# # The alias command for /" + TMP + CMD_WORKPLACE + " WITHOUT the / !!!");
        stream.println(WORKPLACE_CMD + ": '" + workPlaceCmd + "'");
        stream.println("# The command description.");
        stream.println(WORKPLACE_CMD_DESCRIPTION + ": '" + workPlaceCmdDescription + "'");
        stream.println("# The command permissions description.");
        stream.println(WORKPLACE_CMD_PERM_DESCRIPTION + ": '" + workPlaceCmdPermDescription + "'");
        stream.println("# The alias command for /" + TMP + CMD_PRICESET + " WITHOUT the / !!!.");
        stream.println(PRICESET_CMD + ": '" + priceSetCmd + "'");
        stream.println("# The command description.");
        stream.println(PRICESET_CMD_DESCRIPTION + ": '" + priceSetCmdDescription + "'");
        stream.println("# The command permissions description.");
        stream.println(PRICESET_CMD_PERM_DESCRIPTION + ": '" + priceSetCmdPermDescription + "'");
        stream.println();
        stream.println("# The alias for the pricecheck option.");
        stream.println(PRICE_CHECK + ": '" + priceCheck + "'");
        stream.println("# The pricecheck description.");
        stream.println(DISPLAY_PRICE_CHECK_MESSAGE + ": '" + displayPriceCheckMessage + "'");
        stream.println("# The alias for the paydaymessage option.");
        stream.println(DISPLAY_PAY_DAY_MESSAGE + ": '" + displayPayDayMessage + "'");
        stream.println("# The paydaymessage description.");
        stream.println(DISPLAY_DISPLAY_PAY_DAY_MESSAGE_MESSAGE + ": '" + displayDisplayPayDayMessageMessage + "'");
        stream.println("# The alias for the create option.");
        stream.println(WORKPLACE_CREATE_OPTION + ": '" + workplaceCreateOption + "'");
        stream.println("# The create description.");
        stream.println(WORKPLACE_CREATE_DESCRIPTION + ": '" + workplaceCreateDescription + "'");
        stream.println("# The alias for the rename option.");
        stream.println(WORKPLACE_RENAME_OPTION + ": '" + workplaceRenameOption + "'");
        stream.println("# The rename description.");
        stream.println(WORKPLACE_RENAME_DESCRIPTION + ": '" + workplaceRenameDescription + "'");
        stream.println("# The alias for the info option.");
        stream.println(WORKPLACE_INFO_OPTION + ": '" + workplaceInfoOption + "'");
        stream.println("# The info description.");
        stream.println(WORKPLACE_INFO_DESCRIPTION + ": '" + workplaceInfoDescription + "'");
        stream.println("# The alias for the delete option.");
        stream.println(WORKPLACE_DELETE_OPTION + ": '" + workplaceDeleteOption + "'");
        stream.println("# The delete description.");
        stream.println(WORKPLACE_DELETE_DESCRIPTION + ": '" + workplaceDeleteDescription + "'");
        stream.println("# The alias for the set option.");
        stream.println(WORKPLACE_SET_OPTION + ": '" + workplaceSetOption + "'");
        stream.println("# The set description.");
        stream.println(WORKPLACE_SET_DESCRIPTION + ": '" + workplaceSetDescription + "'");

        stream.println();
        stream.println("# The priceset description.");
        stream.println(DISPLAY_PRICE_SET_MESSAGE + ": '" + displayPriceSetMessage + "'");
        stream.println("# on translatation.");
        stream.println(ON + ": '" + on + "'");
        stream.println("# off translatation.");
        stream.println(OFF + ": '" + off + "'");
        stream.println("#  <newPrice>  translatation.");
        stream.println(NEW_PRICE + ": '" + newPrice + "'");
        stream.println("# break translatation.");
        stream.println(BREAK_STRING + ": '" + breakString + "'");
        stream.println("# place translatation.");
        stream.println(PLACE_STRING + ": '" + PLACE_STRING + "'");


        stream.println("# Message displayed when pay for work in your workplace.");
        stream.println(PAYDAY_EMPLOYER_MESSAGE + ": '" + payDayEmployerMessage + "'");
        stream.println("# Message displayed when you work in an workplace.");
        stream.println(PAYDAY_EMPLOYED_MESSAGE + ": '" + payDayEmployedMessage + "'");
        stream.println("# Pricecheck is ON message.");
        stream.println(PRICECHECK_MESSAGES_ON + ": '" + priceCheckMessagesOn + "'");
        stream.println("# Pricecheck is OFF message.");
        stream.println(PRICECHECK_MESSAGES_OFF + ": '" + priceCheckMessagesOff + "'");
        stream.println("# Payday messages on / off");
        stream.println(PAYDAY_MESSAGES_ONOFF + ": '" + payDayMessagesOnOff + "'");
        stream.println("# Can't place and break when you do priceset at the same time");
        stream.println(CANT_BREAK_PLACE_AT_SAME_TIME + ": '" + cantBreakPlaceAtSameTime + "'");
        stream.println("# Right Click to priceset a block with break or place");
        stream.println(RIGHT_CLICK_BLOCK_TO_PRICESET + ": '" + rightClickBlockToPriceSet + "'");
        stream.println("# Help Message displayed when the workplace name is missing.");
        stream.println(WORKPLACE_CREATE_HELP_MESSAGE + ": '" + workplaceCreateHelpMessage + "'");
        stream.println("# Help Message displayed when the <oldname> and / or <newname> are missing.");
        stream.println(WORKPLACE_RENAME_HELP_MESSAGE + ": '" + workplaceRenameHelpMessage + "'");
        stream.println("# Help Messages displayed when variable and / or option are missing.");
        stream.println(WORKPLACE_SET_HELP_MESSAGE + ": '" + workplaceSetHelpMessage + "'");
        stream.println("# Help Messages displayed when workplaces overlap.");
        stream.println(WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE + ": '" + workplaceSelectionOverlapHelpMessage + "'");
        stream.println("# Help Messages displayed when not enough points where selected.");
        stream.println(WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE + ": '" + workplaceSelect2PointsHelpMessage + "'");
        stream.println("# Help Messages displayed when workplace already exists.");
        stream.println(WORKPLACE_ALREADY_EXIST + ": '" + workplaceAlreadyExist + "'");
        stream.println("# Message displayed when workplace is created.");
        stream.println(WORKPLACE_CREATED_MESSAGE + ": '" + workplaceCreatedMessage + "'");
        stream.println("# Message displayed when workplace is deleted.");
        stream.println(WORKPLACE_DELETED_MESSAGE + ": '" + workplaceDeletedMessage + "'");
        stream.println("# Message displayed when workplace isn't owned by player.");
        stream.println(YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE + ": '" + youNeedToOwnTheWorkplaceMessage + "'");
        stream.println("# Message displayed when workplace doesn't exit.");
        stream.println(NO_WORKPLACE_WITH_THIS_NAME_MESSAGE + ": '" + noWorkplaceWithThisNameMessage + "'");
        stream.println("# Message displayed when workplace is renamed.");
        stream.println(WORKPLACE_RENAMED_TO_MESSAGE + ": '" + workplaceRenamedToMessage + "'");
        stream.println("# Info Message displayed for Name of workplace.");
        stream.println(WORKPLACE_NAME_MESSAGE + ": '" + workplaceNameMessage + "'");
        stream.println("# Info Message displayed for owner of workplace.");
        stream.println(WORKPLACE_OWNER_MESSAGE + ": '" + workplaceOwnerMessage + "'");
        stream.println("# Info Message displayed for break type of workplace.");
        stream.println(WORKPLACE_BREAK_TYPE_MESSAGE + ": '" + workplaceBreakTypeMessage + "'");
        stream.println("# Info Message displayed for break amount of workplace.");
        stream.println(WORKPLACE_BREAK_AMOUNT_MESSAGE + ": '" + workplaceBreakAmountMessage + "'");
        stream.println("# Info Message displayed for place type of workplace.");
        stream.println(WORKPLACE_PLACE_TYPE_MESSAGE + ": '" + workplacePlaceTypeMessage + "'");
        stream.println("# Info Message displayed for place amount of workplace.");
        stream.println(WORKPLACE_PLACE_AMOUNT_MESSAGE + ": '" + workplacePlaceAmountMessage + "'");
        stream.println("# Message displayed when wrong type entered.");
        stream.println(WRONG_BREAK_PLACE_TYPE_MESSAGE + ": '" + wrongBreakPlaceTypeMessage + "'");
        stream.println("# Message displayed when variable name is wrong.");
        stream.println(NOT_A_VALID_VARIABLE_MESSAGE + ": '" + notAValidVariableMessage + "'");
        stream.println("# Message displayed when variable newly set.");
        stream.println(VAR_SET_TO_NEW_VALUE_MESSAGE + ": '" + varSetToNewValueMessage + "'");
        stream.println("# Message displayed when player is not in a workplace.");
        stream.println(YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE + ": '" + youAreNotInAWorkplaceMessage + "'");


    }


// *******************************************************************************************************

// And now you need to configure commands and listeners


    private void setupCommands() {
        main.registerCommand(TMP + CMD_GETPAYED, new GetPayedCommand(main));
        main.registerCommand(TMP + CMD_WORKPLACE, new WorkPlaceCommand(main));
        main.registerCommand(TMP + CMD_PRICESET, new PriceSetCommand(main));


        // registering the tmp.workplace.configure permission
        Permission permission = new Permission(PERM_WORKPLACE_CONFIGURE, workPlaceCmdPermDescription);
        main.getServer().getPluginManager().addPermission(permission);
    }

    private void setupListeners() {
        mainConfig.addBlockListeners(Type.BLOCK_BREAK);
        mainConfig.addBlockListeners(Type.BLOCK_PLACE);
        mainConfig.addPlayerListeners(Type.PLAYER_INTERACT);
        mainConfig.addPlayerListeners(Type.PLAYER_LOGIN);
    }


// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!

// ToDO Add your getters and setters for your config variables here.


    public String getNotAValidVariableMessage() {
        return notAValidVariableMessage;
    }

    public String getNoWorkplaceWithThisNameMessage() {
        return noWorkplaceWithThisNameMessage;
    }

    public String getVarSetToNewValueMessage() {
        return varSetToNewValueMessage;
    }

    public String getWorkplaceBreakAmountMessage() {
        return workplaceBreakAmountMessage;
    }

    public String getWorkplaceBreakTypeMessage() {
        return workplaceBreakTypeMessage;
    }

    public String getWorkplaceDeletedMessage() {
        return workplaceDeletedMessage;
    }

    public String getWorkplaceNameMessage() {
        return workplaceNameMessage;
    }

    public String getWorkplaceOwnerMessage() {
        return workplaceOwnerMessage;
    }

    public String getWorkplacePlaceAmountMessage() {
        return workplacePlaceAmountMessage;
    }

    public String getWorkplacePlaceTypeMessage() {
        return workplacePlaceTypeMessage;
    }

    public String getWorkplaceRenamedToMessage() {
        return workplaceRenamedToMessage;
    }

    public String getWrongBreakPlaceTypeMessage() {
        return wrongBreakPlaceTypeMessage;
    }

    public String getYouAreNotInAWorkplaceMessage() {
        return youAreNotInAWorkplaceMessage;
    }

    public String getYouNeedToOwnTheWorkplaceMessage() {
        return youNeedToOwnTheWorkplaceMessage;
    }

    public String getWorkplaceCreatedMessage() {
        return workplaceCreatedMessage;
    }

    public String getWorkplaceAlreadyExist() {
        return workplaceAlreadyExist;
    }

    public String getWorkplaceSelect2PointsHelpMessage() {
        return workplaceSelect2PointsHelpMessage;
    }

    public String getWorkplaceSelectionOverlapHelpMessage() {
        return workplaceSelectionOverlapHelpMessage;
    }

    public String getBREAK_STRING() {
        return BREAK_STRING;
    }

    public String getWorkplaceCreateHelpMessage() {
        return workplaceCreateHelpMessage;
    }

    public String getWorkplaceRenameHelpMessage() {
        return workplaceRenameHelpMessage;
    }

    public String getWorkplaceSetHelpMessage() {
        return workplaceSetHelpMessage;
    }

    public String getWorkplaceCreateDescription() {
        return workplaceCreateDescription;
    }

    public String getWorkplaceCreateOption() {
        return workplaceCreateOption;
    }

    public String getWorkplaceDeleteDescription() {
        return workplaceDeleteDescription;
    }

    public String getWorkplaceDeleteOption() {
        return workplaceDeleteOption;
    }

    public String getWorkplaceInfoDescription() {
        return workplaceInfoDescription;
    }

    public String getWorkplaceInfoOption() {
        return workplaceInfoOption;
    }

    public String getWorkplaceRenameDescription() {
        return workplaceRenameDescription;
    }

    public String getWorkplaceRenameOption() {
        return workplaceRenameOption;
    }

    public String getWorkplaceSetDescription() {
        return workplaceSetDescription;
    }

    public String getWorkplaceSetOption() {
        return workplaceSetOption;
    }

    public String getPERM_WORKPLACE_CONFIGURE() {
        return PERM_WORKPLACE_CONFIGURE;
    }

    public String getCantBreakPlaceAtSameTime() {
        return cantBreakPlaceAtSameTime;
    }

    public String getRightClickBlockToPriceSet() {
        return rightClickBlockToPriceSet;
    }

    public String getPERM_GETPAYED() {
        return PERM_GETPAYED;
    }

    public String getPERM_PRICESET() {
        return PERM_PRICESET;
    }

    public String getPERM_WORKPLACE() {
        return PERM_WORKPLACE;
    }

    public String getPriceSetCmdDescription() {
        return priceSetCmdDescription;
    }

    public String getPriceSetCmdPermDescription() {
        return priceSetCmdPermDescription;
    }

    public String getPayDayMessagesOnOff() {
        return payDayMessagesOnOff;
    }

    public String getPriceCheckMessagesOff() {
        return priceCheckMessagesOff;
    }

    public String getPriceCheckMessagesOn() {
        return priceCheckMessagesOn;
    }

    public String getDisplayDisplayPayDayMessageMessage() {
        return displayDisplayPayDayMessageMessage;
    }

    public String getDisplayPayDayMessage() {
        return displayPayDayMessage;
    }

    public String getDisplayPriceCheckMessage() {
        return displayPriceCheckMessage;
    }

    public String getDisplayPriceSetMessage() {
        return displayPriceSetMessage;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public String getOff() {
        return off;
    }

    public String getOn() {
        return on;
    }

    public String getPlaceString() {
        return placeString;
    }

    public String getPriceCheck() {
        return priceCheck;
    }

    public String getPriceSetCmd() {
        return priceSetCmd;
    }

    public String getBreakString() {
        return breakString;
    }

    public boolean isNotDefaultPrice(Block block) {
        return blockPrices.containsKey(block.getType().name());
    }

    public double getBlockBreakPrice(Block block) {
        return blockPrices.get(block.getType().name()).get("break");

    }

    public double getBlockBreakPrice(String block) {
        return blockPrices.get(block).get("break");
    }

    public double getBlockPlacePrice(Block block) {
        return blockPrices.get(block.getType().name()).get("place");
    }

    public double getBlockPlacePrice(String block) {
        return blockPrices.get((block)).get("place");
    }

    public static String getMODULE_NAME() {
        return MODULE_NAME;
    }

    public String getPayDayEmployerMessage() {
        return payDayEmployerMessage;
    }

    public String getPayDayEmployedMessage() {
        return payDayEmployedMessage;
    }

    public void clearBlockPrices() {
        blockPrices.clear();
    }

    public String getWorkPlaceFarewell() {
        return workPlaceFarewell;
    }

    public int getWorkPlaceSaveInterval() {
        return workPlaceSaveInterval;
    }

    public int getEntryExitRefreshRate() {
        return entryExitRefreshRate;
    }

    public int getPayDayInterval() {
        return payDayInterval;
    }

    public boolean isClearBufferOnPayday() {
        return clearBufferOnPayday;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public String getPayDayMessage() {
        return payDayMessage;
    }

    public Map<String, Map<String, Double>> getBlockPrices() {
        return blockPrices;
    }

    public String getGetPayedCmd() {
        return getPayedCmd;
    }

    public String getHelpOption() {
        return helpOption;
    }

    public String getGetPayedCmdDescription() {
        return getPayedCmdDescription;
    }

    public String getGetPayedCmdPermDescription() {
        return getPayedCmdPermDescription;
    }

    public String getDisplayHelpMessage() {
        return displayHelpMessage;
    }

    public String getWorkPlaceCmd() {
        return workPlaceCmd;
    }


    public String getWorkPlaceCmdDescription() {
        return workPlaceCmdDescription;
    }

    public boolean isPayDayMessageEnabled() {
        return payDayMessageEnabled;
    }

    public void setPayDayMessageEnabled(boolean payDayMessageEnabled) {
        this.payDayMessageEnabled = payDayMessageEnabled;
    }

    public String getWorkPlaceCmdPermDescription() {
        return workPlaceCmdPermDescription;
    }


    public int getWorkPlaceTool() {
        return workPlaceTool;
    }

    public String getDefaultBreakType() {
        return defaultBreakType;
    }

    public double getDefaultBreakAmount() {
        return defaultBreakAmount;
    }

    public String getDefaultPlaceType() {
        return defaultPlaceType;
    }

    public double getDefaultPlaceAmount() {
        return defaultPlaceAmount;
    }

    public String getWorkPlaceGreeting() {
        return workPlaceGreeting;
    }


    public String getConfigFile() {
        return configFile;
    }

    public String getPluginPath() {
        return pluginPath;
    }


    public GetPayedLogger getGetPayedLogger() {
        return getPayedLogger;
    }

    public GetPayedHelper getGetPayedHelper() {
        return getPayedHelper;
    }

    // Last change coming up... choosing the right ClassName for the Logger..

    /**
     * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
     *
     * @return instance of class
     */

    public static GetPayedConfig getInstance() {
        return instance;
    }


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// ToDo.... NOTHING.. you are DONE!


// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


    GetPayedConfig(TheMonkeyPack plugin, String moduleName) {
        super();
        MODULE_NAME = moduleName;
        main = plugin;
        getPayedLogger = new GetPayedLogger(MODULE_NAME);
        mainConfig = main.getMainConfig();
        pluginPath = main.getDataFolder() + System.getProperty("file.separator");
        instance = this;
        setupConfig();
        getPayedHelper = GetPayedHelper.getInstance(main);
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
            getPayedLogger.info("Creating default configuration file");
            defaultConfig();
        }
        try {
            config.load(pluginPath + configFile);
        } catch (IOException e) {
            getPayedLogger.severe("Can't read the " + configFile + " File!", e);
        } catch (InvalidConfigurationException e) {
            getPayedLogger.severe("Problem with the configuration in " + configFile + "!", e);
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
        getPayedLogger.info("Waiting for economy!");
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
            getPayedLogger.info("Using internal Defaults!");
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
        getPayedLogger.debug("configCurrent", configCurrent);
        getPayedLogger.debug("configVer", configVer);
        loadCustomConfig();
        getPayedLogger.info("Configuration v." + configVer + " loaded.");
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
        getPayedLogger.debug("creating config");
        boolean success = false;
        try {
            PrintWriter stream;
            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            PluginDescriptionFile pdfFile = main.getDescription();
            stream = new PrintWriter(pluginPath + configFile);
            getPayedLogger.debug("starting contents");
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
            getPayedLogger.debug("going for customConfig");
            writeCustomConfig(stream);

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            getPayedLogger.warning("Error saving the " + configFile + ".");
        }
        getPayedLogger.debug("DefaultConfig written", success);
        return success;
    }


// Checking if the configVersions differ

    /**
     * Method to check if the configuration version are different.
     * will set #configRequiresUpdate to true if versions are different
     */
    void updateNecessary() {
        if (configVer.equalsIgnoreCase(configCurrent)) {
            getPayedLogger.info("Config is up to date");
        } else {
            getPayedLogger.warning("Config is not up to date!");
            getPayedLogger.warning("Config File Version: " + configVer);
            getPayedLogger.warning("Internal Config Version: " + configCurrent);
            getPayedLogger.warning("It is suggested to update the config.yml!");
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
                getPayedLogger.info("Configuration was updated with new default values.");
                getPayedLogger.info("Please change them to your liking.");
            } else {
                getPayedLogger.warning("Configuration file could not be auto updated.");
                getPayedLogger.warning("Please rename " + configFile + " and try again.");
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
            getPayedLogger.info("Config reloaded");
            msg = "Config was reloaded";
        } else {
            getPayedLogger.severe("Reloading Config before it exists.");
            getPayedLogger.severe("Flog the developer!");
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


