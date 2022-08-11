package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxCore.Chat.ChatChannel;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;

public class PlayerReset {
    @SuppressWarnings("deprecation")
    public static void resetPlayer(Player player) {
        player.setExp(0);
        player.setFoodLevel(40);
        player.setHealth(player.getMaxHealth());
        player.getInventory().clear();
    }

    public static void resetPlayerRole(Player player, BasicInfo.playerRole role) {
        PlayerCache.runnerList.remove(player.getUniqueId());
        PlayerCache.hunterList.remove(player.getUniqueId());
        PlayerCache.observerList.remove(player.getUniqueId());
        PlayerCache.playerList.remove(player.getUniqueId());
        PlayerCache.runnerStatusMap.remove(player.getUniqueId());
        ChatChannel.resetPlayerChat(player.getUniqueId());
        PlayerCache.playerList.add(player.getUniqueId());
        Bukkit.broadcastMessage(ChatColor.GREEN+"玩家"+player.getName()+"加入了"+role.name()+"阵营");
        PlayerCompass.removeCompass(player);
        switch (role) {
            case Hunter:
                PlayerCache.hunterList.add(player.getUniqueId());
                PlayerChat.setPlayerChatHunter(player.getUniqueId());
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case Runner:
                PlayerCache.runnerList.add(player.getUniqueId());
                PlayerChat.setPlayerChatRunner(player.getUniqueId());
                PlayerCache.runnerStatusMap.put(player.getUniqueId(), BasicInfo.runnerStatus.Alive);
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case Observer:
                PlayerCache.observerList.add(player.getUniqueId());
                PlayerChat.setPlayerChatObserver(player.getUniqueId());
                player.setGameMode(GameMode.SPECTATOR);
                break;
            default:
                break;
        }
    }
}
