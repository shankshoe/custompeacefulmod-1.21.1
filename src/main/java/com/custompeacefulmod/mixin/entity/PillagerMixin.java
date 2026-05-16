package com.custompeacefulmod.mixin.entity;

import com.custompeacefulmod.logic.IllagerWeaponRemover;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PillagerEntity.class)
public class PillagerMixin {

    @Inject(
            method = "initialize",
            at = @At("TAIL")
    )
    private void removeCrossbow(
            ServerWorldAccess world,
            LocalDifficulty difficulty,
            SpawnReason spawnReason,
            EntityData entityData,
            CallbackInfoReturnable<EntityData> cir
    ) {

        PillagerEntity pillager = (PillagerEntity) (Object) this;

        IllagerWeaponRemover.tryRemoveWeapon(pillager);
    }
}