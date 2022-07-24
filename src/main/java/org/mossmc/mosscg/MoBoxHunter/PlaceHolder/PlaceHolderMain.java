package org.mossmc.mosscg.MoBoxHunter.PlaceHolder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;

public class PlaceHolderMain extends PlaceholderExpansion {
    @Override
    public String getAuthor() {
        return "MossCG";
    }

    @Override
    public String getIdentifier() {
        return "moboxhunter";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        GameStatus.gameStatus status = GameBasicInfo.gameStatus;
        switch (identifier) {
            case "role":
                BasicInfo.playerRole role = PlayerCache.getPlayerRole(player.getUniqueId());
                if (status == GameStatus.gameStatus.Waiting) {
                    return ChatColor.GRAY+"[等待中]";
                }
                switch (role) {
                    case Runner:
                        return ChatColor.GREEN+"[逃亡者]";
                    case Hunter:
                        return ChatColor.RED+"[猎杀者]";
                    case Observer:
                        return ChatColor.GRAY+"[旁观者]";
                    default:
                        return "未知身份";
                }
            default:
                return "未知变量";
        }
    }
}
