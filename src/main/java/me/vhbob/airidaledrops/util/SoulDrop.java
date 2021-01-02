package me.vhbob.airidaledrops.util;

import me.realized.tokenmanager.TokenManagerPlugin;
import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoulDrop extends Drop {

    private static final FileConfiguration config = AiridaleDrops.getPlugin().getConfig();
    private static final Material displayMaterial = Material.valueOf(config.getString("display.soul.item"));
    private static final String displayTitle = ChatColor.translateAlternateColorCodes('&', config.getString("display.soul.title"));

    public SoulDrop(Location loc, int amt, Player owner) throws IOException, InvalidConfigurationException {
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
        // Create soul item
        FileConfiguration soulConfig = new YamlConfiguration();
        soulConfig.load(new File(AiridaleDrops.getPlugin().getDataFolder(), "soul.yml"));
        ItemStack soul = (ItemStack) soulConfig.get("soul_item");
        // Give soul item
        while (amt > 64) {
            amt -= 64;
            player.getInventory().addItem(soul);
        }
        soul.setAmount(amt);
        player.getInventory().addItem(soul);
        String soundName = config.getString("display.soul.pickup_sound");
        if (!soundName.equalsIgnoreCase("none")) {
            Sound sound = Sound.valueOf(soundName);
            player.getLocation().getWorld().playSound(player.getLocation(), sound, 1, 1);
        }
        String msg = config.getString("messages.soul").replace("%amt%", amt + "");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

}
