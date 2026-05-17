package com.custompeacefulmod.logic;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class IllagerWeaponRemover {

    public static void tryRemoveWeapon(MobEntity mob) {

        MinecraftServer server = mob.getEntityWorld().getServer();

        // Safety check
        if (server == null) {
            return;
        }

        // Get persistent state
        CustomPeacefulState state =
                CustomPeacefulStateManager.getServerState(server);

        // Only remove weapons when enabled
        if (!state.isCustomPeaceful()) {
            return;
        }

        // Remove main hand weapon
        mob.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }
}