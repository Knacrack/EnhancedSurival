package de.knacrack.enhanced_survival.items;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public interface IActiveCustomItem extends ICustomItem, Listener {

    @EventHandler
    public void activeCustomItem(Class<? extends Event> event);
}
