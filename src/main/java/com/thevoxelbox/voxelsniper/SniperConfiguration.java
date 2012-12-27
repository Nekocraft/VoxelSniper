package com.thevoxelbox.voxelsniper;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author MikeMatrix
 */
public class SniperConfiguration
{
    private static final String MAX_BRUSH_SIZE_PATH = "sniper.max-brush-size";
    private static final String MAX_UNDO_CACHE_SIZE_PATH = "sniper.max-undo-cache-size";

    private final FileConfiguration configuration;

    /**
     * @param configuration
     */
    public SniperConfiguration(final FileConfiguration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * @return Maximal Brush size.
     */
    public final int getMaxBrushSize()
    {
        return this.configuration.getInt(MAX_BRUSH_SIZE_PATH);
    }

    /**
     * @return Maximal size of the undo cache.
     */
    public final int getMaxUndoCacheSize()
    {
        return this.configuration.getInt(MAX_UNDO_CACHE_SIZE_PATH);
    }
}
