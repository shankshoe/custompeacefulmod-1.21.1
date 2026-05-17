package com.custompeacefulmod.logic;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public class RegenerationLogic {

    private static final int HEAL_INTERVAL = 15; // 0.5 seconds
    private static final float HEAL_AMOUNT = 1.0F; // half heart

    public static void tick(PlayerEntity player, MinecraftServer server) {

        // Safety checks
        if (player.isDead() || player.getHealth() <= 0.0f) {
            return;
        }

        if (player.getEntityWorld().isClient()) {
            return;
        }

        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);

        // Mod disabled → do nothing (vanilla regen unchanged)
        if (!state.isCustomPeaceful()) {
            return;
        }

        // Only heal if not full
        if (player.getHealth() >= player.getMaxHealth()) {
            return;
        }

        // Tick-based healing
        if (player.age % HEAL_INTERVAL == 0) {
            player.heal(HEAL_AMOUNT);
        }
    }
}