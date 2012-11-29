package com.thevoxelbox.voxelsniper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import com.thevoxelbox.voxelgunsmith.Brush;
import com.thevoxelbox.voxelgunsmith.ToolConfiguration;
import com.thevoxelbox.voxelgunsmith.User;

/**
 * Implementation of the VoxelGunsmith User interface.
 * 
 * @author MikeMatrix
 * 
 */
public class SniperUser implements User
{
    private final Player player;
    private final Map<String, ToolConfiguration> toolConfigurations = new HashMap<String, ToolConfiguration>();
    private final Map<String, List<Brush>> toolBrushInstances = new HashMap<String, List<Brush>>();
    private final Map<String, Brush> toolBrush = new HashMap<String, Brush>();
    private final Map<MaterialData, String> toolMappingArrow = new HashMap<MaterialData, String>();
    private final Map<MaterialData, String> toolMappingPowder = new HashMap<MaterialData, String>();

    /**
     * @param player
     */
    public SniperUser(final Player player)
    {
        this.player = player;
    }

    @Override
    public final Brush getActiveBrush()
    {
        final MaterialData matData = new MaterialData(this.player.getItemInHand().getType(), this.player.getItemInHand().getData().getData());
        String toolId = this.getToolId(matData);

        if (toolId != null)
        {
            return this.getBrush(toolId);
        }
        return null;
    }

    @Override
    public final ToolConfiguration getActiveToolConfiguration()
    {
        final MaterialData matData = new MaterialData(this.player.getItemInHand().getType(), this.player.getItemInHand().getData().getData());
        String toolId = this.getToolId(matData);

        if (toolId != null)
        {
            return this.getToolConfiguration(toolId);
        }
        return null;
    }

    @Override
    public final Brush getBrush(final String toolId)
    {
        return this.toolBrush.get(toolId);
    }

    @Override
    public final Player getPlayer()
    {
        return this.player;
    }

    @Override
    public final ToolConfiguration getToolConfiguration(final String toolId)
    {
        return this.toolConfigurations.get(toolId);
    }

    @Override
    public final void sendMessage(final String message)
    {
        this.player.sendMessage(message);
    }

    @Override
    public final String getToolId(final MaterialData itemInHand)
    {
        String returnValue = null;
        returnValue = this.toolMappingArrow.get(itemInHand);
        if (returnValue != null)
        {
            return returnValue;
        }
        return this.toolMappingPowder.get(itemInHand);
    }
}
