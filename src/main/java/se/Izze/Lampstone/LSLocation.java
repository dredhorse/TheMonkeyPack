/*    */
package se.Izze.Lampstone;

/*    */
/*    */ public class LSLocation
/*    */ {
    /*    */   private int x;
    /*    */   private int y;
    /*    */   private int z;
    /*    */   private int blockDay;
    /*    */   private int blockNight;
    /*    */   private String worldName;

    /*    */
/*    */
    public LSLocation(int x, int y, int z, String worldName, int blockDay, int blockNight)
/*    */ {
/*  8 */
        this.x = x;
/*  9 */
        this.y = y;
/* 10 */
        this.z = z;
/* 11 */
        this.worldName = worldName;
/* 12 */
        this.blockDay = blockDay;
/* 13 */
        this.blockNight = blockNight;
/*    */
    }

    /*    */
/*    */
    public int getBlockDay() {
/* 17 */
        return this.blockDay;
/*    */
    }

    /*    */
/*    */
    public void setBlockDay(int blockDay) {
/* 21 */
        this.blockDay = blockDay;
/*    */
    }

    /*    */
/*    */
    public int getBlockNight() {
/* 25 */
        return this.blockNight;
/*    */
    }

    /*    */
/*    */
    public void setBlockNight(int blockNight) {
/* 29 */
        this.blockNight = blockNight;
/*    */
    }

    /*    */
/*    */
    public int getX() {
/* 33 */
        return this.x;
/*    */
    }

    /*    */
/*    */
    public int getY() {
/* 37 */
        return this.y;
/*    */
    }

    /*    */
/*    */
    public int getZ() {
/* 41 */
        return this.z;
/*    */
    }

    /*    */
/*    */
    public String getWorldName() {
/* 45 */
        return this.worldName;
/*    */
    }

    /*    */
/*    */
    public void setX(int x) {
/* 49 */
        this.x = x;
/*    */
    }

    /*    */
/*    */
    public void setY(int y) {
/* 53 */
        this.y = y;
/*    */
    }

    /*    */
/*    */
    public void setZ(int z) {
/* 57 */
        this.z = z;
/*    */
    }

    /*    */
/*    */
    public void setWorldName(String worldName) {
/* 61 */
        this.worldName = worldName;
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.LSLocation
 * JD-Core Version:    0.6.0
 */
