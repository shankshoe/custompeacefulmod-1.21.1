package com.custompeacefulmod.mixin.entity;

import com.custompeacefulmod.logic.InteractionLogic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class BlazeInteraction {

    @Inject(
        method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onShear(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

        // Only apply to Breeze
        if (!((Object) this instanceof BlazeEntity)) {
            return;
        }

        LivingEntity entity = (LivingEntity) (Object) this;

        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isOf(Items.SHEARS)) {
            return;
        }

        boolean handled = InteractionLogic.handleBlazeShear(
                player,
                entity,
                hand
        );

        if (handled) {
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}