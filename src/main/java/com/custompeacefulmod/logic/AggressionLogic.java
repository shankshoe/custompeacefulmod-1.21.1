package com.custompeacefulmod.logic;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public class AggressionLogic {

    public static boolean shouldPreventTarget(
            MobEntity mob,
            LivingEntity target
    ) {

        // only prevent targeting players
        if (!(target instanceof PlayerEntity)) {
            return false;
        }

        MinecraftServer server = mob.getEntityWorld().getServer();

        if (server == null) {
            return false;
        }

        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);

        // only active when persistent state is enabled
        return state.isCustomPeaceful();
    }

    public static boolean isCustomPeacefulEnabled(Entity entity) {

        MinecraftServer server = entity.getEntityWorld().getServer();

        if (server == null) {
            return false;
        }

        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);

        return state.isCustomPeaceful();
    }
}