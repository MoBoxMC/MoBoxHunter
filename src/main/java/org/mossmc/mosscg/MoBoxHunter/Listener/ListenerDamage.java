package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;

public class ListenerDamage implements Listener {
    @EventHandler
    public static void onDamage(EntityDamageByEntityEvent event) {
        if (!BasicInfo.canDamage) {
            event.setCancelled(true);
        }
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player target = ((Player) event.getEntity()).getPlayer();
        Player sender = ((Player) event.getDamager()).getPlayer();
        assert target != null;
        assert sender != null;
        if (!BasicInfo.teamDamage) {
            if (PlayerCache.runnerList.contains(target.getUniqueId()) && PlayerCache.runnerList.contains(sender.getUniqueId())) {
                event.setCancelled(true);
                return;
            }
            if (PlayerCache.hunterList.contains(target.getUniqueId()) && PlayerCache.hunterList.contains(sender.getUniqueId())) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
