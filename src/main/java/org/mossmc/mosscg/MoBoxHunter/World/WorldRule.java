package org.mossmc.mosscg.MoBoxHunter.World;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.mossmc.mosscg.MoBoxHunter.Player.PlayerLocation;

public class WorldRule {
    public static void setBeforeStartStatus() {
        Bukkit.getWorlds().forEach(world -> {
            world.setPVP(false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            world.setDifficulty(Difficulty.PEACEFUL);
        });
    }

    public static void setAfterStartStatus() {
        Bukkit.getWorlds().forEach(world -> {
            world.setPVP(true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, true);
            world.setGameRule(GameRule.DO_FIRE_TICK, true);
            world.setGameRule(GameRule.MOB_GRIEFING, true);
            world.setGameRule(GameRule.FALL_DAMAGE, true);
            world.setDifficulty(Difficulty.HARD);
        });
        Location spawnLocation = new Location(PlayerLocation.hunterSpawnWorld,0.0,0.0,0.0);
        spawnLocation = PlayerLocation.hunterSpawnWorld.getHighestBlockAt(spawnLocation).getLocation();
        spawnLocation.add(0.0,1.0,0.0);
        PlayerLocation.hunterSpawnWorld.setSpawnLocation(spawnLocation);
    }
}
