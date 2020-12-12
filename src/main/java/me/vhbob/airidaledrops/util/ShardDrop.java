package me.vhbob.airidaledrops.util;

import me.realized.tokenmanager.TokenManagerPlugin;
import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ShardDrop extends Drop {

    private static final Material displayMaterial = Material.valueOf(AiridaleDrops.getPlugin().getConfig().getString("display.shard.item"));
    private static final String displayTitle = ChatColor.translateAlternateColorCodes('&',
            AiridaleDrops.getPlugin().getConfig().getString("display.shard.title"));

    public ShardDrop(Location loc, int amt, Player owner) {
        this.amt = amt;
        this.owner = owner;
        this.safe = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                safe = false;
            }
        }.runTaskLater(AiridaleDrops.getPlugin(), (long) (20 * AiridaleDrops.getPlugin().getConfig().getDouble("safe_period")));
        // Create display
        ItemStack displayItem = new ItemStack(displayMaterial, 1);
        String title = displayTitle.replace("%amt%", amt + "");
        generateDrop(loc, displayItem, title);
        // Register drop
        AiridaleDrops.getPlugin().getActiveDrops().add(this);
    }

    @Override
    public void giveReward(Player player) {
        super.giveReward(player);
        TokenManagerPlugin.getInstance().addTokens(player, amt);
        player.sendMessage(ChatColor.GREEN + "You picked up " + amt + " shard(s)!");
        String soundName = AiridaleDrops.getPlugin().getConfig().getString("display.soul.pickup_sound");
        if (!soundName.equalsIgnoreCase("none")) {
            Sound sound = Sound.valueOf(soundName);
            player.getLocation().getWorld().playSound(player.getLocation(), sound, 1, 1);
        }
    }
}
