package org.mossmc.mosscg.MoBoxHunter;

import org.bukkit.Location;

public class BasicInfo {
    public static String pluginName = "MoBoxHunter";
    public static String pluginVersion = "V1.6.3.1.1915";
    public static String gameName = "Hunter";

    public static boolean compassUnlock = false;
    public static boolean teamDamage = false;
    public static boolean runnerNetherWorld = false;
    public static boolean runnerEndWorld = false;
    public static boolean hunterNetherWorld = false;
    public static boolean hunterEndWorld = false;
    public static boolean canPoint = false;
    public static boolean canDamage = false;
    public static boolean canInteract = false;

    public static playerRole winner;

    public static Location endLocation;

    public static int startTime = 10;

    public enum playerRole {
        Hunter,Runner,Observer
    }

    public enum runnerStatus {
        Alive,Dead
    }
}
