package com.thevoxelbox.voxelsniper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.thevoxelbox.voxelgunsmith.Brush;
import com.thevoxelbox.voxelgunsmith.OperationData;
import com.thevoxelbox.voxelgunsmith.ToolConfiguration;
import com.thevoxelbox.voxelgunsmith.User;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

/**
 * Implementation of the VoxelGunsmith User interface.
 *
 * @author MikeMatrix
 */
public class SniperUser implements User
{
    private final Player player;
    private final Map<String, ToolConfiguration> toolConfigurations = new HashMap<String, ToolConfiguration>();
    private final Map<String, Set<Brush>> toolBrushInstances = new HashMap<String, Set<Brush>>();
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

    /**
     * @param itemInHand
     *
     * @return success.
     */
    public final boolean execute(final MaterialData itemInHand)
    {
        String toolId;
        OperationData operationData;

        toolId = this.getArrowToolId(itemInHand);
        if (toolId != null)
        {
            final ToolConfiguration toolConfiguration = this.getToolConfiguration(toolId);
            final SniperBlockIterator iterator = new SniperBlockIterator(this.player.getLocation(), toolConfiguration.isRangeLimited() ? toolConfiguration.getRangeLimit() : 150);

            Block targetBlock = null;
            Block lastBlock = null;
            while (iterator.hasNext())
            {
                final Block block = iterator.next();
                if (block != null)
                {
                    lastBlock = targetBlock;
                    targetBlock = block;
                }
            }

            if (targetBlock == null || lastBlock == null)
            {
                return false;
            }

            operationData = new SniperOperationData(toolConfiguration, targetBlock, lastBlock);
            this.getBrush(toolId).executeArrow(operationData);
            return true;
        }

        toolId = this.getPowderToolId(itemInHand);
        if (toolId != null)
        {
            final ToolConfiguration toolConfiguration = this.getToolConfiguration(toolId);
            final SniperBlockIterator iterator = new SniperBlockIterator(this.player.getLocation(), toolConfiguration.isRangeLimited() ? toolConfiguration.getRangeLimit() : 150);

            Block targetBlock = null;
            Block lastBlock = null;
            while (iterator.hasNext())
            {
                final Block block = iterator.next();
                if (block != null)
                {
                    lastBlock = targetBlock;
                    targetBlock = block;
                }
            }

            if (targetBlock == null || lastBlock == null)
            {
                return false;
            }

            operationData = new SniperOperationData(toolConfiguration, targetBlock, lastBlock);
            this.getBrush(toolId).executePowder(operationData);
            return true;
        }

        return false;
    }

    @Override
    public final Brush getActiveBrush()
    {
        final MaterialData matData = new MaterialData(this.player.getItemInHand().getType(), this.player.getItemInHand().getData().getData());
        final String toolId = this.getToolId(matData);

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
        final String toolId = this.getToolId(matData);

        if (toolId != null)
        {
            return this.getToolConfiguration(toolId);
        }
        return null;
    }

    /**
     * ToolId for the given item in hand if item in hand is a tool of type arrow.
     *
     * @param itemInHand
     *
     * @return Tool id or null if item in hand is not a arrow tool.
     */
    public final String getArrowToolId(final MaterialData itemInHand)
    {
        return this.toolMappingArrow.get(itemInHand);
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

    /**
     * ToolId for the given item in hand if item in hand is a tool of type powder.
     *
     * @param itemInHand
     *
     * @return Tool id or null if item in hand is not a powder tool.
     */
    public final String getPowderToolId(final MaterialData itemInHand)
    {
        return this.toolMappingPowder.get(itemInHand);
    }

    @Override
    public final ToolConfiguration getToolConfiguration(final String toolId)
    {
        return this.toolConfigurations.get(toolId);
    }

    @Override
    public final String getToolId(final MaterialData itemInHand)
    {
        String returnValue;
        returnValue = this.toolMappingArrow.get(itemInHand);
        if (returnValue != null)
        {
            return returnValue;
        }
        return this.toolMappingPowder.get(itemInHand);
    }

    @Override
    public final void sendMessage(final String message)
    {
        this.player.sendMessage(message);
    }

    /**
     * Sets the current brush for the given toolId.
     *
     * @param toolId
     */
    public final void setBrush(final String toolId, final Class<? extends Brush> brushClass)
    {
        Brush currentBrush = this.toolBrush.get(toolId);
        Set<Brush> brushInstances = this.toolBrushInstances.get(toolId);

        if (brushInstances == null)
        {
            brushInstances = new HashSet<Brush>();
            this.toolBrushInstances.put(toolId, brushInstances);
        }

        if (currentBrush != null)
        {
            brushInstances.add(currentBrush);
        }

        if (brushClass == null)
        {
            this.toolBrush.put(toolId, null);
            return;
        }

        for (Brush brush : brushInstances)
        {
            if (brush.getClass().equals(brushClass))
            {
                this.toolBrush.put(toolId, brush);
                return;
            }
        }

        try
        {
            Brush brush = brushClass.newInstance();
            this.toolBrush.put(toolId, brush);
            brushInstances.add(brush);
        }
        catch (final InstantiationException e)
        {
        }
        catch (final IllegalAccessException e)
        {
        }
    }
}
