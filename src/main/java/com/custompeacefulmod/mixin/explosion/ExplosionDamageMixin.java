package com.custompeacefulmod.mixin.explosion;

import com.custompeacefulmod.logic.ExplosionLogic;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class ExplosionDamageMixin {

    @Inject(
            method = "damage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void custompeaceful$preventExplosionDamage(
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir
    ) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        if (ExplosionLogic.shouldPreventExplosionDamage(player, source)) {

            // false = damage was NOT applied
            cir.setReturnValue(false);
        }
    }
}