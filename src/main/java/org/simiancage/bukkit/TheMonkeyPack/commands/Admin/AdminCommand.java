package org.simiancage.bukkit.TheMonkeyPack.commands.Admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;

/**
 * PluginName: TheMonkeyPack
 * Class: AdminCommand
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 21:20
 */

public class AdminCommand extends Commands implements CommandExecutor {

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
    }

    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {

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

