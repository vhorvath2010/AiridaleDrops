package me.vhbob.airidaledrops;

import me.vhbob.airidaledrops.util.Drop;
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
    }

    public static AiridaleDrops getPlugin() {
        return plugin;
    }

    public ArrayList<Drop> getActiveDrops() {
        return activeDrops;
    }

}
