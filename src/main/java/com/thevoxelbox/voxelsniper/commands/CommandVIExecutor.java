package com.thevoxelbox.voxelsniper.commands;

import com.thevoxelbox.voxelsniper.VoxelSniper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author: MikeMatrix
 */
public class CommandVIExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;

    public CommandVIExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings)
    {
        // TODO: stub.
        return false;
    }
}
