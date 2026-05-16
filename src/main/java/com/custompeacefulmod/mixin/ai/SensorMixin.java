package com.custompeacefulmod.mixin.ai;

import com.custompeacefulmod.logic.AggressionLogic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sensor.class)
public class SensorMixin {

    @Inject(
            method = "testTargetPredicate",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void custompeaceful$preventBrainTargeting(
            ServerWorld world,
            LivingEntity mob,
            LivingEntity target,
            CallbackInfoReturnable<Boolean> cir
    ) {

        // only mobs
        if (!(mob instanceof MobEntity mobEntity)) {
            return;
        }

        // only block player targeting
        if (!(target instanceof PlayerEntity)) {
            return;
        }

        if (AggressionLogic.isCustomPeacefulEnabled(mobEntity)) {
            cir.setReturnValue(false);
        }
    }
}