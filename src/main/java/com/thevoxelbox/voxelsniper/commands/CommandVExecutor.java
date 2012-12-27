package com.thevoxelbox.voxelsniper.commands;

import java.util.Iterator;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import com.thevoxelbox.voxelsniper.SniperBlockIterator;
import com.thevoxelbox.voxelsniper.SniperUser;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.ChatPaginator;

/**
 * @author: MikeMatrix
 */
public class CommandVExecutor implements CommandExecutor
{
    private static final int MAXIMUM_SCAN_RANGE = 150;
    private final VoxelSniper plugin;
    private final HelpJSAP parser;

    public CommandVExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        this.parser = new HelpJSAP("v", "Setting the voxel and datavalue to work with by either passing them to the command or pointing at a block.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.parser.registerParameter(new UnflaggedOption("material", JSAP.STRING_PARSER, false, "Material in [material][:id] format. If either one of them is missing it will be defaulted to 0."));
        }
        catch (final JSAPException e)
        {
        }
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings)
    {
        if (commandSender instanceof Player)
        {
            final Player player = (Player) commandSender;
            final SniperUser sniperUser = this.plugin.getSniperUserManager().getUser(player);
            final JSAPResult result = this.parser.parse(strings);

            if (VoxelSniper.sendHelpOrErrorMessageToPlayer(result, player, this.parser))
            {
                return true;
            }

            final String material = result.getString("material");
            if (material != null)
            {
                final String[] splitedMaterial = material.split(":");
                Material targetMaterial = Material.AIR;
                byte targetData = (byte) 0;

                if (splitedMaterial[0] != null && !splitedMaterial[0].isEmpty())
                {
                    try
                    {
                        final int id = Integer.parseInt(splitedMaterial[0]);
                        final Material parsedMaterial = Material.getMaterial(id);
                        if (parsedMaterial != null && parsedMaterial.isBlock())
                        {
                            targetMaterial = parsedMaterial;
                        }
                    }
                    catch (final NumberFormatException ex)
                    {
                        final Material parsedMaterial = Material.matchMaterial(splitedMaterial[0]);
                        if (parsedMaterial != null && parsedMaterial.isBlock())
                        {
                            targetMaterial = parsedMaterial;
                        }
                    }
                }

                if (splitedMaterial.length > 1 && splitedMaterial[1] != null && !splitedMaterial[1].isEmpty())
                {
                    try
                    {
                        final byte parsedData = Byte.parseByte(splitedMaterial[1]);
                        targetData = parsedData;
                    }
                    catch (final NumberFormatException ex)
                    {
                        // TODO: Report to user that the value is invalid.
                    }
                }

                sniperUser.getActiveToolConfiguration().setMaterialData(new MaterialData(targetMaterial, targetData));
            }
            else
            {
                // get material you are pointing at.
                final Iterator<Block> iterator = new SniperBlockIterator(player.getLocation(), VoxelSniper.MAXIMUM_SCAN_RANGE);

                while (iterator.hasNext())
                {
                    final Block block = iterator.next();
                    if (block == null)
                    {
                        continue;
                    }

                    if (block.getType() != Material.AIR)
                    {
                        sniperUser.getActiveToolConfiguration().setMaterialData(new MaterialData(block.getType(), block.getData()));
                    }
                }
            }

            return true;
        }
        return false;
    }
}
