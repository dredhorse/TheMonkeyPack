package org.simiancage.bukkit.TheMonkeyPack.commands.Admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: TNTControlAdminCommand
 * User: DonRedhorse
 * Date: 31.12.11
 * Time: 20:49
 */

public class TNTControlAdminCommand {
	private TheMonkeyPack main;
	private TNTControlConfig tntControlConfig;
	private TNTControlLogger tntControlLogger;

	public TNTControlAdminCommand(TheMonkeyPack main) {
		this.main = main;
		tntControlConfig = TNTControlConfig.getInstance();
		tntControlLogger = tntControlConfig.getTNTControlLogger();
	}

	public boolean tntcontrolSubCommand(CommandSender sender, String[] args) {

		boolean cmdProcessed = false;
		String subCommand = args[1].toLowerCase();

		if (subCommand.equalsIgnoreCase("limit")) {
			limitOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("yield")) {
			yieldOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("radius")) {
			radiusOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("fire")) {
			fireOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("tool")) {
			toolOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("redstone")) {
			redstoneOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("reclaim")) {
			reclaimOption(sender, args);
			cmdProcessed = true;
		}

		if (subCommand.equalsIgnoreCase("reload")) {
			reloadOption(sender, args);
			cmdProcessed = true;
		}


		return cmdProcessed;
	}

	private void redstoneOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current Restone can detonate TNT status is: " + tntControlConfig.isTntAllowRedstonePrime());
			return;
		}
		String option = args[2];
		if (option.equalsIgnoreCase("on") || option.equalsIgnoreCase("true")) {
			sender.sendMessage(Commands.INFO_MESSAGES + "Setting Redstone can detonate Status to: TRUE");
			tntControlConfig.setTntAllowRedstonePrime(true);
			return;
		}
		if (option.equalsIgnoreCase("off") || option.equalsIgnoreCase("false")) {
			sender.sendMessage(Commands.INFO_MESSAGES + "Setting Redstone can detonate Status to: FALSE");
			tntControlConfig.setTntAllowRedstonePrime(false);
			return;
		}
		sender.sendMessage(Commands.WARNING_MESSAGES + "This setting requires [on|off] or [true|false] as an option.");
	}

	private void reloadOption(CommandSender sender, String[] args) {
		tntControlConfig.writeConfig();
		String msg = tntControlConfig.reloadConfig();
		sender.sendMessage(Commands.INFO_MESSAGES + msg);
	}

	private void reclaimOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current Allow Reclaim Tool Usage status is: " + tntControlConfig.isTntAllowReclaimTool());
			return;
		}
		String option = args[2];
		if (option.equalsIgnoreCase("on") || option.equalsIgnoreCase("true")) {
			sender.sendMessage(Commands.INFO_MESSAGES + "Setting Allow Reclaim Tool Usage Status to: TRUE");
			tntControlConfig.setTntAllowReclaimTool(true);
			return;
		}
		if (option.equalsIgnoreCase("off") || option.equalsIgnoreCase("false")) {
			sender.sendMessage(Commands.INFO_MESSAGES + "Setting Allow Reclaim Tool Usage Status to: FALSE");
			tntControlConfig.setTntAllowReclaimTool(false);
			return;
		}
		sender.sendMessage(Commands.WARNING_MESSAGES + "This setting requires [on|off] or [true|false] as an option.");
	}

	private void toolOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current Reclaim Tool is: " + tntControlConfig.getTntReclaimTool());
			String toolName = Material.getMaterial(tntControlConfig.getTntReclaimTool()).name();
			sender.sendMessage(Commands.INFO_MESSAGES + "which means it is a: " + toolName);
			return;
		}
		int newTool;
		try {
			newTool = Integer.parseInt(args[2]);
		} catch (Exception ex) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You need to supply a new tool as whole ID number!");
			return;
		}
		try {
			Material newMaterial = Material.getMaterial(newTool);
			if (newMaterial == null) {
				throw new Exception();
			}
		} catch (Exception ex) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "Looks like the material ID you specified is not valid!");
			newTool = tntControlConfig.getTntReclaimTool();
		}
		if (newTool < 1) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You cannot specify a material ID lower than 1!");
			newTool = tntControlConfig.getTntReclaimTool();
		}
		sender.sendMessage(Commands.INFO_MESSAGES + "Setting Reclaim tool to: " + newTool);
		String toolName = Material.getMaterial(newTool).name();
		sender.sendMessage(Commands.INFO_MESSAGES + "which means it is a: " + toolName);
		tntControlConfig.setTntReclaimTool(newTool);
	}

	private void fireOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current Blast Causes Fire status is: " + tntControlConfig.isTntBlastCauseFire());
			return;
		}
		String option = args[2];
		if (option.equalsIgnoreCase("on") || option.equalsIgnoreCase("true")) {
			sender.sendMessage(Commands.INFO_MESSAGES + "Setting Blast Causes Fire Status to: TRUE");
			tntControlConfig.setTntBlastCauseFire(true);
			return;
		}
		if (option.equalsIgnoreCase("off") || option.equalsIgnoreCase("false")) {
			sender.sendMessage(Commands.INFO_MESSAGES + "Setting Blast Causes Fire Status to: FALSE");
			tntControlConfig.setTntBlastCauseFire(false);
			return;
		}
		sender.sendMessage(Commands.WARNING_MESSAGES + "This setting requires [on|off] or [true|false] as an option.");
	}

	private void radiusOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current Blast radius is: " + tntControlConfig.getTntBlastRadius());
			return;
		}
		int newRadius;
		try {
			newRadius = Integer.parseInt(args[2]);
		} catch (Exception ex) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You need to supply a new radius as whole number!");
			return;
		}
		if (newRadius > 25) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You cannot specify a radius higher than 25!");
			newRadius = 25;
		}
		if (newRadius < 1) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You cannot specify a radius lower than 1!");
			newRadius = 1;
		}
		sender.sendMessage(Commands.INFO_MESSAGES + "Setting Blast Radius to: " + newRadius);
		tntControlConfig.setTntBlastRadius(newRadius);
	}

	private void yieldOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current yield level is: " + (tntControlConfig.getTntBlastYield() * 100) + "%.");
			return;
		}
		int newYield;
		try {
			newYield = Integer.parseInt(args[2]);
		} catch (Exception ex) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You need to supply the new yield as whole number!");
			return;
		}
		sender.sendMessage(Commands.INFO_MESSAGES + "Setting new yield to: " + newYield + "%.");
		tntControlConfig.setTntBlastYield(newYield);
	}

	private void limitOption(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Commands.INFO_MESSAGES + "The current Blast Limit is: " + tntControlConfig.getTntBlastLimit());
			return;
		}
		int newLevel;
		try {
			newLevel = Integer.parseInt(args[2]);
		} catch (Exception ex) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You need to supply a new level as whole number!");
			return;
		}
		if (newLevel > 128) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You cannot specify a level higher than 128!");
			newLevel = 128;
		}
		if (newLevel < 0) {
			sender.sendMessage(Commands.WARNING_MESSAGES + "You cannot specify a level lower than 0!");
			newLevel = 0;
		}
		sender.sendMessage(Commands.INFO_MESSAGES + "Setting Blast Limit to: " + newLevel);
		tntControlConfig.setTntBlastLimit(newLevel);
	}
}

