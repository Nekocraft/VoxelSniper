package com.thevoxelbox.voxelsniper.commands;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import com.thevoxelbox.voxelgunsmith.Brush;
import com.thevoxelbox.voxelgunsmith.VoxelGunsmith;
import com.thevoxelbox.voxelsniper.SniperUser;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;
import com.thevoxelbox.voxelsniper.jsap.NullableIntegerStringParser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

/**
 * @author: MikeMatrix
 */
public class CommandBExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;
    private HelpJSAP parser;

    public CommandBExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        parser = new HelpJSAP("b", "Setting brush options.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            parser.registerParameter(new FlaggedOption("size", NullableIntegerStringParser.getParser(), null, false, 's', "size", "Defines the size of the brush."));
            parser.registerParameter(new UnflaggedOption("brush", JSAP.STRING_PARSER, false, "Brush to be set."));
            parser.registerParameter(new FlaggedOption("target", JSAP.STRING_PARSER, null, false, 't', "target", "Target data type to be aplied to the affected blocks (if supported by the brush."));
            parser.registerParameter(new FlaggedOption("filter", JSAP.STRING_PARSER, null, false, 'f', "filter", "Selects the filter type to determin apllicable blocks (if supported by the brush."));
            parser.registerParameter(new FlaggedOption("filter", JSAP.STRING_PARSER, null, false, 'o', "option", "Additional performer options."));
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
            final SniperUser sniperUser = plugin.getSniperUserManager().getUser(player);
            final JSAPResult result = parser.parse(strings);

            if (VoxelSniper.sendHelpOrErrorMessageToPlayer(result, player, parser))
            {
                return true;
            }

            if (result.getObject("size") != null)
            {
                int size = result.getInt("size");
                sniperUser.getActiveToolConfiguration().setBrushSize(size);
            }

            final String brush = result.getString("brush");
            Class<Brush> brushClass = VoxelGunsmith.getBrushManager().getBrush(brush);

            final String toolId = sniperUser.getToolId(player.getItemInHand().getData());

            if (toolId != null)
            {
                sniperUser.setBrush(toolId, brushClass);
            }

            return true;
        }
        return false;
    }
}
