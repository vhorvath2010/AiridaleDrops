package me.vhbob.airidaledrops.util;

import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public abstract class Drop {

    Item drop;
    int amt;
    Player owner;

    public void giveReward(Player player) throws IOException, InvalidConfigurationException {
        AiridaleDrops.getPlugin().getActiveDrops().remove(this);
    }

    public void generateDrop(Location loc, ItemStack displayItem, String name) throws IOException, InvalidConfigurationException {
        // Generate drop
        drop = loc.getWorld().dropItem(loc, displayItem);
        drop.setCustomName(name);
        drop.setCustomNameVisible(true);
        // Schedule removal
        new BukkitRunnable() {
            @Override
            public void run() {
                drop.remove();
            }
        }.runTaskLater(AiridaleDrops.getPlugin(), (long) (20 * AiridaleDrops.getPlugin().getConfig().getDouble("item_period")));
        this.giveReward(owner);
    }

    public Item getDrop() {
        return drop;
    }

    public Player getOwner() {
        return owner;
    }
}
