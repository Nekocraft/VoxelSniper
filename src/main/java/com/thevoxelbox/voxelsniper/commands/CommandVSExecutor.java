package com.thevoxelbox.voxelsniper.commands;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.QualifiedSwitch;
import com.martiansoftware.jsap.Switch;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

/**
 * @author MikeMatrix
 */
public class CommandVSExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;
    private final HelpJSAP parser;

    public CommandVSExecutor(VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        parser = new HelpJSAP("vs", "VoxelSniper information command.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            parser.registerParameter(new QualifiedSwitch("range", JSAP.INTEGER_PARSER, null, false, 'r', "range", "If no range given, it will toggle range limitation. If range given, it will enable range limitation and set the range to the given value."));
            parser.registerParameter(new Switch("list-brushes", JSAP.NO_SHORTFLAG, "list-brushes", "List all registered brushes."));
            parser.registerParameter(new Switch("list-placement-options", JSAP.NO_SHORTFLAG, "list-placement-options", "List all placement options."));
            parser.registerParameter(new Switch("list-filter-options", JSAP.NO_SHORTFLAG, "list-filter-options", "List all filter options."));
            parser.registerParameter(new Switch("list-operation-options", JSAP.NO_SHORTFLAG, "list-operation-options", "List all operation options."));
        }
        catch (final JSAPException e)
        {
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
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

            return true;
        }
        return false;
    }
}
