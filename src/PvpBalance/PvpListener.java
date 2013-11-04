package PvpBalance;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PvpListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event)
	{
		ItemStack is = event.getItem();
		if(!is.getType().equals(Material.POTION))
			return;
		Player p = event.getPlayer();
		PVPPlayer dp = PvpHandler.getPvpPlayer(p);
		long time = new Date().getTime();
		if((time-dp.getDefencePotionTimer())/1000<=PvpHandler.getDefencePotionCD())
		{
			p.sendMessage(ChatColor.RED + "You Still Have " + ChatColor.AQUA + (15-((time-dp.getDefencePotionTimer())/1000)) + ChatColor.RED + " Seconds Left On That");
			event.setCancelled(true);
			p.updateInventory();
		}
		else
		{
			dp.setDefencePotionTimer(time);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		ItemStack is = event.getPlayer().getItemInHand();
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		if(!is.getType().equals(Material.POTION))
			return;
		boolean check = false;
		try
		{
			Potion.fromItemStack(is);
		}
		catch(Exception e)
		{
			check = true;
		}
		if(!check)
		{
			if(!Potion.fromItemStack(is).isSplash())
				return;
		}
		Player p = event.getPlayer();
		PVPPlayer dp = PvpHandler.getPvpPlayer(p);
		long time = new Date().getTime();
		if(check || isOffencPotion(is))
		{
			if((time-dp.getOffencePotionTimer())/1000<=PvpHandler.getOffencePotionCD())
			{
				p.sendMessage(ChatColor.RED + "You Still Have " + ChatColor.AQUA + (15-((time-dp.getOffencePotionTimer())/1000)) + ChatColor.RED + " Seconds Left On That");
				event.setCancelled(true);
				p.updateInventory();
			}
			else
			{
				dp.setOffencePotionTimer(time);
			}
		}
		else
		{
			if((time-dp.getDefencePotionTimer())/1000<=PvpHandler.getDefencePotionCD())
			{
				p.sendMessage(ChatColor.RED + "You Still Have " + ChatColor.AQUA + (15-((time-dp.getDefencePotionTimer())/1000)) + ChatColor.RED + " Seconds Left On That");
				event.setCancelled(true);
				p.updateInventory();
			}
			else
			{
				dp.setDefencePotionTimer(time);
			}
		}
		/*if(is.getAmount() > 1)
		{
			ItemStack clone = is.clone();
			clone.setAmount(1);
			for(int i=0;i<is.getAmount()-1;++i)
			{
				if(!isFull(p.getInventory()))
					p.getInventory().addItem(clone);
				else
					p.getWorld().dropItemNaturally(p.getLocation(), clone);
			}
			p.setItemInHand(null);
			p.updateInventory();
		}*/
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event)
	{
		Player player = event.getPlayer();
		ItemStack is = player.getInventory().getItem(event.getNewSlot());
		if(is == null)
			return;
		if(!is.getType().equals(Material.POTION))
			return;
		if(is.getAmount() > 1)
		{
			player.getInventory().setItem(event.getNewSlot(), null);
			ItemStack clone = is.clone();
			clone.setAmount(1);
			for(int i=0;i<is.getAmount()-1;++i)
			{
				if(!isFull(player.getInventory()))
					player.getInventory().addItem(clone);
				else
					player.getWorld().dropItemNaturally(player.getLocation(), clone);
			}
			player.updateInventory();
		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
	{
		if(event.getDamage() <= 0 || event.isCancelled())
			return;
		Entity en = event.getEntity();
		if(!(en instanceof LivingEntity))
			return;
		LivingEntity le = (LivingEntity)en;
		if(!(le instanceof Player))
			return;
		Date d = new Date();
		en = event.getDamager();
		if(en instanceof Player)
		{
			PVPPlayer dp = PvpHandler.getPvpPlayer((Player)en);
			if(!dp.isInPVP())
			{
				dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
						ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
			}
			dp.setPvpTimer(d.getTime());
			dp = PvpHandler.getPvpPlayer((Player)le);
			if(!dp.isInPVP())
			{
				dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
						ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
			}
			dp.setPvpTimer(d.getTime());
		}
		else if(en instanceof Arrow)
		{
			Arrow a = (Arrow)en;
			if(a.getShooter() instanceof Player)
			{
				PVPPlayer dp = PvpHandler.getPvpPlayer((Player)a.getShooter());
				if(!dp.isInPVP())
				{
					dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
							ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
				}
				dp.setPvpTimer(d.getTime());
				dp = PvpHandler.getPvpPlayer((Player)le);
				if(!dp.isInPVP())
				{
					dp.getPlayer().sendMessage(ChatColor.GOLD + "You Are Now In PVP, You Have To Stay Out Of PVP For " + ChatColor.AQUA + PvpHandler.getPvpTimer() + 
							ChatColor.GOLD + " Seconds Before Logging Out Or You Will Die");
				}
				dp.setPvpTimer(d.getTime());
			}
		}
	}
	
	public boolean isOffencPotion(ItemStack is)
	{
		Potion p = Potion.fromItemStack(is);
		for(PotionEffect pe : p.getEffects())
		{
			if(pe.getType().equals(PotionEffectType.POISON))
				return true;
			else if(pe.getType().equals(PotionEffectType.WEAKNESS))
				return true;
			else if(pe.getType().equals(PotionEffectType.SLOW))
				return true;
			else if(pe.getType().equals(PotionEffectType.HARM))
				return true;
		}
		return false;
	}
	
	public boolean isFull(PlayerInventory inv)
	{
		for(ItemStack is : inv.getContents())
		{
			if(is == null)
				return false;
			if(is.getType().equals(Material.AIR))
				return false;
		}
		return true;
	}
}
