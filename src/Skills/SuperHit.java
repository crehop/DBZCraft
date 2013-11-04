package Skills;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;
import Util.Utils;

public class SuperHit {
	public static void superHit(Player damager, Player damagee) 
	{
		PVPPlayer pvp = PvpHandler.getPvpPlayer(damager);
		if(pvp.canSuperhit() == true){
			pvp.setSuperhitCooldown(3);
			pvp.setCanSuperhit(false);
			pvp.setStamina((int) (pvp.getStamina() - 50));
			Vector v = damager.getLocation().add(0, 2, 0).getDirection();
	    	float hForce = 3;
	        v.normalize();
	        v.multiply(hForce);
	        v.setY(v.getY() + 2);
	        if(Utils.clear(damagee)){
	        	damagee.setVelocity(v);
	        }
	        PVPPlayer damaged = PvpHandler.getPvpPlayer(damagee);
	        damaged.setWasSuperHit(true);
	        damagee.setFlying(false);
	        damaged.setSuperMode(false);
		}
	}
	public static void superHit(Vector direction, Player damagee) 
	{
		Vector v = direction;
    	float hForce = 3;
        v.normalize();
        v.multiply(hForce);
        if(Utils.clear(damagee)){
        	damagee.setVelocity(v);
        }
        PVPPlayer damaged = PvpHandler.getPvpPlayer(damagee);
        damaged.setWasSuperHit(true);
        damagee.setFlying(false);
        damaged.setSuperMode(false);
	}
}

