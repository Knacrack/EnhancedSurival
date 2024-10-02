package de.knacrack.enhanced_survival.items;

import net.md_5.bungee.api.ChatColor;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;

public enum ItemType {
    WILD("Wild",new Color(178,34,34, 255)),
    WORLD("World", new Color(76, 187, 23)),
    MYTHIC("Mythic", new Color(134,18,134)),
    LEGENDARY("Legendary", new Color(255, 215, 0, 1)),
    RUNE("Rune", new Color(165, 42, 42, 1));

    String loreName;
    Color color;

    ItemType(String name,Color color){
        loreName = name;
        this.color = color;
    }

    String getLoreName() {
        return "" + ChatColor.of(color) + ChatColor.BOLD + loreName.toUpperCase();
    }

    Color getColor() {
        return color;
    }

    ChatColor getChatColor() {
        return ChatColor.of(color);
    }



}
