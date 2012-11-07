package com.thevoxelbox.voxelsniper;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * @author MikeMatrix
 *
 */
public class SniperUserManager {
    private Map<Player, SniperUser> playerUserMap = new HashMap<Player, SniperUser>();
    
    /**
     * @param player
     * @return SniperUser
     */
    public final SniperUser getUser(final Player player) {
        SniperUser _returnUser = playerUserMap.get(player);
        if (_returnUser == null) {
            _returnUser = new SniperUser(player);
            playerUserMap.put(player, _returnUser);
        }
        return _returnUser;        
    }
}
