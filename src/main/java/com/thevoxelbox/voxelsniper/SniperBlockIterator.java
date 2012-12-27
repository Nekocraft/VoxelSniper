package com.thevoxelbox.voxelsniper;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * @author MikeMatrix
 */
public class SniperBlockIterator implements Iterator<Block>
{
    private final World world;
    private double maxLength;
    private final Vector step;
    private final Vector tDelta;
    private final Vector tMax;
    private final Vector current;
    private int length;

    public SniperBlockIterator(final Location location, final double maxLength)
    {
        this(location.getWorld(), location.toVector(), location.getDirection(), maxLength);
    }

    public SniperBlockIterator(final World world, final Vector start, final Vector direction, final double maxLength)
    {
        this.world = world;
        final Vector start1 = start.clone();

        this.current = start.clone();
        this.current.setX(Math.round(this.current.getX()));
        this.current.setY(Math.round(this.current.getY()));
        this.current.setZ(Math.round(this.current.getZ()));

        final Vector direction1 = direction.clone().normalize();
        this.maxLength = maxLength;

        this.step = new Vector();
        this.step.setX(Math.signum(direction1.getX()));
        this.step.setY(Math.signum(direction1.getY()));
        this.step.setZ(Math.signum(direction1.getZ()));

        final Vector cellBoundary = new Vector();
        cellBoundary.setX(this.current.getX() + (this.step.getX() > 0 ? 1 : 0));
        cellBoundary.setY(this.current.getY() + (this.step.getY() > 0 ? 1 : 0));
        cellBoundary.setZ(this.current.getZ() + (this.step.getZ() > 0 ? 1 : 0));

        this.tMax = new Vector();
        this.tMax.setX((cellBoundary.getX() - start1.getX()) / direction1.getX());
        this.tMax.setY((cellBoundary.getY() - start1.getY()) / direction1.getY());
        this.tMax.setZ((cellBoundary.getZ() - start1.getZ()) / direction1.getZ());

        if (Double.isNaN(this.tMax.getX()))
        {
            this.tMax.setX(Double.POSITIVE_INFINITY);
        }
        if (Double.isNaN(this.tMax.getY()))
        {
            this.tMax.setY(Double.POSITIVE_INFINITY);
        }
        if (Double.isNaN(this.tMax.getZ()))
        {
            this.tMax.setZ(Double.POSITIVE_INFINITY);
        }

        this.tDelta = new Vector();
        this.tDelta.setX(this.step.getX() / direction1.getX());
        this.tDelta.setY(this.step.getY() / direction1.getY());
        this.tDelta.setZ(this.step.getZ() / direction1.getZ());

        if (Double.isNaN(this.tDelta.getX()))
        {
            this.tDelta.setX(Double.POSITIVE_INFINITY);
        }
        if (Double.isNaN(this.tDelta.getY()))
        {
            this.tDelta.setY(Double.POSITIVE_INFINITY);
        }
        if (Double.isNaN(this.tDelta.getZ()))
        {
            this.tDelta.setZ(Double.POSITIVE_INFINITY);
        }

        double mainDirection = 0;
        double secondDirection = 0;
        double thirdDirection = 0;

        if (Math.abs(direction1.getX()) > mainDirection)
        {
            mainDirection = Math.abs(direction1.getX());
            secondDirection = Math.abs(direction1.getY());
            thirdDirection = Math.abs(direction1.getZ());
        }
        if (Math.abs(direction1.getY()) > mainDirection)
        {
            mainDirection = Math.abs(direction1.getY());
            secondDirection = Math.abs(direction1.getZ());
            thirdDirection = Math.abs(direction1.getX());
        }
        if (Math.abs(direction1.getZ()) > mainDirection)
        {
            mainDirection = Math.abs(direction1.getZ());
            secondDirection = Math.abs(direction1.getX());
            thirdDirection = Math.abs(direction1.getY());
        }

        this.maxLength = Math.round(maxLength / (Math.sqrt(mainDirection * mainDirection + secondDirection * secondDirection + thirdDirection * thirdDirection) / mainDirection));
    }

    @Override
    public boolean hasNext()
    {
        return this.length < this.maxLength;
    }

    @Override
    public Block next()
    {
        this.length++;
        final Block returnValue = (this.current.getY() > this.world.getMaxHeight() || this.current.getY() < 0) ? null : this.current.toLocation(this.world).getBlock();

        if (this.tMax.getX() < this.tMax.getY() && this.tMax.getX() < this.tMax.getZ())
        {
            this.current.setX(this.current.getX() + this.step.getX());
            this.tMax.setX(this.tMax.getX() + this.tDelta.getX());
        }
        else if (this.tMax.getY() < this.tMax.getZ())
        {
            this.current.setY(this.current.getY() + this.step.getY());
            this.tMax.setY(this.tMax.getY() + this.tDelta.getY());
        }
        else
        {
            this.current.setZ(this.current.getZ() + this.step.getZ());
            this.tMax.setZ(this.tMax.getZ() + this.tDelta.getZ());
        }
        return returnValue;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Block removal is not supported by " + this.getClass().getCanonicalName());
    }
}
