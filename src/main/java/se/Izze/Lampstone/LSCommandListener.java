/*    */
package se.Izze.Lampstone;
/*    */
/*    */

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class LSCommandListener
/*    */ implements CommandExecutor
/*    */ {
    /*    */   private static Lampstone plugin;

    /*    */
/*    */
    public LSCommandListener(Lampstone instance)
/*    */ {
/* 14 */
        plugin = instance;
/*    */
    }

    /*    */
/*    */
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
/*    */ {
/* 19 */
        if ((command.getName().equalsIgnoreCase("lampstone")) &&
/* 20 */       ((sender instanceof Player))) {
/* 21 */
            Player player = (Player) sender;
/*    */
/* 23 */
            if ((Lampstone.permissionHandler != null) &&
/* 24 */         (!Lampstone.permissionHandler.has(player, "Lampstone.create"))) {
/* 25 */
                return false;
/*    */
            }
/*    */
/* 29 */
            if (plugin.isPlacing(player)) {
/* 30 */
                plugin.removeFromPlacingList(player);
/* 31 */
                player.sendMessage("Lampstone mode disabled");
/*    */
            } else {
/* 33 */
                int blockDay = 0;
/* 34 */
                int blockNight = 0;
/*    */
/* 36 */
                if (args.length > 0) {
/* 37 */
                    blockDay = Integer.parseInt(args[0]);
/*    */
/* 39 */
                    if (args.length > 1)
/* 40 */ {
                        blockNight = Integer.parseInt(args[1]);
                    }
/*    */
                    else
/* 42 */ {
                        blockNight = plugin.blockNight;
                    }
/*    */
                }
/*    */
                else {
/* 45 */
                    blockDay = plugin.blockDay;
/* 46 */
                    blockNight = plugin.blockNight;
/*    */
                }
/*    */
/* 50 */
                plugin.addToPlacingList(player, blockDay, blockNight);
/* 51 */
                player.sendMessage("Lampstone mode enabled, using IDs " + blockDay + " and " + blockNight + ".");
/* 52 */
                player.sendMessage("Place your lampstones now.");
/*    */
            }
/*    */
/* 55 */
            return true;
/*    */
        }
/*    */
/* 59 */
        return false;
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.LSCommandListener
 * JD-Core Version:    0.6.0
 */
