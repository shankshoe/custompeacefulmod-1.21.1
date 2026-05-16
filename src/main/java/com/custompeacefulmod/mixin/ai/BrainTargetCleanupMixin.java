package com.custompeacefulmod.mixin.ai;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.custompeacefulmod.logic.AggressionLogic;

import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(MobEntity.class)
public abstract class BrainTargetCleanupMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void custompeaceful$clearBrainPlayerTargets(CallbackInfo ci) {

        MobEntity self = (MobEntity)(Object)this;

        if (!AggressionLogic.isCustomPeacefulEnabled(self)) {
            return;
        }

        Brain<?> brain = self.getBrain();
        if (brain == null) {
            return;
        }

        // ATTACK_TARGET (safe)
        var attackTarget = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if (attackTarget != null && attackTarget.isPresent()) {
            if (attackTarget.get() instanceof PlayerEntity) {
                brain.forget(MemoryModuleType.ATTACK_TARGET);
            }
        }

        // ANGRY_AT (piglins etc.)
        brain.forget(MemoryModuleType.ANGRY_AT);

        // NEAREST_ATTACKABLE (safe)
        var nearest = brain.getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE);
        if (nearest != null && nearest.isPresent()) {
            if (nearest.get() instanceof PlayerEntity) {
                brain.forget(MemoryModuleType.NEAREST_ATTACKABLE);
            }
        }
    }
    
}