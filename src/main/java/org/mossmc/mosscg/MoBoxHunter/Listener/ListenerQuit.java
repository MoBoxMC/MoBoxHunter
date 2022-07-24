package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerReconnect;

public class ListenerQuit implements Listener {
    @EventHandler
    public static void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        switch (GameBasicInfo.gameStatus) {
            case Waiting:
                PlayerCache.playerList.remove(player.getUniqueId());
                break;
            case Starting:
            case Running:
                if (PlayerCache.playerList.contains(player.getUniqueId())) {
                    if (PlayerCache.getPlayerRole(player.getUniqueId()).equals(BasicInfo.playerRole.Runner)) {
                        if (PlayerCache.runnerStatusMap.get(player.getUniqueId()).equals(BasicInfo.runnerStatus.Dead)) {
                            break;
                        }
                    }
                    PlayerReconnect.addReconnectPlayer(player);
                } else {
                    PlayerCache.observerList.remove(player.getUniqueId());
                }
                break;
            default:
                break;
        }
    }
}
