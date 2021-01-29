package me.vhbob.airidaledrops.events;

import me.vhbob.airidaledrops.AiridaleDrops;
import me.vhbob.airidaledrops.util.ShardDrop;
import me.vhbob.airidaledrops.util.SoulDrop;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Random;

public class DropEvent implements Listener {

    private static final FileConfiguration config = AiridaleDrops.getPlugin().getConfig();

    @EventHandler
    public void onBreak(BlockBreakEvent e) throws IOException, InvalidConfigurationException {
        // Attempt to drop souls
        String type = e.getBlock().getType().toString();
        // Calculate fortune effect
        ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
        int fortuneMulti = 1;
        if (hand.hasItemMeta() && hand.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
            fortuneMulti += hand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        }
        if (config.contains("drops." + type + ".soul.chance")) {
            double chance = config.getDouble("drops." + type + ".soul.chance");
            int min = config.getInt("drops." + type + ".soul.min");
            int max = config.getInt("drops." + type + ".soul.max") * fortuneMulti;
            if (Math.random() * 100 <= chance) {
                int amt = randomBetween(min, max);
                SoulDrop drop = new SoulDrop(e.getBlock().getLocation().add(.5,.5,.5), amt, e.getPlayer());
            }
        }
        // Attempt to drop shards
        if (config.contains("drops." + type + ".shard.chance")) {
            double chance = config.getDouble("drops." + type + ".shard.chance");
            int min = config.getInt("drops." + type + ".shard.min");
            int max = config.getInt("drops." + type + ".shard.max");
            if (Math.random() * 100 <= chance) {
                int amt = randomBetween(min, max);
                ShardDrop drop = new ShardDrop(e.getBlock().getLocation().add(.5,.5,.5), amt, e.getPlayer());
            }
        }
    }

    private int randomBetween(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - (min - 1)) + min;
    }

}
