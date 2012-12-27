package com.thevoxelbox.voxelsniper;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * @author MikeMatrix
 */
public class SniperUserManager
{
    private final Map<Player, SniperUser> playerUserMap = new HashMap<Player, SniperUser>();

    /**
     * @param player
     *
     * @return SniperUser
     */
    public final SniperUser getUser(final Player player)
    {
        SniperUser returnUser = playerUserMap.get(player);
        if (returnUser == null)
        {
            returnUser = new SniperUser(player);
            playerUserMap.put(player, returnUser);
        }
        return returnUser;
    }
}
