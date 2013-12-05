package PvpBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import Party.CommandParty;
import Party.PartyListener;
import SaveLoad.LoadSave;
import SaveLoad.Save;
import Skills.Kamehameha;
import Util.ItemUtils;
import Util.MenuHandler;

public class PvpBalance extends JavaPlugin
{
	public static final Logger logger = Logger.getLogger("Minecraft");
    public static PvpBalance plugin;
	public LoadSave LoadSave;
	private final float SPEED = 0.2F;
	private final int AMOUNT = 300;
	private static int everyOther = 0;
	private boolean debug = false;
	private static List<Chicken> chickens = new ArrayList<Chicken>();
	public static List<Player> superSaiens = new ArrayList<Player>();
	public static List<Player> superCharge = new ArrayList<Player>();
	public static List<Player> tackled = new ArrayList<Player>();
	private boolean faction = false;
	
	private Save sDamage, protection;
	@Override
 	public void onDisable()
 	{
		for(PVPPlayer pp : PvpHandler.getPvpPlayers())
		{
			if(pp == null)
				continue;
			pp.setCombatCoolDown(0);
		}
		PvpHandler.clear();
	 	PluginDescriptionFile pdfFile = this.getDescription();
	 	logger.info(pdfFile.getName() + " Has Been Disabled!!");
 	}
 
 	public void onEnable()
 	{
	 	plugin = this;
	 	
	 	for(Player p : Bukkit.getOnlinePlayers())
	 	{
	 		if(p == null)
	 			continue;
	 		PVPPlayer pp = new PVPPlayer(p);
	 		PvpHandler.addPvpPlayer(pp);
	 	}
	 	
	 	PvpHandler.load();
	 	
	 	sDamage = new Save(this, "Damage.yml");
	 	protection = new Save(this, "Protection.yml");
	 	LoadSave  = new LoadSave(this);
	    getCommand("party").setExecutor(new CommandParty(this));
	 	
	 	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "Scoreboard");
	 	
	 	Damage.LoadSave = LoadSave;
	 	
