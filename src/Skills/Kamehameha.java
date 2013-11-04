package Skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import PvpBalance.PvpHandler;
import Util.Utils;

public class Kamehameha {
	Player shooter;
	int power;
	Vector direction;
	Fireball ball;
	
	public Kamehameha(Player damager)
	{
		this.shooter = damager;
		this.ball = this.shooter.getLocation().getWorld().spawn(damager.getLocation().add(0, 2, 0), Fireball.class);
		ball.setVelocity(ball.getVelocity().multiply(3));
		direction = ball.getDirection();
		if(Util.Target.getTarget(damager) != null)
		{
			Player target = (Player) Util.Target.getTarget(damager);
			ball.setVelocity(Utils.getTargetVector(damager.getLocation(), target.getLocation()).multiply(0.4f).normalize());
		}
		ball.setShooter(damager);
		ball.setIsIncendiary(true);
		ball.setTicksLived(100);
		PvpBalance.PvpHandler.kameya.add(this);
	}
	public Player getShooter()
	{
		return this.shooter;
	}
	public int getPower()
	{
		return this.power;
	}
	public Vector getDirection()
	{
		return this.direction;
	}
	public Fireball getFireball()
	{
		return this.ball;
	}
}
