package com.thevoxelbox.voxelsniper.commands;

import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
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
public class CommandDExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;
    private final HelpJSAP parser;

    public CommandDExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        this.parser = new HelpJSAP("d", "Defaults sniper.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.parser.registerParameter(new Switch("resetall", 'a', "restall", "If flag is set it will reset all tools."));
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
            final JSAPResult result = parser.parse(strings);

            if (VoxelSniper.sendHelpOrErrorMessageToPlayer(result, player, parser))
            {
                return true;
            }

            // TODO: Execute

            return true;
        }
        return false;
    }
}
