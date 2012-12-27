package com.thevoxelbox.voxelsniper.commands;

import java.util.HashSet;
import java.util.Set;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.ChatPaginator;

/**
 * @author MikeMatrix
 */
public class CommandVRExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;
    private final HelpJSAP parser;

    public CommandVRExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        this.parser = new HelpJSAP("vr", "List of to be filtered Materials", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            final UnflaggedOption materialOption = new UnflaggedOption("material", JSAP.STRING_PARSER, false, "Materials in [material][:id] format. If either one of them is missing it will be defaulted to 0.");
            materialOption.setList(true);
            this.parser.registerParameter(materialOption);
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
            final JSAPResult result = this.parser.parse(strings);

            if (VoxelSniper.sendHelpOrErrorMessageToPlayer(result, player, this.parser))
            {
                return true;
            }

            // TODO: Execute
            String[] materials = result.getStringArray("material");
            Set<MaterialData> newFilter = new HashSet<MaterialData>();

            for (String material : materials)
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
                        targetData = Byte.parseByte(splitedMaterial[1]);
                    }
                    catch (final NumberFormatException ex)
                    {
                        // TODO: Report to user that the value is invalid.
                        continue;
                    }
                }

                newFilter.add(new MaterialData(targetMaterial, targetData));
            }

            // TODO: finish executing

            return true;
        }
        return false;
    }
}
