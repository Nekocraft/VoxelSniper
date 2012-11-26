package com.thevoxelbox.voxelsniper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.thevoxelbox.voxelgunsmith.VoxelGunsmith;

/**
 * @author Voxel
 * 
 */
public class VoxelSniperListener implements Listener
{
	private SniperUserManager sniperUserManager;
	
    /**
     * @param sniperUserManager 
     * 
     */
    public VoxelSniperListener(SniperUserManager sniperUserManager)
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
    	
    	if(executingPlayer != null) 
    	{
    		SniperUser sniperUser = this.sniperUserManager.getUser(executingPlayer);
    		
    		// TODO: Execute snipe over sniperUser.
    		// TODO: Cancel event if sniper was successfull.
    	}
    }

}
