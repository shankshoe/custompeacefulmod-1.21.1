package com.custompeacefulmod.mixin.hunger;

import com.custompeacefulmod.logic.HungerLogic;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerHungerResetMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void custompeacefulmod$hungerTick(CallbackInfo ci) {

        HungerLogic.handleHungerTick((PlayerEntity)(Object)this);
    }
}