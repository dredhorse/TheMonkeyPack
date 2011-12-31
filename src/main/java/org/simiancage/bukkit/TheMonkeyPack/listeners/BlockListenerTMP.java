package org.simiancage.bukkit.TheMonkeyPack.listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.simiancage.bukkit.TheMonkeyPack.TheMonkeyPack;
import org.simiancage.bukkit.TheMonkeyPack.configs.MainConfig;
import org.simiancage.bukkit.TheMonkeyPack.events.GPBlockEvent;
import org.simiancage.bukkit.TheMonkeyPack.events.TCBlockEvent;
import org.simiancage.bukkit.TheMonkeyPack.interfaces.Listeners;
import org.simiancage.bukkit.TheMonkeyPack.loging.MainLogger;

/**
 * PluginName: TheMonkeyPack
 * Class: BlockListenerTMP
 * User: DonRedhorse
 * Date: 10.12.11
 * Time: 14:46
 */

public class BlockListenerTMP extends BlockListener implements Listeners {
    private static TheMonkeyPack main;
    private MainLogger mainLogger;
    private MainConfig mainConfig;
    private GPBlockEvent gpBlockEvent;
    private TCBlockEvent tcBlockEvent;


    public BlockListenerTMP(TheMonkeyPack plugin) {
        main = plugin;
        mainLogger = plugin.getMainLogger();
        mainConfig = plugin.getMainConfig();
        mainLogger.debug("BlockListenerTMP active");

    }

    @Override
    public void onEnable(TheMonkeyPack main) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDisable() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onBlockBreak(BlockBreakEvent event) {
        if (mainConfig.isGetPayedActive()) {
            gpBlockEvent = GPBlockEvent.getInstance(main);
            gpBlockEvent.getPayedBlockBreakEvent(event);
        }
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        if (mainConfig.isGetPayedActive()) {
            gpBlockEvent = GPBlockEvent.getInstance(main);
            gpBlockEvent.getPayedBlockPlaceEvent(event);
        }
        if (mainConfig.isEnableTNTControl()) {
            tcBlockEvent = TCBlockEvent.getInstance(main);
            tcBlockEvent.tntControlBlockPlace(event);
        }
    }

    public void onBlockDamage(BlockDamageEvent event) {
        if (mainConfig.isEnableTNTControl()) {
            tcBlockEvent = TCBlockEvent.getInstance(main);
            tcBlockEvent.tntControlBlockDamage(event);
        }
    }
}
