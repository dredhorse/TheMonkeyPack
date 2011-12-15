package org.simiancage.bukkit.TheMonkeyPack.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.KitConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.KitHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.KitLogger;
import org.simiancage.bukkit.TheMonkeyPack.modules.KitObject;

import java.util.Date;
import java.util.HashMap;

/**
 * PluginName: TheMonkeyPack
 * Class: KitCommand
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 21:42
 */

// contains code from http://forums.bukkit.org/threads/38557/

public class KitCommand extends Commands implements CommandExecutor {

    private KitConfig kitConfig = KitConfig.getInstance();
    private KitLogger kitLogger;
    private TheMonkeyPack main;

    public KitCommand(TheMonkeyPack instance) {
        super(instance);
        main = (TheMonkeyPack) instance;
        this.setCommandName("kit");
        this.setCommandDesc("show a list of kit names or get a named kit");
        this.setCommandUsage(COMMAND_COLOR + "/kit" + OPTIONAL_COLOR + " [kitname] [player]");
        this.setCommandExample(COMMAND_COLOR + "/kit " + SUB_COLOR + "beginners");
        this.setPermission("tmp.kit", "allows access of the kit comamnd");
        this.setHasSubCommands(true);
        kitLogger = kitConfig.getKitLogger();
        main.registerPlayerCommand(this.getCommandName(), this);
        mainLogger.debug("Kit command registered");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            kitLogger.debug("kit command executing");
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
        kitLogger.debug("kit command executing");
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
        sender.sendMessage("/kit " + SUB_COLOR + "help" + DEFAULT_COLOR + " displays this help.");
    }


