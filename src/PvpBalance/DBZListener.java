package PvpBalance;

import java.util.ArrayList;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.palmergames.bukkit.towny.utils.CombatUtil;

import Event.PBEntityDamageEntityEvent;
import Event.PBEntityDamageEvent;
import Event.PBEntityDeathEvent;
import Event.PBEntityRegainHealthEvent;
import SaveLoad.LoadSave;
import Skills.Incapacitate;
import Skills.Kamehameha;
import Skills.PileDrive;
import Skills.SuperSpeed;
import Util.Utils;


public class DBZListener implements Listener
{
	static int tick = 0;
	public static PvpBalance plugin;
	public LoadSave LoadSave;
	public DBZListener(PvpBalance instance, LoadSave LoadSave)
	{
		this.LoadSave = LoadSave;
		plugin = instance;
	}	
	@EventHandler
	public static void blockHitGround(EntityChangeBlockEvent event){
		if(event.getEntity().getType() == EntityType.FALLING_BLOCK){
			event.setCancelled(true);
		}

	}
	@EventHandler
	public static void explosionPrimeEvent(ExplosionPrimeEvent event)
	{
		if(event.getEntity().getType() == EntityType.FIREBALL)
		{
			Fireball ball = (Fireball) event.getEntity();
			if(ball.getShooter() instanceof Player){
				if(PvpHandler.kameya.contains(PvpHandler.getKamehameha((Player)ball.getShooter())))
				{
					Location loc = event.getEntity().getLocation();
					ball.setYield(2);
					TNTPrimed primed = loc.getWorld().spawn(loc, TNTPrimed.class);
					primed.setYield(4);
		        	primed.setFuseTicks(0);
		        	Effects.impactEffect(primed.getLocation());
		        	TNTPrimed primed2 = loc.getWorld().spawn(loc, TNTPrimed.class);
		        	primed2.setYield(6.5f);
		        	primed2.setFuseTicks(3);
		        	primed2.setFallDistance(0f);
					PvpHandler.kameya.remove(ball);
					ball.remove();
				}
			}
		}
	}
	@EventHandler
	public static void getExplosion(EntityExplodeEvent event) {
		List<Block> explosionBlocks = new ArrayList<Block>();
        explosionBlocks.addAll(event.blockList());
        double height = .04;
        int every3 = 0;
    	for(Block block:explosionBlocks){
    		every3++;
    		height+= .005;
    		if(height > 1){
    			height = .05;
    		}
    		if(every3 == 3){
    			every3 = 0;
    			continue;
    		}
    		FallingBlock thrown = block.getLocation().getWorld()
                    .spawnFallingBlock(block.getLocation(), block.getType(), (byte) 0);
    		thrown.setVelocity(new Vector(0,height,0));
    		thrown.setDropItem(false);
    		thrown.setTicksLived(100);
    	}
        
    }
	@EventHandler
	public void vehicleDismountEvent(PlayerTeleportEvent event)
	{
		PVPPlayer pvp = PvpHandler.getPvpPlayer(event.getPlayer());
		if(pvp.getIsDoubleJumping() > 0){
			event.setCancelled(true);
		}
		if(!event.getPlayer().hasPermission("essentials.kick"))
		{
			Effects.teleportGreen(event.getPlayer());
		}
	}
	@EventHandler
	public void vehicleDismountEvent(VehicleExitEvent event)
	{
		if(event.getExited().getType().equals(EntityType.CHICKEN))
		{
			event.getExited().remove();
		}
	}
	@EventHandler
	public void playerQuit(PlayerQuitEvent quitevent)
	{
		Player quitPlayer = quitevent.getPlayer();
		PVPPlayer pp = PvpHandler.getPvpPlayer(quitPlayer);
		if(pp.isInCombat())
		{
			quitPlayer.setHealth(0f);
		}
		if(pp.isInPVP())
		{
			pp.sethealth(0);
			quitevent.getPlayer().setHealth(0D);
		}
		if(pp.isInParty())
		{
			pp.getParty().leave(pp);
		}
		PvpHandler.removePvpPlayer(pp);
	}
	
