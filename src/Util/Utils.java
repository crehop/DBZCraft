package Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Utils
{
	public static Vector getTargetVector(Location shooter, Location target)
	{
		Location first_location = shooter.add(0, 1, 0);
		Location second_location = target.add(0, 1, 0);
		Vector vector = second_location.toVector().subtract(first_location.toVector());
		return vector;
		
	}
	
    public static Entity getTarget(Player player)
    {
        List<Entity> nearbyE = player.getNearbyEntities(180, 180, 180);
        ArrayList<Player> nearPlayers = new ArrayList<Player>();
        for (Entity e : nearbyE)
        {
            if (e instanceof Player)
            {
                nearPlayers.add((Player) e);
            }
        }
        Entity target = null;
        BlockIterator bItr = new BlockIterator(player, 20);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        while (bItr.hasNext())
        {
 
            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            for (Player e : nearPlayers)
            {
                loc = e.getLocation();
                ex = loc.getX();
                ey = loc.getY();
                ez = loc.getZ();
                if ((bx - 2.75 <= ex && ex <= bx + 1.75) && (bz - 2.75 <= ez && ez <= bz + 1.75) && (by - 2.75 <= ey && ey <= by + 3.5))
                {
                    target = (Entity)e;
                    break;
                }
            }
            if(target == null)
            {
                for (Entity e : nearbyE)
                {
                	if(e.getType() != EntityType.FIREBALL){
                		loc = e.getLocation();
                		ex = loc.getX();
                		ey = loc.getY();
                		ez = loc.getZ();
                		if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5))
                		{
                			target = e;
                			break;
                		}
                	}
                }            	
            }
        }
        return target;
    }
    public static String getVerticalMovementDirection(PlayerMoveEvent event)
    {
    	String answer;
    	String direction;
    	double X = event.getTo().getX()-event.getFrom().getX();
    	double Y = event.getTo().getY()-event.getFrom().getY();
    	double Z = event.getTo().getZ()-event.getFrom().getZ();
    	if(X < 0)
    	{
    		X = X*-1;
    	}
    	if(Z < 0)
    	{
    		Z = Z*-1;
    	}
    	if(Y > 0)
    	{
    		direction = "up";
    	}
    	else
    	{
    		direction = "down";
    	}
    	answer = direction;

    	return answer;
    } 
	public static boolean clear(Player damagee)
	{
		int check = 0;
		if(correctType(damagee.getLocation().add(1, 0, 0).getBlock()) == true)
		{
		}
		else
		{
			check += 1;
		}
		if(correctType(damagee.getLocation().subtract(1, 0, 0).getBlock()) == true)
		{
		}
		else
		{
			check += 1;
		}
		if(correctType(damagee.getLocation().add(0, 0, 1).getBlock()) == true)
		{
		}
		else
		{
			check += 1;
		}
		if(correctType(damagee.getLocation().subtract(0, 0, 1).getBlock()) == true)
		{
		}
		else
		{
			check += 1;
		}
		if(correctType(damagee.getLocation().add(1, 0, 0).subtract(0, 0, 1).getBlock()) == true)
		{
		}
		else
		{
			check += 10;
		}
		if(correctType(damagee.getLocation().add(1, 0, 1).getBlock()) == true)
		{
		}
		else
		{
			check += 10;
		}
		if(correctType(damagee.getLocation().subtract(1, 0, 0).add(0, 0, 1).getBlock()) == true)
		{
		}
		else
		{
			check += 1;
		}
		if(correctType(damagee.getLocation().subtract(1, 0, 1).getBlock()) == true)
		{
		}
		else
		{
			check += 10;
		}
		if(check == 0)
		{
			return true;
		}
		return false;
	}
	public static boolean correctType(Block check)
	{
		if(check.getType() == Material.AIR || check.getType() == Material.LONG_GRASS || check.getType() == Material.RED_ROSE || check.getType() == Material.YELLOW_FLOWER || check.getType() == Material.GRASS)
		{
			return true;
		}
		return false;
	}
	public void explosionBlockFire(Block block) {
		   Bukkit.broadcastMessage("INTERACT");
	    
	    
			   //FallingBlock FallingBlock = l.getWorld().spawnFallingBlock(l, is.getType(), is.getData().getData());
			   //FallingBlock.setVelocity(v);
			
	    
	    
	}

}
