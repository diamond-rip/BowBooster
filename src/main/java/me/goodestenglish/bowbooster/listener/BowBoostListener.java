package me.goodestenglish.bowbooster.listener;

import me.goodestenglish.bowbooster.BowBooster;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

public class BowBoostListener implements Listener {

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        LivingEntity entity = e.getEntity();
        Vector dir = entity.getLocation().getDirection();
        Arrow a = (Arrow) e.getProjectile();
        double speed = a.getVelocity().length();
        Vector vel = dir.multiply(speed);
        a.setVelocity(vel);
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e) {
        Arrow arrow;
        Entity damager;
        Player p = e.getPlayer();
        Vector velocity = e.getVelocity();
        EntityDamageEvent event = p.getLastDamageCause();
        if (event != null && !event.isCancelled() && event instanceof EntityDamageByEntityEvent && (damager = ((EntityDamageByEntityEvent) event).getDamager()) instanceof Arrow && (arrow = (Arrow) damager).getShooter().equals(p)) {
            double speed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
            Vector dir = arrow.getLocation().getDirection().normalize();
            double xVelocity = BowBooster.INSTANCE.getConfigFile().getDouble("knockback-velocity.x");
            double zVelocity = BowBooster.INSTANCE.getConfigFile().getDouble("knockback-velocity.z");
            Vector newVelocity = new Vector((dir.getX() * speed * -1.0) * xVelocity, velocity.getY(), dir.getZ() * speed * zVelocity);

            e.setVelocity(newVelocity);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (BowBooster.INSTANCE.getConfigFile().getBoolean("minimize-damage")) {
            if (event.getEntity() instanceof Player && event.getEntity() instanceof Arrow) {
                Player entity = (Player) event.getEntity();
                Player damager = (Player) ((Arrow)event.getDamager()).getShooter();
                if (entity == damager) {
                    event.setDamage(0);
                }
            }
        }
    }

}
