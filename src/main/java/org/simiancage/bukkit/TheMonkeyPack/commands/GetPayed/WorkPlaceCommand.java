package org.simiancage.bukkit.TheMonkeyPack.commands.GetPayed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.GetPayedLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * PluginName: TheMonkeyPack
 * Class: PriceSetCommand
 * User: DonRedhorse
 * Date: 23.12.11
 * Time: 23:56
 */

// contains code from http://forums.bukkit.org/threads/20984/

public class WorkPlaceCommand extends Commands implements CommandExecutor {

    private GetPayedConfig getPayedConfig = GetPayedConfig.getInstance();
    private GetPayedLogger getPayedLogger;
    private GetPayedHelper getPayedHelper;
    private TheMonkeyPack main;
    private MainConfig mainConfig;
    private String workplaceCmd;
    private String workplacePerm;

    private String helpOption;
    private String displayHelpMessage;
    private String cmdDescription;
    private String cmdPermDescription;
    private String workplaceCreateOption;
    private String workplaceCreateDescription;
    private String workplaceRenameOption;
    private String workplaceRenameDescription;
    private String workplaceInfoOption;
    private String workplaceInfoDescription;
    private String workplaceDeleteOption;
    private String workplaceDeleteDescription;
    private String workplaceSetOption;
    private String workplaceSetDescription;
    private String workplaceConfigurePerm;


    public WorkPlaceCommand(TheMonkeyPack instance) {
        super(instance);
        main = (TheMonkeyPack) instance;
        mainConfig = main.getMainConfig();
        helpOption = getPayedConfig.getHelpOption();
        displayHelpMessage = getPayedConfig.getDisplayHelpMessage();
        workplaceCmd = getPayedConfig.getWorkPlaceCmd();
        cmdDescription = getPayedConfig.getWorkPlaceCmdDescription();
        workplacePerm = getPayedConfig.getPERM_WORKPLACE();
        cmdPermDescription = getPayedConfig.getWorkPlaceCmdPermDescription();
        workplaceCreateOption = getPayedConfig.getWorkplaceCreateOption();
        workplaceCreateDescription = getPayedConfig.getWorkplaceCreateDescription();
        workplaceRenameOption = getPayedConfig.getWorkplaceRenameOption();
        workplaceRenameDescription = getPayedConfig.getWorkplaceRenameDescription();
        workplaceInfoOption = getPayedConfig.getWorkplaceInfoOption();
        workplaceInfoDescription = getPayedConfig.getWorkplaceInfoDescription();
        workplaceDeleteOption = getPayedConfig.getWorkplaceDeleteOption();
        workplaceDeleteDescription = getPayedConfig.getWorkplaceDeleteDescription();
        workplaceSetOption = getPayedConfig.getWorkplaceSetOption();
        workplaceSetDescription = getPayedConfig.getWorkplaceSetDescription();
        workplaceConfigurePerm = getPayedConfig.getPERM_WORKPLACE_CONFIGURE();
        this.setCommandName(workplaceCmd);
        this.setCommandDesc(cmdDescription);
        this.setCommandUsage(COMMAND_COLOR + "/" + workplaceCmd + OPTIONAL_COLOR + " [option] [option");
        this.setCommandExample(COMMAND_COLOR + "/" + workplaceCmd + SUB_COLOR + " " + workplaceCreateOption + " NewWorkPlace");
        this.setPermission(workplacePerm, cmdPermDescription);
        this.setHasSubCommands(true);
        getPayedLogger = getPayedConfig.getGetPayedLogger();
        getPayedHelper = getPayedConfig.getGetPayedHelper();
        main.registerPlayerCommand(this.getCommandName(), this);
        mainLogger.debug(workplaceCmd + " command registered");
    }


    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {
        getPayedHelper = getPayedConfig.getGetPayedHelper();
        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();
        }

        if (player == null) {
            sender.sendMessage("Please look up the command syntax! This is not a console command");
            return;
        }

        // not enough arguments as we need always at least 2
        if (args.length == 0) {
            displayHelp(player, this);
            return;
        }


        String command = args[0];


        if (command.equalsIgnoreCase(workplaceInfoOption)) {
            if (args.length > 1) {
                displayHelp(player, this);
                return;
            }
            workplaceInfo(player);
            return;
        }

        boolean hasWorkplaceConfigure = main.hasPermission(player, workplaceConfigurePerm);

