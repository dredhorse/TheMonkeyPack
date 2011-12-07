/*    */
package se.Izze.Lampstone;
/*    */
/*    */

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class LSBlockListener extends BlockListener
/*    */ {
    /*    */   private static Lampstone plugin;

    /*    */
/*    */
    public LSBlockListener(Lampstone instance)
/*    */ {
/* 14 */
        plugin = instance;
/*    */
    }

    /*    */
/*    */
    public void onBlockBreak(BlockBreakEvent event)
/*    */ {
/* 19 */
        Block block = event.getBlock();
/*    */
/* 21 */
        if ((Lampstone.permissionHandler != null) &&
/* 22 */       (!Lampstone.permissionHandler.has(event.getPlayer(), "Lampstone.break"))) {
/* 23 */
            if (plugin.isLampstoneBlock(block)) {
/* 24 */
                event.setCancelled(true);
/*    */
            }
/* 26 */
            return;
/*    */
        }
/*    */
/* 30 */
        if (plugin.isLampstoneBlock(block)) {
/* 31 */
            plugin.removeLampstoneBlock(block);
/*    */
/* 33 */
            System.out.println("[Lampstone] Lampstone block removed by " + event.getPlayer().getDisplayName());
/*    */
        }
/*    */
    }

    /*    */
/*    */
    public void onBlockPlace(BlockPlaceEvent event)
/*    */ {
/* 39 */
        Block block = event.getBlockPlaced();
/* 40 */
        Player player = event.getPlayer();
/* 41 */
        int[] blockTypes = plugin.getBlockTypes(player);
/*    */
/* 43 */
        if ((plugin.isPlacing(player)) && (block.getTypeId() == blockTypes[1])) {
/* 44 */
            plugin.addLampstoneBlock(block, blockTypes[0], blockTypes[1]);
/* 45 */
            player.sendMessage("Lampstone block placed.");
/*    */
        }
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.LSBlockListener
 * JD-Core Version:    0.6.0
 */
