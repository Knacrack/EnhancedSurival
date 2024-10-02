package de.knacrack.enhanced_survival.utils.listeners;

import de.knacrack.enhanced_survival.Main;
import de.knacrack.enhanced_survival.utils.IRegister;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public interface Listener extends org.bukkit.event.Listener, IRegister {


    default void registerListener() {
        if(register()) {
            Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        }
    }

    default List<Listener> getEnabledListeners(){
        return (List<Listener>) listeners.stream().filter(IRegister::register).toList();
    }

    default List<Listener> getDisabledListeners(){
        return (List<Listener>) listeners.stream().filter(f -> !f.register()).toList();
    }

    default List<Listener> getListeners(){
        return (List<Listener>) listeners;
    }

}
