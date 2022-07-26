package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerDistribute {
    public static void distribute() {
        //除重，cacheList后续会用到
        List<UUID> cacheList = new ArrayList<>();
        PlayerCache.playerList.forEach(uuid -> {
            if (!cacheList.contains(uuid)) {
                cacheList.add(uuid);
            }
        });
        PlayerCache.playerList.clear();
        PlayerCache.playerList = new ArrayList<>(cacheList);

        //初始化数据，这里的数据都是不会直接改变的
        int count = PlayerCache.playerList.size();//玩家总数
        int partRunner = Main.getConfig.getInt("partRunner");//逃亡者比例
        int partHunter = Main.getConfig.getInt("partHunter");//猎杀者比例

        //初始化变量，这里都是会动的变量
        int remain = count;
        int remainRunner = partRunner;
        int remainHunter = partHunter;
        Random random = new Random();

        //分配
        while (remain > 0) {
            remain--;
            UUID playerUUID = cacheList.get(random.nextInt(cacheList.size()));
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
            cacheList.remove(playerUUID);
            if (remainRunner <= 0 && remainHunter <= 0) {
                remainRunner = partRunner;
                remainHunter = partHunter;
            }
            if (remainRunner > 0) {
                PlayerCache.runnerList.add(playerUUID);
                PlayerCache.runnerStatusMap.put(playerUUID, BasicInfo.runnerStatus.Alive);
                Main.logger.info(ChatColor.GREEN+"玩家"+player.getName()+"已被分配到逃亡者");
                remainRunner--;
                continue;
            }
            if (remainHunter > 0) {
                PlayerCache.hunterList.add(playerUUID);
                Main.logger.info(ChatColor.GREEN+"玩家"+player.getName()+"已被分配到猎杀者");
                remainHunter--;
            }
        }
    }
}
