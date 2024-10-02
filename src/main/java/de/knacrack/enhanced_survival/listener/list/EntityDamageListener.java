package de.knacrack.enhanced_survival.listener.list;

import de.knacrack.enhanced_survival.listener.ListenerConstructor;
import de.knacrack.enhanced_survival.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener extends ListenerConstructor {


    public EntityDamageListener() {
        super("EntityDamageListener");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getFinalDamage() == 0) return;
        if(!(event.getEntity() instanceof LivingEntity entity)) return;

        Location location = entity.getLocation().add(0, 0.5, 0);
        double speed = 0.025;

        if(Utils.isSkeleton(entity.getType())) {
            spawnParticle(location, 14, 0.2, 1, 0.2, speed, Material.BONE_BLOCK);
        } else if(entity.getType() == EntityType.SLIME) {
            Slime slime = (Slime) entity;

            spawnParticle(location, 14 * slime.getSize(), 0.2 * slime.getSize(), 0.2 * slime.getSize(), 0.2 * slime.getSize(), speed, Material.SLIME_BLOCK);
        } else if(entity.getType() == EntityType.CREEPER) {
            spawnParticle(location, 14, 0.2, 1, 0.2, speed, Material.TNT);
        } else if(entity.getType() == EntityType.ENDERMAN || entity.getType() == EntityType.ENDERMITE) {
            spawnParticle(location, 14, 0.2, 1, 0.2, speed, Material.PURPLE_SHULKER_BOX);
        } else if(entity.getType() == EntityType.STRAY || entity.getType() == EntityType.POLAR_BEAR) {
            spawnParticle(location, 14, 0.2, 1, 0.2, speed, Material.BLUE_ICE);
        } else if(entity.getType() == EntityType.HUSK) {
            spawnParticle(location, 14, 0.2, 1, 0.2, speed, Material.SAND);
        } else {
            spawnParticle(location, 14, 0.2, 1, 0.2, speed, Material.REDSTONE_BLOCK);
        }
    }

    private void spawnParticle(Location location, int amount, double offsetX, double offsetY, double offsetZ, double speed, Material material) {
        String blockDataString = "minecraft:" + material.name().toLowerCase();
        BlockData blockData = Bukkit.getServer().createBlockData(blockDataString);

        location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, amount, offsetX, offsetY, offsetZ, speed, blockData);
    }

    @Override
    public boolean register() {
        return true;
    }
}