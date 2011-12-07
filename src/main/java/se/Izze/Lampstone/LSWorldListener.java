/*    */
package se.Izze.Lampstone;
/*    */
/*    */

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

/*    */
/*    */
/*    */ public class LSWorldListener extends WorldListener
/*    */ {
    /*    */   private static Lampstone plugin;

    /*    */
/*    */
    public LSWorldListener(Lampstone instance)
/*    */ {
/* 10 */
        plugin = instance;
/*    */
    }

    /*    */
/*    */
    public void onWorldLoad(WorldLoadEvent event) {
/* 14 */
        plugin.addWorld(event.getWorld());
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.LSWorldListener
 * JD-Core Version:    0.6.0
 */
