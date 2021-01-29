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
        scheduleRemoval();
        this.giveReward(owner);
    }

    private void scheduleRemoval() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // Remove drop if player's inventory not full
                if (owner.getInventory().firstEmpty() != -1) {
                    drop.remove();
                } else {
                    scheduleRemoval();
                }
            }
        }.runTaskLater(AiridaleDrops.getPlugin(), (long) (20 * AiridaleDrops.getPlugin().getConfig().getDouble("item_period")));
    }

    public Item getDrop() {
        return drop;
    }

    public Player getOwner() {
        return owner;
    }
}
