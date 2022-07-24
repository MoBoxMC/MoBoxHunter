package org.mossmc.mosscg.MoBoxHunter.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxHunter.Main;

public class PlayerRadar {
    public static int warnDistance = 50;

    public static void runRadar() {
        warnDistance = Main.getConfig.getInt("radarWarnDistance");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameBasicInfo.gameStatus.equals(GameStatus.gameStatus.Running)) {
                    return;
                }
                PlayerCache.runnerList.forEach(uuidRunner -> {
                    Player runner = Bukkit.getPlayer(uuidRunner);
                    if (runner != null) {
                        final double[] minDistance = {warnDistance + 1};
                        PlayerCache.hunterList.forEach(uuidHunter -> {
                            Player hunter = Bukkit.getPlayer(uuidHunter);
                            if (hunter != null) {
                                if (hunter.isOnline() && !hunter.isDead()) {
                                    if (hunter.getWorld().equals(runner.getWorld()) && !runner.getGameMode().equals(GameMode.SPECTATOR)) {
                                        double distance = hunter.getLocation().distance(runner.getLocation());
                                        if (distance < minDistance[0]) {
                                            minDistance[0] = distance;
                                        }
                                    }
                                }
                            }
                        });
                        String message;
                        if (minDistance[0] < warnDistance) {
                            message = ChatColor.DARK_RED + "猎人正在靠近你！当前距离：" + String.format("%.2f", minDistance[0]);
                        } else {
                            message = ChatColor.DARK_GREEN + "当前周围环境没有猎人";
                        }
                        TextComponent textComponent = new TextComponent(message);
                        runner.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
                    }
                });
            }
        }.runTaskTimer(Main.instance,0,20);
    }
}
