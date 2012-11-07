package com.thevoxelbox.voxelsniper.brush;

import org.bukkit.block.Block;

import com.thevoxelbox.voxelgunsmith.Brush;
import com.thevoxelbox.voxelgunsmith.OperationData;
import com.thevoxelbox.voxelgunsmith.Performer;
import com.thevoxelbox.voxelgunsmith.ToolConfiguration;
import com.thevoxelbox.voxelgunsmith.User;

/**
 * A brush that creates a solid ball. 
 * http://www.voxelwiki.com/minecraft/Voxelsniper#The_Ball_Brush
 * 
 * @author Piotr
 */
public class BallBrush implements Brush {
    private static final String[] HANDLES = { "ball", "b" };
    private final double trueCircle = 0;

    @Override
    public final void executeArrow(final OperationData operationData) {
        this.ball(operationData, operationData.getTargetBlock());
    }

    @Override
    public final void executePowder(final OperationData operationData) {
        this.ball(operationData, operationData.getLastBlock());
    }

    @Override
    public final String getInfo() {
        return "Generates a ball out of given material at targeted location.";
    }

    @Override
    public final String getName() {
        return "Ball";
    }

    @Override
    public final String getPermissionNode() {
        return "voxelsniper.primitives.ball";
    }

    @Override
    public final String[] getShorthandles() {
        return BallBrush.HANDLES;
    }

    @Override
    public void onInitialize(final User user, final String toolId) {

    }

    private void ball(final OperationData operationData, final Block targetBlock) {
        final ToolConfiguration _toolConfiguration = operationData.getToolConfiguration();
        final double _bPow = Math.pow(_toolConfiguration.getBrushSize() + this.trueCircle, 2);
        final Performer _performer = _toolConfiguration.getPerformer();

        _performer.perform(operationData.getTargetBlock());
        for (int _z = 1; _z <= _toolConfiguration.getBrushSize(); _z++) {
            final double _zPow = Math.pow(_z, 2);

            _performer.perform(targetBlock.getRelative(_z, 0, 0));
            _performer.perform(targetBlock.getRelative(-_z, 0, 0));
            _performer.perform(targetBlock.getRelative(0, _z, 0));
            _performer.perform(targetBlock.getRelative(0, -_z, 0));
            _performer.perform(targetBlock.getRelative(0, 0, _z));
            _performer.perform(targetBlock.getRelative(0, 0, -_z));

            for (int _x = 1; _x <= _toolConfiguration.getBrushSize(); _x++) {
                final double _xPow = Math.pow(_x, 2);

                if (_zPow + Math.pow(_x, 2) <= _bPow) {
                    _performer.perform(targetBlock.getRelative(_z, 0, _x));
                    _performer.perform(targetBlock.getRelative(_z, 0, -_x));
                    _performer.perform(targetBlock.getRelative(-_z, 0, _x));
                    _performer.perform(targetBlock.getRelative(-_z, 0, -_x));
                    _performer.perform(targetBlock.getRelative(_z, _x, 0));
                    _performer.perform(targetBlock.getRelative(_z, -_x, 0));
                    _performer.perform(targetBlock.getRelative(-_z, _x, 0));
                    _performer.perform(targetBlock.getRelative(-_z, -_x, 0));
                    _performer.perform(targetBlock.getRelative(0, _z, _x));
                    _performer.perform(targetBlock.getRelative(0, _z, -_x));
                    _performer.perform(targetBlock.getRelative(0, -_z, _x));
                    _performer.perform(targetBlock.getRelative(0, -_z, -_x));
                }

                for (int _y = 1; _y <= _toolConfiguration.getBrushSize(); _y++) {
                    if ((_xPow + Math.pow(_y, 2) + _zPow) <= _bPow) {
                        _performer.perform(targetBlock.getRelative(_x, _y, _z));
                        _performer.perform(targetBlock.getRelative(_x, _y, -_z));
                        _performer.perform(targetBlock.getRelative(-_x, _y, _z));
                        _performer.perform(targetBlock.getRelative(-_x, _y, -_z));
                        _performer.perform(targetBlock.getRelative(_x, -_y, _z));
                        _performer.perform(targetBlock.getRelative(_x, -_y, -_z));
                        _performer.perform(targetBlock.getRelative(-_x, -_y, _z));
                        _performer.perform(targetBlock.getRelative(-_x, -_y, -_z));
                    }
                }
            }
        }
    }
}
