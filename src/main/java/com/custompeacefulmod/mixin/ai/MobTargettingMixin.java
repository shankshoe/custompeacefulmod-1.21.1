package com.custompeacefulmod.mixin.ai;

import com.custompeacefulmod.logic.AggressionLogic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobTargettingMixin {

    @Unique
    private MobEntity custompeaceful$self() {
        return (MobEntity) (Object) this;
    }

    /**
     * Prevent mobs from targeting players.
     */
    @Inject(
            method = "setTarget",
            at = @At("HEAD"),
            cancellable = true
    )
    private void custompeaceful$preventPlayerTargeting(
            LivingEntity target,
            CallbackInfo ci
    ) {

        MobEntity self = custompeaceful$self();

        if (AggressionLogic.shouldPreventTarget(self, target)) {
            ci.cancel();
        }
    }

    /**
     * Safety net for mobs that bypass setTarget().
     * Covers Breeze, Ghast, Piglin Brute, Bees, etc.
     */
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void custompeaceful$clearPlayerTarget(
            CallbackInfo ci
    ) {

        MobEntity self = custompeaceful$self();

        LivingEntity target = self.getTarget();

        if (target instanceof PlayerEntity
                && AggressionLogic.isCustomPeacefulEnabled(self)) {

            self.setTarget(null);
        }
    }
}