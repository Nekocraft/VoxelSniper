package com.thevoxelbox.voxelsniper.commands;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;

/**
 * @author MikeMatrix
 */
public class CommandUExecutor implements CommandExecutor
{
    private final VoxelSniper plugin;
    private final HelpJSAP parser;

    public CommandUExecutor(final VoxelSniper voxelSniper)
    {
        plugin = voxelSniper;
        this.parser = new HelpJSAP("u", "Undo/Redo actions.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.parser.registerParameter(new Switch("redo", 'r', "redo", "If flag is set it will redo actions."));
            this.parser.registerParameter(new UnflaggedOption("amount", JSAP.INTEGER_PARSER, false, "Amount of steps to undo/redo."));
        }
        catch (final JSAPException e)
        {
        }
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings)
    {
        // TODO: stub.
        return false;
    }
}
