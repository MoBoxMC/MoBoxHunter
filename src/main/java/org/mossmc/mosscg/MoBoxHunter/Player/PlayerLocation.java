package org.mossmc.mosscg.MoBoxHunter.Player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.mossmc.mosscg.MoBoxHunter.BasicInfo;
import org.mossmc.mosscg.MoBoxHunter.Main;

import java.util.Random;

public class PlayerLocation {
    public static double hunterXBasic = 5;
    public static int hunterXRandom = 5;
    public static double hunterZBasic = 5;
    public static int hunterZRandom = 5;
    public static double hunterYBasic = 2;
    public static String hunterSpawnWorldName;
    public static World hunterSpawnWorld;

    public static double runnerXBasic = 20;
    public static int runnerXRandom = 5;
    public static double runnerZBasic = 20;
    public static int runnerZRandom = 5;
    public static double runnerYBasic = 2;
    public static String runnerSpawnWorldName;
    public static World runnerSpawnWorld;

    public static void initLocation() {
        hunterXBasic = Main.getConfig.getDouble("hunterXBasic");
        hunterXRandom = Main.getConfig.getInt("hunterXRandom");
        hunterZBasic = Main.getConfig.getDouble("hunterZBasic");
        hunterZRandom = Main.getConfig.getInt("hunterZRandom");
        hunterYBasic = Main.getConfig.getDouble("hunterYBasic");
        hunterSpawnWorldName = Main.getConfig.getString("hunterSpawnWorld");
        assert hunterSpawnWorldName != null;
        hunterSpawnWorld = Bukkit.getWorld(hunterSpawnWorldName);

        runnerXBasic = Main.getConfig.getDouble("runnerXBasic");
        runnerXRandom = Main.getConfig.getInt("runnerXRandom");
        runnerZBasic = Main.getConfig.getDouble("runnerZBasic");
        runnerZRandom = Main.getConfig.getInt("runnerZRandom");
        runnerYBasic = Main.getConfig.getDouble("runnerYBasic");
        runnerSpawnWorldName = Main.getConfig.getString("runnerSpawnWorld");
        assert runnerSpawnWorldName != null;
        runnerSpawnWorld = Bukkit.getWorld(runnerSpawnWorldName);

    }

    public static Location getRandomLocation(BasicInfo.playerRole role) {
        Location location;
        Random random = new Random();
        boolean direction;//true为正方向，反之为反方向
        double randomX;
        double randomZ;
        switch (role) {
            case Hunter:
                location = new Location(hunterSpawnWorld,0.0,0.0,0.0);
                direction = random.nextBoolean();
                if (direction) {location.setX(hunterXBasic);} else {location.setX(-hunterXBasic);}
                direction = random.nextBoolean();
                if (direction) {location.setZ(hunterZBasic);} else {location.setZ(-hunterZBasic);}
                randomX = random.nextInt(hunterXRandom*2)-hunterXRandom;
                randomZ = random.nextInt(hunterZRandom*2)-hunterZRandom;
                location.add(randomX,0.0,randomZ);
                location = setHighestBlockLocation(location);
                location.add(0.5,hunterYBasic,0.5);
                break;
            case Runner:
                location = new Location(runnerSpawnWorld,0.0,0.0,0.0);
                direction = random.nextBoolean();
                if (direction) {location.setX(runnerXBasic);} else {location.setX(-runnerXBasic);}
                direction = random.nextBoolean();
                if (direction) {location.setZ(runnerZBasic);} else {location.setZ(-runnerZBasic);}
                randomX = random.nextInt(runnerXRandom*2)-runnerXRandom;
                randomZ = random.nextInt(runnerZRandom*2)-runnerZRandom;
                location.add(randomX,0.0,randomZ);
                location = setHighestBlockLocation(location);
                location.add(0.5,runnerYBasic,0.5);
                break;
            case Observer:
            default:
                return null;
        }
        return location;
    }

    public static Location setHighestBlockLocation(Location location) {
        Bed bed = null;
        World world = location.getWorld();
        assert world != null;
        Block block = world.getHighestBlockAt(location);
        block.setType(Material.GLASS);
        return block.getLocation();
    }
}
