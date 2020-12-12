package me.vhbob.airidaledrops.util;

import me.realized.tokenmanager.TokenManagerPlugin;
import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SoulDrop extends Drop {

    private static final FileConfiguration config = AiridaleDrops.getPlugin().getConfig();
    private static final Material displayMaterial = Material.valueOf(config.getString("display.soul.item"));
    private static final String displayTitle = ChatColor.translateAlternateColorCodes('&', config.getString("display.soul.title"));

    public SoulDrop(Location loc, int amt, Player owner) {
        this.amt = amt;
        this.owner = owner;
        this.safe = true;
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
        player.sendMessage(ChatColor.GREEN + "You picked up " + amt + " souls!");
        // Create soul item
        Material soulMat = Material.valueOf(config.getString("soul.item.type"));
        ItemStack soul = new ItemStack(soulMat, 64);
        ItemMeta soulM = soul.getItemMeta();
        soulM.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("soul.item.name")));
        List<String> configLore = config.getStringList("soul.item.lore");
        ArrayList<String> lore = new ArrayList<>();
        for (String line : configLore) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        soulM.setLore(lore);
        soul.setItemMeta(soulM);
        // Give soul item
        while (amt > 64) {
            amt -= 64;
            player.getInventory().addItem(soul);
        }
        soul.setAmount(amt);
        player.getInventory().addItem(soul);
    }

}
