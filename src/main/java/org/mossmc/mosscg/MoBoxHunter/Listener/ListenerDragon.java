package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Step.StepEnding;

public class ListenerDragon implements Listener {
    @EventHandler
    public static void onDragonDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            BasicInfo.winner = BasicInfo.playerRole.Runner;
            if (event.getEntity().getKiller() != null) {
                Bukkit.broadcastMessage(ChatColor.GOLD+"逃亡者"+event.getEntity().getKiller().getName()+"成功击杀末影龙！游戏结束！");
            } else {
                Bukkit.broadcastMessage(ChatColor.GOLD+"逃亡者成功击杀末影龙！游戏结束！");
            }
            StepEnding.runStep();
        }
    }
}
