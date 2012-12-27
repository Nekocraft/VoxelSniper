package com.thevoxelbox.voxelsniper;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.thevoxelbox.voxelgunsmith.OperationData;
import com.thevoxelbox.voxelgunsmith.ToolConfiguration;

/**
 * @author MikeMatrix
 */
public final class SniperOperationData implements OperationData
{
	private final ToolConfiguration toolConfiguration;

	private final Block targetBlock;
	private final Block lastBlock;

	/**
	 * @param toolConfiguration
	 * @param targetBlock
	 * @param lastBlock
	 */
	public SniperOperationData(final ToolConfiguration toolConfiguration, final Block targetBlock, final Block lastBlock)
	{
		this.toolConfiguration = toolConfiguration;
		this.targetBlock = targetBlock;
		this.lastBlock = lastBlock;
	}

	@Override
	public Block getLastBlock()
	{
		return this.lastBlock;
	}

	@Override
	public Block getTargetBlock()
	{
		return this.targetBlock;
	}

	@Override
	public ToolConfiguration getToolConfiguration()
	{
		return this.toolConfiguration;
	}

	@Override
	public void perform(final Block block)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void performManual(final Block block, final byte data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void performManual(final Block block, final Material material)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void performManual(final Block block, final Material material, byte data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void performManual(final Block block, final Material material, final byte data, final boolean update)
	{
		// TODO Auto-generated method stub

	}

}