    @Override
    public void runCommand(CommandSender sender, String label, String[] args) {
        Player player = null;
        String pname = "(Console)";
        if ((sender instanceof Player)) {
            player = (Player) sender;
            pname = player.getName();
        }

        if (args.length == 0) {
            kitLogger.debug(pname + " requested a list of kits ");

            String kitnames = "";
            for (KitObject kit : kitConfig.getKits()) {
                if ((player == null) || (main.hasPermission(player, "tmp.kit." + kit.Name().toLowerCase()))) {
                    if (!kitnames.matches("")) {
                        kitnames = kitnames.concat(", ");
                    }
                    kitnames = kitnames.concat(kit.Name());
                    if ((main.isEconomyEnabled()) && (kit.Cost().intValue() > 0)) {
                        kitnames = kitnames.concat(" (" + kit.Cost() + ")");
                    }
                }
            }
            kitnames = "You have the following kits available: \n" + INFO_MESSAGES + kitnames;
            if (kitnames.equals("")) {
                kitnames = WARNING_MESSAGES + "You don't have access to any kits";
            }
            main.sendPlayerMessage(player, kitnames);
            return;
        }
        if (args.length >= 1) {
            if (args[0].toLowerCase().equalsIgnoreCase("help")) {
                displayHelp(player, this);
                return;
            }
            kitLogger.debug(pname + " requested kit " + args[0]);
            HashMap history = KitHelper.loadHistory(player);
            String[] players;
            if (args.length == 1) {
                players = new String[1];
                players[0] = pname;
            } else {
                players = new String[args.length - 1];
                for (Integer n = Integer.valueOf(1); n.intValue() < args.length; n = Integer.valueOf(n.intValue() + 1)) {
                    players[(n.intValue() - 1)] = args[n.intValue()];
                }

            }

            boolean found = false;
            for (KitObject kit : kitConfig.getKits()) {
                if (!args[0].equalsIgnoreCase(kit.Name())) {
                    continue;
                }
                kitLogger.debug("Found kit '" + kit.Name().toLowerCase() + "', checking access");
                if (player == null) {
                    kitLogger.debug("Console access granted");
                } else if (main.hasPermission(player, "tmp.kit." + kit.Name().toLowerCase())) {
                    kitLogger.debug("Access granted");
                } else {
                    main.sendPlayerMessage(player, WARNING_MESSAGES + "You don't have access to this kit!");
                    kitLogger.debug("Access to kit '" + kit.Name().toLowerCase() + "' denied for " + pname);
                    return;
                }

                found = true;

                Long now = new Date().getTime() / 1000L;
                Long seconds = 0L;
                Long last = (Long) history.get(kit.Name());
                if (last == null) {
                    last = 0L;
                }

                if (kit.Cooldown() != 0) {
                    seconds = last + kit.Cooldown() - now;

                    if ((player.hasPermission("kit.proxy")) && (seconds > 0L)) {
                        kitLogger.debug("Ignoring cooldown for " + pname + " (" + seconds + " seconds) because of 'kit.proxy' permission");
                        seconds = 0L;
                    }
                }
                if (seconds > 0L) {
                    main.sendPlayerMessage(player, INFO_MESSAGES + "Please try again in " + KitHelper.timeUntil(seconds) + ".");
                    kitLogger.debug("Refused kit for " + pname + ": " + kit.Name() + " (need cooldown)");
                    return;
                }

                if ((kit.Cost() > 0) && (player != null) && (main.isEconomyEnabled())) {
                    if (player.hasPermission("kit.proxy")) {
                        kitLogger.debug("Ignoring cost of " + kit.Cost() + " for " + pname + " (" + seconds + " seconds) because of 'kit.proxy' permission");
                    } else {
                        double kitCost = Double.valueOf(kit.Cost());
                        if (main.getEconomy().getBalance(player.getName()) > kitCost) {
                            main.getEconomy().withdrawPlayer(player.getName(), kitCost);
                            main.sendPlayerMessage(player, main.getEconomy().format(kitCost) + " deducted");
                            kitLogger.debug("Deducted " + main.getEconomy().format(kitCost) + " from " + pname);
                        } else {
                            main.sendPlayerMessage(player, WARNING_MESSAGES + "You can't afford that");
                            kitLogger.debug(pname + " can't afford the kit '" + kit.Name() + "'");
                            return;
                        }
                    }

                }

                kitLogger.debug("Giving a kit to " + pname + ": " + kit.Name());
                String[] elements;
                for (String item : kit.Components().keySet()) {
                    int count = (Integer) kit.Components().get(item);
                    elements = item.split(":");
                    Integer id = kit.ComponentId(item);
                    Byte data = kit.ComponentData(item);
                    Short dura = kit.ComponentDurability(item);
                    kitLogger.debug("item=" + item + " count=" + count + " dura=" + dura + " data=" + data);
                    ItemStack itemstack = new ItemStack(id.intValue(), count, dura.shortValue(), data);
                    for (String playername : players) {
                        Player p = main.getServer().getPlayer(playername);
                        if ((p == null) || ((player != null) && (!p.equals(player)) && (!main.hasPermission(p, "tmp.kit.proxy")))) {
                            continue;
                        }
                        PlayerInventory inventory = p.getInventory();
                        inventory.addItem(new ItemStack[]{itemstack});
                    }

                }

                history.put(kit.Name(), now);
                KitHelper.saveHistory(player, history);
                Integer count = (elements = players).length;
                for (Integer localInteger1 = 0; localInteger1 < count; localInteger1++) {
                    String playername = elements[localInteger1];
                    Player p = main.getServer().getPlayer(playername);
                    if (p == null) {
                        main.sendPlayerMessage(player, WARNING_MESSAGES + playername + DEFAULT_COLOR + " is not online");
                    } else if (p.equals(player)) {
                        main.sendPlayerMessage(player, "Enjoy the Kit ;)");
                    } else {
                        main.sendPlayerMessage(player, p.getName() + " has received a kit!");
                        main.sendPlayerMessage(p, pname + " gave you a kit!");
                    }

                }

                return;
            }

            if (!found) {
                main.sendPlayerMessage(player, WARNING_MESSAGES + "Please type " + COMMAND_COLOR + "'/kit'" + WARNING_MESSAGES + " for a list of valid kits or " + COMMAND_COLOR + "'/kit help'" + WARNING_MESSAGES + " for syntax help.");
                kitLogger.debug(player.getName() + " requested unknown kit '" + args[0] + "'");
            }
            return;
        }
    }


}
