package Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuHandler implements Listener
{
	@EventHandler
	public void playerjoin(PlayerJoinEvent event)
	{
		this.setMainMenu(event.getPlayer());
	}
	@EventHandler
	public void respawn(PlayerRespawnEvent event)
	{
		this.setMainMenu(event.getPlayer());
	}
	@EventHandler
	public void inventoryEvent(InventoryClickEvent event){
		if(isMenuItem(event.getCurrentItem()) == true){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void inventoryDrop(PlayerDeathEvent event){
		for(ItemStack item:event.getDrops()){
			if(isMenuItem(item) == true){
				item.setType(Material.AIR);
			}
		}
	}
	@EventHandler
	public void blockPlace(BlockPlaceEvent e)
	{
		if(isMenuItem(e.getItemInHand()))
		{
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void itemUse(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(isMenuItem(e.getItem()) == true){
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void playerdrop(PlayerDropItemEvent e)
	{
    	if(isMenuItem(e.getItemDrop().getItemStack()) == true){
    		e.setCancelled(true);
        }   
    }
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
    	if(isMenuItem(event.getEntity().getItemStack()))
    	{
    		event.getEntity().remove();
    	}
    }
	public static void setMainMenu(Player player){
		ItemStack punch = new ItemStack (Material.SLIME_BALL,1);
		ItemMeta punchMeta = punch.getItemMeta();
		punchMeta.setDisplayName(ChatColor.YELLOW + "Left Click: Super Hit (0)");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.BLUE + "Right Click: Intercept (50)");
		lore.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "50 Powerlevel");
		punchMeta.setLore(lore);
		punch.setItemMeta(punchMeta);
		ItemStack kameha = new ItemStack (Material.FIREBALL,1);
		ItemMeta kamehaMeta = kameha.getItemMeta();
		kamehaMeta.setDisplayName(ChatColor.RED + "Left Click: Tackle (50)");
		List<String> lore2 = new ArrayList<String>();
		lore2.add("Right Click: Kamehameha (50)");
		lore2.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "50 Powerlevel");
		kamehaMeta.setLore(lore2);
		kameha.setItemMeta(kamehaMeta);
		ItemStack superSayien = new ItemStack (Material.BLAZE_POWDER,1);
		ItemMeta superSayienMeta = superSayien.getItemMeta();
		superSayienMeta.setDisplayName(ChatColor.GREEN + "Right Click: Toggle SuperSayien (250)");
		List<String> lore3 = new ArrayList<String>();
		lore3.add("CROUCH: Toggle SuperRegen");
		lore3.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "1000 Powerlevel");
		superSayienMeta.setLore(lore3);
		superSayien.setItemMeta(superSayienMeta);
	    player.getInventory().setItem(0, punch);
	    player.getInventory().setItem(1, kameha);
	    player.getInventory().setItem(2, superSayien);
	}
	public boolean isMenuItem(ItemStack stack){
		if(stack == null)
			return false;
	    if(stack.getType() == Material.BLAZE_POWDER ||stack.getType() == Material.FIREBALL || stack.getType() == Material.SLIME_BALL && stack.getType() != null)
	    {
	    	return true;
	    }
	    return false;
	}
}
