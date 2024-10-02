package de.knacrack.enhanced_survival.items.list;

import de.knacrack.enhanced_survival.Main;
import de.knacrack.enhanced_survival.items.IActiveCustomItem;
import de.knacrack.enhanced_survival.items.ICustomItem;
import de.knacrack.enhanced_survival.utils.ItemAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Pokeball implements IActiveCustomItem {

    private static ICustomItem item;

    public Pokeball() {
        //super("Pokeball");
        if (item == null) {
            item = this;
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemAPI(Material.STICK).setCustomModelData(getId()).setName(getItemName()).addLore("Leer").get();
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public String getItemName() {
        return "Pokeball";
    }

    //@Override
    public boolean register() {
        return true;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(Main.getInstance(), "pokeball");
    }

    @EventHandler
    public void onPlayerInteractPokeball(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack itemStack = player.getInventory().getItem(event.getHand());
        if (itemStack.isSimilar(getItem()) && itemStack.getAmount() == 1) {
            @NotNull String entityType = entity.getType().getName();
            @NotNull String entityName = entity.getName();
            Boolean entitySilent = entity.isSilent();

            ItemMeta meta = itemStack.getItemMeta();
            if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_type"), PersistentDataType.STRING)) {
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_type"), PersistentDataType.STRING, entityType);
            }

            if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_name"), PersistentDataType.STRING)) {
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_name"), PersistentDataType.STRING, entityName);
            }

            if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_silent"), PersistentDataType.BYTE)) {
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_silent"), PersistentDataType.BYTE, (entitySilent == true ? Byte.parseByte("1") : Byte.parseByte("0")));
            }
            itemStack.setItemMeta(meta);
            itemStack = new ItemAPI(itemStack).setLore(List.of(entityType.toString() + ":" + entityName)).get();
            entity.remove();
        }
    }

    @EventHandler
    public void playerPlacePokeball(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack;

        if (Action.RIGHT_CLICK_BLOCK.equals(action)) {
            itemStack = player.getInventory().getItem(event.getHand());
            if (itemStack.getAmount() == 1) {
                @NotNull String entityType = "";
                @NotNull String entityName = "";
                @NotNull boolean entitySilent = false;

                ItemMeta meta = itemStack.getItemMeta();
                if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_type"), PersistentDataType.STRING)) {
                    entityType = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_type"), PersistentDataType.STRING);
                }

                if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_name"), PersistentDataType.STRING)) {
                    String s = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_name"), PersistentDataType.STRING);
                    entityName = String.valueOf(s);
                }

                if (!meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_silent"), PersistentDataType.BYTE)) {
                    boolean s = Boolean.parseBoolean(meta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), getKey().getNamespace() + "_silent"), PersistentDataType.BYTE).toString());
                    entitySilent = s;
                }
                itemStack = getItem();
                Main.getInstance().getLogger().info(entityName);
                Entity entity = player.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0, 1, 0), EntityType.valueOf(entityType), CreatureSpawnEvent.SpawnReason.CUSTOM);
                entity.setSilent(entitySilent);
                entity.setCustomName(entityName);
            }
        }
    }

    @Override
    public void activeCustomItem(Class<? extends Event> event) {

    }



}
