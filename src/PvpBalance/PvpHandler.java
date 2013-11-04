package PvpBalance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import Skills.Kamehameha;

public class PvpHandler
{
	private static List<PVPPlayer> players = new ArrayList<PVPPlayer>();
	public static List<Kamehameha> kameya = new ArrayList<Kamehameha>();

	private static int pvpTimer;
	private static int defencePotionCD;
	private static int offencePotionCD;
	
	public static PVPPlayer getPvpPlayer(Player player)
	{
		for(PVPPlayer pp : players)
		{
			if(pp == null)
				continue;
			if(pp.getPlayer() == player)
				return pp;
		}
		return null;
	}
	public static Kamehameha getKamehameha(Player shooter)
	{
		for(Kamehameha fire:PvpHandler.kameya)
		{
			if(fire.getShooter().getName() == shooter.getName())
			{
				return fire;
			}
		}
		return null;
	}
	public static boolean isInList(PVPPlayer pp)
	{
		for(PVPPlayer pp1 : players)
		{
			if(pp1 == null)
			{
				continue;
			}
			if(pp1.getPlayer().getDisplayName() == pp.getPlayer().getDisplayName())
			{
				return true;
			}
		}
		return false;
	}
	
	public static void addPvpPlayer(PVPPlayer pp)
	{
		if(isInList(pp) == false){
			players.add(pp);
		}
		
	}
	public static boolean removePvpPlayer(PVPPlayer pp)
	{
		return players.remove(pp);
	}
	
	public static boolean removePvpPlayer(Player player)
	{
		for(PVPPlayer pp : players)
		{
			if(pp == null)
				continue;
			if(pp.getPlayer().equals(player))
				return players.remove(pp);
		}
		return false;
	}
	
	public static List<PVPPlayer> getPvpPlayers()
	{
		return players;
	}
	
	public static void clear()
	{
		players.clear();
	}
	
	public static int getDefencePotionCD()
	{
		return defencePotionCD;
	}
	
	public static int getOffencePotionCD()
	{
		return offencePotionCD;
	}
	
	public static int getPvpTimer()
	{
		return pvpTimer;
	}
	
	public static void load()
	{
		File folder = new File("plugins/PvpBalance");
		if(!folder.exists())
		{
			folder.mkdirs();
		}
		File tempFile = new File("plugins/PvpBalance/pvp.yml");
		if(!tempFile.exists())
		{
			try
			{
				tempFile.createNewFile();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		FileConfiguration config = new YamlConfiguration();
		try
		{
			config.load(tempFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		defencePotionCD = config.getInt("DefencePotionCD");
		offencePotionCD = config.getInt("OffencePotionCD");
		pvpTimer = config.getInt("PVPTimer");
	}
}
