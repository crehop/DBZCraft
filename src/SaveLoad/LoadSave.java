package SaveLoad;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import PvpBalance.PvpBalance;

public class LoadSave
{
	PvpBalance plugin;
	
	//Protection
	public static int Dhelmet = 0, Dchest = 0, Dleggings = 0, Dboots = 0;
	public static int Ihelmet = 0, Ichest = 0, Ileggings = 0, Iboots = 0;
	public static int Ghelmet = 0, Gchest = 0, Gleggings = 0, Gboots = 0;
	public static int Chelmet = 0, Cchest = 0, Cleggings = 0, Cboots = 0;
	public static int Lhelmet = 0, Lchest = 0, Lleggings = 0, Lboots = 0;
	public static int Ehelmet = 0, Echest = 0, Eleggings = 0, Eboots = 0;
	public static int protect = 0, HealPot = 0, Regen = 0;
	
	//Damage
	
	public static int HitCooldown = 0;
	public static int Diamond = 0, Iron = 0, Gold = 0, Stone = 0, Wood = 0,Bow = 0, Normal = 0;
	public static int Sharpness = 0, Multi = 0, Explosion_Mob = 0;
	public static int Firetick = 0, Voide = 0, Contact = 0, Drowning = 0, Poison = 0, Fall = 0, Wither = 0, Explosion = 0, Lightning = 0;
	
	public LoadSave(PvpBalance plugin)
	{
		this.plugin = plugin;
		
		Bow = plugin.getSDamage().getCustomConfig().getInt("Weapon.Bow");
		Diamond = plugin.getSDamage().getCustomConfig().getInt("Weapon.Diamond");
		Iron = plugin.getSDamage().getCustomConfig().getInt("Weapon.Iron");
		Gold = plugin.getSDamage().getCustomConfig().getInt("Weapon.Gold");
		Stone = plugin.getSDamage().getCustomConfig().getInt("Weapon.Stone");
		Wood = plugin.getSDamage().getCustomConfig().getInt("Weapon.Wood");
		Normal = plugin.getSDamage().getCustomConfig().getInt("Weapon.Normal");
		HitCooldown = plugin.getSDamage().getCustomConfig().getInt("Weapon.HitCooldown");
		
		
		Sharpness = plugin.getSDamage().getCustomConfig().getInt("Enchant.Sharpness");
		Multi = plugin.getSDamage().getCustomConfig().getInt("Mobs.Multiplier");
		Explosion_Mob = plugin.getSDamage().getCustomConfig().getInt("Mobs.ExplosionMob");
		
		HealPot = plugin.getSDamage().getCustomConfig().getInt("Natural.HealPot");
		Regen = plugin.getSDamage().getCustomConfig().getInt("Natural.Regen");
		Firetick = plugin.getSDamage().getCustomConfig().getInt("Natural.FireTick");
		Voide = plugin.getSDamage().getCustomConfig().getInt("Natural.Void");
		Contact = plugin.getSDamage().getCustomConfig().getInt("Natural.Contact");
		Drowning = plugin.getSDamage().getCustomConfig().getInt("Natural.Drowning");
		Poison = plugin.getSDamage().getCustomConfig().getInt("Natural.Poison");
		Fall = plugin.getSDamage().getCustomConfig().getInt("Natural.Fall");
		Wither = plugin.getSDamage().getCustomConfig().getInt("Natural.Wither");
		Explosion = plugin.getSDamage().getCustomConfig().getInt("Natural.Explosion");
		Lightning = plugin.getSDamage().getCustomConfig().getInt("Natural.Lightning");
		
		
		Dhelmet = plugin.getProtection().getCustomConfig().getInt("Diamond.Helmet");
		Dchest = plugin.getProtection().getCustomConfig().getInt("Diamond.Chest");
		Dleggings = plugin.getProtection().getCustomConfig().getInt("Diamond.Leggings");
		Dboots = plugin.getProtection().getCustomConfig().getInt("Diamond.Boots");
		
		Ihelmet = plugin.getProtection().getCustomConfig().getInt("Iron.Helmet");
		Ichest = plugin.getProtection().getCustomConfig().getInt("Iron.Chest");
		Ileggings = plugin.getProtection().getCustomConfig().getInt("Iron.Leggings");
		Iboots = plugin.getProtection().getCustomConfig().getInt("Iron.Boots");
		
		Ghelmet = plugin.getProtection().getCustomConfig().getInt("Gold.Helmet");
		Gchest = plugin.getProtection().getCustomConfig().getInt("Gold.Chest");
		Gleggings = plugin.getProtection().getCustomConfig().getInt("Gold.Leggings");
		Gboots  = plugin.getProtection().getCustomConfig().getInt("Gold.Boots");
		
		Chelmet = plugin.getProtection().getCustomConfig().getInt("Chain.Helmet");
		Cchest = plugin.getProtection().getCustomConfig().getInt("Chain.Chest");
		Cleggings = plugin.getProtection().getCustomConfig().getInt("Chain.Leggings");
		Cboots  = plugin.getProtection().getCustomConfig().getInt("Chain.Boots");
		
		Lhelmet = plugin.getProtection().getCustomConfig().getInt("Leather.Helmet");
		Lchest = plugin.getProtection().getCustomConfig().getInt("Leather.Chest");
		Lleggings = plugin.getProtection().getCustomConfig().getInt("Leather.Leggings");
		Lboots  = plugin.getProtection().getCustomConfig().getInt("Leather.Boots");
		
		Ehelmet = plugin.getProtection().getCustomConfig().getInt("Epic.Helmet");
		Echest = plugin.getProtection().getCustomConfig().getInt("Epic.Chest");
		Eleggings = plugin.getProtection().getCustomConfig().getInt("Epic.Leggings");
		Eboots = plugin.getProtection().getCustomConfig().getInt("Epic.Boots");
		
		protect = plugin.getProtection().getCustomConfig().getInt("Enchant.Protection");
	}
	
