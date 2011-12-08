package org.simiancage.bukkit.TheMonkeyPack.interfaces;

/**
 * PluginName: ${plugin}
 * Class: PermissionsInterface
 * User: DonRedhorse
 * Date: 08.12.11
 * Time: 20:09
 */

import org.bukkit.command.CommandSender;

// contains code from https://github.com/PneumatiCraft/CommandHandler

public interface PermissionsInterface {
    public boolean hasPermission(CommandSender sender, String node, boolean isOpRequired);
}
