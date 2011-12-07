/*    */
package se.Izze.Lampstone;
/*    */
/*    */

import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class LoadProperties
/*    */ {
    /*    */
    public static void load(Lampstone instance)
/*    */ {
/* 12 */
        File file = new File(instance.getDataFolder(), Lampstone.propertiesFile);
/*    */
/* 15 */
        if (!file.exists()) {
/* 16 */
            System.out.println("\tConfiguration doesn't exist");
/*    */
            try
/*    */ {
/* 19 */
                file.createNewFile();
/* 20 */
                System.out.println("\tCreated new configuration file");
/*    */
            } catch (IOException e) {
/* 22 */
                e.printStackTrace();
/*    */
            }
/*    */
/* 25 */
            Configuration c = new Configuration(file);
/*    */
/* 28 */
            c.setProperty("blockDay", Integer.valueOf(20));
/* 29 */
            c.setProperty("blockNight", Integer.valueOf(89));
/*    */
/* 32 */
            if (!c.save()) {
/* 33 */
                System.out.println("Unable to save configuration file.");
/* 34 */
                instance.getPluginLoader().disablePlugin(instance);
/*    */
            }
/*    */
        }
/*    */
/* 38 */
        Configuration config = new Configuration(file);
/* 39 */
        config.load();
/*    */
/* 41 */
        int blockDay = config.getInt("blockDay", 20);
/* 42 */
        int blockNight = config.getInt("blockNight", 89);
/*    */
/* 45 */
        if (Material.getMaterial(blockDay) == null) {
/* 46 */
            System.out.println("Lampstone: Your blockDay ID is invalid. Please change it in lampstoneProperties.yml. Will fall back to default ID (20).");
/* 47 */
            blockDay = 20;
/*    */
        }
/*    */
/* 50 */
        if (Material.getMaterial(blockNight) == null) {
/* 51 */
            System.out.println("Lampstone: Your blockNight ID is invalid. Please change it in lampstoneProperties.yml. Will fall back to default ID (89).");
/* 52 */
            blockNight = 89;
/*    */
        }
/*    */
/* 55 */
        instance.blockDay = blockDay;
/* 56 */
        instance.blockNight = blockNight;
/*    */
    }
/*    */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.LoadProperties
 * JD-Core Version:    0.6.0
 */