	 	getServer().getPluginManager().registerEvents(new DBZListener(this, LoadSave), this);
	 	getServer().getPluginManager().registerEvents(new PartyListener(), this);
	 	getServer().getPluginManager().registerEvents(new PvpListener(), this);
	 	getServer().getPluginManager().registerEvents(new MenuHandler(), this);

	 	
	 	PluginDescriptionFile pdfFile = this.getDescription();

		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
		    @Override  
		    public void run()
		    {
		    	if(everyOther == 0)
		    	{
		    		everyOther = 1;
		    		applySuperEffects();
		    		applyBallEffects();
		    		applyChargeEffects();
			    	for(Player all : Bukkit.getServer().getOnlinePlayers())
			    	{
							try
							{
					    		// BASIC EFFECT APLICATIONS ==========================================================================================
					    		//ON FIRE
					    		if(all.getFireTicks() > 1){
					    			Effects.igniteFirePlayers(all);
					    		}	    
					    		//ENCHANTED SWORD
					    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
					    		{
					    			Effects.effectSharpnessPlayers(all);
					    		}
					    		//CONFUSED
					    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
					    		{
					    			Effects.effectConfuse(all);
					    		}
					    		//WITHERED
					    		if(all.getActivePotionEffects().toString().contains("WITHER"))
					    		{
					    			Effects.effectWither(all);
					    		}	
					    		//POISONED
					    		if(all.getActivePotionEffects().toString().contains("POISON"))
					    		{
					    			Effects.effectPoison(all);
					    		}
					    		//BLIND
					    		if(all.getActivePotionEffects().toString().contains("Blindness"))
					    		{
					    			Effects.effectBlind(all);
					    		}
					    		//SPEED POT
					    		if(all.getActivePotionEffects().toString().contains("SPEED"))
					    		{
					    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
					    		}
					    		if(all.getActivePotionEffects().toString().contains("SLOW"))
					    		{
					    			Effects.effectSlow(all);
					    		}
					    		//REGENERATING
					    		if(all.getActivePotionEffects().toString().contains("REGENERATION"))
					    		{
					    			Effects.effectHealthPlayers(all, (float) 0.3, 30);
					    			PVPPlayer pvp = PvpHandler.getPvpPlayer(all);
					    			pvp.sethealth(pvp.gethealth() + SaveLoad.LoadSave.Regen);
					    		}
					    		//LOW HEALTH
					    		if(all.getHealth() < 9f)
					    		{
					    			Effects.bleed(all);
					    		}
					    		//PVP ABILITIES ====================================================================================
							
					    }
						catch (Exception e1)
						{
							e1.printStackTrace();
							logger.info("Main Effect!");
						}
				}
		    }
		    else if(everyOther == 1)
		    {
		    	everyOther = 2;
		    	applySuperEffects();
		    	applyBallEffects();
		    	applyChargeEffects();
	    		//ARMOR EFFECTS =================================================================================
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
		    		if(all == null)
		    			continue;
		    		try
		    		{
		    			ArmorEffects.checkForGlowTick(all);
		    		}
		    		catch (Exception e1)
		    		{
							e1.printStackTrace();
							logger.info("Main ArmorEffect!");
					}
		    		
				}
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
						try
						{
				    		// BASIC EFFECT APLICATIONS ==========================================================================================
				    		//ON FIRE
				    		if(all.getFireTicks() > 1){
				    			Effects.igniteFirePlayers(all);
				    		}	    
				    		//ENCHANTED SWORD
				    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
				    		{
				    			Effects.effectSharpnessPlayers(all);
				    		}
				    		//CONFUSED
				    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
				    		{
				    			Effects.effectConfuse(all);
				    		}
				    		//WITHERED
				    		if(all.getActivePotionEffects().toString().contains("WITHER"))
				    		{
				    			Effects.effectWither(all);
				    		}	
				    		//POISONED
				    		if(all.getActivePotionEffects().toString().contains("POISON"))
				    		{
				    			Effects.effectPoison(all);
				    		}
				    		//BLIND
				    		if(all.getActivePotionEffects().toString().contains("Blindness"))
				    		{
				    			Effects.effectBlind(all);
				    		}
				    		//SPEED POT
				    		if(all.getActivePotionEffects().toString().contains("SPEED"))
				    		{
				    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
				    		}
				    		if(all.getActivePotionEffects().toString().contains("SLOW"))
				    		{
				    			Effects.effectSlow(all);
				    		}
				    		//REGENERATING
				    		//SPRINTING
				    		//LOW HEALTH
				    		if(all.getHealth() < 9)
				    		{
				    			Effects.bleed(all);
				    		}
				    		//PVP ABILITIES ====================================================================================
						
				    }
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Effect!");
					}
		    	}
		    }
		    //CLEANUP
		    else if(everyOther == 2)
		    {
		    	everyOther = 3;
		    	//CLEANUP FIREBALLS
		    	for(int fireball = 0; fireball <= PvpHandler.kameya.size(); fireball++)
		    	{
		    		Fireball ball = PvpHandler.kameya.get(fireball).getFireball();
		    		if(ball == null || ball.isValid() == false || ball.getTicksLived() > 300)
		    		{
		    			PvpHandler.kameya.remove(ball);
		    			ball.remove();
		    			continue;
		    		}
		    	}
		    	applySuperEffects();
		    	applyChargeEffects();
		    	applyBallEffects();
		    	for(Chicken chick:chickens)
		    	{
		    		if(chick.getPassenger() == null)
		    		{
		    			chick.remove();
		    		    PvpBalance.chickenList().remove(chick);
		    		}
		    	}
		    	applyBallEffects();
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
						try
						{
				    		// BASIC EFFECT APLICATIONS ==========================================================================================
				    		//ON FIRE
				    		if(all.getFireTicks() > 1){
				    			Effects.igniteFirePlayers(all);
				    		}	    
				    		//ENCHANTED SWORD
				    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
				    		{
				    			Effects.effectSharpnessPlayers(all);
				    		}
				    		//CONFUSED
				    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
				    		{
				    			Effects.effectConfuse(all);
				    		}
				    		//WITHERED
				    		if(all.getActivePotionEffects().toString().contains("WITHER"))
				    		{
				    			Effects.effectWither(all);
				    		}	
				    		//POISONED
				    		if(all.getActivePotionEffects().toString().contains("POISON"))
				    		{
				    			Effects.effectPoison(all);
				    		}
				    		//BLIND
				    		if(all.getActivePotionEffects().toString().contains("Blindness"))
				    		{
				    			Effects.effectBlind(all);
				    		}
				    		//SPEED POT
				    		if(all.getActivePotionEffects().toString().contains("SPEED"))
				    		{
				    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
				    		}
				    		if(all.getActivePotionEffects().toString().contains("SLOW"))
				    		{
				    			Effects.effectSlow(all);
				    		}
				    		//REGENERATING
				    		//SPRINTING
				    		//LOW HEALTH
				    		if(all.getHealth() < 9)
				    		{
				    			Effects.bleed(all);
				    		}
				    		//PVP ABILITIES ====================================================================================
						
				    }
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Effect!");
					}
		    	}
		    }
		    else if(everyOther == 3)
		    {
		    	everyOther = 0;
		    	tackled();
		    	applySuperEffects();
		    	applyBallEffects();
		    	applyChargeEffects();
		    	for(PVPPlayer all: PvpHandler.getPvpPlayers())
		    	{
					try
					{
						all.tick();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Tick!");
					}
				}
		    	for(Player all : Bukkit.getServer().getOnlinePlayers())
		    	{
						try
						{
				    		// BASIC EFFECT APLICATIONS ==========================================================================================
				    		//ON FIRE
				    		if(all.getFireTicks() > 1){
				    			Effects.igniteFirePlayers(all);
				    		}	    
				    		//ENCHANTED SWORD
				    		if(all.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) || all.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE))
				    		{
				    			Effects.effectSharpnessPlayers(all);
				    		}
				    		//CONFUSED
				    		if(all.getActivePotionEffects().toString().contains("CONFUSION"))
				    		{
				    			Effects.effectConfuse(all);
				    		}
				    		//WITHERED
				    		if(all.getActivePotionEffects().toString().contains("WITHER"))
				    		{
				    			Effects.effectWither(all);
				    		}	
				    		//POISONED
				    		if(all.getActivePotionEffects().toString().contains("POISON"))
				    		{
				    			Effects.effectPoison(all);
				    		}
				    		//BLIND
				    		if(all.getActivePotionEffects().toString().contains("Blindness"))
				    		{
				    			Effects.effectBlind(all);
				    		}
				    		//SPEED POT
				    		if(all.getActivePotionEffects().toString().contains("SPEED"))
				    		{
				    			Effects.effectSpeedPlayers(all, SPEED, AMOUNT);
				    		}
				    		if(all.getActivePotionEffects().toString().contains("SLOW"))
				    		{
				    			Effects.effectSlow(all);
				    		}
				    		//REGENERATING
				    		//SPRINTING
				    		if(all.isSprinting())
				    		{
				    			Effects.effectSprintPlayers(all, SPEED, (int)SPEED*5);
				    		}
				    		//LOW HEALTH
				    		if(all.getHealth() < 9)
				    		{
				    			Effects.bleed(all);
				    		}
				    		//PVP ABILITIES ====================================================================================
						
				    }
					catch (Exception e1)
					{
						e1.printStackTrace();
						logger.info("Main Effect!");
					}
		    	}
		    }
		}
	}, 0L, 5L);
		
	 	logger.info(pdfFile.getName() + " Has Been Enabled!!");
 	}
	
	protected void applyChargeEffects() {
		for(Player player:PvpBalance.superCharge)
		{
			Effects.superRegen(player);
		}
		
	}

	protected void applyBallEffects() {
		for(Kamehameha ball:PvpHandler.kameya)
    	{
			for(Entity e:ball.getFireball().getNearbyEntities(3, 3, 3))
	    	{
				if(e instanceof Player){
					if(e != ball.getShooter()){
						Fireball shoot = ball.getFireball();
						shoot.teleport(e.getLocation());
					}
				}
	    	}
    		Effects.kameya(ball.getFireball());
    	}
		
	}

	public boolean isDebug()
	{
		return debug;
	}
	
	public void setDebug(boolean value)
	{
		debug = value;
	}
	
	public boolean hasFaction()
	{
		return faction;
	}
	
	
	public Save getSDamage()
	{
		return sDamage;
	}
	
	public Save getProtection()
	{
		return protection;
	}
	public static void applySuperEffects()
	{	
		for(Player player:superSaiens)
		{
			Effects.superSaien(player);
			Material check = player.getLocation().subtract(0, 3, 0).getBlock().getType();
			if(check != Material.AIR)
			{
				Effects.superSaienGroundShake(player);
			}
			else
			{
				player.setFlySpeed((float) 0.07);
				if(player.getLocation().getY()> 100){
					player.setFlying(false);
					player.sendMessage(ChatColor.RED + "YOU'VE FLOWN TOO HIGH AND DROP FROM THE SKY");
				}
			}
		}
	}
	public static void tackled()
	{
		for(int tackledPlayer = 0; tackledPlayer < tackled.size(); tackledPlayer++)
		{
			Player player = tackled.get(tackledPlayer);
			if(player.getLocation().subtract(0, 2, 0).getBlock().getType() != Material.AIR)
			{
	        	TNTPrimed primed = player.getLocation().getWorld().spawn(player.getLocation().add(0, 0, 0), TNTPrimed.class);
	        	primed.setYield(4);
	        	primed.setFuseTicks(0);
	        	Effects.impactEffect(primed.getLocation());
	        	Effects.impactEffect(primed.getLocation());
	        	Effects.impactEffect(primed.getLocation());
	        	tackled.remove(player);
			}
			else
			{
				Vector target = new Vector(0,-10,0);
				player.setVelocity(target);
			}
		}
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		//Location location = player.getLocation();
		if(commandLabel.equalsIgnoreCase("pvpdebug") && player.hasPermission("particles.admin"))
		{
			if(debug == false)
			{
				debug = true;
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG MODE ENABLED");
				Bukkit.broadcastMessage("PVP PLAYER: " + PvpHandler.getPvpPlayer(player));
				Bukkit.broadcastMessage("PVP PLAYER NAME: " + PvpHandler.getPvpPlayer(player).getPlayer().getName());
			}
			else
				debug = false;
			if(PvpHandler.getPvpPlayer(player) == null)
			{
				PVPPlayer newPVP = new PVPPlayer(player);
				PvpHandler.addPvpPlayer(newPVP);
			}
		}
		else if(commandLabel.equalsIgnoreCase("pvpgod") && player.hasPermission("particles.admin"))
		{
			PVPPlayer PVPPlayer = PvpHandler.getPvpPlayer(player);
			if(PVPPlayer.isGod())
			{
				PVPPlayer.setGod(false);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "GOD MODE DISABLED");
			}
			else
			{
				PVPPlayer.setGod(true);
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "GOD MODE ENABLED");
				player.setFoodLevel(20);
				PVPPlayer.sethealth(PVPPlayer.getMaxHealth());
			}
		}
		else if(commandLabel.equalsIgnoreCase("pvpver") && player.hasPermission("particles.admin"))
		{
			ItemStack paper = new ItemStack(Material.PAPER);
			List<String> lore = new ArrayList<String>();
			lore.add("Polishing Cloth " + ChatColor.MAGIC + " " + ArmorEffects.CODE_PAPER);
			lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			ItemUtils.setLore(paper, lore);
			ItemUtils.setName(paper, ChatColor.GOLD + "Polishing Cloth");
			player.getInventory().addItem(paper);
			player.sendMessage(ChatColor.GREEN + "[Armor Polish]: Greetings Administrator " + player.getDisplayName() + " a cloth has been provided");
			player.getInventory().addItem(paper);
			player.sendMessage("VERSION 1.6");
			SaveLoad.LoadSave.reloadValues(this, player);
			player.setFoodLevel(0);
		}
		else if(commandLabel.equalsIgnoreCase("polish"))
		{
			ArmorEffects.polish(player);
		}
		else if(commandLabel.equalsIgnoreCase("pvpstats"))
		{
			//PVPPlayer pvpPlayer = Commands.getPVPPlayer(player); 
//			player.sendMessage(ChatColor.GREEN + "IF NULL HERE ERROR :" + pvpPlayer);
//			player.sendMessage(ChatColor.GREEN + "PLAYER NAME : " + pvpPlayer.getPlayer().getName());
//			player.sendMessage(ChatColor.GREEN + "MAXIMUM HEALTH  : " + pvpPlayer.getMaxHealth());
//			player.sendMessage(ChatColor.GREEN + "Health THIS TICK: " + pvpPlayer.gethealth());
//			player.sendMessage(ChatColor.GREEN + "Health LAST TICK: " + pvpPlayer.getHealthLastTick());
//			player.sendMessage(ChatColor.GREEN + "COOLDOWN (regen): " + pvpPlayer.getCooldown());
//			player.sendMessage(ChatColor.GREEN + "COMBAT COOLDOWN (FASTREGEN): " + pvpPlayer.getCombatCoolDown());
//			player.sendMessage(ChatColor.GREEN + "IS DEAD T/F : " + pvpPlayer.isDead);
//			player.sendMessage(ChatColor.GREEN + "IS IN COMBAT T/F : " + pvpPlayer.isInCombat);
//			player.sendMessage(ChatColor.GREEN + "HITCOOLDOWN : " + pvpPlayer.getHitCooldown());
//			player.sendMessage(ChatColor.GREEN + "CAN REGEN HEALTH T/F: " + pvpPlayer.canRegen);
			PVPPlayer pp = PvpHandler.getPvpPlayer(player);
			if(pp.isPvpstats())
			{
				pp.setPvpstats(false);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DAMAGE CHECK MODE DISABLED SAY /PVPSTATS again to Enable");
			}
			else
			{
				pp.setPvpstats(true);
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "DAMAGE CHECK MODE ENABLED SAY /PVPSTATS again to Disable");
			}
		}
        return true;
	}
	public static List<Chicken> chickenList()
	{
		return PvpBalance.chickens;
	}

}