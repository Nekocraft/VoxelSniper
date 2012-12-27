package com.thevoxelbox.voxelsniper;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * @author MikeMatrix
 */
class SniperBlockIterator implements Iterator<Block>
{
	private final World world;
	private final Vector start;
	private final Vector direction;
	private double maxLength;
	private final Vector step;
	private final Vector cellBoundary;
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
		this.start = start.clone();

		this.current = start.clone();
		this.current.setX(Math.round(this.current.getX()));
		this.current.setY(Math.round(this.current.getY()));
		this.current.setZ(Math.round(this.current.getZ()));

		this.direction = direction.clone().normalize();
		this.maxLength = maxLength;

		this.step = new Vector();
		this.step.setX(Math.signum(this.direction.getX()));
		this.step.setY(Math.signum(this.direction.getY()));
		this.step.setZ(Math.signum(this.direction.getZ()));

		this.cellBoundary = new Vector();
		this.cellBoundary.setX(this.current.getX() + (this.step.getX() > 0 ? 1 : 0));
		this.cellBoundary.setY(this.current.getY() + (this.step.getY() > 0 ? 1 : 0));
		this.cellBoundary.setZ(this.current.getZ() + (this.step.getZ() > 0 ? 1 : 0));

		this.tMax = new Vector();
		this.tMax.setX((this.cellBoundary.getX() - this.start.getX()) / this.direction.getX());
		this.tMax.setY((this.cellBoundary.getY() - this.start.getY()) / this.direction.getY());
		this.tMax.setZ((this.cellBoundary.getZ() - this.start.getZ()) / this.direction.getZ());

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
		this.tDelta.setX(this.step.getX() / this.direction.getX());
		this.tDelta.setY(this.step.getY() / this.direction.getY());
		this.tDelta.setZ(this.step.getZ() / this.direction.getZ());

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

		if (Math.abs(this.direction.getX()) > mainDirection)
		{
			mainDirection = Math.abs(this.direction.getX());
			secondDirection = Math.abs(this.direction.getY());
			thirdDirection = Math.abs(this.direction.getZ());
		}
		if (Math.abs(this.direction.getY()) > mainDirection)
		{
			mainDirection = Math.abs(this.direction.getY());
			secondDirection = Math.abs(this.direction.getZ());
			thirdDirection = Math.abs(this.direction.getX());
		}
		if (Math.abs(this.direction.getZ()) > mainDirection)
		{
			mainDirection = Math.abs(this.direction.getZ());
			secondDirection = Math.abs(this.direction.getX());
			thirdDirection = Math.abs(this.direction.getY());
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
