package com.custompeacefulmod.logic;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.MinecraftServer;

public class ExplosionLogic {

    public static boolean shouldPreventExplosionDamage(
            PlayerEntity player,
            DamageSource source
    ) {

        MinecraftServer server = player.getEntityWorld().getServer();

        // Safety check
        if (server == null) {
            return false;
        }

        CustomPeacefulState state =
                CustomPeacefulStateManager.getServerState(server);

        // Mod disabled -> vanilla behavior
        if (!state.isCustomPeaceful()) {
            return false;
        }

        // Only block explosion damage
        return source.isIn(DamageTypeTags.IS_EXPLOSION);
    }
}