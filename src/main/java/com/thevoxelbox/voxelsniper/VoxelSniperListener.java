package com.thevoxelbox.voxelsniper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Voxel
 * 
 */
public class VoxelSniperListener implements Listener
{
    /**
     * 
     */
    public VoxelSniperListener()
    {
        MetricsManager.setSnipeCounterInitTimeStamp(System.currentTimeMillis());
    }

    /**
     * @param event
     */
    @EventHandler
    public final void onPlayerInteract(final PlayerInteractEvent event)
    {
        if (event.isBlockInHand())
        {
            return;
        }
        final Player _player = event.getPlayer();

        try
        {
            final Sniper _vs = VoxelSniper.getSniperPermissionHelper().getSniperInstance(_player);
            if (_vs == null)
            {
                return;
            }
            else if (_vs.snipe(_player, event.getAction(), event.getMaterial(), event.getClickedBlock(), event.getBlockFace()))
            {
                MetricsManager.increaseSnipeCounter();
                event.setCancelled(true);
            }
        }
        catch (final Exception _ex)
        {
            return;
        }
    }

}
