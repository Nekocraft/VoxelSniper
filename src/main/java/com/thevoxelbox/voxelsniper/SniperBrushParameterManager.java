package com.thevoxelbox.voxelsniper;

import java.util.HashMap;
import java.util.Map;

import com.thevoxelbox.voxelgunsmith.Brush;
import com.thevoxelbox.voxelgunsmith.BrushParameterManager;
import com.thevoxelbox.voxelgunsmith.BrushParameters;
import com.thevoxelbox.voxelgunsmith.User;

/**
 * Implementation of the VoxelGunsmith BrushParameterManager.
 *
 * @author MikeMatrix
 */
public class SniperBrushParameterManager implements BrushParameterManager
{

    private Map<User, Map<String, Map<Class<? extends Brush>, BrushParameters>>> mapping = new HashMap<User, Map<String, Map<Class<? extends Brush>, BrushParameters>>>();

    @Override
    public final void clear()
    {
        for (Map<String, Map<Class<? extends Brush>, BrushParameters>> userMap : mapping.values())
        {
            for (Map<Class<? extends Brush>, BrushParameters> parametersMap : userMap.values())
            {
                parametersMap.clear();
            }
        }
    }

    @Override
    public final void clear(final User user)
    {
        Map<String, Map<Class<? extends Brush>, BrushParameters>> userMap = mapping.get(user);
        for (Map<Class<? extends Brush>, BrushParameters> parametersMap : userMap.values())
        {
            parametersMap.clear();
        }
    }

    @Override
    public final void clear(final User user, final String toolId)
    {
        Map<String, Map<Class<? extends Brush>, BrushParameters>> userMap = mapping.get(user);
        if (userMap == null)
        {
            return;
        }
        Map<Class<? extends Brush>, BrushParameters> parametersMap = userMap.get(toolId);
        if (parametersMap == null)
        {
            return;
        }
        parametersMap.clear();
    }

    @Override
    public final BrushParameters getInstance(final User user, final String toolId, final Brush brush)
    {
        Map<String, Map<Class<? extends Brush>, BrushParameters>> userMap = mapping.get(user);
        if (userMap == null)
        {
            userMap = new HashMap<String, Map<Class<? extends Brush>, BrushParameters>>();
            mapping.put(user, userMap);
        }
        Map<Class<? extends Brush>, BrushParameters> toolIdMap = userMap.get(toolId);
        if (toolIdMap == null)
        {
            toolIdMap = new HashMap<Class<? extends Brush>, BrushParameters>();
            userMap.put(toolId, toolIdMap);
        }
        BrushParameters result = toolIdMap.get(brush.getClass());
        if (result == null)
        {
            // TODO: create and store new BrushParametrs instance.
        }
        return result;
    }

    @Override
    public void removeInstance(final User user, final String toolId, final Brush brush)
    {
        // TODO Auto-generated method stub

    }

}
