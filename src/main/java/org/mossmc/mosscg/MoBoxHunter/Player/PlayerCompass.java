package org.mossmc.mosscg.MoBoxHunter.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.mossmc.mosscg.MoBoxCore.Game.GameBasicInfo;
import org.mossmc.mosscg.MoBoxCore.Game.GameStatus;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerCompass {
    public static void runCompass() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameBasicInfo.gameStatus.equals(GameStatus.gameStatus.Running)) {
                    return;
                }
                if (BasicInfo.compassUnlock) {
                    PlayerCache.hunterList.forEach(uuid -> {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            if (!player.getInventory().contains(Material.COMPASS)) {
                                giveCompass(player);
                            }
                            AtomicBoolean hasCompass = new AtomicBoolean(false);
                            player.getInventory().forEach(itemStack -> {
                                if (itemStack != null) {
                                    if (itemStack.getType().equals(Material.COMPASS)) {
                                        if (hasCompass.get()) {
                                            itemStack.setAmount(0);
                                        }
                                        if (itemStack.getAmount() > 1) {
                                            itemStack.setAmount(1);
                                        }
                                        hasCompass.set(true);
                                    }
                                }
                            });
                            hunterCompass(player);
                        }
                    });
                } else {
                    PlayerCache.hunterList.forEach(uuid -> {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            player.setCompassTarget(player.getLocation());
                        }
                    });
                }
            }
        }.runTaskTimer(Main.instance,0,20);
    }

    public static void hunterCompass(Player hunter) {
        if (!hunter.isOnline()) {
            return;
        }
        final boolean[] sameWorld = {false};
        final double[] minDistance = {0};
        final Player[] closeRunner = new Player[1];
        PlayerCache.runnerList.forEach(uuid -> {
            Player runner = Bukkit.getPlayer(uuid);
            if (runner != null) {
                if (runner.isOnline() && !runner.isDead() && PlayerCache.runnerStatusMap.get(uuid).equals(BasicInfo.runnerStatus.Alive)) {
                    if (runner.getWorld().equals(hunter.getWorld())) {
                        double distance = hunter.getLocation().distance(runner.getLocation());
                        if (distance < minDistance[0] || minDistance[0] == 0) {
                            minDistance[0] = distance;
                            closeRunner[0] = runner;
                        }
                        sameWorld[0] = true;
                    }
                }
            }
        });
        String message;
        if (sameWorld[0]) {
            message = ChatColor.GREEN+"距离你最近的逃亡者："+closeRunner[0].getName()+"，距离："+String.format("%.2f", minDistance[0]);
            if (hunter.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                hunter.getInventory().forEach(itemStack -> {
                    if (itemStack != null) {
                        if (itemStack.getType().equals(Material.COMPASS)) {
                            CompassMeta compassMeta = (CompassMeta) itemStack.getItemMeta();
                            if(compassMeta != null){
                                if (compassMeta.hasLodestone()) {
                                    itemStack.setAmount(0);
                                }
                            }
                        }
                    }
                });
                hunter.setCompassTarget(closeRunner[0].getLocation());
            } else {
                hunter.getInventory().forEach(itemStack -> {
                    if (itemStack != null) {
                        if (itemStack.getType().equals(Material.COMPASS)) {
                            CompassMeta compassMeta = (CompassMeta) itemStack.getItemMeta();
                            if(compassMeta != null){
                                compassMeta.setLodestone(closeRunner[0].getLocation());
                                compassMeta.setLodestoneTracked(false);
                                itemStack.setItemMeta(compassMeta);
                            }
                        }
                    }
                });
            }
        } else {
            message = ChatColor.RED+"目前没有逃亡者跟你所处一个世界，无法追踪距离！";
        }
        TextComponent textComponent = new TextComponent(message);
        hunter.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

    public static void listRemoveCompass(List<UUID> list) {
        list.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                removeCompass(player);
            }
        });
    }

    public static void listGiveCompass(List<UUID> list) {
        list.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                giveCompass(player);
            }
        });
    }

    public static void removeCompass(Player player) {
        player.getInventory().remove(Material.COMPASS);
    }

    public static void giveCompass(Player player) {
        player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
    }
}
