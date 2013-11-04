package Skills;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.Effects;
import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class Teleport {
	public static void intercept(Player damager, Player damagee){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(damager);
		if(pvp.getStamina() > 50 && pvp.canIntercept() == true){
			pvp.setStamina((int) (pvp.getStamina() - 50));
			damagee.setVelocity(new Vector(0,0,0));
			Effects.intercept(damager);
			damager.teleport(damagee);
			Effects.intercept(damager);
			pvp.setCanUseSkill(false);
			pvp.setSkillCooldown(2);
		}
	}
	public static void dart(Player player){
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.getStamina() > 50 && pvp.canIntercept() == true){
			Vector direction = player.getLocation().getDirection();
			direction.multiply(3);
			player.setVelocity(direction);
			Effects.intercept(player);
			pvp.setCanUseSkill(false);
			pvp.setSkillCooldown(2);
		}
	}
}
