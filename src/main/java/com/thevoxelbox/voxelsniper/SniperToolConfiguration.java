package com.thevoxelbox.voxelsniper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.material.MaterialData;

import com.thevoxelbox.voxelgunsmith.ToolConfiguration;

/**
 * @author MikeMatrix
 * 
 */
public class SniperToolConfiguration implements ToolConfiguration
{
    private MaterialData materialData;
    private Set<MaterialData> filter;
    private int brushSize;
    private int height;
    private double rangeLimit;
    private boolean rangeLimited;

    @Override
    public final MaterialData getMaterialData()
    {
        return this.materialData;
    }

    @Override
    public final void setMaterialData(final MaterialData materialData)
    {
        this.materialData = materialData;
    }

    @Override
    public final Set<MaterialData> getFilter()
    {
        return Collections.unmodifiableSet(filter);
    }

    @Override
    public final void setFilter(final Set<MaterialData> materialDataSet)
    {
        this.filter = new HashSet<MaterialData>(materialDataSet);
    }

    @Override
    public final void addFilter(final MaterialData materialData)
    {
        this.filter.add(materialData);
    }

    @Override
    public final void removeFilter(final MaterialData materialData)
    {
        this.filter.remove(materialData);
    }

    @Override
    public final int getBrushSize()
    {
        return this.brushSize;
    }

    @Override
    public final void setBrushSize(final int brushSize)
    {
        this.brushSize = brushSize;
    }

    @Override
    public final int getHeight()
    {
        return this.height;
    }

    @Override
    public final void setHeight(final int height)
    {
        this.height = height;
    }

    @Override
    public final boolean isRangeLimited()
    {
        return this.rangeLimited;
    }

    @Override
    public final void setRangeLimited(final boolean rangeLimited)
    {
        this.rangeLimited = rangeLimited;
    }

    @Override
    public final void setRangeLimit(final double rangeLimit)
    {
        this.rangeLimit = rangeLimit;
    }

    @Override
    public final double getRangeLimit()
    {
        return this.rangeLimit;
    }

}
