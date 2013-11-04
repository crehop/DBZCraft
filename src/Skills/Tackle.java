package Skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.palmergames.bukkit.towny.utils.CombatUtil;

import PvpBalance.Damage;
import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class Tackle {
	public static void tackle(Player player)
	{
		for(Entity near:player.getNearbyEntities(2, 2, 2)){
			if(near instanceof Player)
			{
				Player tackled = (Player)near;
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.getStamina() > 50){
					pvp.setStamina((int) (pvp.getStamina() - 50));
					if(Damage.partyCanHit(tackled, player) == true && CombatUtil.preventDamageCall(tackled,player) == false && tackled.isFlying() == true)
					{
						PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer(tackled);
						pvpDamagee.setTackleTimer(4);
						pvpDamagee.damage(200);
						pvpDamagee.setSuperMode(false);
						pvpDamagee.getPlayer().setAllowFlight(false);
						pvpDamagee.getPlayer().setFlying(false);
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + "YOU GROUND POUND " + tackled.getDisplayName() + " CROUCH TO EXIT");
						tackled.sendMessage(ChatColor.RED  + "" + ChatColor.BOLD + "" + player.getDisplayName()+ " HAS GROUND POUNDED YOU!");
						Vector target = new Vector(0,-30,0);
						tackled.setVelocity(target);
						PvpBalance.PvpBalance.tackled.add(tackled);
						break;
					}
				}
				
			}
		}
	}
}

