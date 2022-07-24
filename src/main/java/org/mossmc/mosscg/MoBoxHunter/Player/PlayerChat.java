package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.ChatColor;
import org.mossmc.mosscg.MoBoxCore.Chat.ChatChannel;

import java.util.UUID;

public class PlayerChat {
    public static void initPlayerChat() {
        ChatChannel.addChannel("runner");
        ChatChannel.addChannel("hunter");
        ChatChannel.addChannel("observer");
        ChatChannel.setChatCopyChannel("runner","observer");
        ChatChannel.setChatCopyChannel("hunter","observer");
        PlayerCache.runnerList.forEach(PlayerChat::setPlayerChatRunner);
        PlayerCache.hunterList.forEach(PlayerChat::setPlayerChatHunter);
        ChatChannel.useChannelChat = true;
    }

    public static void setPlayerChatRunner(UUID player) {
        ChatChannel.resetPlayerChat(player);
        ChatChannel.addPlayerChatChannel(player,"runner");
        ChatChannel.setPlayerChatColor(player, ChatColor.GRAY);
        ChatChannel.setPlayerChatPrefix(player,ChatColor.GREEN+"[逃亡者]");
    }

    public static void setPlayerChatHunter(UUID player) {
        ChatChannel.resetPlayerChat(player);
        ChatChannel.addPlayerChatChannel(player,"hunter");
        ChatChannel.setPlayerChatColor(player, ChatColor.GRAY);
        ChatChannel.setPlayerChatPrefix(player,ChatColor.RED+"[猎杀者]");
    }

    public static void setPlayerChatObserver(UUID player) {
        ChatChannel.resetPlayerChat(player);
        ChatChannel.addPlayerChatChannel(player,"observer");
        ChatChannel.setPlayerChatColor(player, ChatColor.GRAY);
        ChatChannel.setPlayerChatPrefix(player,ChatColor.GRAY+"[观察者]");
    }
}
