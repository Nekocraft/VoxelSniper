package com.thevoxelbox.voxelsniper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Voxel
 */
public class VoxelSniperListener implements Listener
{
    private final SniperUserManager sniperUserManager;

    /**
     * @param sniperUserManager
     */
    public VoxelSniperListener(final SniperUserManager sniperUserManager)
    {
        this.sniperUserManager = sniperUserManager;
        MetricsManager.setSnipeCounterInitTimeStamp(System.currentTimeMillis());
    }

    /**
     * @param event
     */
    @EventHandler
    public final void onPlayerInteract(final PlayerInteractEvent event)
    {
        Player executingPlayer = event.getPlayer();

        if (executingPlayer != null)
        {
            SniperUser sniperUser = this.sniperUserManager.getUser(executingPlayer);

            event.setCancelled(sniperUser.execute(executingPlayer.getItemInHand().getData()));
        }
    }

}
