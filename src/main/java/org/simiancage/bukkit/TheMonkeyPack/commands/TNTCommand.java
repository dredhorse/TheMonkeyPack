package org.simiancage.bukkit.TheMonkeyPack.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.Messages;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.TNT_COMMAND;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig.TNT_CONTROL_PERMISSIONS;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTCommand
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 21:42
 */

// contains code from http://forums.bukkit.org/threads/8145/

public class TNTCommand extends Commands implements CommandExecutor {

	private TNTControlConfig tntControlConfig = TNTControlConfig.getInstance();
	private TNTControlLogger tntControlLogger;
	private TNTControlHelper tntControlHelper;
	private TheMonkeyPack main;
	private String cmd;
	private String helpOption;
	private String cmdDescription;
	private String cmdPermDescription;
	private String displayHelpMessage;


	public TNTCommand(TheMonkeyPack instance) {
		super(instance);
		main = (TheMonkeyPack) instance;
		cmd = tntControlConfig.getTNTCommandConfig(TNT_COMMAND.TNT_CMD);
		helpOption = tntControlConfig.getTNTCommandConfig(TNT_COMMAND.TNT_HELP_OPTION);
		cmdDescription = tntControlConfig.getTNTCommandConfig(TNT_COMMAND.TNT_CMD_DESCRIPTION);
		cmdPermDescription = tntControlConfig.getTNTCommandConfig(TNT_COMMAND.TNT_CMD_PERMISSION_DESCRIPTION);
		displayHelpMessage = tntControlConfig.getTNTCommandConfig(TNT_COMMAND.TNT_HELP_MESSAGE);
		this.setCommandName(cmd);
		this.setCommandDesc(cmdDescription);
		this.setCommandUsage(COMMAND_COLOR + "/" + cmd);
		this.setCommandExample(COMMAND_COLOR + "/" + cmd);
		this.setPermission(tntControlConfig.getPERM_TNT_COMMAND(), cmdPermDescription);
		this.setHasSubCommands(false);
		tntControlLogger = tntControlConfig.getTNTControlLogger();
		main.registerPlayerCommand(this.getCommandName(), this);
		mainLogger.debug(cmd + " command registered");
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			tntControlLogger.debug("tnt command executing");
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

	public boolean onPlayerCommand(Player player, String[] args) {
		tntControlLogger.debug("tnt command executing");
		if (!main.hasPermission(player, getPermission())) {
			permDenied(player, this);
			return true;
		} else {
			runCommand(player, this.getCommandName(), args);
			return true;
		}


	}

	@Override
	public void subCommands(CommandSender sender) {

	}


	@Override
	public void runCommand(CommandSender sender, String label, String[] args) {
		Player player = null;
		String pname = "(Console)";
		if ((sender instanceof Player)) {
			player = (Player) sender;
			pname = player.getName();


			if (args.length == 0) {
				String msg = INFO_MESSAGES + "[" + tntControlConfig.getMODULE_NAME() + "] v " + main.getDescription().getVersion() + " Configuration";
				main.sendPlayerMessage(player, msg);
				msg = tntControlConfig.getMessage(Messages.INFO_ELEVATION_LIMIT).replace("%abovelevel", Integer.toString(tntControlConfig.getTntBlastLimit()));
				main.sendPlayerMessage(player, INFO_MESSAGES + msg);
				msg = tntControlConfig.getMessage(Messages.INFO_BLAST_YIELD).replace("%yield", Float.toString(tntControlConfig.getTntBlastYield()));
				main.sendPlayerMessage(player, INFO_MESSAGES + msg);
				msg = tntControlConfig.getMessage(Messages.INFO_BLAST_RADIUS).replace("%radius", Integer.toString(tntControlConfig.getTntBlastRadius()));
				main.sendPlayerMessage(player, INFO_MESSAGES + msg);
				msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_BLAST_FIRE);
				if (tntControlConfig.isTntBlastCauseFire()) {
					msg = msg.replace("%onOff", tntControlConfig.getMessage(Messages.ON_STRING));
				} else {
					msg = msg.replace("%onOff", tntControlConfig.getMessage(Messages.OFF_STRING));
				}
				main.sendPlayerMessage(player, msg);
				msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_TNT_ALLOW_REDSTONE_PRIME);
				if (tntControlConfig.isTntAllowRedstonePrime()) {
					msg = msg.replace("%onOff", tntControlConfig.getMessage(Messages.ON_STRING));
				} else {
					msg = msg.replace("%onOff", tntControlConfig.getMessage(Messages.OFF_STRING));
				}
				main.sendPlayerMessage(player, msg);
				msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_ON_SURFACE);
				if (tntControlConfig.isTntOnSurfaceEnabled()) {
					msg = msg.replace("%onOff", tntControlConfig.getMessage(Messages.ON_STRING));
				} else {
					msg = msg.replace("%onOff", tntControlConfig.getMessage(Messages.OFF_STRING));
				}
				main.sendPlayerMessage(player, msg);
				if (tntControlConfig.getTntBelowSurfaceLevel() > 0) {
					msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_SLACK).replace("%blocks", Integer.toString(tntControlConfig.getTntBelowSurfaceLevel()));
					main.sendPlayerMessage(player, msg);
				}
				main.sendPlayerMessage(player, "");
				if (TNT_CONTROL_PERMISSIONS.TNT_ALLOWED.hasPermission(player)) {
					main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_ARE_ALLOWED));
					if (!TNT_CONTROL_PERMISSIONS.TNT_ABOVELIMIT_PLACE.hasPermission(player)) {
						msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_YOU_ARE_LIMITED).replace("%placeActivate", tntControlConfig.getMessage(Messages.PLACE_STRING));
						msg = msg.replace("%abovelevel", Integer.toString(tntControlConfig.getTntBlastLimit()));
						main.sendPlayerMessage(player, msg);
					}
					if (!TNT_CONTROL_PERMISSIONS.TNT_ABOVELIMIT_ACTIVATE.hasPermission(player)) {
						msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_YOU_ARE_LIMITED).replace("%placeActivate", tntControlConfig.getMessage(Messages.ACTIVATE_STRING));
						msg = msg.replace("%abovelevel", Integer.toString(tntControlConfig.getTntBlastLimit()));
						main.sendPlayerMessage(player, msg);
					}
					if (tntControlConfig.isTntAllowReclaimTool()) {
						String toolName = Material.getMaterial(tntControlConfig.getTntReclaimTool()).name();
						msg = INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_ALLOWED_RECLAIM_TOOL).replace("%tool", toolName);
						main.sendPlayerMessage(player, msg);
					}
					if (main.hasPermission(player, tntControlConfig.getPERM_TNT_RECLAIM_COMMAND())) {
						main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.INFO_ALLOWED_RECLAIM_COMMAND));
						if (tntControlHelper.isOnReclaim(player)) {
							main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.RECLAIMING_IS_ONOFF).replace("onOff", tntControlConfig.getMessage(Messages.ON_STRING)));
						} else {
							main.sendPlayerMessage(player, INFO_MESSAGES + tntControlConfig.getMessage(Messages.RECLAIMING_IS_ONOFF).replace("%onOff", tntControlConfig.getMessage(Messages.OFF_STRING)));
						}
					}
				} else {
					main.sendPlayerMessage(player, WARNING_MESSAGES + tntControlConfig.getMessage(Messages.INFO_ARE_NOT_ALLOWED));
				}
				return;
			}
			if (args.length >= 1) {
				tntControlLogger.debug("option", args[0]);
				displayHelp(player, this);

			}
			return;
		}
		sender.sendMessage("This is not a console command!");
	}
}
