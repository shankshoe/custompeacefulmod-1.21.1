package com.custompeacefulmod.mixin.spawn;

import com.custompeacefulmod.logic.SpawnLogic;
import com.custompeacefulmod.util.ServerHolder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnGroup.class)
public class SpawnRatesMixin {

    @Inject(method = "getCapacity", at = @At("RETURN"), cancellable = true)
    private void custompeaceful$modifyMobCap(CallbackInfoReturnable<Integer> cir) {

        MinecraftServer server = ServerHolder.SERVER;

        if (server == null) {
            return;
        }

        SpawnGroup spawnGroup = (SpawnGroup) (Object) this;

        cir.setReturnValue(
                SpawnLogic.modifySpawnCap(
                        server,
                        spawnGroup,
                        cir.getReturnValue()
                )
        );
    }
}