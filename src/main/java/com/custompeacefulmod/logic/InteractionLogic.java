package com.custompeacefulmod.logic;

import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InteractionLogic {

    public static boolean handleBlazeShear(
            PlayerEntity player,
            LivingEntity entity,
            Hand hand
    ) {
        return handleGenericShear(player, entity, hand, Items.BLAZE_ROD);
    }

    public static boolean handleBreezeShear(
            PlayerEntity player,
            LivingEntity entity,
            Hand hand,
            ItemStack customDrop
    ) {
        return handleGenericShear(player, entity, hand, customDrop.getItem());
    }

    private static boolean handleGenericShear(
            PlayerEntity player,
            LivingEntity entity,
            Hand hand,
            net.minecraft.item.Item dropItem
    ) {
        World world = player.getEntityWorld();

        if (world.isClient()) return false;
        if (!(world instanceof net.minecraft.server.world.ServerWorld serverWorld)) {
            return false;
        }

        MinecraftServer server = serverWorld.getServer();
        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);

        if (!state.isCustomPeaceful()) {
            return false;
        }

        ItemStack stack = player.getStackInHand(hand);

        // must be shears
        if (stack.getItem() != Items.SHEARS) {
            return false;
        }

        stack.damage(
                    30,
                    player,
                    hand == Hand.MAIN_HAND ?
                    net.minecraft.entity.EquipmentSlot.MAINHAND : net.minecraft.entity.EquipmentSlot.OFFHAND
                );

        entity.dropStack(serverWorld, new ItemStack(dropItem, 1));

        world.playSound(
                null,
                entity.getBlockPos(),
                net.minecraft.sound.SoundEvents.ENTITY_SHEEP_SHEAR,
                net.minecraft.sound.SoundCategory.PLAYERS,
                1.0f,
                1.0f
            );

        return true;
    }
}