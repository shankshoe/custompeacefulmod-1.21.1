package com.custompeacefulmod.mixin.entity;

import com.custompeacefulmod.logic.WitherSkeletonLogic;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class WitherSkeletonInteraction {

    @Inject(method = "tick", at = @At("HEAD"))
    private void custompeaceful$witherSkeletonPickup(CallbackInfo ci) {

        ItemEntity self = (ItemEntity)(Object)this;

        if (self.getWorld().isClient()) return;
        if (self.age % 40 != 0) return;
        if (!(self.getWorld() instanceof ServerWorld serverWorld)) return;

        if (!self.getStack().isOf(net.minecraft.item.Items.CARVED_PUMPKIN)) return;


        WitherSkeletonLogic.tryPickupPumpkin(serverWorld, self);
    }
}