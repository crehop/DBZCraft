package PvpBalance;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

//Will house effect methods
public class Effects
{
	Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
	public static void teleportGreen(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.HAPPY_VILLAGER , player.getLocation().add(0, 1, 0),0.3f,0.62f,0.3f, (float)0.65, 150);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void magicWhiteSwirls(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.SPELL , player.getLocation(),0.2f,0.2f,0.2f, (float)0.02, 160);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void kameya(Fireball ball)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.LARGE_EXPLODE , ball.getLocation(),0.1f,0.1f,0.1f, (float)0.1, 30);
			ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK , ball.getLocation(),0.1f,0.1f,0.1f, (float)0.01, 130);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect kameya!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void impactEffect(Location location){
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.CLOUD, location,0.3f,0.3f,0.3f, (float)0.3, 300);
			ParticleEffect.sendToLocation(ParticleEffect.EXPLODE, location,0.3f,0.3f,0.3f, (float)0.001, 300);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
	}
	public static void igniteFirePlayers(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation().add(0, 1, 0),0.2f,0.2f,0.2f, (float)0.02, 60);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void blockedPlayer(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.EXPLODE, player.getLocation().add(0, 1, 0),0.2f,0.2f,0.2f, (float)0.02, 13);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect igniteFirePlayer!");
		}
		//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
	}
	public static void effectPoison(Player player)
	{	
		try
		{
			ParticleEffect.sendCrackToLocation(true, 295, (byte) 0, player.getLocation().add(0, 1.3, 0), 0.2f, 0.2f, 0.2f, 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect poisonPlayer!");
		}
	}//Particle.playParticle("iconcrack_295", player.getLocation().add(0, 1, 0), 0.35f, 0.05f, 30);	
	
	public static void effectBlind(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, player.getLocation().add(0, 2, 0),0.1f,0.1f,0.1f, (float)0.01, 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect blindPlayer!");
		}
	}
	
	public static void effectSlow(Player player)
	{
		try
		{
			//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
			ParticleEffect.sendToLocation(ParticleEffect.CLOUD, player.getLocation().subtract(0, 0.1, 0) ,0.2f,-0.2f,0.2f, (float)0.0001, 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect SlowPlayer!");
		}
	}
	public static void effectSuperJump(Player player)
	{
		try
		{
			//ParticleEffect.sendToLocation(effect, location, offsetX, offsetY, offsetZ, speed, count)
			ParticleEffect.sendToLocation(ParticleEffect.CLOUD, player.getLocation().subtract(0, 0.1, 0) ,0.4f,-0.4f,0.4f, (float)0.004, 100);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect SlowPlayer!");
		}
	}
	public static void effectWither(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.TOWN_AURA, player.getLocation().add(0, 1, 0),0.2f,0.5f,0.2f, (float)0.02, 200);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect witherPlayer!");
		}	
	}
	public static void intercept(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.FOOTSTEP, player.getLocation().add(0, 1, 0),0.3f,0.8f,0.3f, (float)0.00, 300);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect witherPlayer!");
		}	
	}
	
	public static void effectSharpnessPlayers(Player player)
	{
		float enchantsO = 0;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE)*3;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_FIRE)*2;
		enchantsO += (float)player.getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
		float amount = enchantsO;
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.PORTAL, player.getLocation().add(0, 0.2, 0),0.2f,0.2f,0.2f, (float)0.15f, (int)amount*5);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void effectSprintPlayers(Player player, float speed, int amount)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.FOOTSTEP, player.getLocation().add(0, 0.02, 0),0.2f,0f,0.2f, (float)0.15, 1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect sprintPlayer!");
		}
	}
	
	public static void effectSpeedPlayers(Player player, float speed, int amount)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.SMOKE, player.getLocation(),0.3f,0.2f,0.3f, (float)0.03, 70);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect speedPlayer!");
		}
	}	
	
	public static void effectHealthPlayers(Player player, float speed, int amount)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.HEART, player.getLocation().add(0,2.2,0),0f,0f,0f, (float)0.01, 3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect healthPlayer!");
		}
	}	
	
	public static void effectConfuse(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.CRIT, player.getLocation(),0f,1f,0f, (float)0.02, 6);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect confusePlayer!");
		}
	}
	
	public static void bleed(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.DRIP_LAVA, player.getLocation(),0.35f,0.35f,0.35f, (float)0.02, 40);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect bleedPlayer!");
		}
	}
	public static void superSaienToggle(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, player.getLocation().add(0, 1, 0), 0.5f,0.2f,0.5f, (float)0.2, 100);
			ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation().add(0, 1, 0),0.00f,0.00f,0.00f, (float)0.2, 30);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect supersaienPlayer!");
		}
	}
	public static void superSaienGroundShake(Player player)
	{
		try
		{
			Location loc = player.getLocation().subtract(0, 4, 0);
			int checks = 0;
			while(loc.getBlock().getType() != Material.AIR){
				checks++;
				loc = loc.add(0, 1, 0);
			}
			float speed = (float) (0.09 * checks);
			if(speed > 0.2){
				speed = (float) 0.2;
			}
			player.setFlySpeed(speed);
			ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, loc,0.5f,0.2f,0.5f, (float)0.2, 50*checks);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect supersaienPlayer!");
		}
	}
	
	public static void superSaien(Player player)
	{
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation().add(0, 0.5, 0),0.3f,0.2f,0.3f, (float)0.045, 30);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect supersaienPlayer!");
		}
	}
	
	public static void admin(Player player)
	{
		if(player.hasPermission("particle.admin"))
		{
			try
			{
				ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, player.getLocation(),0.35f,0.35f,0.35f, (float)0.02, 100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				PvpBalance.logger.info("Effect adminPlayer!");
			}
		}
	}
	public static void superSaienSpray(Player player) {
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.SPLASH, player.getLocation().subtract(0, 2, 0),0.75f,0.35f,0.75f, (float)0.05, 200);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect adminPlayer!");
		}
	}
	public static void superRegen(Player player) {
		try
		{
			ParticleEffect.sendToLocation(ParticleEffect.FLAME, player.getLocation().add(0, 0.5, 0),0.3f,0.2f,0.3f, (float)0.5, 30);
			ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, player.getLocation().add(0, 0.5, 0),0.3f,0.2f,0.3f, (float)0.5, 50);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PvpBalance.logger.info("Effect supersaienPlayer!");
		}
		
	}
}
