package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCompass;

public class ListenerCraft implements Listener {
    @EventHandler
    public static void onCraft(CraftItemEvent event) {
        if (event.getRecipe().getResult().getType() != Material.COMPASS) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        switch (PlayerCache.getPlayerRole(player.getUniqueId())) {
            case Runner:
                if (BasicInfo.compassUnlock) {
                    BasicInfo.compassUnlock = false;
                    Bukkit.broadcastMessage(ChatColor.DARK_GREEN+"逃亡者"+player.getName()+"合成了指南针！猎人的指南针已被破坏！");
                    PlayerCompass.listRemoveCompass(PlayerCache.hunterList);
                }
                break;
            case Hunter:
                if (!BasicInfo.compassUnlock) {
                    BasicInfo.compassUnlock = true;
                    Bukkit.broadcastMessage(ChatColor.GREEN+"猎人"+player.getName()+"合成了指南针！逃亡者的方向已暴露！");
                    PlayerCompass.listGiveCompass(PlayerCache.hunterList);
                }
                break;
            default:
                break;
        }
    }
}
