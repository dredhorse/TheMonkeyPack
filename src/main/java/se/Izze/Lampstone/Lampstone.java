/*     */
package se.Izze.Lampstone;
/*     */
/*     */

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class Lampstone extends JavaPlugin
/*     */ {
    /*  22 */   private final LSBlockListener blockListener = new LSBlockListener(this);
    /*     */
/*  24 */   private LSTimeThread thread = null;
    /*     */
/*  26 */   private HashMap<String, LSLocation> lampstoneBlocks = new HashMap();
    /*  27 */   private ArrayList<PlayerAndBlockTypes> playersPlacing = new ArrayList();
    /*  28 */   private ArrayList<World> worldsLoaded = new ArrayList();
    /*     */
/*  30 */   private SaverAndLoader sAndL = new SaverAndLoader(this);
    /*  31 */   public static String locationsFile = "lampstoneLocations.yml";
    /*  32 */   public static String propertiesFile = "lampstoneProperties.yml";
    /*  33 */   public int blockDay = 20;
    /*  34 */   public int blockNight = 89;
    /*     */   public static PermissionHandler permissionHandler;

    /*     */
/*     */
    public void onDisable()
/*     */ {
/*     */
    }

    /*     */
/*     */
    public void onEnable()
/*     */ {
/*  46 */
        PluginDescriptionFile pdfFile = getDescription();
/*  47 */
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled.");
/*     */
/*  49 */
        PluginManager pm = getServer().getPluginManager();
/*  50 */
        pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
/*  51 */
        pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
/*     */
/*  53 */
        getCommand("lampstone").setExecutor(new LSCommandListener(this));
/*     */
/*  56 */
        setupPermissions();
/*     */
/*  58 */
        this.worldsLoaded = ((ArrayList) getServer().getWorlds());
/*     */
/*  60 */
        LoadProperties.load(this);
/*     */
/*  62 */
        this.thread = new LSTimeThread(this);
/*  63 */
        BukkitScheduler scheduler = getServer().getScheduler();
/*  64 */
        scheduler.scheduleSyncRepeatingTask(this, this.thread, 0L, 40L);
/*     */
/*  66 */
        this.lampstoneBlocks = this.sAndL.loadBlocks();
/*     */
    }

    /*     */
/*     */
    public boolean isPlacing(Player player)
/*     */ {
/*  72 */
        for (PlayerAndBlockTypes o : this.playersPlacing) {
/*  73 */
            if (o.getPlayer().equals(player)) {
/*  74 */
                return true;
/*     */
            }
/*     */
        }
/*  77 */
        return false;
/*     */
    }

    /*     */
/*     */
    public int[] getBlockTypes(Player player) {
/*  81 */
        int[] blockTypes = {-1, -1};
/*     */
/*  83 */
        for (PlayerAndBlockTypes o : this.playersPlacing) {
/*  84 */
            if (o.getPlayer().equals(player)) {
/*  85 */
                blockTypes[0] = o.getBlockDay();
/*  86 */
                blockTypes[1] = o.getBlockNight();
/*     */
            }
/*     */
/*     */
        }
/*     */
/*  91 */
        return blockTypes;
/*     */
    }

    /*     */
/*     */
    public void addToPlacingList(Player player, int blockDay, int blockNight)
/*     */ {
/*  96 */
        if (!isPlacing(player))
/*  97 */ {
            this.playersPlacing.add(new PlayerAndBlockTypes(player, blockDay, blockNight));
        }
/*     */
    }

    /*     */
/*     */
    public void removeFromPlacingList(Player player)
/*     */ {
/* 103 */
        for (PlayerAndBlockTypes o : this.playersPlacing)
/* 104 */ {
            if (o.getPlayer().equals(player)) {
/* 105 */
                this.playersPlacing.remove(o);
/* 106 */
                break;
/*     */
            }
        }
/*     */
    }

    /*     */
/*     */
    public boolean isLampstoneBlock(Block block)
/*     */ {
/* 115 */
        LSLocation l = new LSLocation(block.getX(), block.getY(), block.getZ(), block.getWorld().getName(), 0, 0);
/*     */
/* 119 */
        for (LSLocation l1 : this.lampstoneBlocks.values()) {
/* 120 */
            if ((l1.getX() == l.getX()) && (l1.getY() == l.getY()) && (l1.getZ() == l.getZ()) && (l1.getWorldName().equals(l.getWorldName()))) {
/* 121 */
                return true;
/*     */
            }
/*     */
        }
/*     */
/* 125 */
        return false;
/*     */
    }

    /*     */
/*     */
    public void addLampstoneBlock(Block block, int blockDay, int blockNight)
/*     */ {
/* 130 */
        if (!this.lampstoneBlocks.containsValue(new LSLocation(block.getX(), block.getY(), block.getZ(), block.getWorld().getName(), blockDay, blockNight))) {
/* 131 */
            String uid = String.valueOf(block.hashCode() + System.currentTimeMillis());
/* 132 */
            this.lampstoneBlocks.put(uid, new LSLocation(block.getX(), block.getY(), block.getZ(), block.getWorld().getName(), blockDay, blockNight));
/* 133 */
            this.sAndL.saveBlock(uid, new LSLocation(block.getX(), block.getY(), block.getZ(), block.getWorld().getName(), blockDay, blockNight));
/*     */
        }
/*     */
    }

    /*     */
/*     */
    public void removeLampstoneBlock(Block block)
/*     */ {
/* 139 */
        String uid = null;
/* 140 */
        LSLocation l = new LSLocation(block.getX(), block.getY(), block.getZ(), block.getWorld().getName(), 0, 0);
/*     */
/* 144 */
        for (String s : this.lampstoneBlocks.keySet()) {
/* 145 */
            LSLocation l1 = (LSLocation) this.lampstoneBlocks.get(s);
/*     */
/* 147 */
            if ((l1.getX() == l.getX()) && (l1.getY() == l.getY()) && (l1.getZ() == l.getZ()) && (l1.getWorldName().equals(l.getWorldName()))) {
/* 148 */
                uid = s;
/* 149 */
                break;
/*     */
            }
/*     */
/*     */
        }
/*     */
/* 154 */
        if (uid == null) {
/* 155 */
            System.out.println("ÅÅH");
/*     */
        }
/*     */
/* 158 */
        this.lampstoneBlocks.remove(uid);
/* 159 */
        this.sAndL.removeBlock(uid);
/*     */
    }

    /*     */
/*     */
    public HashMap<String, LSLocation> getLampstones() {
/* 163 */
        return this.lampstoneBlocks;
/*     */
    }

    /*     */
/*     */
    public Boolean worldIsLoaded(String worldName)
/*     */ {
/* 168 */
        for (World world : this.worldsLoaded) {
/* 169 */
            if (world.getName().equalsIgnoreCase(worldName)) {
/* 170 */
                return Boolean.valueOf(true);
/*     */
            }
/*     */
/*     */
        }
/*     */
/* 175 */
        return Boolean.valueOf(false);
/*     */
    }

    /*     */
/*     */
    public void addWorld(World world) {
/* 179 */
        this.worldsLoaded.add(world);
/*     */
    }

    /*     */
/*     */
    public void removeWorld(World world) {
/* 183 */
        if (this.worldsLoaded.contains(world))
/* 184 */ {
            this.worldsLoaded.remove(world);
        }
/*     */
    }

    /*     */
/*     */
    private void setupPermissions()
/*     */ {
/* 190 */
        Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");
/*     */
/* 192 */
        if (permissionHandler == null)
/* 193 */ {
            if (permissionsPlugin != null)
/* 194 */ {
                permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            }
/*     */
            else
/* 196 */ {
                System.out.println("[Lampstone] Permission system not detected, everybody can create and break lampstones.");
            }
        }
/*     */
    }
/*     */
}

/* Location:           C:\Users\mbreiden\Downloads\bukkit\Lampstone.jar
 * Qualified Name:     se.Izze.Lampstone.Lampstone
 * JD-Core Version:    0.6.0
 */
