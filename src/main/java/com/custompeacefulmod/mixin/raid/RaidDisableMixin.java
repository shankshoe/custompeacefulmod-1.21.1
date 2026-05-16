package com.custompeacefulmod.mixin.raid;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class RaidDisableMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void custompeaceful$disableRaid(ServerWorld world, CallbackInfo ci) {

        if (world == null || world.getServer() == null) {
            return;
        }

        CustomPeacefulState state =
                CustomPeacefulStateManager.getServerState(world.getServer());

        if (state != null && state.isCustomPeaceful()) {
            ci.cancel();
        }
    }
}