package Skills;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import PvpBalance.PVPPlayer;
import PvpBalance.PvpHandler;

public class SuperSpeed {
	
	public static void speedOn(Player player)
	{
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU SPRINT! PRESS "+ ChatColor.AQUA + "" + ChatColor.BOLD + "SNEAK" + ChatColor.GREEN + "" + ChatColor.BOLD + " AGAIN TO STOP!");
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		pvp.setUsedSpeedSkill(true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 3));
	}
	public static void speedOff(Player player)
	{
		player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU STOP SPRINTING!");
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		pvp.setUsedSpeedSkill(false);
		player.setSprinting(false);
		player.removePotionEffect(PotionEffectType.SPEED);
	}
}
