package Skills;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.Effects;
import PvpBalance.FireworkEffectPlayer;
import PvpBalance.PVPPlayer;

public class GrappleShot {
		public static void grappleShotPlayerHit(Player damagee, Player damager,PVPPlayer pvp)
		{
			pvp.setStamina((int) (pvp.getStamina() - 25));
			pvp.getPlayer().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU FIRE A GRAPPLE SHOT AND PULL " + damagee.getDisplayName() + " TO YOU!");
			Vector direction = damager.getLocation().add(0, 6, 0).toVector().subtract(damagee.getLocation().toVector()).normalize();
			direction.multiply(3);
			damagee.setVelocity(direction);
			FireworkEffectPlayer player = new FireworkEffectPlayer();
			FireworkEffect pulled = FireworkEffect.builder().with(Type.CREEPER).withColor(Color.RED).withFade(Color.GRAY).build();
			try {
				player.playFirework(damagee.getWorld(), damagee.getLocation().add(0, 1, 0), pulled);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Effects.magicWhiteSwirls(damager);
			pvp.setGrappleEnd(null);
			pvp.setGrappleStart(null);
			pvp.setGrapplePlayer(null);
			pvp.setGrappleArrow(null);
	        pvp.setCanUseGrappleShot(false);
	        pvp.setIsUsingGrappleShot(false);
		}
		public static void grappleShotBlockHit(Player shooter, Block hit,PVPPlayer pvp)
		{
			pvp.setStamina((int) (pvp.getStamina() - 25));
			pvp.getPlayer().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU FIRE A GRAPPLE SHOT AND PULL YOURSELF TO THE BLOCK!");
			Location delta = shooter.getLocation().add(hit.getLocation().add(0, 5, 0));
		    Vector direction = delta.getDirection();
		    direction.multiply(3);
	        shooter.setVelocity(direction);
			Effects.magicWhiteSwirls(shooter);
			Effects.magicWhiteSwirls(shooter);
			Effects.magicWhiteSwirls(shooter);
			pvp.setGrappleEnd(null);
			pvp.setGrappleStart(null);
			pvp.setGrapplePlayer(null);
			pvp.setGrappleArrow(null);
	        pvp.setCanUseGrappleShot(false);
	        pvp.setIsUsingGrappleShot(false);
		}

}
