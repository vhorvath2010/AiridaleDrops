package me.vhbob.airidaledrops.commands;

import me.vhbob.airidaledrops.AiridaleDrops;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class SetSoul implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("SetSoul")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                ItemStack hand = p.getInventory().getItemInMainHand();
                if (hand != null) {
                    FileConfiguration config = new YamlConfiguration();
                    config.set("soul_item", hand);
                    try {
                        config.save(new File(AiridaleDrops.getPlugin().getDataFolder(), "soul.yml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.sendMessage(ChatColor.GREEN + "Set the soul item!");
                    return true;
                }
            }
        }
        return false;
    }
}
