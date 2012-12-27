package com.thevoxelbox.voxelsniper.commands;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.ChatPaginator;

/**
 * @author MikeMatrix
 */
public class CommandGotoExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;
    private final HelpJSAP parser;

    public CommandGotoExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        this.parser = new HelpJSAP("goto", "Teleporting to a coordinate in the current world.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.parser.registerParameter(new UnflaggedOption("x", JSAP.INTEGER_PARSER, true, "X position in current world to teleport to."));
            this.parser.registerParameter(new UnflaggedOption("z", JSAP.INTEGER_PARSER, true, "Z position in current world to teleport to."));
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
            Player player = (Player) commandSender;
            if (player.hasPermission("voxelsniper.goto"))
            {
                final JSAPResult result = parser.parse(strings);

                if (VoxelSniper.sendHelpOrErrorMessageToPlayer(result, player, parser))
                {
                    return true;
                }

                final int x = result.getInt("x");
                final int z = result.getInt("z");

                player.teleport(new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z), PlayerTeleportEvent.TeleportCause.PLUGIN);

            }

            return true;
        }
        return false;
    }
}
