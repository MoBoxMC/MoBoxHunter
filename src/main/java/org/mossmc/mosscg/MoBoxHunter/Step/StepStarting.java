package org.mossmc.mosscg.MoBoxHunter.Step;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxCore.Entity.EntityKill;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStart;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxCore.Player.PlayerMove;
import org.mossmc.mosscg.MoBoxHunter.Main;
import org.mossmc.mosscg.MoBoxHunter.Player.*;

public class StepStarting {
    public static void runStep() {
        Main.logger.info(ChatColor.GREEN+"游戏正在进入启动阶段！");
        GameBasicInfo.gameStatus = GameStatus.gameStatus.Starting;
        PlayerDistribute.distribute();
        PlayerLocation.initLocation();
        PlayerReconnect.initReconnect();
        PlayerChat.initPlayerChat();
        PlayerCache.playerList.forEach(uuid -> {
            try {
                Player player = Bukkit.getPlayer(uuid);
                Location location = PlayerLocation.getRandomLocation(PlayerCache.getPlayerRole(uuid));
                if (location != null && player != null) {
                    player.teleport(location);
                    player.setBedSpawnLocation(location);
                    PlayerReset.resetPlayer(player);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        PlayerCompass.runCompass();
        PlayerRadar.runRadar();
        PlayerMove.disableAllMove();
        EntityKill.addKillList(EntityType.DROPPED_ITEM);
        EntityKill.addKillList(EntityType.EXPERIENCE_ORB);
        EntityKill.killAllWorldTargetEntity();
        Main.logger.info(ChatColor.GREEN+"游戏进入到启动阶段！");
        GameStart.startStart();
    }
}
