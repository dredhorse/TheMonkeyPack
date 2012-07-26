package org.simiancage.bukkit.TheMonkeyPack.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: PlayerChatListener
 * User: DonRedhorse
 * Date: 15.12.11
 * Time: 23:17
 */

public class PlayerChatListener implements Listener {
    MainConfig mainConfig;
    MainLogger mainLogger;
    TheMonkeyPack main;


    public PlayerChatListener(TheMonkeyPack instance) {
        main = instance;
        mainConfig = main.getMainConfig();
        mainLogger = main.getMainLogger();
        mainLogger.debug("PlayerChat Listener for commands registered");
    }


    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length < 1) {
            mainLogger.debug("There is something weird with the args, they are empty");
            return;
        }
        String command = args[0].substring(1);

        if (!isValidCommand(command)) {
            mainLogger.debug("not a valid TheMonkeyPack command");
            return;
        }
        event.setCancelled(true);
        mainLogger.debug("Executing command now: ", command);
        Player player = event.getPlayer();
        String commandArgs[] = new String[args.length - 1];
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                commandArgs[i - 1] = args[i];
            }
        }
        main.getPlayerCommand(command).onPlayerCommand(player, commandArgs);
    }

    public boolean isValidCommand(String command) {
        return main.isValidPlayerCommand(command);
    }
}

