package org.mossmc.mosscg.MoBoxHunter.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxCore.Player.PlayerDamage;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCompass;

public class ListenerRespawn implements Listener {
    @EventHandler
    public static void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerDamage.setNoDamageTick(player, Main.getConfig.getInt("respawnSafeTime")*20);
        if (GameBasicInfo.gameStatus.equals(GameStatus.gameStatus.Ending)) {
            event.setRespawnLocation(BasicInfo.endLocation);
        }
        if (!GameBasicInfo.gameStatus.equals(GameStatus.gameStatus.Running)) {
            if (PlayerCache.getPlayerRole(player.getUniqueId()).equals(BasicInfo.playerRole.Runner)) {
                event.setRespawnLocation(BasicInfo.endLocation);
            }
            return;
        }
        if (PlayerCache.getPlayerRole(player.getUniqueId()).equals(BasicInfo.playerRole.Hunter)) {
            if (BasicInfo.compassUnlock) {
                PlayerCompass.giveCompass(player);
            }
        }
    }
}
