package me.vhbob.airidaledrops.util;

import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
    }

    private void scheduleRemoval() {
        Drop dropType = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                // Check for shard drop
                if (dropType instanceof SoulDrop) {
                    // Create soul item
                    FileConfiguration soulConfig = new YamlConfiguration();
                    try {
                        soulConfig.load(new File(AiridaleDrops.getPlugin().getDataFolder(), "soul.yml"));
                    } catch (IOException | InvalidConfigurationException e) {
                        e.printStackTrace();
                    }
                    ItemStack soul = (ItemStack) soulConfig.get("soul_item");
                    // Remove drop if player's inventory not full
                    int unfilled = 0;
                    for (ItemStack item : owner.getInventory().getStorageContents()) {
                        if (item != null && item.isSimilar(soul)) {
                            unfilled += item.getMaxStackSize() - item.getAmount();
                        }
                    }
                    if (owner.getInventory().firstEmpty() != -1 || unfilled >= dropType.amt) {
                        drop.remove();
                        try {
                            dropType.giveReward(dropType.getOwner());
                        } catch (IOException | InvalidConfigurationException e) {
                            e.printStackTrace();
                        }
                    } else {
                        scheduleRemoval();
                    }
                } else {
                    drop.remove();
                }
            }
        }.runTaskLater(AiridaleDrops.getPlugin(), (long) (20 * AiridaleDrops.getPlugin().getConfig().getDouble("safe_period")));
    }

    public Item getDrop() {
        return drop;
    }

    public Player getOwner() {
        return owner;
    }
}
