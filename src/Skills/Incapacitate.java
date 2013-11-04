package Skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import PvpBalance.PvpHandler;

public class Incapacitate {
	public static void Incapacitate(Player damagee, Player damager){
		double d = 3.5;
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 300, 3);
		PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 240, 4);
		PotionEffect potionEffect3 = new PotionEffect(PotionEffectType.SLOW, 200, 3);
		damagee.addPotionEffect(potionEffect);
		damagee.addPotionEffect(potionEffect2);
		damagee.addPotionEffect(potionEffect3);
		damager.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU KNOCK BACK AND INCAPACITATED " + damagee.getDisplayName());
		damagee.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE BEEN KNOCKED BACK AND INCAPACITATED BY " + damager.getDisplayName());
        float hForce = 15 / 10.0F;
        float vForce = 12 / 10.0F;
        Vector direction = damagee.getLocation().getDirection();
        Vector forward = direction.multiply(3);
        forward.multiply(-3.5);
        Vector v = damagee.getLocation().toVector().subtract(damagee.getLocation().add(0,6,0).toVector());
        v.add(forward);
        v.setY(5);
        v.normalize();
        v.multiply(hForce*d);
        v.setY(vForce*d);
        damagee.setVelocity(v);
	}

}
