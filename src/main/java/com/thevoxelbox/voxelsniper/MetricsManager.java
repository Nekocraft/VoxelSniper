package com.thevoxelbox.voxelsniper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.thevoxelbox.voxelgunsmith.Brush;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

/**
 * @author Monofraps
 */
public final class MetricsManager
{
    private static int snipesDone = 0;
    private static long snipeCounterInitTimeStamp = 0;
    private static MetricsManager instance;

    /**
     * @return {@link MetricsManager}
     */
    public static MetricsManager getInstance()
    {
        if (MetricsManager.instance == null)
        {
            MetricsManager.instance = new MetricsManager();
        }

        return MetricsManager.instance;
    }

    /**
     * Increase the Snipes Counter.
     */
    public static void increaseSnipeCounter()
    {
        MetricsManager.snipesDone++;
    }

    /**
     * Set Initialization time for reference when calculating average Snipes per Minute.
     *
     * @param currentTimeMillis
     */
    public static void setSnipeCounterInitTimeStamp(final long currentTimeMillis)
    {
        MetricsManager.snipeCounterInitTimeStamp = currentTimeMillis;
    }

    private MetricsManager()
    {
    }

    /**
     * Start sending Metrics.
     */
    public void start()
    {
        try
        {
            final Metrics metrics = new Metrics(VoxelSniper.getInstance());

            final Graph graph = metrics.createGraph("Snipers Online");
            graph.addPlotter(new Metrics.Plotter("Snipers Online")
            {

                @Override
                public int getValue()
                {
                    int count = 0;
                    for (final Player player : Bukkit.getOnlinePlayers())
                    {
                        if (VoxelSniper.getSniperPermissionHelper().isSniper(player))
                        {
                            count++;
                        }
                    }
                    return count;
                }
            });
            graph.addPlotter(new Metrics.Plotter("Litesnipers Online")
            {

                @Override
                public int getValue()
                {
                    int count = 0;
                    for (final Player player : Bukkit.getOnlinePlayers())
                    {
                        if (VoxelSniper.getSniperPermissionHelper().isLiteSniper(player))
                        {
                            count++;
                        }
                    }
                    return count;
                }
            });

            metrics.addCustomData(new Metrics.Plotter("Average Snipes per Minute")
            {

                @Override
                public int getValue()
                {
                    final int currentSnipes = MetricsManager.snipesDone;
                    final long initializationTimeStamp = MetricsManager.snipeCounterInitTimeStamp;
                    final double deltaTime = System.currentTimeMillis() - initializationTimeStamp;

                    double avg;
                    if (deltaTime < 60000)
                    {
                        avg = currentSnipes;
                    }
                    else
                    {
                        final double timeRunning = deltaTime / 60000;
                        avg = currentSnipes / timeRunning;
                    }

                    // quite unlikely ...
                    if (avg > 10000)
                    {
                        avg = 0;
                    }

                    return NumberConversions.floor(avg);
                }
            });

            final Graph graphBrushUsage = metrics.createGraph("Brush Usage");

            final HashMap<String, Brush> temp = SniperBrushes.getSniperBrushes();
            for (final Entry<String, Brush> entry : temp.entrySet())
            {
                graphBrushUsage.addPlotter(new Metrics.Plotter(SniperBrushes.getName(entry.getValue()))
                {
                    @Override
                    public int getValue()
                    {
                        return entry.getValue().getTimesUsed();
                    }

                    @Override
                    public void reset()
                    {
                        entry.getValue().setTimesUsed(0);
                    }
                });
            }

            final Graph graphJavaVersion = metrics.createGraph("Java Version");
            graphJavaVersion.addPlotter(new Metrics.Plotter(System.getProperty("java.version"))
            {

                @Override
                public int getValue()
                {
                    return 1;
                }
            });

            final Graph graphOsName = metrics.createGraph("OS Name");
            graphOsName.addPlotter(new Metrics.Plotter(System.getProperty("os.name"))
            {

                @Override
                public int getValue()
                {
                    return 1;
                }
            });

            final Graph graphOsArch = metrics.createGraph("OS Architecture");
            graphOsArch.addPlotter(new Metrics.Plotter(System.getProperty("os.arch"))
            {

                @Override
                public int getValue()
                {
                    return 1;
                }
            });

            metrics.start();
        }
        catch (final IOException e)
        {
            VoxelSniper.getInstance().getLogger().finest("Failed to submit Metrics Data.");
        }
    }
}
