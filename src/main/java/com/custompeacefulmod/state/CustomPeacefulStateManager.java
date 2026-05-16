package com.custompeacefulmod.state;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentStateManager;

public class CustomPeacefulStateManager {

    private static final String KEY = "custompeaceful_state";

    public static CustomPeacefulState getServerState(MinecraftServer server) {
        PersistentStateManager manager = server.getOverworld().getPersistentStateManager();

        return manager.getOrCreate(
                CustomPeacefulState.getType(),
                KEY
        );
    }
}