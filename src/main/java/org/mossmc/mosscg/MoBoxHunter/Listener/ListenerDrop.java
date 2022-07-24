package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerDrop implements Listener {
    @EventHandler
    public static void onDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        if (item.getItemStack().getType().equals(Material.COMPASS)) {
            event.setCancelled(true);
        }
    }
}
