package me.vhbob.airidaledrops;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.vhbob.airidaledrops.commands.SetSoul;
import me.vhbob.airidaledrops.events.DropEvent;
import me.vhbob.airidaledrops.events.PickupEvents;
import me.vhbob.airidaledrops.util.Drop;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class AiridaleDrops extends JavaPlugin {

    public StateFlag DROPS_ENABLED_FLAG;
    private static AiridaleDrops plugin;
    private ArrayList<Drop> activeDrops;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        activeDrops = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(new DropEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PickupEvents(), this);
        getCommand("SetSoul").setExecutor(new SetSoul());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // Load flag
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            StateFlag flag = new StateFlag("airidaledrops", false);
            registry.register(flag);
            DROPS_ENABLED_FLAG = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("airidaledrops");
            if (existing instanceof StateFlag) {
                DROPS_ENABLED_FLAG = (StateFlag) existing;
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }
    }

    public static AiridaleDrops getPlugin() {
        return plugin;
    }

    public ArrayList<Drop> getActiveDrops() {
        return activeDrops;
    }

}
