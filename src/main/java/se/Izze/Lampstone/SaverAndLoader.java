/*     */
package se.Izze.Lampstone;
/*     */
/*     */

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class SaverAndLoader
/*     */ {
    /*     */   private static Lampstone plugin;
    /*     */   private static Configuration file;

    /*     */
/*     */
    public SaverAndLoader(Lampstone instance)
/*     */ {
/*  18 */
        plugin = instance;
/*     */
/*  20 */
        File p = plugin.getDataFolder() != null ? plugin.getDataFolder() : new File("plugins/Lampstone");
/*  21 */
        File f = new File(p, Lampstone.locationsFile);
/*     */
/*  24 */
        if (!p.exists()) {
/*  25 */
            p.mkdirs();
/*     */
        }
/*     */
/*  29 */
        if (!f.exists()) {
/*     */
            try {
/*  31 */
                f.createNewFile();
/*     */
            } catch (IOException e) {
/*  33 */
                e.printStackTrace();
/*     */
            }
/*     */
/*  37 */
            file = new Configuration(f);
/*  38 */
            file.setProperty("locations", null);
/*  39 */
            file.save();
/*     */
        } else {
/*  41 */
            file = new Configuration(f);
/*     */
        }
/*     */
    }

    /*     */
/*     */
    public void saveBlock(String uid, LSLocation block)
/*     */ {
/*  47 */
        file.load();
/*     */
/*  49 */
        ArrayList location = new ArrayList();
/*  50 */
        double x = block.getX();
/*  51 */
        double y = block.getY();
/*  52 */
        double z = block.getZ();
/*  53 */
        String w = block.getWorldName();
/*  54 */
        int bd = block.getBlockDay();
/*  55 */
        int bn = block.getBlockNight();
/*     */
/*  57 */
        location.add(Double.valueOf(x));
/*  58 */
        location.add(Double.valueOf(y));
/*  59 */
        location.add(Double.valueOf(z));
/*  60 */
        location.add(w);
/*  61 */
        location.add(Integer.valueOf(bd));
/*  62 */
        location.add(Integer.valueOf(bn));
/*     */
/*  64 */
        file.setProperty("locations." + uid, location);
/*     */
/*  66 */
        file.save();
/*     */
    }

    /*     */
/*     */
    public void removeBlock(String uid)
/*     */ {
/*  71 */
        file.load();
/*     */
/*  73 */
        file.removeProperty("locations." + uid);
/*     */
/*  75 */
        file.save();
/*     */
    }

    /*     */
/*     */
    public HashMap<String, LSLocation> loadBlocks()
/*     */ {
/*  80 */
        file.load();
/*     */
/*  82 */
        HashMap blockLocations = new HashMap();
/*     */
/*  84 */
        List locations = file.getKeys("locations");
/*     */
/*  86 */
        long loaded = 0L;
/*     */
/*  88 */
        if (locations != null)
/*     */ {
/*  93 */
            for (int i = 0; i < locations.size(); i++) {
/*  94 */
                List location = file.getList("locations." + (String) locations.get(i));
/*     */
/*  96 */
                int x = (int) Double.parseDouble(location.get(0).toString());
/*  97 */
                int y = (int) Double.parseDouble(location.get(1).toString());
/*  98 */
                int z = (int) Double.parseDouble(location.get(2).toString());
/*  99 */
                String w = location.get(3).toString();
/*     */
/* 101 */
                int bd = -1;
/* 102 */
                int bn = -1;
/*     */
                try
/*     */ {
/* 105 */
                    bd = Integer.parseInt(location.get(4).toString());
/* 106 */
                    bn = Integer.parseInt(location.get(5).toString());
/*     */
                }
/*     */ catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
/*     */ {
/*     */
                }
/* 111 */
                if (bd == -1) {
/* 112 */
                    bd = plugin.blockDay;
/*     */
                }
/* 114 */
                if (bn == -1) {
/* 115 */
                    bn = plugin.blockNight;
/*     */
                }
/*     */
/* 131 */
                blockLocations.put((String) locations.get(i), new LSLocation(x, y, z, w, bd, bn));
/*     */
/* 133 */
                loaded += 1L;
/*     */
            }
/*     */
        }
/*     */
/* 137 */
        System.out.println("[Lampstone] " + loaded + " lampstones loaded.");
/*     */
/* 139 */
        return blockLocations;
/*     */
    }
/*     */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.SaverAndLoader
 * JD-Core Version:    0.6.0
 */
