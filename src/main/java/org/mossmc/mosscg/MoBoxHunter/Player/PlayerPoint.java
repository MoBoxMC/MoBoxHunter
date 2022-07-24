package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;
import org.mossmc.mosscg.MoBoxPoint.User.UserUpdate;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerPoint {
    public static Map<UUID,Integer> playerPointMap = new HashMap<>();
    public static Map<UUID,Integer> playerPointKillMap = new HashMap<>();
    public static Map<Integer,UUID> playerPointRankMap = new HashMap<>();

    public static List<UUID> playerCompleteList = new ArrayList<>();

    public static int basicPoint;
    public static int pointRunnerKill;
    public static int pointHunterKill;
    public static int pointWinnerRunner;
    public static int pointWinnerHunter;

    public static String pointNameMain;
    public static String pointNameTotal;
    public static String pointNameWin;
    public static String pointNameKill;
    public static String pointNameDeath;

    public static void countPlayerPoint() {
        basicPoint = Main.getConfig.getInt("pointBasic");
        pointRunnerKill = Main.getConfig.getInt("pointRunnerKill");
        pointHunterKill = Main.getConfig.getInt("pointHunterKill");
        pointWinnerRunner = Main.getConfig.getInt("pointWinnerRunner");
        pointWinnerHunter = Main.getConfig.getInt("pointWinnerHunter");

        pointNameMain = Main.getConfig.getString("pointNameMain");
        pointNameTotal = Main.getConfig.getString("pointNameTotal");
        pointNameWin = Main.getConfig.getString("pointNameWin");
        pointNameKill = Main.getConfig.getString("pointNameKill");
        pointNameDeath = Main.getConfig.getString("pointNameDeath");

        PlayerCache.runnerList.forEach(uuid -> {
            addPlayerPoint(uuid,basicPoint);
            if (PlayerStatic.killCount.containsKey(uuid)) {
                int kill = PlayerStatic.killCount.get(uuid);
                playerPointKillMap.put(uuid,pointRunnerKill*kill);
                addPlayerPoint(uuid,pointRunnerKill*kill);
            }
        });

        PlayerCache.hunterList.forEach(uuid -> {
            addPlayerPoint(uuid,basicPoint);
            if (PlayerStatic.killCount.containsKey(uuid)) {
                int kill = PlayerStatic.killCount.get(uuid);
                playerPointKillMap.put(uuid,pointHunterKill*kill);
                addPlayerPoint(uuid,pointHunterKill*kill);
            }
        });

        if (BasicInfo.winner.equals(BasicInfo.playerRole.Runner)) {
            PlayerCache.runnerList.forEach(uuid -> {
                addPlayerPoint(uuid, pointWinnerRunner);
            });
        }

        if (BasicInfo.winner.equals(BasicInfo.playerRole.Hunter)) {
            PlayerCache.hunterList.forEach(uuid -> {
                addPlayerPoint(uuid, pointWinnerHunter);
            });
        }

        Map<UUID,Integer> cacheMap = new HashMap<>(playerPointMap);
        UUID rankPlayer;
        rankPlayer = getMapMaxKey(cacheMap);
        playerPointRankMap.put(1,rankPlayer);
        rankPlayer = getMapMaxKey(cacheMap);
        playerPointRankMap.put(2,rankPlayer);
        rankPlayer = getMapMaxKey(cacheMap);
        playerPointRankMap.put(3,rankPlayer);
    }

    public static void savePlayerPoint() {
        if (!BasicInfo.canPoint) {
            return;
        }
        //增加本场积分（包括基础积分，击杀积分，胜场积分）
        playerPointMap.forEach((uuid, integer) -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            if (!playerCompleteList.contains(uuid)) {
                UserUpdate.userAddScore(player.getName(),pointNameMain,integer);
                playerCompleteList.add(uuid);
            }
        });

        //记录玩家数据
        playerCompleteList.forEach(uuid -> UserUpdate.userAddScore(uuid,pointNameTotal,1));
        PlayerStatic.killCount.forEach((uuid, integer) -> UserUpdate.userAddScore(uuid,pointNameKill,integer));
        PlayerStatic.deathCount.forEach((uuid, integer) -> UserUpdate.userAddScore(uuid,pointNameDeath,integer));
        if (BasicInfo.winner.equals(BasicInfo.playerRole.Hunter)) {
            PlayerCache.hunterList.forEach(uuid -> UserUpdate.userAddScore(uuid,pointNameWin,1));
        }
        if (BasicInfo.winner.equals(BasicInfo.playerRole.Runner)) {
            PlayerCache.runnerList.forEach(uuid -> UserUpdate.userAddScore(uuid,pointNameWin,1));
        }
    }

    public static void addPlayerPoint(UUID uuid,int point) {
        if (!playerPointMap.containsKey(uuid)) {
            playerPointMap.put(uuid,0);
        }
        int oldPoint = playerPointMap.get(uuid);
        playerPointMap.replace(uuid,oldPoint+point);
    }

    public static int getPlayerWinnerPoint(UUID uuid) {
        switch (PlayerCache.getPlayerRole(uuid)) {
            case Runner:
                return pointWinnerRunner;
            case Hunter:
                return pointWinnerHunter;
            default:
                return 0;
        }
    }

    public static UUID getMapMaxKey(Map<UUID,Integer> targetMap) {
        AtomicReference<UUID> playerReturn = new AtomicReference<>();
        final int[] playerPointMax = {0};
        targetMap.forEach((player, integer) -> {
            if (integer > playerPointMax[0]) {
                playerReturn.set(player);
                playerPointMax[0] = integer;
            }
        });
        targetMap.remove(playerReturn.get());
        return playerReturn.get();
    }

    public static String getRankPlayerName(int rank) {
        if (playerPointRankMap.get(rank) == null) {
            return "无";
        } else {
            return Bukkit.getOfflinePlayer(playerPointRankMap.get(rank)).getName();
        }

    }

    public static String getRankPlayerPoint(int rank) {
        if (playerPointRankMap.get(rank) == null) {
            return "0";
        } else {
            return String.valueOf(PlayerPoint.playerPointMap.get(playerPointRankMap.get(rank)));
        }
    }
}
