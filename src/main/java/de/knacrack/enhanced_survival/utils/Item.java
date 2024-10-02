package de.knacrack.enhanced_survival.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {

    private ItemStack item;
    private ItemMeta meta;

    public Item(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public Item(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public Item(Material material) {
        this(material, 1);
    }

    public Item setName(String displayName, NamedTextColor textColor, TextDecoration... textDecoration){
        meta.displayName().append(Component.text(displayName).color(textColor).decorate(textDecoration));
        return this;
    }

    public Item setName(String displayName) {
        setName(displayName, null,null);
        return this;
    }

    public Item setName(String displayName, NamedTextColor textColor) {
        meta.displayName().append(Component.text(displayName).color(textColor));
        return this;
    }

    public ItemStack getItem() {
        item.setItemMeta(meta);
        return item;
    }
}
