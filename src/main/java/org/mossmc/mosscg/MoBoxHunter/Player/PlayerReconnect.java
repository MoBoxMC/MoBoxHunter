package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mossmc.mosscg.MoBoxCore.Chat.ChatChannel;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxHunter.Main;

import java.util.*;

public class PlayerReconnect {
    public static Map<UUID,Long> reconnectMap = new HashMap<>();
    public static List<UUID> kickCache = new ArrayList<>();

    public static int reconnectSecond = 300;

    public static void initReconnect() {
        reconnectSecond = Main.getConfig.getInt("reconnectTime");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameBasicInfo.gameStatus.equals(GameStatus.gameStatus.Running)) {
                    return;
                }
                reconnectMap.forEach((player, leave) -> {
                    if (leave+reconnectSecond*1000L < System.currentTimeMillis()) {
                        kickPlayer(player);
                    }
                });
                kickCache.forEach(player -> {
                    reconnectMap.remove(player);
                });
                kickCache.clear();
            }
        }.runTaskTimer(Main.instance,0,20);
    }

    public static void addReconnectPlayer(Player player) {
        reconnectMap.put(player.getUniqueId(),System.currentTimeMillis());
        Bukkit.broadcastMessage(ChatColor.YELLOW + "玩家"+player.getName()+"离开了游戏！"+reconnectSecond+"秒后将自动移出本场游戏！");
    }

    public static void kickPlayer(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        Bukkit.getPlayer(uuid);
        PlayerCache.playerList.remove(uuid);
        PlayerCache.hunterList.remove(uuid);
        PlayerCache.runnerList.remove(uuid);
        PlayerCache.runnerStatusMap.remove(uuid);
        PlayerCache.observerList.remove(uuid);
        PlayerStatic.killCount.remove(uuid);
        ChatChannel.resetPlayerChat(uuid);
        kickCache.add(uuid);
        PlayerCheck.check();
        Bukkit.broadcastMessage(ChatColor.RED + "玩家"+player.getName()+"超出等待时间未归！已被移出本场游戏！");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "当前玩家列表：");
        Bukkit.broadcastMessage(ChatColor.RED + "猎人: " + PlayerCache.getHunterNameList());
        Bukkit.broadcastMessage(ChatColor.GREEN + "逃亡者: " + PlayerCache.getRunnerNameList());
    }

    public static void reconnectPlayer(UUID uuid) {
        reconnectMap.remove(uuid);
        Player player = Bukkit.getPlayer(uuid);
        assert player != null;
        player.setInvulnerable(false);
        Bukkit.broadcastMessage(ChatColor.GREEN + "玩家"+player.getName()+"已回到游戏！");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "当前玩家列表：");
        Bukkit.broadcastMessage(ChatColor.RED + "猎人: " + PlayerCache.getHunterNameList());
        Bukkit.broadcastMessage(ChatColor.GREEN + "逃亡者: " + PlayerCache.getRunnerNameList());
    }
}