	@EventHandler
	public void foodChangeEvent(FoodLevelChangeEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(PvpHandler.getPvpPlayer(player).isGod())
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamageEvent(EntityDamageByEntityEvent event)
	{
		//if(event.isCancelled())
		//	return;
		if(!Damage.partyCanHit(event.getEntity(), event.getDamager()))
		{
			event.setCancelled(true);
			return;
		}
		if(event.getDamage() <= 0)
		{
			event.setCancelled(true);
			return;
		}
		double dealtDamage = 0.0f;
		Entity e = event.getEntity();
		if (e instanceof Player)
		{
			double rawDamage = event.getDamage();
			Player damagee = (Player) e;
			if(damagee.getNoDamageTicks() > 10)
			{
				event.setCancelled(true);
				return;
			}
			
			PVPPlayer pvpDamagee = PvpHandler.getPvpPlayer(damagee);
			if(pvpDamagee.isGod())
			{
				event.setCancelled(true);
				return;
			}
			if(event.getDamager() instanceof Arrow)
			{
				Arrow arrow = (Arrow)event.getDamager();
				Entity damager = arrow.getShooter();
				if(CombatUtil.preventDamageCall(((Arrow)event.getDamager()).getShooter(), damagee))
				{
					event.setCancelled(true);
					return;
				}
				if(damager instanceof Player && event.getEntity() instanceof Player)
				{
					PVPPlayer pvpDamager = PvpHandler.getPvpPlayer((Player)arrow.getShooter());
					if(pvpDamager.isUsingGrappleShot() == true)
					{
						pvpDamager.setGrapplePlayer(damagee);
					}
					dealtDamage = Damage.calcDamage((Player)damager, damagee);
					pvpDamagee.damage((int)dealtDamage);
					pvpDamager.setCombatCoolDown(20);
					pvpDamagee.setCombatCoolDown(20);
				}
				if(!(damager instanceof Player))
				{	
					dealtDamage = event.getDamage() * SaveLoad.LoadSave.Multi;
				}
				pvpDamagee.setCombatCoolDown(20);
			}
			else if(event.getCause().equals(DamageCause.PROJECTILE))
			{
				if(CombatUtil.preventDamageCall(((Projectile)event.getDamager()).getShooter(), damagee))
				{
					event.setCancelled(true);
					return;
				}
				else if((event.getDamager().getType() == EntityType.WITHER_SKULL))
				{
					event.setDamage(0f);
					dealtDamage += 85;
				}
				else if((event.getDamager().getType() == EntityType.SMALL_FIREBALL))
				{
					dealtDamage += 65;
				}
				else if(event.getEntity().getType() == EntityType.FIREBALL)
				{

					dealtDamage += 175;
				}
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				dealtDamage = pbdEvent.getDamage();
				if(!pvpDamagee.damage((int)dealtDamage))
				{
					event.setCancelled(true);
					return;
				}
			}
			else if(event.getDamager() instanceof Player)
			{
				Player damager = (Player)event.getDamager();
				PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
				if(CombatUtil.preventDamageCall(damager, damagee))
				{
					event.setCancelled(true);
					return;
				}
				if(!pvpDamager.canHit())
				{
					event.setCancelled(true);
					return;
				}
				if(damager.getItemInHand().getType().equals(Material.BOW) && !(event.getDamager() instanceof Arrow))
				{
					dealtDamage = 20;
				}
				else
				{
					if(pvpDamagee.getPlayer().isBlocking() == true && pvpDamagee.getStamina() > 10){
						pvpDamagee.setStamina((int)pvpDamagee.getStamina() - 10);
						pvpDamagee.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU BLOCK THE ATTACK! AND TAKE HALF DAMAGE!");
						dealtDamage = Damage.calcDamage(damager,damagee) / 2;
						Skills.Block.block(pvpDamager.getPlayer());
						
					}
					else if(pvpDamager.canUsePileDrive() && damager.isSneaking() == false){
						PileDrive.pileDrive(damagee,damager);
					}
					else if(pvpDamager.canUsePileDrive() && damager.isSneaking() == true){
						Incapacitate.Incapacitate(damagee, damager);
					}
					else
					{
						if(pvpDamagee.getPlayer().isBlocking() == true)
						{
							pvpDamagee.getPlayer().sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "NOT ENOUGH STAMINA TO BLOCK!");
						}
						dealtDamage = Damage.calcDamage(damager,damagee);
					}
				}
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, damager, (int)dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				dealtDamage = pbdEvent.getDamage();
				if(!pvpDamagee.damage((int)dealtDamage))
				{
					event.setCancelled(true);
					return;
				}
				
				String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + pvpDamagee.gethealth();
				Bukkit.getMessenger().dispatchIncomingMessage(damager, "Scoreboard", message.getBytes());
				pvpDamager.setHitCoolDown(SaveLoad.LoadSave.HitCooldown);
				pvpDamager.setCombatCoolDown(20);
				pvpDamagee.setCombatCoolDown(20);
				if(PvpBalance.plugin.isDebug() || pvpDamager.isPvpstats())
				{
					damager.sendMessage(ChatColor.RED + "DAMAGE DEALT: " + dealtDamage);
				}
			}
			else
			{
				if(event.getDamager() instanceof Arrow && ((Arrow)event.getDamager()).getShooter() instanceof Player)
				{
					Player damager = (Player)((Arrow)event.getDamager()).getShooter();
					PVPPlayer pvpDamager = PvpHandler.getPvpPlayer(damager);
					dealtDamage = Damage.calcDamage(damager,damagee);
					pvpDamager.setCombatCoolDown(20);
					pvpDamagee.setCombatCoolDown(20);
				}
				else
				{
					dealtDamage = rawDamage * SaveLoad.LoadSave.Multi;
				}
				PBEntityDamageEntityEvent pbdEvent = new PBEntityDamageEntityEvent(damagee, event.getDamager(), (int)dealtDamage, event.getCause());
				Bukkit.getPluginManager().callEvent(pbdEvent);
				if(pbdEvent.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				dealtDamage = pbdEvent.getDamage();
				if(!pvpDamagee.damage((int)dealtDamage))
				{
					event.setCancelled(true);
					return;
				}
			}
			event.setDamage(0D);
			event.setCancelled(false);
		}
		else if(e instanceof LivingEntity)
		{
			if(!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Arrow))
				return;
			double health = (((LivingEntity)e).getHealth() - event.getDamage());
			if(health < 0)
				health = 0;
			String message = "SIDEBAR,Health," + ChatColor.RED + "Enemy:" + ChatColor.RESET + "," + (int)health;
			if(event.getDamager() instanceof Player)
			{
				Bukkit.getMessenger().dispatchIncomingMessage((Player)event.getDamager(), "Scoreboard", message.getBytes());
			}
			else
			{
				if(!(((Arrow)event.getDamager()).getShooter() instanceof Player))
					return;
				Bukkit.getMessenger().dispatchIncomingMessage((Player)((Arrow)event.getDamager()).getShooter(), "Scoreboard", message.getBytes());
			}
		}
	}
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer newPVP = new PVPPlayer(player);
		if(player.getWorld().getName().contains("world"))
		{
			if(player.hasPermission("particles.admin"))
			{
				player.sendMessage(ChatColor.RED + "Welcome Administrator :" + player.getDisplayName() + ChatColor.GREEN + " Please Enjoy your stay on the medieval lords server.. a personal Concierge will be with you shortly to handle your every whim");
				newPVP.setGod(true);
			}
			else
			{
				player.teleport(new Location(player.getWorld(), -730.50, 105, 319.50));
			}
		}
		PvpHandler.addPvpPlayer(newPVP);
		Damage.calcArmor(event.getPlayer());
		if(player.getHealth() > 0){
			newPVP.sethealth(newPVP.getMaxHealth());
		}
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		for(PotionEffect effect:player.getActivePotionEffects()){
			PotionEffectType potion = effect.getType();
			player.removePotionEffect(potion);
		}
		if(PvpHandler.getPvpPlayer(player) == null)
		{
			PVPPlayer newPVP = new PVPPlayer(player);
			PvpHandler.addPvpPlayer(newPVP);
		}
		Damage.calcArmor(player);
		PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
		PVPPlayer.setIsDead(false);
		PVPPlayer.setStamina((int) PVPPlayer.getMaxStamina());
		PVPPlayer.sethealth(PVPPlayer.getMaxHealth());
	}
	@EventHandler
	public void projectileHitEvent(ProjectileHitEvent event)
	{
		if(event.getEntity().getType() == EntityType.FIREBALL)
		{
			Fireball fireball = (Fireball)event.getEntity();
			Kamehameha ball = PvpHandler.getKamehameha((Player)fireball.getShooter());
			Player damager = (Player) fireball.getShooter();
	        List<Entity> nearbyE = event.getEntity().getNearbyEntities(3,
	                3, 3);
	 
	        for (Entity e : nearbyE) {
	            if (e instanceof Player) {
	    			Skills.SuperHit.superHit(ball.getFireball().getDirection(), (Player) e);
	    		}
	        }
			PvpHandler.kameya.remove(ball);
		}
		if(event.getEntity().getShooter() instanceof Player){
			Player player = (Player)event.getEntity().getShooter();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			if(pvp.isUsingGrappleShot() == true)
			{
				pvp.setGrappleEnd(event.getEntity().getLocation());
			}
		}
		
	}
	@EventHandler
	public void entityDeath(EntityDeathEvent event)
	{
		if(event.getEntityType().equals(EntityType.CHICKEN))
		{
			event.getDrops().clear();
			event.getEntity().remove();
		}
	}
	//JUMP SKILL!
	@EventHandler
	public void playerToggleFlightEvent(PlayerToggleFlightEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.getSuperMode() == false){
			if(pvp.getStamina() > 10 && pvp.canUseSkill() == true && event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getPlayer().getWorld().getName().contains("world")){
				if(pvp.getSuperMode() == false && pvp.getUsedSpeedSkill() == true && player.isFlying() == false)
				{
					Skills.SuperJump.Jump(player , 0.9);
					pvp.setSkillCooldown(3);
					pvp.setCanUseSkill(false);
					pvp.setStamina((int) (pvp.getStamina() - 50));
					pvp.getPlayer().setFlySpeed(0.07f);
					pvp.getPlayer().setAllowFlight(false);
					pvp.getPlayer().setFlying(false);
				
				}
				else{
					Skills.SuperJump.Jump(player , 0.9);
					pvp.setSkillCooldown(3);
					pvp.setCanUseSkill(false);
					pvp.setStamina((int) (pvp.getStamina() - 10));
					event.setCancelled(true);
					pvp.getPlayer().setAllowFlight(false);
					pvp.getPlayer().setFlying(false);
				}

		}
			else if(event.getPlayer().getGameMode() == GameMode.SURVIVAL && pvp.getSuperMode() == false){
				event.setCancelled(true);
				event.getPlayer().setAllowFlight(false);
				event.getPlayer().setFlying(false);
			}
		}
		else if(player.getLocation().subtract(0,2,0).getBlock().getType() != Material.AIR && pvp.getStamina() > 10 && pvp.canUseSkill() == true && event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getPlayer().getWorld().getName().contains("world"))
		{
			Skills.SuperJump.Jump(player , 0.9);
			pvp.setSkillCooldown(3);
			pvp.setCanUseSkill(false);
			pvp.setStamina((int) (pvp.getStamina() - 50));
		}
	}
	@EventHandler
	public void playerToggleSprintEvent(PlayerToggleSprintEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		pvp.setWasSprinting(2);
		pvp.setCanUseSkill(true);
	}
	@EventHandler
	public void playerToggleSneakEvent(PlayerToggleSneakEvent event)
	{
		Player player = event.getPlayer();
		PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
		if(pvp.wasFirstToggle() == true)
		{
			pvp.setFirstToggle(false);
			return;
		}
		else if(pvp.getWasSprinting() > 0 && pvp.canUseSkill() == true && pvp.getStamina() > 5 && pvp.getUsedSpeedSkill() == false){
			event.setCancelled(true);
			player.setSneaking(false);
			SuperSpeed.speedOn(player);
			pvp.setUsedSpeedSkill(true);
			pvp.setFirstToggle(true);
			return;
		}
		else if(pvp.getUsedSpeedSkill() == true && pvp.wasFirstToggle() == false)
		{
			pvp.setUsedSpeedSkill(false);
			pvp.setCanUseSkill(false);
			SuperSpeed.speedOff(player);
		}
	}
	@EventHandler
	public void regenEvent(EntityRegainHealthEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			if(event.getRegainReason() == RegainReason.MAGIC)
			{
				Player player = (Player)event.getEntity();
				if(PvpHandler.getPvpPlayer(player) == null)
				{
					PVPPlayer newPVP = new PVPPlayer(player);
					PvpHandler.addPvpPlayer(newPVP);
				}
				PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
				int heal = SaveLoad.LoadSave.HealPot;
				PBEntityRegainHealthEvent pberh = new PBEntityRegainHealthEvent(player, heal, event.getRegainReason());
				Bukkit.getPluginManager().callEvent(pberh);
				if(pberh.isCancelled())
				{
					event.setCancelled(true);
					return;
				}
				PVPPlayer.sethealth(PVPPlayer.gethealth() + pberh.getAmount());
			}
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void playerinteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if((event.useItemInHand() == Result.DEFAULT && !event.isBlockInHand()) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			if(isArmor(player.getItemInHand()))
			{
					Damage.calcArmor(player);
					PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
			}

		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(player.getItemInHand().getType() == Material.SLIME_BALL){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.canIntercept() == true && Utils.getTarget(player) != null){
					if(Utils.getTarget(player) instanceof Player){
						Skills.Teleport.intercept(player, (Player) Utils.getTarget(player));
						pvp.setInterceptCooldown(3);
						pvp.setCanIntercept(false);
					}
				}
				else if(pvp.canIntercept() == true)
				{
					Skills.Teleport.dart(player);
					pvp.setInterceptCooldown(3);
					pvp.setCanIntercept(false);
				}
			}
			if(player.getItemInHand().getType() == Material.FIREBALL)
			{
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.canKameha() == true && pvp.getStamina() > 50)
				{
					pvp.setStamina((int)(pvp.getStamina() - 150));
					Kamehameha fire = new Skills.Kamehameha(player);
					pvp.setCanKameha(false);
					pvp.setKamehaCooldown(6);
				}
			}
			else if(player.getItemInHand().getType() == Material.BLAZE_POWDER)
			{
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.getSuperMode() == false && pvp.getStamina() > 250 && pvp.canUseSkill()== true){
					PvpBalance.superSaiens.add(player);
					pvp.setCanUseSkill(false);
					pvp.setSkillCooldown(2);
					pvp.setSuperMode(true);
					player.setAllowFlight(true);
					player.setFlying(true);
					Vector v = new Vector(0,20,0);
					player.setVelocity(v);
					Effects.superSaienToggle(player);
					pvp.getPlayer().setFlySpeed(0.09f);
					pvp.setStamina((int)pvp.getStamina() - 250);
				}
				else if(pvp.getSuperMode() == true){
					pvp.setSuperMode(false);
					PvpBalance.superSaiens.remove(player);
				}
			}
			else if(player.getItemInHand().getType() == Material.DIAMOND_SWORD || player.getItemInHand().getType()== Material.IRON_SWORD
				|| player.getItemInHand().getType() == Material.GOLD_SWORD || player.getItemInHand().getType() == Material.WOOD_SWORD
				|| player.getItemInHand().getType() == Material.IRON_AXE || player.getItemInHand().getType() == Material.GOLD_AXE
				|| player.getItemInHand().getType() == Material.WOOD_AXE || player.getItemInHand().getType() == Material.DIAMOND_AXE
				|| player.getItemInHand().getType() == Material.IRON_HOE || player.getItemInHand().getType() == Material.GOLD_HOE
				|| player.getItemInHand().getType() == Material.WOOD_HOE || player.getItemInHand().getType() == Material.DIAMOND_HOE){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.getComboReady() < 7)
				{
					pvp.setComboReady(pvp.getComboReady() + 2);
					if(pvp.getComboReady() >= 6 && pvp.getStamina() >= 51 && pvp.canUsePileDrive() == false){
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "COMBO READY! HIT PLAYER TO PILEDRIVE, SNEAK HIT TO INCAPACITATE!");
						pvp.setCanUsePileDrive(true);
						pvp.setCanUseGrappleShot(false);
					}
					if(pvp.getComboReady() >=6 && pvp.getStamina() < 51){
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "PILEDRIVE NEEDS 50 STAMINA!");
					}
				}
			}
			if(player.getItemInHand().getType() == Material.BOW){
				PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
				if(pvp.getComboReady() < 7)
				{
					pvp.setComboReady(pvp.getComboReady() + 2);
					if(pvp.getComboReady() >= 7 && pvp.getStamina() >= 25){
						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "GRAPLE READY SHOOT ARROW TO ACTIVATE!");
						pvp.setCanUsePileDrive(false);
						pvp.setCanUseGrappleShot(true);
					}
					if(pvp.getComboReady() >= 7 && pvp.getStamina() < 25){
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GRAPPLE NEEDS 25 STAMINA!");
					}
				}
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(EntityDamageEvent event)
	{
		//if(event.isCancelled())
		//	return;
		if(event instanceof EntityDamageByEntityEvent)
			return;
		int damage = 0;
		if(event.getCause() == DamageCause.SUFFOCATION && event.getEntityType() == EntityType.CHICKEN && event.getEntity().getPassenger() != null)
		{
			event.setCancelled(true);
		}
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			if(event.getCause().equals(DamageCause.STARVATION))
			{
				event.setDamage(0D);
				return;
			}
			else if(PvpHandler.getPvpPlayer(player).isGod())
			{
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.FIRE_TICK))
			{
				int theDamage = SaveLoad.LoadSave.Firetick;
				for(PotionEffect effect:pvp.getPlayer().getActivePotionEffects())
				{
					if(effect.getType() == PotionEffectType.FIRE_RESISTANCE)
					{
						int level = effect.getAmplifier();
						theDamage = theDamage / level;
					}
				}
				for(ItemStack item:pvp.getPlayer().getInventory().getArmorContents())
				{
					theDamage = theDamage - item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE)/2;
				}
				if(theDamage < 0)
				{
					theDamage = 0;
				}
				pvp.uncheckedDamage(theDamage);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.VOID))
			{
				damage = SaveLoad.LoadSave.Voide;
			}
			else if(event.getCause().equals(DamageCause.CONTACT))
			{
				damage = SaveLoad.LoadSave.Contact;
			}
			else if(event.getCause().equals(DamageCause.DROWNING))
			{
				int theDamage = SaveLoad.LoadSave.Firetick;
				for(PotionEffect effect:pvp.getPlayer().getActivePotionEffects())
				{
					if(effect.getType() == PotionEffectType.POISON)
					{
						int level = effect.getAmplifier();
						theDamage = theDamage * level;
					}
					if(effect.getType() == PotionEffectType.DAMAGE_RESISTANCE)
					{
						int level = effect.getAmplifier();
						theDamage = theDamage / level;
					}
				}
				pvp.uncheckedDamage(theDamage);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.POISON))
			{
				pvp.uncheckedDamage(SaveLoad.LoadSave.Poison);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}
			else if(event.getCause().equals(DamageCause.FALL))
			{
				damage = SaveLoad.LoadSave.Fall;
			}
			else if(event.getCause().equals(DamageCause.WITHER))
			{
				pvp.uncheckedDamage(SaveLoad.LoadSave.Wither);
				int prevNoDamageTicks = player.getNoDamageTicks();
				player.damage(0D);
				player.setNoDamageTicks(prevNoDamageTicks);
				event.setCancelled(true);
				return;
			}

			else if(event.getCause().equals(DamageCause.ENTITY_EXPLOSION))
			{
				damage = SaveLoad.LoadSave.Explosion_Mob;
			}
			else if(event.getCause().equals(DamageCause.BLOCK_EXPLOSION))
			{
				damage = SaveLoad.LoadSave.Explosion;
			}
			else if(event.getCause().equals(DamageCause.LIGHTNING))
			{
				damage = SaveLoad.LoadSave.Lightning;
			}
			else if(event.getCause().equals(DamageCause.SUFFOCATION))
			{
				damage = 1;
			}
			//THIS MUST BE LAST ==================================================================================
			else if(!(event.getCause().equals(DamageCause.PROJECTILE)) && !(event.getCause().equals(DamageCause.ENTITY_ATTACK)))
			{
				damage = (int)event.getDamage();
			}
			//THIS MUST BE LAST ================================================================================
			PBEntityDamageEvent pbdEvent = new PBEntityDamageEvent(player, damage, event.getCause());
			Bukkit.getPluginManager().callEvent(pbdEvent);
			if(pbdEvent.isCancelled())
			{
				event.setCancelled(true);
				return;
			}
			pvp.uncheckedDamage(damage);
			event.setCancelled(true);
			player.damage(0D);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(final PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		PVPPlayer dead = PvpHandler.getPvpPlayer(player);
		if(event.getEntity().getKiller() instanceof Player){
			PVPPlayer killer = PvpHandler.getPvpPlayer((Player)event.getEntity().getKiller());
			killer.setMaxStamina((int)(killer.getStamina() + dead.getMaxStamina()/10));
		}
		dead.setIsDead(true);
		
		//player.teleport(player.getWorld().getSpawnLocation());
		Bukkit.getScheduler().scheduleSyncDelayedTask(PvpBalance.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				final PBEntityDeathEvent pbede = new PBEntityDeathEvent(event.getEntity(), event.getDrops(), event.getDroppedExp());
				Bukkit.getPluginManager().callEvent(pbede);
				event.setDroppedExp(pbede.getDropExp());
			}
		},1L);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(event.isShiftClick())
		{
			if(isArmor(event.getCurrentItem()))
			{
    			Player player = (Player) event.getWhoClicked();
    			if(PvpHandler.getPvpPlayer(player) == null)
    			{
    				PVPPlayer newPVP = new PVPPlayer(player);
    				PvpHandler.addPvpPlayer(newPVP);
    			}
    			PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
    		}
    	}
	    if(event.getSlotType() == SlotType.ARMOR)
	    {
	    	Player player = (Player) event.getWhoClicked();
			if(PvpHandler.getPvpPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				PvpHandler.addPvpPlayer(newPVP);
			}
	        PvpHandler.getPvpPlayer(player).setArmorEventLastTick(1);
	    }
	}
	@EventHandler
	public void shotBow(EntityShootBowEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			PVPPlayer pvp = PvpHandler.getPvpPlayer(player);
			pvp.setComboReady(pvp.getComboReady() - 2);
			if(event.getForce() < 0.95){
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU MUST PULL ALL THE WAY BACK TO FIRE BOW!");
				return;
			}
			if(pvp.canUseGrappleShot() == true)
			{
				if(pvp.getStamina() < 25)
				{
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "CANNOT USE GRAPPLE SHOT NOT ENOUGH STAMINA (25)!");
				}
				else if(pvp.getStamina() > 25 && pvp.getSkillCooldown() == 0){
					player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU USE GRAPPLE SHOT!");
					pvp.setGrappleArrow((Arrow)event.getProjectile());
					pvp.setSkillCooldown(5);
					pvp.setGrappleStart(pvp.getPlayer().getLocation());
					pvp.setGrappleEnd(null);
					pvp.setIsUsingGrappleShot(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event)
	{
		Damage.calcArmor(event.getPlayer());
	}
	
	private boolean isArmor(ItemStack is)
	{
		if(is == null)
			return false;
		int check = 0;
		if(is.getType() == Material.DIAMOND_HELMET){
			check = 310;
		}
		else if(is.getType() == Material.DIAMOND_CHESTPLATE){
			check = 311;
		}
		else if(is.getType() == Material.DIAMOND_LEGGINGS){
			check = 312;
		}
		else if(is.getType() == Material.DIAMOND_BOOTS){
			check = 313;
		}
		else if(is.getType() == Material.GOLD_HELMET){
			check = 314;
		}
		else if(is.getType() == Material.GOLD_CHESTPLATE){
			check = 315;
		}
		else if(is.getType() == Material.GOLD_LEGGINGS){
			check = 316;
		}
		else if(is.getType() == Material.GOLD_BOOTS){
			check = 317;
		}
		else if(is.getType() == Material.IRON_HELMET){
			check = 306;
		}
		else if(is.getType() == Material.IRON_CHESTPLATE){
			check = 307;
		}
		else if(is.getType() == Material.IRON_LEGGINGS){
			check = 308;
		}
		else if(is.getType() == Material.IRON_BOOTS){
			check = 309;
		}
		else if(is.getType() == Material.CHAINMAIL_HELMET){
			check = 302;
		}
		else if(is.getType() == Material.CHAINMAIL_CHESTPLATE){
			check = 303;
		}
		else if(is.getType() == Material.CHAINMAIL_LEGGINGS){
			check = 304;
		}
		else if(is.getType() == Material.CHAINMAIL_BOOTS){
			check = 305;
		}
		else if(is.getType() == Material.LEATHER_HELMET){
			check = 298;
		}
		else if(is.getType() == Material.LEATHER_CHESTPLATE){
			check = 299;
		}
		else if(is.getType() == Material.LEATHER_LEGGINGS){
			check = 300;
		}
		else if(is.getType() == Material.LEATHER_BOOTS){
			check = 301;
		}
		switch(check)
		{
		case 298:
		case 299:
		case 300:
		case 301:
		case 302:
		case 303:
		case 304:
		case 305:
		case 306:
		case 307:
		case 308:
		case 309:
		case 310:
		case 311:
		case 312:
		case 313:
		case 314:
		case 315:
		case 316:
		case 317:
			return true;
		default:
			return false;
		}
	}
}
	
		
		