package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Step.StepEnding;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerCheck {
    public static void check() {
        if (PlayerCache.hunterList.size() == 0) {
            BasicInfo.winner = BasicInfo.playerRole.Runner;
            Bukkit.broadcastMessage(ChatColor.GOLD+"由于所有猎人均已离线！游戏结束！本局将不计入积分！");
            BasicInfo.canPoint = false;
            StepEnding.runStep();
            return;
        }
        if (PlayerCache.runnerList.size() == 0) {
            BasicInfo.winner = BasicInfo.playerRole.Hunter;
            Bukkit.broadcastMessage(ChatColor.GOLD+"由于所有逃亡者均已离线！游戏结束！本局将不计入积分！");
            BasicInfo.canPoint = false;
            StepEnding.runStep();
            return;
        }
        AtomicBoolean gameEnd = new AtomicBoolean(true);
        PlayerCache.runnerList.forEach(player -> {
            if (PlayerCache.runnerStatusMap.get(player).equals(BasicInfo.runnerStatus.Alive)) {
                gameEnd.set(false);
            }
        });
        if (gameEnd.get()) {
            BasicInfo.winner = BasicInfo.playerRole.Hunter;
            Bukkit.broadcastMessage(ChatColor.GOLD+"猎人已消灭所有逃亡者！游戏结束！");
            StepEnding.runStep();
        }
    }
}