	public static void reloadValues(PvpBalance plugin, Player player)
	{
		Bow = plugin.getSDamage().getCustomConfig().getInt("Weapon.Bow");
		player.sendMessage(ChatColor.DARK_GREEN + "Bow Base: " + ChatColor.RED + LoadSave.Bow);
		Diamond = plugin.getSDamage().getCustomConfig().getInt("Weapon.Diamond");
		player.sendMessage(ChatColor.DARK_GREEN + "Diamond Sword Base: " + ChatColor.RED + LoadSave.Diamond);
		Iron = plugin.getSDamage().getCustomConfig().getInt("Weapon.Iron");
		player.sendMessage(ChatColor.DARK_GREEN + "Iron Sword Base: " + ChatColor.RED + LoadSave.Iron);
		Gold = plugin.getSDamage().getCustomConfig().getInt("Weapon.Gold");
		player.sendMessage(ChatColor.DARK_GREEN + "Gold Sword Base: " + ChatColor.RED + LoadSave.Gold);
		Stone = plugin.getSDamage().getCustomConfig().getInt("Weapon.Stone");
		player.sendMessage(ChatColor.DARK_GREEN + "Stone Sword Base: " + ChatColor.RED + LoadSave.Stone);
		Wood = plugin.getSDamage().getCustomConfig().getInt("Weapon.Wood");
		player.sendMessage(ChatColor.DARK_GREEN + "Wooden Sword Base: " + ChatColor.RED + LoadSave.Wood);
		Normal = plugin.getSDamage().getCustomConfig().getInt("Weapon.Normal");
		player.sendMessage(ChatColor.DARK_GREEN + "Punch Base: " + ChatColor.RED + LoadSave.Normal);
		HitCooldown = plugin.getSDamage().getCustomConfig().getInt("Weapon.HitCooldown");
		
		
		Sharpness = plugin.getSDamage().getCustomConfig().getInt("Enchant.Sharpness");
		player.sendMessage(ChatColor.DARK_GREEN + "Sharpness Modifier: " + ChatColor.RED + LoadSave.Sharpness);
		Multi = plugin.getSDamage().getCustomConfig().getInt("Mobs.Multiplier");
		player.sendMessage(ChatColor.DARK_GREEN + "Mob Damage Multiplier: " + ChatColor.RED + LoadSave.Multi);
		Explosion_Mob = plugin.getSDamage().getCustomConfig().getInt("Mobs.ExplosionMob");
		player.sendMessage(ChatColor.DARK_GREEN + "Creeper Explosion: " + ChatColor.RED + LoadSave.Explosion_Mob);
		
		HealPot = plugin.getSDamage().getCustomConfig().getInt("Natural.HealPot");
		player.sendMessage(ChatColor.DARK_GREEN + "Health Potion Heal: " + ChatColor.GREEN + LoadSave.HealPot);
		Regen = plugin.getSDamage().getCustomConfig().getInt("Natural.Regen");
		player.sendMessage(ChatColor.DARK_GREEN + "Health Potion Heal: " + ChatColor.GREEN + LoadSave.Regen);
		Firetick = plugin.getSDamage().getCustomConfig().getInt("Natural.FireTick");
		player.sendMessage(ChatColor.DARK_GREEN + "Fire Tick: " + ChatColor.RED + LoadSave.Firetick);
		Voide = plugin.getSDamage().getCustomConfig().getInt("Natural.Void");
		player.sendMessage(ChatColor.DARK_GREEN + "Void Damage: " + ChatColor.RED + LoadSave.Voide);
		Contact = plugin.getSDamage().getCustomConfig().getInt("Natural.Contact");
		player.sendMessage(ChatColor.DARK_GREEN + "Cacti: " + ChatColor.RED + LoadSave.Contact);
		Drowning = plugin.getSDamage().getCustomConfig().getInt("Natural.Drowning");
		player.sendMessage(ChatColor.DARK_GREEN + "Drowning: " + ChatColor.RED + LoadSave.Drowning);

		Poison = plugin.getSDamage().getCustomConfig().getInt("Natural.Poison");
		player.sendMessage(ChatColor.DARK_GREEN + "Poison: " + ChatColor.RED + LoadSave.Poison);
		Fall = plugin.getSDamage().getCustomConfig().getInt("Natural.Fall");
		player.sendMessage(ChatColor.DARK_GREEN + "Fall Damage: " + ChatColor.RED + LoadSave.Fall);
		Wither = plugin.getSDamage().getCustomConfig().getInt("Natural.Wither");
		player.sendMessage(ChatColor.DARK_GREEN + "Wither Damage: " + ChatColor.RED + LoadSave.Wither);
		Explosion = plugin.getSDamage().getCustomConfig().getInt("Natural.Explosion");
		player.sendMessage(ChatColor.DARK_GREEN + "Explosion: " + ChatColor.RED + LoadSave.Explosion);
		Lightning = plugin.getSDamage().getCustomConfig().getInt("Natural.Lightning");
		player.sendMessage(ChatColor.DARK_GREEN + "Lightning: " + ChatColor.RED + LoadSave.Lightning);

		
		
		Dhelmet = plugin.getProtection().getCustomConfig().getInt("Diamond.Helmet");
		Dchest = plugin.getProtection().getCustomConfig().getInt("Diamond.Chest");
		Dleggings = plugin.getProtection().getCustomConfig().getInt("Diamond.Leggings");
		Dboots = plugin.getProtection().getCustomConfig().getInt("Diamond.Boots");
		
		Ihelmet = plugin.getProtection().getCustomConfig().getInt("Iron.Helmet");
		Ichest = plugin.getProtection().getCustomConfig().getInt("Iron.Chest");
		Ileggings = plugin.getProtection().getCustomConfig().getInt("Iron.Leggings");
		Iboots = plugin.getProtection().getCustomConfig().getInt("Iron.Boots");
		
		Ghelmet = plugin.getProtection().getCustomConfig().getInt("Gold.Helmet");
		Gchest = plugin.getProtection().getCustomConfig().getInt("Gold.Chest");
		Gleggings = plugin.getProtection().getCustomConfig().getInt("Gold.Leggings");
		Gboots  = plugin.getProtection().getCustomConfig().getInt("Gold.Boots");
		
		Chelmet = plugin.getProtection().getCustomConfig().getInt("Chain.Helmet");
		Cchest = plugin.getProtection().getCustomConfig().getInt("Chain.Chest");
		Cleggings = plugin.getProtection().getCustomConfig().getInt("Chain.Leggings");
		Cboots  = plugin.getProtection().getCustomConfig().getInt("Chain.Boots");
		
		Lhelmet = plugin.getProtection().getCustomConfig().getInt("Leather.Helmet");
		Lchest = plugin.getProtection().getCustomConfig().getInt("Leather.Chest");
		Lleggings = plugin.getProtection().getCustomConfig().getInt("Leather.Leggings");
		Lboots  = plugin.getProtection().getCustomConfig().getInt("Leather.Boots");
		
		Ehelmet = plugin.getProtection().getCustomConfig().getInt("Epic.Helmet");
		Echest = plugin.getProtection().getCustomConfig().getInt("Epic.Chest");
		Eleggings = plugin.getProtection().getCustomConfig().getInt("Epic.Leggings");
		Eboots = plugin.getProtection().getCustomConfig().getInt("Epic.Boots");
		
		protect = plugin.getProtection().getCustomConfig().getInt("Enchant.Protection");

		int fullDiamond = Dhelmet+Dchest+Dleggings+Dboots;
		int fullEpic = Ehelmet + Echest + Eleggings + Eboots;
		int fullGold = Ghelmet + Gchest + Gleggings + Eboots;
		int fullIron = Ihelmet + Ichest + Ileggings + Iboots;
		int prot1 = protect * 4;
		int prot2 = protect * 8;
		int prot3 = protect * 12;
        int prot4 = protect * 16;
		player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Set: " + fullEpic);
		player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot1: " + (fullEpic + prot1));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot2: " + (fullEpic + prot2));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot3: " + (fullEpic + prot3));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Epic Prot4: " + (fullEpic + prot4));
		
		player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Set: " + fullDiamond);
		player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot1: " + (fullDiamond + prot1));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot2: " + (fullDiamond + prot2));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot3: " + (fullDiamond + prot3));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Diamond Prot4: " + (fullDiamond + prot4));

		player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Set: " + fullIron);
		player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot1: " + (fullIron + prot1));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot2: " + (fullIron + prot2));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot3: " + (fullIron + prot3));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Iron Prot4: " + (fullIron + prot4));

		player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Set: " + fullGold);
		player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot1: " + (fullGold + prot1));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot2: " + (fullGold + prot2));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot3: " + (fullGold + prot3));
		player.sendMessage(ChatColor.DARK_GREEN + "Full Gold Prot4: " + (fullGold + prot4));
	}
}
