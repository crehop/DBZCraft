package Skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.Effects;

public class Block {
	public static void block(Player player)
	{
		double d = 0.9;
        float hForce = 15 / 10.0F;
        float vForce = 12 / 10.0F;
		Vector direction = player.getLocation().getDirection();
	    Vector forward = direction.multiply(-3);
	    Vector v = player.getLocation().toVector().subtract(player.getLocation().add(0,0,0).toVector());
	    v.add(forward);
	    v.setY(0.7);
	    v.normalize();
	    v.multiply(hForce*d);
	    v.setY(vForce*d);
	    player.setVelocity(v);
		Effects.blockedPlayer(player);
		player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE BEEN BLOCKED AND FORCED BACK!");
	}

}
