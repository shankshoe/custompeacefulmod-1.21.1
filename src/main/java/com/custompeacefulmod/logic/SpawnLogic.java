package com.custompeacefulmod.logic;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.MinecraftServer;

public class SpawnLogic {

    public static int modifySpawnCap(
            MinecraftServer server,
            SpawnGroup spawnGroup,
            int originalCap
    ) {

        // Only hostile mobs
        if (spawnGroup != SpawnGroup.MONSTER) {
            return originalCap;
        }

        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);

        // Mod disabled → vanilla behavior
        if (!state.isCustomPeaceful()) {
            return originalCap;
        }
        
        double multiplier = state.getSpawnrates();

        // 0 = no hostile mobs
        if (multiplier <= 0.0) {
            return 0;
        }

        return Math.max(1, (int) Math.round(originalCap * multiplier));
    }
}