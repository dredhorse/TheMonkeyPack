/*    */
package se.Izze.Lampstone;
/*    */
/*    */

import org.bukkit.entity.Player;

/*    */
/*    */ public class PlayerAndBlockTypes
/*    */ {
    /*    */   private Player player;
    /*    */   private int blockDay;
    /*    */   private int blockNight;

    /*    */
/*    */
    public PlayerAndBlockTypes(Player player, int blockDay, int blockNight)
/*    */ {
/* 11 */
        this.player = player;
/* 12 */
        this.blockDay = blockDay;
/* 13 */
        this.blockNight = blockNight;
/*    */
    }

    /*    */
/*    */
    public Player getPlayer() {
/* 17 */
        return this.player;
/*    */
    }

    /*    */
/*    */
    public void setPlayer(Player player) {
/* 21 */
        this.player = player;
/*    */
    }

    /*    */
/*    */
    public int getBlockDay() {
/* 25 */
        return this.blockDay;
/*    */
    }

    /*    */
/*    */
    public void setBlockDay(int blockDay) {
/* 29 */
        this.blockDay = blockDay;
/*    */
    }

    /*    */
/*    */
    public int getBlockNight() {
/* 33 */
        return this.blockNight;
/*    */
    }

    /*    */
/*    */
    public void setBlockNight(int blockNight) {
/* 37 */
        this.blockNight = blockNight;
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.PlayerAndBlockTypes
 * JD-Core Version:    0.6.0
 */