        if (command.equalsIgnoreCase(workplaceCreateOption)) {
            if (!hasWorkplaceConfigure) {
                permDenied(player, this, workplaceCreateOption, workplaceConfigurePerm);
                return;
            }
            if (args.length < 2) {
                main.sendPlayerMessage(player, INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_CREATE_HELP_MESSAGE));
                return;
            }
            workplaceCreate(player, args[1]);
            return;
        }

        if (command.equalsIgnoreCase(workplaceRenameOption)) {
            if (!hasWorkplaceConfigure) {
                permDenied(player, this, workplaceRenameOption, workplaceConfigurePerm);
                return;
            }
            if (args.length < 3) {
                main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_RENAME_HELP_MESSAGE));
                return;
            }
            workplaceRename(player, args[1], args[2]);
            return;
        }

        if (command.equalsIgnoreCase(workplaceDeleteOption)) {
            if (!hasWorkplaceConfigure) {
                permDenied(player, this, workplaceDeleteOption, workplaceConfigurePerm);
                return;
            }
            if (args.length < 2) {
                main.sendPlayerMessage(player, INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_CREATE_HELP_MESSAGE));
                return;
            }
            workplaceDelete(player, args[1]);
            return;
        }


        if (command.equalsIgnoreCase(workplaceSetOption)) {
            if (!hasWorkplaceConfigure) {
                permDenied(player, this, workplaceSetOption, workplaceConfigurePerm);
                return;
            }
            if (args.length < 3) {
                main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_SET_HELP_MESSAGE));
                return;
            }
            workplaceSet(player, args[1], args[2]);
            return;
        }

        displayHelp(player, this);
        return;

    }

    public void workplaceSet(Player player, String varName, String newValue) {

        if (getPayedHelper.isPlayerInWorksPlace(player)) {

            int workplaceIndex = getPayedHelper.getPlayerWorkPlaceIndex(player);
            Map<String, Object> info = getPayedHelper.getWorkPlaceInfoRecord(workplaceIndex);
            if (checkIfOwnerOrAdmin(player, info)) {
                return;
            }

            if ((varName.equalsIgnoreCase("breakAmount")) || (varName.equalsIgnoreCase("placeAmount"))) {

                info.remove(varName);
                if (varName.equalsIgnoreCase("breakAmount")) {
                    info.put("breakAmount", Double.parseDouble(newValue));
                } else {
                    info.put("placeAmount", Double.parseDouble(newValue));
                }
            } else if ((varName.equalsIgnoreCase("breakType")) || (varName.equalsIgnoreCase("placeType"))) {
                if ((newValue.equalsIgnoreCase("percent")) || (newValue.equalsIgnoreCase("flat"))) {

                    info.remove(varName);
                    String tempName = "";
                    String tempValue = "";

                    if (varName.equalsIgnoreCase("BreakType")) {
                        tempName = "breakType";
                    } else {
                        tempName = "placeType";
                    }

                    if (newValue.equalsIgnoreCase("percent")) {
                        tempValue = "percent";
                    } else {
                        tempValue = "flat";
                    }

                    info.put(tempName, tempValue);
                } else {
                    main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WRONG_BREAK_PLACE_TYPE_MESSAGE));
                    return;
                }
            } else {
                main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.NOT_A_VALID_VARIABLE_MESSAGE));
                return;
            }
            String msg = getPayedConfig.getMessage(Messages.VAR_SET_TO_NEW_VALUE_MESSAGE).replace("%varname", varName);
            msg = msg.replace("%newvalue", newValue);
            main.sendPlayerMessage(player, INFO_MESSAGES + msg);

        } else {
            main.sendPlayerMessage(player, INFO_MESSAGES + getPayedConfig.getMessage(Messages.YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE));

        }

        return;

    }


    public void workplaceInfo(Player player) {

        if (getPayedHelper.isPlayerInWorksPlace(player)) {
            Map temp = getPayedHelper.getWorkPlaceInfoRecord(getPayedHelper.getPlayerWorkPlaceIndex(player));
            String msg = INFO_MESSAGES + "-----------------------------------------------------";
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_NAME_MESSAGE) + ": " + DEFAULT_COLOR + temp.get("Name");
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_OWNER_MESSAGE) + ": " + DEFAULT_COLOR + temp.get("OwnedBy");
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_BREAK_TYPE_MESSAGE) + ": " + DEFAULT_COLOR + temp.get("breakType");
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_BREAK_AMOUNT_MESSAGE) + ": " + DEFAULT_COLOR + temp.get("breakAmount");
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_PLACE_TYPE_MESSAGE) + ": " + DEFAULT_COLOR + temp.get("placeType");
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_PLACE_AMOUNT_MESSAGE) + ": " + DEFAULT_COLOR + temp.get("placeAmount");
            main.sendPlayerMessage(player, msg);
            msg = INFO_MESSAGES + "-----------------------------------------------------";
            main.sendPlayerMessage(player, msg);
        } else {
            main.sendPlayerMessage(player, INFO_MESSAGES + getPayedConfig.getMessage(Messages.YOU_ARE_NOT_IN_A_WORKPLACE_MESSAGE));
        }

        return;


    }

    public void workplaceDelete(Player player, String workplaceName) {

        if (getPayedHelper.workplaceAlreadyExist(workplaceName)) {
            int workplaceIndex = getPayedHelper.getWorkPlaceNamesIndex(workplaceName);
            Map<String, Object> info = getPayedHelper.getWorkPlaceInfoRecord(workplaceIndex);
            if (checkIfOwnerOrAdmin(player, info)) {
                return;
            }


            if (main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentWPTaskId())) {
                while (main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentWPTaskId())) {
                    ;
                }
                main.getServer().getScheduler().cancelTask(getPayedHelper.getCurrentWPTaskId());
            }

            if (main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentWPSaveTaskId())) {
                while (main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentWPSaveTaskId())) {
                    ;
                }
                main.getServer().getScheduler().cancelTask(getPayedHelper.getCurrentWPSaveTaskId());
            }

            getPayedHelper.removeWorkPlaceNames(workplaceName);
            getPayedHelper.removeWorkPlaceInfoRecord(workplaceIndex);
            getPayedHelper.removeWorkplacesX1(workplaceIndex);
            getPayedHelper.removeWorkplacesX2(workplaceIndex);
            getPayedHelper.removeWorkplacesZ1(workplaceIndex);
            getPayedHelper.removeWorkplacesZ2(workplaceIndex);


            if (getPayedHelper.getNumberOfWorkplaces() > 0) {
                getPayedHelper.shrinkData(workplaceIndex);
            }

            main.sendPlayerMessage(player, INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_DELETED_MESSAGE).replace("%workplace", workplaceName));

            getPayedHelper.setCurrentWPTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, getPayedHelper.getWorkPlaceCheck(), getPayedConfig.getEntryExitRefreshRate()));
            getPayedHelper.setCurrentWPSaveTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, getPayedHelper.getWorkPlaceSaveRoutine(), getPayedConfig.getWorkPlaceSaveInterval()));
        } else {
            main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.NO_WORKPLACE_WITH_THIS_NAME_MESSAGE));
        }
        return;

    }


    public void workplaceRename(Player player, String oldWorkplaceName, String newWorkplaceName) {

        if (getPayedHelper.workplaceAlreadyExist(oldWorkplaceName)) {
            int workplaceIndex = getPayedHelper.getWorkPlaceNamesIndex(oldWorkplaceName);
            Map<String, Object> info = getPayedHelper.getWorkPlaceInfoRecord(workplaceIndex);
            if (checkIfOwnerOrAdmin(player, info)) {
                return;
            }

            if (main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentWPSaveTaskId())) {
                while (main.getServer().getScheduler().isCurrentlyRunning(getPayedHelper.getCurrentWPSaveTaskId())) {
                    ;
                }
                main.getServer().getScheduler().cancelTask(getPayedHelper.getCurrentWPSaveTaskId());
            }

            info.remove("Name");
            info.put("Name", newWorkplaceName);

            getPayedHelper.removeWorkPlaceInfoRecord(workplaceIndex);
            getPayedHelper.addWorkPlaceInfoRecord(workplaceIndex, info);

            getPayedHelper.removeWorkPlaceNames(oldWorkplaceName);
            getPayedHelper.addWorkPlaceNames(newWorkplaceName, workplaceIndex);
            String msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_RENAMED_TO_MESSAGE).replace("%oldname", oldWorkplaceName);
            msg = msg.replace("%newname", newWorkplaceName);
            main.sendPlayerMessage(player, msg);
            getPayedHelper.setCurrentTaskId(main.getServer().getScheduler().scheduleAsyncDelayedTask(main, getPayedHelper.getWorkPlaceSaveRoutine(), getPayedConfig.getWorkPlaceSaveInterval()));
        } else {
            main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.NO_WORKPLACE_WITH_THIS_NAME_MESSAGE));

        }

    }

    private boolean checkIfOwnerOrAdmin(Player player, Map<String, Object> info) {
        if (!main.hasPermission(player, mainConfig.getPERM_ADMIN_WORKPLACE())) {
            if (!(info.get("OwnedBy").toString().equalsIgnoreCase(player.getName()))) {
                main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.YOU_NEED_TO_OWN_THE_WORKPLACE_MESSAGE));
                return true;
            }
        }
        return false;
    }


    public void workplaceCreate(Player player, String workplaceName) {
        String msg;
        if (getPayedHelper.playerHasSelectedPoints(player)) {
            if (!getPayedHelper.workplaceAlreadyExist(workplaceName)) {

                Map temp = getPayedHelper.getPlayersWorkplaceTempPoints(player);

                if ((Integer) temp.get("pointsSelected") == 1) {
                    int tempX1 = 0;
                    int tempX2 = 0;
                    int tempZ1 = 0;
                    int tempZ2 = 0;

                    if ((Integer) temp.get("x1") < (Integer) temp.get("x2")) {
                        tempX1 = (Integer) temp.get("x1");
                        tempX2 = (Integer) temp.get("x2");
                    } else {
                        tempX2 = (Integer) temp.get("x1");
                        tempX1 = (Integer) temp.get("x2");
                    }

                    if ((Integer) temp.get("z1") < (Integer) temp.get("z2")) {
                        tempZ1 = (Integer) temp.get("z1");
                        tempZ2 = (Integer) temp.get("z2");
                    } else {
                        tempZ2 = (Integer) temp.get("z1");
                        tempZ1 = (Integer) temp.get("z2");
                    }

                    if (!getPayedHelper.doesThisCrossOver(tempX1, tempX2, tempZ1, tempZ2)) {
                        getPayedHelper.addWorkplacesX1(tempX1);
                        getPayedHelper.addWorkplacesX2(tempX2);
                        getPayedHelper.addWorkplacesZ1(tempZ1);
                        getPayedHelper.addWorkplacesZ2(tempZ2);

                        Map infoTemp = new HashMap();
                        infoTemp.put("Name", workplaceName);
                        infoTemp.put("Welcome", INFO_MESSAGES + getPayedConfig.getWorkPlaceGreeting());
                        infoTemp.put("Exit", INFO_MESSAGES + getPayedConfig.getWorkPlaceFarewell());
                        infoTemp.put("breakType", getPayedConfig.getDefaultBreakType());
                        infoTemp.put("breakAmount", getPayedConfig.getDefaultBreakAmount());
                        infoTemp.put("placeType", getPayedConfig.getDefaultPlaceType());
                        infoTemp.put("placeAmount", getPayedConfig.getDefaultPlaceAmount());
                        infoTemp.put("OwnedBy", player.getName());
                        getPayedHelper.addWorkPlaceInfoRecord(getPayedHelper.getNextWorkplace(), infoTemp);
                        getPayedHelper.addWorkPlaceNames(workplaceName, getPayedHelper.getNextWorkplace());
                        getPayedHelper.removePlayersTempSelectionPoints(player);
                        msg = INFO_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_CREATED_MESSAGE).replace("%workplace", workplaceName);
                        main.sendPlayerMessage(player, msg);
                    } else {
                        main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_SELECTION_OVERLAP_HELP_MESSAGE));
                        getPayedHelper.removePlayersTempSelectionPoints(player);
                    }
                } else {
                    main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE));
                }
            } else {
                main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_ALREADY_EXIST));

            }
        } else {
            main.sendPlayerMessage(player, WARNING_MESSAGES + getPayedConfig.getMessage(Messages.WORKPLACE_SELECT_2_POINTS_HELP_MESSAGE));

        }


    }


    @Override
    public void subCommands(CommandSender sender) {
        String msg = "/" + COMMAND_COLOR + workplaceCmd + SUB_COLOR + " ";
        sender.sendMessage(msg + helpOption + DEFAULT_COLOR + " " + displayHelpMessage);
        sender.sendMessage(msg + workplaceInfoOption + DEFAULT_COLOR + " " + workplaceInfoDescription);
        sender.sendMessage(msg + workplaceCreateOption + DEFAULT_COLOR + " " + workplaceCreateDescription);
        sender.sendMessage(msg + workplaceRenameOption + DEFAULT_COLOR + " " + workplaceRenameDescription);
        sender.sendMessage(msg + workplaceDeleteOption + DEFAULT_COLOR + " " + workplaceDeleteDescription);
        sender.sendMessage(msg + workplaceSetOption + DEFAULT_COLOR + " " + workplaceSetDescription);
    }


    @Override
    public boolean onPlayerCommand(Player player, String[] args) {
        getPayedLogger.debug(workplaceCmd + " command executing");
        if (!main.hasPermission(player, getPermission())) {
            permDenied(player, this);
            return true;
        } else {
            runCommand(player, this.getCommandName(), args);
            return true;
        }


    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            getPayedLogger.debug(workplaceCmd + " command executing");
            if (!main.hasPermission(player, getPermission())) {
                permDenied(player, this);
                return true;
            } else {
                runCommand(commandSender, label, args);
                return true;
            }
        }

        return false;
    }


}

