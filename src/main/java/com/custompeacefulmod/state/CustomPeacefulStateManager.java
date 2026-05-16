package com.custompeacefulmod.state;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class CustomPeacefulStateManager {

    public static CustomPeacefulState getServerState(MinecraftServer server) {

        PersistentStateManager manager =
                server.getWorld(World.OVERWORLD).getPersistentStateManager();

        return manager.getOrCreate(CustomPeacefulState.TYPE);
    }
}