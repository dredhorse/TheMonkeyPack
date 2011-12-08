package org.simiancage.bukkit.TheMonkeyPack.helpers;

import org.bukkit.command.CommandSender;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.commands.Commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PluginName: TheMonkeyPack
 * Class: CommandHelper
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 20:01
 */

// contains code from https://github.com/PneumatiCraft/CommandHandler

public class CommandHelper {
    protected TheMonkeyPack main;
    protected List<Commands> allCommands;

    public CommandHelper(TheMonkeyPack plugin) {
        this.main = plugin;
        this.allCommands = new ArrayList<Commands>();
    }

    public boolean locateAndRunCommand(CommandSender sender, List<String> args) {
        ArrayList<String> parsedArgs = parseAllQuotedStrings(args);
        String key = null;

        Iterator<Commands> iterator = this.allCommands.iterator();
        Commands foundCommand = null;
        while (iterator.hasNext() && key == null) {
            foundCommand = iterator.next();
            key = foundCommand.getKey(parsedArgs);
            if (key != null) {
                // This method, removeKeyArgs mutates parsedArgs
                foundCommand.removeKeyArgs(parsedArgs, key);
                checkAndRunCommand(sender, parsedArgs, foundCommand);
            }
        }
        return true;
    }

    public void registerCommand(Commands command) {
        this.allCommands.add(command);
    }

    /**
     * Combines all quoted strings
     *
     * @param args
     *
     * @return
     */
    private ArrayList<String> parseAllQuotedStrings(List<String> args) {
        // TODO: Allow '
        // TODO: make less awkward, less magical
        ArrayList<String> newArgs = new ArrayList<String>();
        // Iterate through all command params:
        // we could have: "Fish dog" the man bear pig "lives today" and maybe "even tomorrow" or "the" next day
        int start = -1;
        for (int i = 0; i < args.size(); i++) {

            // If we aren't looking for an end quote, and the first part of a string is a quote
            if (start == -1 && args.get(i).substring(0, 1).equals("\"")) {
                start = i;
            }
            // Have to keep this separate for one word quoted strings like: "fish"
            if (start != -1 && args.get(i).substring(args.get(i).length() - 1, args.get(i).length()).equals("\"")) {
                // Now we've found the second part of a string, let's parse the quoted one out
                // Make sure it's i+1, we still want I included
                newArgs.add(parseQuotedString(args, start, i + 1));
                // Reset the start to look for more!
                start = -1;
            } else if (start == -1) {
                // This is a word that is NOT enclosed in any quotes, so just add it
                newArgs.add(args.get(i));
            }
        }
        // If the string was ended but had an open quote...
        if (start != -1) {
            // ... then we want to close that quote and make that one arg.
            newArgs.add(parseQuotedString(args, start, args.size()));
        }

        return newArgs;
    }


    /**
     * Returns the given flag value
     *
     * @param flag A param flag, like -s or -g
     * @param args All arguments to search through
     *
     * @return A string or null
     */
    public static String getFlag(String flag, List<String> args) {
        int i = 0;
        try {
            for (String s : args) {
                if (s.equalsIgnoreCase(flag)) {
                    return args.get(i + 1);
                }
                i++;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Takes a string array and returns a combined string, excluding the stop position, including the start
     *
     * @param args
     * @param start
     * @param stop
     *
     * @return
     */
    private String parseQuotedString(List<String> args, int start, int stop) {
        String returnVal = args.get(start);
        for (int i = start + 1; i < stop; i++) {
            returnVal += " " + args.get(i);
        }
        return returnVal.replace("\"", "");
    }

    private void checkAndRunCommand(CommandSender sender, List<String> parsedArgs, Commands foundCommand) {
        if (foundCommand.checkArgLength(parsedArgs)) {
            // Or used so if someone doesn't implement permissions interface, all commands will run.
            if (main.hasPermission(sender, foundCommand.getPermission())) {
                foundCommand.runCommand(sender, parsedArgs);
            } else {
                sender.sendMessage("You do not have the required permission (" + foundCommand.getPermission() + ").");
            }
        } else {
            // TODO make me pretty
            sender.sendMessage(foundCommand.commandUsage);
        }
    }
}




