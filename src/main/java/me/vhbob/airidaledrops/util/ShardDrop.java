package me.vhbob.airidaledrops.util;

import me.realized.tokenmanager.TokenManagerPlugin;
import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShardDrop extends Drop {

    private static final Material displayMaterial = Material.valueOf(AiridaleDrops.getPlugin().getConfig().getString("display.shard.item"));
    private static final String displayTitle = ChatColor.translateAlternateColorCodes('&',
            AiridaleDrops.getPlugin().getConfig().getString("display.shard.title"));

    public ShardDrop(Location loc, int amt, Player owner) {
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
        TokenManagerPlugin.getInstance().addTokens(player, amt);
        player.sendMessage(ChatColor.GREEN + "You picked up " + amt + " shards!");
    }
}