package com.thevoxelbox.voxelsniper.commands;

import com.thevoxelbox.voxelgunsmith.VoxelGunsmith;
import com.thevoxelbox.voxelsniper.SniperUser;
import com.thevoxelbox.voxelsniper.VoxelSniper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author: MikeMatrix
 */
public class CommandPExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;

    public CommandPExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            SniperUser sniperUser = this.plugin.getSniperUserManager().getUser(player);
            VoxelGunsmith.getBrushParameterManager().getInstance(sniperUser, null, sniperUser.getActiveBrush());
            // TODO Builder Performer and register it to current tool configuration.
            return false;
        }
        return false;
    }
}
