package me.vhbob.airidaledrops.events;

import me.vhbob.airidaledrops.AiridaleDrops;
import me.vhbob.airidaledrops.util.Drop;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;

import java.io.IOException;

public class PickupEvents implements Listener {

    @EventHandler
    public void onMerge(ItemMergeEvent e) {
        for (Drop d : AiridaleDrops.getPlugin().getActiveDrops()) {
            if (d.getDrop().equals(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) throws IOException, InvalidConfigurationException {
        // Do drop procedure if this is a Airidale Drop
        Drop airidaleDrop = null;
        for (Drop drop : AiridaleDrops.getPlugin().getActiveDrops()) {
            if (drop.getDrop().equals(e.getItem())) {
                airidaleDrop = drop;
                break;
            }
        }
        if (airidaleDrop != null) {
            // Stop if safe period
            if ((airidaleDrop.isSafe() && !airidaleDrop.getOwner().equals(e.getEntity())) || !(e.getEntity() instanceof Player)) {
                e.setCancelled(true);
                return;
            }
            // Allow drop to occur
            e.getItem().remove();
            e.setCancelled(true);
            airidaleDrop.giveReward((Player) e.getEntity());
        }
    }

}
