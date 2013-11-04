package PvpBalance;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import Util.ItemUtils;

public class ArmorEffects
{
	public static final String CODE_PAPER = "32157";
	public static final String CODE_ARMOR = "32188";

	public static void checkForGlowTick(Player player)
	{
		if(player == null)
			return;
		LeatherArmorMeta[] metas = new LeatherArmorMeta[5];
		for(ItemStack item : player.getInventory().getArmorContents())
		{
			if(item == null)
				continue;
			if(item.hasItemMeta() && (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
					item.getType() == Material.LEATHER_HELMET|| item.getType() == Material.LEATHER_LEGGINGS))
			{
				PVPPlayer pvpPlayer = PvpHandler.getPvpPlayer(player);
				if(pvpPlayer == null)
					return;
				LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
				if(ItemUtils.getLore(item) == null || ItemUtils.getLore(item).isEmpty())
					return;
				if(item.getItemMeta().getLore().toString().contains(CODE_ARMOR))
				{
					if(meta.getColor().toString().contains("A06540")){
						continue;
					}
					int nr = (Fade.type(item)-1);
					if(metas[nr] != null)
					{
						meta.setColor(metas[nr].clone().getColor());
						item.setItemMeta(meta);
					}
					else
					{
						if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
								item.getType() == Material.LEATHER_HELMET|| item.getType() == Material.LEATHER_LEGGINGS){
							metas[nr] = glow(item,pvpPlayer);
							continue;
						}
					}
				}
				else if(item.getItemMeta().getLore().size() > 1)
				{
					if(item.getItemMeta().getLore().toString().contains(CODE_ARMOR))
					{
						if(meta.getColor().toString().contains("A06540")){
							continue;
						}
						int nr1 = (Fade.type(item)-1);
						if(metas[nr1] != null)
						{
							meta.setColor(metas[nr1].clone().getColor());
							item.setItemMeta(meta);
						}
						else
						{
							if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
									item.getType() == Material.LEATHER_HELMET|| item.getType() == Material.LEATHER_LEGGINGS){
								metas[nr1] = glow(item,pvpPlayer);
								continue;
							}
						}
					}
				}
			}
		}
	}

	public static LeatherArmorMeta glow(ItemStack item, PVPPlayer pvpPlayer)
	{
			LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
			int blue = meta.getColor().getBlue();
			int red = meta.getColor().getRed();
			int green = meta.getColor().getGreen();
			if(Fade.type(item) == 1)
			{
				if(blue >= 90 && red >= 90 && green >= 90)
				{
					pvpPlayer.setColorUp(false);
				}
				else if(blue <= 0 && red <= 0 && green <= 0)
				{
					pvpPlayer.setColorUp(true);
				}
				
				if(pvpPlayer.isColorUp())
				{
					red += 9;
					blue += 9;
					green += 9;
				}
				else
				{
					red -= 9;
					blue -= 9;
					green -= 9;
				}
			}
			else if(Fade.type(item) == 2)
			{
				if(red >= 0 && green >= 0 && blue >= 248)
				{
					pvpPlayer.setColorUp(false);
				}
				else if(red <= 0 && green <= 0 && blue <= 128)
				{
					pvpPlayer.setColorUp(true);
				}
				if(pvpPlayer.isColorUp())
				{
					red += 0;
					green += 0;
					blue += 12;
				}
				else
				{
					red -= 0;
					green -= 0;
					blue -= 12;
				}
			}
			else if(Fade.type(item) == 3)
			{
				if(red >= 255 && green >= 0 && blue >= 0)
				{
					pvpPlayer.setColorUp(false);
				}
				else if(red <= 125 && green <= 0 && blue <= 0)
				{
					pvpPlayer.setColorUp(true);
				}
			if(pvpPlayer.isColorUp())
			{
				red += 13;
				blue = 0;
				green = 0;
			}
			else
			{
				red -= 13;
				blue = 0;
				green = 0;
			}
		}
		else if(Fade.type(item) == 4)
		{
			if(red >= 120 && green >= 250 && blue >= 0)
			{
				pvpPlayer.setColorUp(false);
			}
			else if(red <= 0 && green <= 100 && blue <= 0)
			{
				pvpPlayer.setColorUp(true);
			}
			if(pvpPlayer.isColorUp())
			{
				red += 12;
				green += 15;
				blue = 0;
			}
			else
			{
				red -= 12;
				green -= 15;
				blue = 0;
			}
		}
		else if(Fade.type(item) == 5 || item.getItemMeta().getLore().toString().contains("Polished Prized"))
		{
			if(item.hasItemMeta() == true && item.getItemMeta().getLore().size() == 1)
			{
				List<String> lore = new ArrayList<String>();
				lore.add(0, item.getItemMeta().getLore().get(0));
				lore.add(1, "Polished Prized");
				meta.setLore(lore);
			}
			if(red == Fade.PRIZED.getRed() && blue == Fade.PRIZED.getBlue() && green == Fade.PRIZED.getGreen())
			{
				red = Fade.CURSED.getRed();
				blue = Fade.CURSED.getBlue();
				green = Fade.CURSED.getGreen();
			}
			else if(red == Fade.CURSED.getRed() && blue == Fade.CURSED.getBlue() && green == Fade.CURSED.getGreen())
			{
				red = Fade.FLAME.getRed();
				blue = Fade.FLAME.getBlue();
				green = Fade.FLAME.getGreen();
			}
			else if(red == Fade.FLAME.getRed() && blue == Fade.FLAME.getBlue() && green == Fade.FLAME.getGreen())
			{
				red = Fade.MITHRIL.getRed();
				blue = Fade.MITHRIL.getBlue();
				green = Fade.MITHRIL.getGreen();
			}
			else if(red == Fade.MITHRIL.getRed() && blue == Fade.MITHRIL.getBlue() && green == Fade.MITHRIL.getGreen())
			{
				red = Fade.VERITE.getRed();
				blue = Fade.VERITE.getBlue();
				green = Fade.VERITE.getGreen();
			}
			else if(red == Fade.VERITE.getRed() && blue == Fade.VERITE.getBlue() && green == Fade.VERITE.getGreen())
			{
				red = Fade.PRIZED.getRed();
				blue = Fade.PRIZED.getBlue();
				green = Fade.PRIZED.getGreen();
			}
			else
			{
				red = Fade.PRIZED.getRed();
				blue = Fade.PRIZED.getBlue();
				green = Fade.PRIZED.getGreen();
			}

		}	
		if(red < 0)
			red = 0;
		else if(red > 255)
			red = 255;

		if(green < 0)
			green = 0;
		else if(green > 255)
			green = 255;

		if(blue < 0)
			blue = 0;
		else if(blue > 255)
			blue = 255;

		Color color = Color.fromRGB(red, green, blue);
		meta.setColor(color);
		item.setItemMeta(meta);
		return meta.clone();
	}
	public static void polish(Player player)
	{
		boolean hasCloth = false;
		boolean alreadyRemoved = false;
		boolean correctItemInHand = false;
		ItemStack item = null;
		if(player.getItemInHand() != null)
		{
		 	item = player.getItemInHand();
			if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS)
		 	{
				correctItemInHand = true;
		 	}
				if(player.getInventory().contains(Material.PAPER) && item != null)
				{
					for(ItemStack i: player.getInventory())
					{
						try
						{
							ItemMeta itemMeta = null;
							if(i != null)
							{
								if(i.hasItemMeta())
								{
									itemMeta = i.getItemMeta();
								}
								if(i.hasItemMeta() && itemMeta.hasLore() && correctItemInHand == true)
								{
									if(i.getItemMeta().getLore().get(0).toString().contains(CODE_PAPER))
									{
										if(i.getAmount() > 1 && alreadyRemoved == false)
										{
											alreadyRemoved = true;
											hasCloth = true;
											i.setAmount(i.getAmount() - 1);
											if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
													item.getType() == Material.LEATHER_HELMET|| item.getType() == Material.LEATHER_LEGGINGS && hasCloth == true)
											{
												if(i.getItemMeta().getLore().get(0).toString().contains(CODE_PAPER))
												{
													ItemMeta metah = item.getItemMeta();
													LeatherArmorMeta meta = (LeatherArmorMeta) metah;
													if(!meta.getColor().toString().contains("A06540"))
													{
														List<String> lore = new ArrayList<String>();
														lore = meta.getLore();
														if(lore.size() != 0){
															lore.add("Polished " + ChatColor.MAGIC + " " + CODE_ARMOR);
															ItemUtils.setLore(item, lore);
															if(meta.getColor().getBlue() == 255 && meta.getColor().getGreen() == 255 && meta.getColor().getRed() == 255)
															{
																meta.setColor(Color.fromRGB(254, 255, 255));
																item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
															}
														}
													}
													else
													{
														player.sendMessage("THIS IS NORMAL ARMOR DONT TRY TO CHEAT!");
													}	
												}
											}
										}
										else if(alreadyRemoved == false)
										{
											alreadyRemoved = true;
											hasCloth = true;
											player.getInventory().removeItem(i);
											if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
													item.getType() == Material.LEATHER_HELMET|| item.getType() == Material.LEATHER_LEGGINGS && hasCloth == true)
											{
												if(i.getItemMeta().getLore().get(0).toString().contains(CODE_PAPER))
												{
													ItemMeta metah = item.getItemMeta();
													LeatherArmorMeta meta = (LeatherArmorMeta)metah;
													if(!meta.getColor().toString().contains("A06540"))
													{
														List<String> lore = new ArrayList<String>();
														lore = meta.getLore();
														if(lore.size() != 0){
															lore.add("Polished " + ChatColor.MAGIC + " " + CODE_ARMOR);
															ItemUtils.setLore(item, lore);
															if(meta.getColor().getBlue() == 255 && meta.getColor().getGreen() == 255 && meta.getColor().getRed() == 255)
															{
																meta.setColor(Color.fromRGB(254, 255, 255));
																item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
															}
														}
													}
													else
													{
														player.sendMessage("THIS IS NORMAL ARMOR DONT TRY TO CHEAT!");
													}	
												}
											}
										}
									}
								}
							}
						}catch (Exception e1){
							e1.printStackTrace();
							PvpBalance.logger.info("ArmorEffects!");
						}
			 		}
			}
			if(player.isOp() && !hasCloth)
		 	{
				ItemStack paper = new ItemStack(Material.PAPER);
				List<String> lore = new ArrayList<String>();
				lore.add("Polishing Cloth " + ChatColor.MAGIC + " " + CODE_PAPER);
				lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
				ItemUtils.setLore(paper, lore);
				ItemUtils.setName(paper, ChatColor.GOLD + "Polishing Cloth");
				player.getInventory().addItem(paper);
				player.sendMessage(ChatColor.GREEN + "[Armor Polish]: Greetings Administrator " + player.getDisplayName() + " a cloth has been provided please try again.");
		 	}
			if(!correctItemInHand && hasCloth)
		 	{
				player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You are not holding epic armor in your hand " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
				ItemUtils.setColor(item, Color.fromRGB(254, 255, 255));
				item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		 	}
			if(!hasCloth)
			{
				player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You did not have a Polishing Cloth or are not holding the right item please say " + ChatColor.GREEN + "" + ChatColor.BOLD + "/rules polish");
			}
			if(!correctItemInHand && hasCloth && alreadyRemoved)
			{
				player.sendMessage(ChatColor.YELLOW + "[Armor Polish]:" + ChatColor.RED + " You polish the armor to a brilliant shine " + ChatColor.GOLD + "Bling Bling! ");
			}
		}
	}
}
