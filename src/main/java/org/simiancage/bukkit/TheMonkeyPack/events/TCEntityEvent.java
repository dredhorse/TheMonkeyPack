package org.simiancage.bukkit.TheMonkeyPack.events;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.configs.TNTControlConfig;
import org.simiancage.bukkit.TheMonkeyPack.helpers.TNTControlHelper;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;
import org.simiancage.bukkit.TheMonkeyPack.loging.TNTControlLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: TCBlockEvent
 * User: DonRedhorse
 * Date: 21.12.11
 * Time: 21:36
 */


// contains code from http://forums.bukkit.org/threads/8145/

public class TCEntityEvent {

    protected TheMonkeyPack main;
    private MainConfig mainConfig;
    protected MainLogger mainLogger;
    protected TNTControlConfig tntControlConfig;
    protected TNTControlLogger tntControlLogger;
    static TCEntityEvent instance;
    protected TNTControlHelper tntControlHelper;


    private TCEntityEvent(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = main.getMainLogger();
        mainConfig = main.getMainConfig();
        tntControlConfig = TNTControlConfig.getInstance();
        tntControlLogger = tntControlConfig.getTNTControlLogger();
        tntControlHelper = tntControlConfig.getTNTControlHelper();
    }

    public static TCEntityEvent getInstance(TheMonkeyPack plugin) {
        if (instance == null) {
            instance = new TCEntityEvent(plugin);
        }
        return instance;
    }

    public void tntControlExplosionPrime(ExplosionPrimeEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof TNTPrimed) {

                event.setRadius(tntControlConfig.getTntBlastRadius());
                event.setFire(tntControlConfig.isTntBlastCauseFire());
            }
        }
    }


    public void tntControlEntityExplode(EntityExplodeEvent event) {

        if (!event.isCancelled()) {
            if (event.getEntity() instanceof TNTPrimed) {
                event.setYield(tntControlConfig.getTntBlastYield());
            }
        }
    }


}

