package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerCache {
    //playerList包含hunterList和runnerList
    //observerList包含其它游戏之外的玩家
    public static List<UUID> playerList = new ArrayList<>();
    public static List<UUID> hunterList = new ArrayList<>();
    public static List<UUID> runnerList = new ArrayList<>();
    public static List<UUID> observerList = new ArrayList<>();

    public static Map<UUID,BasicInfo.runnerStatus> runnerStatusMap = new HashMap<>();

    public static String getHunterNameList() {
        StringBuilder nameList = new StringBuilder();
        AtomicBoolean first = new AtomicBoolean(false);
        AtomicBoolean alive = new AtomicBoolean(false);
        hunterList.forEach(uuid -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            if (first.get()) {
                nameList.append("、");
            }
            nameList.append(player.getName());
            first.set(true);
            alive.set(true);
        });
        if (!alive.get()) {
            nameList.append("无人生还");
        }
        return nameList.toString();
    }

    public static String getRunnerNameList() {
        StringBuilder nameList = new StringBuilder();
        AtomicBoolean first = new AtomicBoolean(false);
        AtomicBoolean alive = new AtomicBoolean(false);
        runnerList.forEach(uuid -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            if (runnerStatusMap.get(uuid).equals(BasicInfo.runnerStatus.Alive)) {
                if (first.get()) {
                    nameList.append("、");
                }
                nameList.append(player.getName());
                first.set(true);
                alive.set(true);
            }
        });
        if (!alive.get()) {
            nameList.append("无人生还");
        }
        return nameList.toString();
    }

    public static BasicInfo.playerRole getPlayerRole(UUID player) {
        if (hunterList.contains(player)) {
            return BasicInfo.playerRole.Hunter;
        }
        if (runnerList.contains(player)) {
            return BasicInfo.playerRole.Runner;
        }
        return BasicInfo.playerRole.Observer;
    }
}
