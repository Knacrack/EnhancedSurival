package de.knacrack.enhanced_survival.items.list;

import de.knacrack.enhanced_survival.Main;
import de.knacrack.enhanced_survival.items.ICustomItem;
import de.knacrack.enhanced_survival.listener.ListenerConstructor;
import de.knacrack.enhanced_survival.utils.ItemAPI;
import de.knacrack.enhanced_survival.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class RespawnHeart extends ListenerConstructor implements ICustomItem {

    private static Random RANDOM = new Random();

    private static ICustomItem item;

    public RespawnHeart() {
        super("Respawn Heart");
        if (item == null) {
            item = this;
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemAPI(Material.STICK).setCustomModelData(3).setName(ChatColor.of(new Color(107, 0, 5)) + getItemName())
                .addLore("§7Dropped by an undead creature.", "§7This Heart will prevent the loss", "§7of items after death.").get();
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getItemName() {
        return "Respawn Heart";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(Main.getInstance(), "respawn_heart");
    }

    @Override
    public boolean register() {
        return true;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();

        if(Utils.isZombie(entityType)) {
            if(RANDOM.nextInt(1000) == 0) { // chance of 0.1%
                entity.getWorld().dropItem(event.getEntity().getLocation(), getItem());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        for (ItemStack itemStack : event.getDrops()) {
            if (itemStack.isSimilar(getItem())) {
                event.setKeepInventory(true);
                event.getDrops().clear();
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack itemStack1 = player.getInventory().getItem(i);

            if (itemStack1 == null)
                continue;

            if (itemStack1.isSimilar(getItem())) {
                if (itemStack1.getAmount() > 1) {
                    itemStack1.setAmount(itemStack1.getAmount() - 1);
                }
                else {
                    player.getInventory().setItem(i, null);
                }

                break;
            }
        }
    }

    public static ICustomItem getCustomItem() {
        if (item == null) {
            item = new Gram();
        }
        return item;
    }
}
