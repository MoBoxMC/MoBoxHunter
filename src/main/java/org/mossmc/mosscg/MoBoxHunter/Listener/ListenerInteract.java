package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;

public class ListenerInteract implements Listener {
    @EventHandler
    public static void onInteract(PlayerInteractEvent event) {
        if (!BasicInfo.canInteract) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onInteract(PlayerInteractAtEntityEvent event) {
        if (!BasicInfo.canInteract) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onInteract(PlayerInteractEntityEvent event) {
        if (!BasicInfo.canInteract) {
            event.setCancelled(true);
        }
    }
}
