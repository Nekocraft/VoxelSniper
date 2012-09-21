package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;

/**
 * 
 * @author Voxel
 */
public class VoxelDiscBrush extends PerformBrush {
    private static int timesUsed = 0;

    /**
     * 
     */
    public VoxelDiscBrush() {
        this.setName("Voxel Disc");
    }

    private final void disc(final SnipeData v) {
        final int _bSize = v.getBrushSize();

        for (int x = _bSize; x >= -_bSize; x--) {
            for (int y = _bSize; y >= -_bSize; y--) {
                this.current.perform(this.clampY(this.getBlockPositionX() + x, this.getBlockPositionY(), this.getBlockPositionZ() + y));
            }
        }
        v.storeUndo(this.current.getUndo());
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.disc(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.setBlockPositionX(this.getLastBlock().getX());
        this.setBlockPositionY(this.getLastBlock().getY());
        this.setBlockPositionZ(this.getLastBlock().getZ());
        this.disc(v);
    }
    
    @Override
    public final void info(final Message vm) {
    	vm.brushName(this.getName());
    	vm.size();
    	// voxelMessage.voxel();
    }
    
    @Override
    public final int getTimesUsed() {
    	return VoxelDiscBrush.timesUsed;
    }
    
    @Override
    public final void setTimesUsed(final int tUsed) {
    	VoxelDiscBrush.timesUsed = tUsed;
    }
}