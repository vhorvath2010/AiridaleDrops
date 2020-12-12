package me.vhbob.airidaledrops;

import me.vhbob.airidaledrops.events.DropEvent;
import me.vhbob.airidaledrops.events.PickupEvents;
import me.vhbob.airidaledrops.util.Drop;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class AiridaleDrops extends JavaPlugin {

    private static AiridaleDrops plugin;
    private ArrayList<Drop> activeDrops;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        activeDrops = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(new DropEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PickupEvents(), this);
    }

    public static AiridaleDrops getPlugin() {
        return plugin;
    }

    public ArrayList<Drop> getActiveDrops() {
        return activeDrops;
    }

}
