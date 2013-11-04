package Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class Target {
    public static Player getTarget(Player player) {
        List<Entity> nearbyE = player.getNearbyEntities(55,
                55, 55);
        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
 
        for (Entity e : nearbyE) {
            if (e instanceof Player) {
                livingE.add((Player) e);
            }
        }
        BlockIterator bItr = new BlockIterator(player, 55);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        // loop through player's line of sight
        while (bItr.hasNext()) {
        	block = bItr.next();
        	bx = block.getX();
        	by = block.getY();
        	bz = block.getZ();
        	// check for entities near this block in the line of sight
        	for (LivingEntity e : livingE) {
        		loc = e.getLocation();
        		ex = loc.getX();
        		ey = loc.getY();
        		ez = loc.getZ();
        		if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {
        			// entity is close enough, set target and stop
        			return (Player) e;
        		}
        	}
        }
        return null;
    }
}
