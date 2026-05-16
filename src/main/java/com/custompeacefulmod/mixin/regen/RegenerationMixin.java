package com.custompeacefulmod.mixin.regen;

import com.custompeacefulmod.logic.RegenerationLogic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class RegenerationMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void customPeaceful$regenTick(CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        // Only run on server side
        if (player.getWorld().isClient()) {
            return;
        }

        // Get server instance from world
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }

        MinecraftServer server = serverWorld.getServer();

        RegenerationLogic.tick(player, server);
    }
}