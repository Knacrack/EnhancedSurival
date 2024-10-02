package de.knacrack.enhanced_survival.items.list;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import de.knacrack.enhanced_survival.items.ICustomItem;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import de.knacrack.enhanced_survival.Main;
import de.knacrack.enhanced_survival.utils.ItemAPI;

public class Scythe implements ICustomItem, Listener {

    private static final ItemStack item = new ItemAPI(Material.NETHERITE_HOE).addItemFlags(ItemFlag.HIDE_UNBREAKABLE).isUnbreakable(true).setName("§6§lScythe").get();

    private static final Scythe instance = new Scythe();


    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getItemName() {
        return null;
    }


    public static final Scythe get() {
        return instance;
    }


    public void register() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    @EventHandler
    public void onAction(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getItem().isSimilar(getItem())) return;
        if (Action.LEFT_CLICK_AIR.equals(event.getAction()) && Action.LEFT_CLICK_BLOCK.equals(event.getAction())) {
            return;
        }

        Player player = event.getPlayer();
        Location loc = player.getEyeLocation();

        // Functioning Part of the Code//
        final Vector vector = loc.getDirection().normalize();
        @NotNull Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();

        AtomicInteger atomic = new AtomicInteger();
        atomic.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

            int i = 0;


            @Override
            public void run() {

                Vector offsetVector = vector.clone().multiply(0.75).multiply(1 + i);
                activePotionEffects.clear();
                spawnParticle(offsetVector, loc.clone());

                for (double j = 0.05; j <= 0.5; j += 0.05) {
                    spawnParticle(offsetVector.clone().rotateAroundY(j), loc.clone());
                }
                for (double j = -0.05; j >= -0.5; j -= 0.05) {
                    spawnParticle(offsetVector.clone().rotateAroundY(j), loc.clone());
                }

                //                spawnParticle(offsetVector.clone().rotateAroundY(0.05d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.10d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.15d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.20d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.25d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.3d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.35d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.40d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.45d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(0.5d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.05d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.10d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.15d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.20d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.25d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.3d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.35d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.40d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.45d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);
                //                spawnParticle(offsetVector.clone().rotateAroundY(-0.5d).rotateAroundZ(loc.clone().getPitch()), loc.clone(), activePotionEffects);

                //                spawnParticle(offsetVector, loc.clone());
                //                spawnParticle(offsetVector.add(new Vector(1f, 0f, 0f)), loc.clone());
                //                spawnParticle(offsetVector.add(new Vector(1f, 0f, 0f)), loc.clone());
                //                spawnParticle(offsetVector.add(new Vector(1f, 0f, 0f)), loc.clone());
                //                spawnParticle(offsetVector.add(new Vector(1f, 0f, 0f)), loc.clone());
                //                spawnParticle(offsetVector.add(new Vector(1f, 0f, 0f)), loc.clone());
                //                spawnParticle(offsetVector.add(new Vector(1f, 0f, 0f)), loc.clone());

                if (i == 50) {
                    Bukkit.getScheduler().cancelTask(atomic.get());
                }

                i++;
            }
        }, 0, 1));

        if (!activePotionEffects.isEmpty()) for (PotionEffect potionEffect : activePotionEffects) {
            player.removePotionEffect(potionEffect.getType());
        }
    }


    private static final Particle.DustOptions dust = new Particle.DustOptions(Color.fromBGR(150 / 255, 0, 0), 1f);


    private void spawnParticle(Vector vector, Location location) {
        Location offsetLocation = location.add(vector);

        location.getWorld().spawnParticle(Particle.REDSTONE, location.add(vector), 1, 0f, 0, 0, 0, dust);

        offsetLocation.getWorld().getNearbyEntities(offsetLocation, 1, 1, 1).stream().filter(entity -> entity instanceof LivingEntity).forEach(e -> ((LivingEntity) e).damage(10));
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return null;
    }

    //    class circle {
    //
    //        public static void onAction(PlayerInteractEvent event) {
    //            if (event.getItem() == null)
    //                return;
    //            if (!event.getItem().isSimilar(item))
    //                return;
    //            if (Action.LEFT_CLICK_AIR.equals(event.getAction()) && Action.LEFT_CLICK_BLOCK.equals(event.getAction())) {
    //                return;
    //            }
    //
    //            Player player = event.getPlayer();
    //            Location loc = player.getEyeLocation();
    //
    //            final Vector vector = loc.getDirection().normalize();
    //            AtomicInteger atomic = new AtomicInteger();
    //            atomic.set(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
    //
    //                int i = 0;
    //
    //                double t;
    //
    //
    //
    //                @Override
    //                public void run() {
    //                    Vector offsetVector = vector.clone().multiply(0.75).multiply(1 + i);
    //                    double radius = Math.sin(t);
    //
    //                    for (double a = 0; a < Math.PI * 2; a += Math.PI / 8) {
    //                        double x = Math.sin(a) * radius, z = Math.cos(a) * radius;
    //                        Vector vector = new Vector(x, 0, z);
    //
    //                        vector = rotateX(vector, loc.getPitch());
    //                        vector = rotateY(vector, -loc.getYaw());
    //
    //                        spawnParticle(offsetVector.clone().add(vector), loc.clone());
    //                    }
    //
    //                    t += Math.PI / 8;
    //
    //                    if (i == 100) {
    //                        Bukkit.getScheduler().cancelTask(atomic.get());
    //                    }
    //
    //                    i++;
    //                }
    //            }, 0, 1));
    //
    //        }
    //
    //
    //
    //        private void spawnParticle(Vector vector, Location location) {
    //            Location offsetLocation = location.add(vector);
    //
    //            location.getWorld().spawnParticle(Particle.SWEEP_ATTACK, location.add(vector), 1, 0f, 0, 0, 0);
    //
    //            offsetLocation.getWorld().getNearbyEntities(offsetLocation, 1, 1, 1).stream().filter(entity -> entity instanceof LivingEntity)
    //                    .forEach(e -> ((LivingEntity)e).damage(10));
    //        }
    //
    //
    //
    //        private Vector rotateX(Vector vector, double a) {
    //            a = Math.toRadians(a);
    //
    //            double cos = Math.cos(a), sin = Math.sin(a);
    //            double y = vector.getY() * cos - vector.getZ() * sin;
    //            double z = vector.getY() * sin + vector.getZ() * cos;
    //
    //            return vector.setY(y).setZ(z);
    //        }
    //
    //
    //
    //        private Vector rotateY(Vector vector, double a) {
    //            a = Math.toRadians(a);
    //
    //            double cos = Math.cos(a), sin = Math.sin(a);
    //            double x = vector.getX() * cos + vector.getZ() * sin;
    //            double z = vector.getX() * -sin + vector.getZ() * cos;
    //
    //            return vector.setX(x).setZ(z);
    //        }
    //    }
}
