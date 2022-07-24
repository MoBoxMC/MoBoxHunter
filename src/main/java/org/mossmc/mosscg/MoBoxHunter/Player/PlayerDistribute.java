package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;

import java.util.Random;

public class PlayerDistribute {
    public static void distribute() {
        //初始化数据，这里的数据都是不会直接改变的
        int count = PlayerCache.playerList.size();//玩家总数
        int maxRunner = Main.getConfig.getInt("maxRunner");//最大逃亡者数
        int maxPlayer = Main.getConfig.getInt("maxPlayer");//最大玩家数
        int step = maxPlayer/maxRunner;//每增加一个逃亡者所需要的人数
        //计算逃亡者数量
        int runner = 1;//逃亡者数
        int countCache = count;
        while (countCache > step) {
            countCache = countCache - step;
            runner++;
        }
        //随机分配逃亡者
        Random random = new Random();
        int runnerCache = runner;
        while (runnerCache > 0) {
            int target = random.nextInt(count-1);
            OfflinePlayer player = Bukkit.getOfflinePlayer(PlayerCache.playerList.get(target));
            if (PlayerCache.runnerList.contains(PlayerCache.playerList.get(target))) {
                runnerCache--;
                continue;
            }
            PlayerCache.runnerList.add(PlayerCache.playerList.get(target));
            PlayerCache.runnerStatusMap.put(PlayerCache.playerList.get(target), BasicInfo.runnerStatus.Alive);
            Main.logger.info(ChatColor.GREEN+"玩家"+player.getName()+"已被分配到逃亡者");
            runnerCache--;
        }
        //剩下为猎人
        PlayerCache.playerList.forEach(target -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(target);
            if (!PlayerCache.runnerList.contains(target)) {
                PlayerCache.hunterList.add(target);
                Main.logger.info(ChatColor.GREEN+"玩家"+player.getName()+"已被分配到猎杀者");
            }
        });
        //自此，分配完成
    }
}
