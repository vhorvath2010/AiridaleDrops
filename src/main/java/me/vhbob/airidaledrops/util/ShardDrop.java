package me.vhbob.airidaledrops.util;

import me.realized.tokenmanager.TokenManagerPlugin;
import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class ShardDrop extends Drop {

    private static final Material displayMaterial = Material.valueOf(AiridaleDrops.getPlugin().getConfig().getString("display.shard.item"));
    private static final String displayTitle = ChatColor.translateAlternateColorCodes('&',
            AiridaleDrops.getPlugin().getConfig().getString("display.shard.title"));

    public ShardDrop(Location loc, int amt, Player owner) throws IOException, InvalidConfigurationException {
        this.amt = amt;
        this.owner = owner;
        // Create display
        ItemStack displayItem = new ItemStack(displayMaterial, 1);
        String title = displayTitle.replace("%amt%", amt + "");
        generateDrop(loc, displayItem, title);
        // Register drop
        AiridaleDrops.getPlugin().getActiveDrops().add(this);
    }

    @Override
    public void giveReward(Player player) throws IOException, InvalidConfigurationException {
        super.giveReward(player);
        TokenManagerPlugin.getInstance().addTokens(player, amt);
        String msg = AiridaleDrops.getPlugin().getConfig().getString("messages.shard").replace("%amt%", amt + "");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        String soundName = AiridaleDrops.getPlugin().getConfig().getString("display.soul.pickup_sound");
        if (!soundName.equalsIgnoreCase("none")) {
            Sound sound = Sound.valueOf(soundName);
            player.getLocation().getWorld().playSound(player.getLocation(), sound, 1, 1);
        }
    }
}
