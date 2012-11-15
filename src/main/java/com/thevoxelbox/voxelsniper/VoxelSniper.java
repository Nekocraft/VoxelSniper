package com.thevoxelbox.voxelsniper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.ChatPaginator;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.QualifiedSwitch;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 * @author Voxel
 * 
 */
public class VoxelSniper extends JavaPlugin
{
    private static final int MAXIMUM_SCAN_RANGE = 150;
    private VoxelSniperListener voxelSniperListener;
    private SniperUserManager sniperUserManager;

    private SniperConfiguration sniperConfiguration;
    
    private final HelpJSAP gotoParser;
    private final HelpJSAP vsParser;
    private final HelpJSAP vParser;

    /**
     * Default Constructor.
     */
    public VoxelSniper()
    {
        this.gotoParser = new HelpJSAP("goto", "Teleporting to a coordinate in the current world.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.gotoParser.registerParameter(new UnflaggedOption("x", JSAP.INTEGER_PARSER, true, JSAP.NO_HELP));
            this.gotoParser.registerParameter(new UnflaggedOption("z", JSAP.INTEGER_PARSER, true, JSAP.NO_HELP));
        }
        catch (final JSAPException e)
        {
        }
        this.vsParser = new HelpJSAP("vs", "VoxelSniper information command.", ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.vsParser.registerParameter(new QualifiedSwitch("range", JSAP.INTEGER_PARSER, null, false, 'r', "range", "If no range given, it will toggle range limitation. If range given, it will enable range limitation and set the range to the given value."));
            this.vsParser.registerParameter(new Switch("list-brushes", JSAP.NO_SHORTFLAG, "list-brushes", "List all registered brushes."));
            this.vsParser.registerParameter(new Switch("list-placement-options", JSAP.NO_SHORTFLAG, "list-placement-options", "List all placement options."));
            this.vsParser.registerParameter(new Switch("list-filter-options", JSAP.NO_SHORTFLAG, "list-filter-options", "List all filter options."));
            this.vsParser.registerParameter(new Switch("list-operation-options", JSAP.NO_SHORTFLAG, "list-operation-options", "List all operation options."));
        }
        catch (final JSAPException e)
        {
        }
        this.vParser = new HelpJSAP("v", "Setting the voxel and datavalue to work with by either passing them to the command or pointing at a block.",
                ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH);
        try
        {
            this.vParser.registerParameter(new UnflaggedOption("material", JSAP.STRING_PARSER, false,
                    "Material in [material][:id format]. If either one of them is missing it will be defaulted to 0."));
        }
        catch (final JSAPException e)
        {
        }

    }

    /**
     * @return the sniperConfiguration
     */
    public final SniperConfiguration getSniperConfiguration()
    {
        return this.sniperConfiguration;
    }

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args)
    {
        if (sender instanceof Player)
        {
            final String[] strippedArgs = Arrays.copyOfRange(args, 1, args.length);

            final Player player = (Player) sender;
            final SniperUser sniperUser = this.sniperUserManager.getUser(player);
            final String lowerCaseCommandName = command.getName();

            if ("goto".equals(lowerCaseCommandName))
            {
                return this.commandGoto(strippedArgs, player);
            }
            else if ("vs".equals(lowerCaseCommandName))
            {
                return this.commandVs(strippedArgs, sniperUser);
            }
            else if ("v".equals(lowerCaseCommandName))
            {
                return this.commandV(strippedArgs, sniperUser);
            }
            else if ("vr".equals(lowerCaseCommandName))
            {
                return this.commandVr(strippedArgs);
            }
            else if ("d".equals(lowerCaseCommandName))
            {
                return this.commandD(strippedArgs);
            }
            else if ("b".equals(lowerCaseCommandName))
            {
                return this.commandB(strippedArgs);
            }
            else if ("p".equals(lowerCaseCommandName))
            {
                return this.commandP(strippedArgs);
            }
        }
        return false;
    }

    @Override
    public final void onEnable()
    {
        this.voxelSniperListener = new VoxelSniperListener();
        this.sniperUserManager = new SniperUserManager();
        Bukkit.getPluginManager().registerEvents(this.voxelSniperListener, this);

        this.reloadConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.sniperConfiguration = new SniperConfiguration(this.getConfig());

        MetricsManager.getInstance().start();

    }

    private boolean commandB(final String[] args)
    {
        // TODO Auto-generated method stub
        return false;
    }

    private boolean commandD(final String[] args)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param args
     * @param player
     */
    private boolean commandGoto(final String[] args, final Player player)
    {
        if (player.hasPermission("voxelsniper.goto"))
        {
            final JSAPResult result = this.gotoParser.parse(args);

            if (this.sendHelpOrErrorMessageToPlayer(result, player, this.gotoParser))
            {
                return true;
            }

            final int x = result.getInt("x");
            final int z = result.getInt("z");

            player.teleport(new Location(player.getWorld(), x, player.getWorld().getHighestBlockYAt(x, z), z), TeleportCause.PLUGIN);

        }

        return true;
    }

    private boolean commandP(final String[] args)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param args
     * @param player
     */
    private boolean commandV(final String[] args, final SniperUser sniperUser)
    {
        final JSAPResult result = this.vParser.parse(args);
        final Player player = sniperUser.getPlayer();

        if (this.sendHelpOrErrorMessageToPlayer(result, player, this.vParser))
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

    private boolean commandVr(final String[] args)
    {
        // TODO Auto-generated method stub
        return false;
    }

    private boolean commandVs(final String[] args, final SniperUser sniperUser)
    {
        final JSAPResult result = this.vsParser.parse(args);
        final Player player = sniperUser.getPlayer();

        if (this.sendHelpOrErrorMessageToPlayer(result, player, this.vsParser))
        {
            return true;
        }

        // TODO: Execute

        return true;
    }

    /**
     * @param result
     * @param player
     * @param helpJSAP
     * @return if a message was sent.
     */
    private boolean sendHelpOrErrorMessageToPlayer(final JSAPResult result, final Player player, final HelpJSAP helpJSAP)
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
}
