package org.mossmc.mosscg.MoBoxHunter.Step;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxCore.Info.InfoCountDown;
import org.mossmc.mosscg.MoBoxCore.Player.PlayerMove;
import org.mossmc.mosscg.MoBoxCore.Player.PlayerPick;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerCache;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerDamage;
import org.mossmc.mosscg.MoBoxHunter.World.WorldRule;

@SuppressWarnings("deprecation")
public class StepRunning {
    public static void runStep() {
        Main.logger.info(ChatColor.GREEN+"游戏正在进入游玩阶段！");
        GameBasicInfo.gameStatus = GameStatus.gameStatus.Running;
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "====================================");
        Bukkit.broadcastMessage(ChatColor.GOLD + "欢迎来到MoBoxMC-猎人游戏");
        Bukkit.broadcastMessage(ChatColor.AQUA + "游戏规则：猎人需要阻止逃亡者击杀末影龙或击杀逃亡者以取得胜利。");
        Bukkit.broadcastMessage(ChatColor.AQUA + "逃亡者需要在猎人的追杀下击败末影龙以取得胜利。逃亡者无法复活且由于任何原因死亡均会导致猎人胜利。");
        Bukkit.broadcastMessage(ChatColor.AQUA + "猎人可以通过合成指南针来定位逃亡者的方向；逃亡者可以通过合成指南针摧毁猎人的指南针。");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "祝君好运，末地见！");
        Bukkit.broadcastMessage(ChatColor.GREEN + "逃亡者: " + PlayerCache.getRunnerNameList());
        Bukkit.broadcastMessage(ChatColor.RED + "猎人: " + PlayerCache.getHunterNameList());
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "====================================");
        Main.logger.info(ChatColor.GREEN+"游戏进入到游玩阶段！");
        BasicInfo.startTime = Main.getConfig.getInt("startTime");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (BasicInfo.startTime > 0) {
                    String titleMain = InfoCountDown.getRemainSecondString(BasicInfo.startTime);
                    String titleSub = ChatColor.GREEN+"观察四周...";
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendTitle(titleMain,titleSub);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1.0f,1.0f);
                    });
                    BasicInfo.startTime--;
                } else {
                    String titleMain = ChatColor.AQUA+"Run!";
                    PlayerCache.playerList.forEach(uuid -> {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            player.resetTitle();
                            player.sendTitle(titleMain, null);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
                            player.setGameMode(GameMode.SURVIVAL);
                            PlayerDamage.enableDamage(player);
                            PlayerPick.enablePickUp(player);
                        }
                    });
                    PlayerMove.enableAllMove();
                    BasicInfo.canInteract = true;
                    BasicInfo.canDamage = true;
                    WorldRule.setAfterStartStatus();
                    cancel();
                }
            }
        }.runTaskTimer(Main.instance, 0, 20);
    }
}
