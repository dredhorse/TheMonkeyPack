package org.simiancage.bukkit.TheMonkeyPack.commands.Admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;
import org.simiancage.bukkit.TheMonkeyPack.configs.GetPayedConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.GetPayedHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * PluginName: TheMonkeyPack
 * Class: AdminCommand
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 21:20
 */

public class AdminCommand extends Commands implements CommandExecutor {
    private GetPayedHelper getPayedHelper;
    private GetPayedConfig getPayedConfig;

    public AdminCommand(TheMonkeyPack instance) {
        super(instance);
        this.setCommandName("tmpadmin");
        this.setCommandDesc("administer TheMonkeyPack");
        this.setCommandUsage(COMMAND_COLOR + "/tmpadmin");
        this.setCommandExample("/tmpadmin " + DEFAULT_COLOR + "To show a list of commands");
        this.setPermission("tmp.admin", "allows access to the tmpadmin commands.");
        this.hasSubCommands = true;
        mainLogger.debug("tmpadmin registered");
    }

    @Override
    public void subCommands(CommandSender sender) {
        sender.sendMessage("/tmpadmin " + SUB_COLOR + "reload" + DEFAULT_COLOR + " reloads the configuration");
        sender.sendMessage("/tmpadmin " + SUB_COLOR + "save" + DEFAULT_COLOR + " saving the configuration");
        sender.sendMessage("/tmpadmin " + SUB_COLOR + "version" + DEFAULT_COLOR + " check the version of plugin and config");
        sender.sendMessage("/tmpadmin " + SUB_COLOR + "debug" + DEFAULT_COLOR + " enable debug logging (messy!");
        sender.sendMessage("/tmpadmin " + SUB_COLOR + "getpayed" + DEFAULT_COLOR + " configure getpayed module");
    }

    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {

        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();
        }

        String subCommand = args[0];

        if (mainConfig.isGetPayedActive() && subCommand.equalsIgnoreCase("getpayed")) {
            if (args.length < 2) {
                sender.sendMessage(INFO_MESSAGES + "This command requires an option!");
                sender.sendMessage(INFO_MESSAGES + "strip = removes blocks from config with 0.0 for break and place");
                sender.sendMessage(INFO_MESSAGES + "complete = adds all missing blocks to config with 0.0 value");
                return;
            }
            getPayedConfig = mainConfig.getPayedConfig;
            getPayedHelper = getPayedConfig.getGetPayedHelper();
            getpayedSubCommand(sender, args[1]);
        }

    }


    private void getpayedSubCommand(CommandSender sender, String subCommand) {

        if (subCommand.equalsIgnoreCase("complete")) {
            for (int i = 0; i < getPayedHelper.sizeOfOrderingList(); i++) {
                if (getPayedConfig.blockPricesContainsBlock(getPayedHelper.getItemViaIndex(i))) {
                    continue;
                }
                Map temp = new HashMap();
                temp.put("break", 0.0D);
                temp.put("place", 0.0D);
                getPayedConfig.setBlockPrices(getPayedHelper.getItemViaIndex(i), temp);
            }
            getPayedConfig.writeConfig();
            String msg = getPayedConfig.reloadConfig();
            sender.sendMessage(INFO_MESSAGES + msg);

            return;
        }
        if (subCommand.equalsIgnoreCase("strip")) {
            for (int i = 0; i < getPayedHelper.sizeOfOrderingList(); i++) {
                String blockType = getPayedHelper.getItemViaIndex(i);
                if (!getPayedConfig.blockPricesContainsBlock(blockType)) {
                    continue;
                }
                if ((getPayedConfig.getBlockBreakPrice(blockType) != 0.0D) ||
                        (getPayedConfig.getBlockPlacePrice(blockType) != 0.0D)) {
                    continue;
                }
                getPayedConfig.removeBlockFromBlockPrices(blockType);
            }
            getPayedConfig.writeConfig();
            String msg = getPayedConfig.reloadConfig();
            sender.sendMessage(INFO_MESSAGES + msg);

        }
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            mainLogger.debug("tmpadmin command executing");
            if (!main.hasPermission(player, getPermission())) {
                permDenied(player, this);
                return true;
            } else {
                if (args.length > 0) {
                    runCommand(commandSender, label, args);
                    return true;
                } else {
                    displayHelp(player, this);
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public boolean onPlayerCommand(Player player, String[] args) {
        return false;
    }
}

