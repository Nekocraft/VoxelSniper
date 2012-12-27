package com.thevoxelbox.voxelsniper;

import java.util.List;

import com.martiansoftware.jsap.JSAPResult;
import com.thevoxelbox.voxelsniper.commands.CommandBExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandDExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandGotoExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandPExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandUExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandVExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandVIExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandVRExecutor;
import com.thevoxelbox.voxelsniper.commands.CommandVSExecutor;
import com.thevoxelbox.voxelsniper.jsap.HelpJSAP;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Voxel
 */
public class VoxelSniper extends JavaPlugin
{
    private SniperUserManager sniperUserManager;
    private SniperConfiguration sniperConfiguration;

    /**
     * @param result
     * @param player
     * @param helpJSAP
     *
     * @return if a message was sent.
     */
    public static boolean sendHelpOrErrorMessageToPlayer(final JSAPResult result, final Player player, final HelpJSAP helpJSAP)
    {
        final List<String> output = helpJSAP.writeHelpOrErrorMessageIfRequired(result);
        if (!output.isEmpty())
        {
            for (final String string : output)
            {
                player.sendMessage(string);
            }
            return true;
        }
        return false;
    }

    public SniperUserManager getSniperUserManager()
    {
        return sniperUserManager;
    }

    /**
     * @return the sniperConfiguration
     */
    public final SniperConfiguration getSniperConfiguration()
    {
        return this.sniperConfiguration;
    }

    @Override
    public final void onEnable()
    {
        this.sniperUserManager = new SniperUserManager();
        Bukkit.getPluginManager().registerEvents(new VoxelSniperListener(this.sniperUserManager), this);

        this.reloadConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.sniperConfiguration = new SniperConfiguration(this.getConfig());

        MetricsManager.getInstance().start();

        this.getCommand("b").setExecutor(new CommandBExecutor(this));
        this.getCommand("d").setExecutor(new CommandDExecutor(this));
        this.getCommand("goto").setExecutor(new CommandGotoExecutor(this));
        this.getCommand("u").setExecutor(new CommandUExecutor(this));
        this.getCommand("v").setExecutor(new CommandVExecutor(this));
        this.getCommand("vi").setExecutor(new CommandVIExecutor(this));
        this.getCommand("vr").setExecutor(new CommandVRExecutor(this));
        this.getCommand("vs").setExecutor(new CommandVSExecutor(this));
        this.getCommand("p").setExecutor(new CommandPExecutor(this));
    }
}
