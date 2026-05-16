package com.custompeacefulmod.logic;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public class HungerLogic {

    // Players currently auto-refilling
    private static final Set<UUID> REFILLING_PLAYERS = new HashSet<>();

    // How often to restore hunger (ticks)
    private static final int REFILL_INTERVAL = 3; // once per second

    public static void handleHungerTick(PlayerEntity player) {

        // SERVER ONLY
        if (player.getWorld().isClient()) return;

        MinecraftServer server = player.getServer();
        if (server == null) return;

        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);

        if (!state.isCustomPeaceful()) return;

        if (player.isCreative() || player.isSpectator()) return;

        var hunger = player.getHungerManager();
        int food = hunger.getFoodLevel();
        UUID uuid = player.getUuid();

        // STEP 1:
        // Trigger refill mode once hunger reaches 1 or below
        if (food <= 1) {
            REFILLING_PLAYERS.add(uuid);
        }

        // STEP 2:
        // Smooth refill while active
        if (REFILLING_PLAYERS.contains(uuid)) {

            // Add 1 hunger point every second
            if (player.age % REFILL_INTERVAL == 0) {
                hunger.add(1, 0.0f);
            }

            // STEP 3:
            // Stop when full
            if (hunger.getFoodLevel() >= 20) {
                REFILLING_PLAYERS.remove(uuid);
            }
        }
    }
}