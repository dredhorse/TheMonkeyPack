/*    */
package se.Izze.Lampstone;
/*    */
/*    */

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class LSTimeThread
/*    */ implements Runnable
/*    */ {
    /*    */   private static Lampstone plugin;

    /*    */
/*    */
    public LSTimeThread(Lampstone instance)
/*    */ {
/* 17 */
        plugin = instance;
/*    */
    }

    /*    */
/*    */
    public void run()
/*    */ {
/* 22 */
        HashMap lampstones = plugin.getLampstones();
/*    */
/* 25 */
        for (LSLocation blockLocation : lampstones.values())
/*    */ {
/* 27 */
            if (plugin.getServer().getWorld(blockLocation.getWorldName()) == null) {
/*    */
                continue;
/*    */
            }
/* 30 */
            World world = plugin.getServer().getWorld(blockLocation.getWorldName());
/* 31 */
            Block block = world.getBlockAt(blockLocation.getX(),
/* 32 */         blockLocation.getY(),
/* 33 */         blockLocation.getZ());
/* 34 */
            int blockDay = blockLocation.getBlockDay();
/* 35 */
            int blockNight = blockLocation.getBlockNight();
/*    */
/* 37 */
            long time = world.getTime();
/*    */
/* 40 */
            if (!world.isChunkLoaded(block.getChunk()))
/*    */ {
                continue;
            }
/* 42 */
            if ((time > 13000L) && (time < 23400L)) {
/* 43 */
                if (!block.getType().equals(Material.getMaterial(blockNight)))
/* 44 */ {
                    block.setType(Material.getMaterial(blockNight));
                }
/*    */
            }
/*    */
            else {
/* 47 */
                if (((time <= 23400L) || (time >= 24000L)) && ((time <= 0L) || (time >= 13000L) ||
/* 48 */           (block.getType().equals(Material.getMaterial(blockDay))))) {
                    continue;
                }
/* 49 */
                block.setType(Material.getMaterial(blockDay));
/*    */
            }
/*    */
        }
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.LSTimeThread
 * JD-Core Version:    0.6.0
 */
