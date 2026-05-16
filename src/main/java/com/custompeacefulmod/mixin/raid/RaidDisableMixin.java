package com.custompeacefulmod.mixin.raid;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class RaidDisableMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void disableRaid(CallbackInfo ci) {

        Raid raid = (Raid) (Object) this;

        // Get server safely
        MinecraftServer server = raid.getWorld().getServer();

        if (server == null) {
            return;
        }

        // Get persistent state
        CustomPeacefulState state =
                CustomPeacefulStateManager.getServerState(server);

        // Only disable raids when CustomPeaceful is enabled
        if (state.isCustomPeaceful()) {
            raid.invalidate();
        }
    }
}