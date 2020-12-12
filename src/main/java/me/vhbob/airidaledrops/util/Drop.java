package me.vhbob.airidaledrops.util;

import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Drop {

    Item drop;
    int amt;
    boolean safe;
    Player owner;

    public void giveReward(Player player) {
        AiridaleDrops.getPlugin().getActiveDrops().remove(this);
    }

    public void generateDrop(Location loc, ItemStack displayItem, String name) {
        // Generate armor stand
        drop = (Item) loc.getWorld().spawnEntity(loc, EntityType.DROPPED_ITEM);
        drop.setItemStack(displayItem);
        drop.setCustomName(name);
        drop.setCustomNameVisible(true);
    }

    public Item getDrop() {
        return drop;
    }

    public boolean isSafe() {
        return safe;
    }

}
