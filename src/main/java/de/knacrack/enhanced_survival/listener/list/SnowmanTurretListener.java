package de.knacrack.enhanced_survival.listener.list;

import de.knacrack.enhanced_survival.listener.ListenerConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class SnowmanTurretListener extends ListenerConstructor {
    public SnowmanTurretListener() {
        super("SnowmanTurretListener");
    }

    @EventHandler
    public void SnowmanTurretDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.SNOWBALL) {
            ProjectileSource shooter = ((Snowball)event.getDamager()).getShooter();
            if (shooter instanceof Snowman snowman && snowman.getCustomName() != null && snowman.getCustomName().contains("Turret")
                    && !EntityType.SNOWMAN.equals(event.getEntity().getType())) {
                event.setDamage(event.getDamage() + 20.00);
            }
        }

    }

    @Override
    public boolean register() {
        return true;
    }
}
