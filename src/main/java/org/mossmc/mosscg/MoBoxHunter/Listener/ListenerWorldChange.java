package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;

public class ListenerWorldChange implements Listener {
    @EventHandler
    public static void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World.Environment environment = player.getWorld().getEnvironment();
        if (PlayerCache.getPlayerRole(player.getUniqueId()).equals(BasicInfo.playerRole.Runner)) {
            if (!BasicInfo.runnerNetherWorld && environment.equals(World.Environment.NETHER)) {
                BasicInfo.runnerNetherWorld = true;
                Bukkit.broadcastMessage(ChatColor.RED+""+ChatColor.BOLD+"逃亡者已到达下界！");
            }
            if (!BasicInfo.runnerEndWorld && environment.equals(World.Environment.THE_END)) {
                BasicInfo.runnerEndWorld = true;
                Bukkit.broadcastMessage(ChatColor.RED+""+ChatColor.BOLD+"逃亡者已到达末地！");
            }
        }
        if (PlayerCache.getPlayerRole(player.getUniqueId()).equals(BasicInfo.playerRole.Hunter)) {
            if (!BasicInfo.hunterNetherWorld && environment.equals(World.Environment.NETHER)) {
                BasicInfo.hunterNetherWorld = true;
                Bukkit.broadcastMessage(ChatColor.RED+""+ChatColor.BOLD+"猎杀者已到达下界！");
            }
            if (!BasicInfo.hunterEndWorld && environment.equals(World.Environment.THE_END)) {
                BasicInfo.hunterEndWorld = true;
                Bukkit.broadcastMessage(ChatColor.RED+""+ChatColor.BOLD+"猎杀者已到达末地！");
            }
        }
    }
}
