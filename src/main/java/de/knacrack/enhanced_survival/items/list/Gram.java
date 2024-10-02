package de.knacrack.enhanced_survival.items.list;

import de.knacrack.enhanced_survival.Main;
import de.knacrack.enhanced_survival.items.ICustomItem;
import de.knacrack.enhanced_survival.listener.ListenerConstructor;
import de.knacrack.enhanced_survival.utils.ItemAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Gram extends ListenerConstructor implements ICustomItem {

    private static ICustomItem item;

    public Gram() {
        super("Gram");
        if (item == null) {
            item = this;
        }
    }

    @Override
    public ItemStack getItem() {

        ItemStack itemStack = new ItemAPI(Material.NETHERITE_SWORD).isUnbreakable(true).setName(net.md_5.bungee.api.ChatColor.of("#4B0082") + "§l" + getItemName())
                .addLore(ChatColor.GRAY + "Das Schwert eines Königs!").get();

        AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ATTACK_SPEED.name(), -1.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier attackDamage = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ATTACK_DAMAGE.name(), 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier knockbackResistance = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_KNOCKBACK_RESISTANCE.name(), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ARMOR.name(), 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        ItemMeta meta = itemStack.getItemMeta();

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamage);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistance);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

        // return itemStack;
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public int getId() {
        return 14;
    }

    @Override
    public String getItemName() {
        return "Gram";
    }

    @Override
    public boolean register() {
        return false;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(Main.getInstance(), "gram");
    }

    public static ICustomItem getCustomItem() {
        if (item == null) {
            item = new Gram();
        }
        return item;
    }
}
