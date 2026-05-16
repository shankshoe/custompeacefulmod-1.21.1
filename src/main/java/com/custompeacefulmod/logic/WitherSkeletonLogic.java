package com.custompeacefulmod.logic;

import java.util.List;

import com.custompeacefulmod.CustomPeacefulMod;
import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.state.CustomPeacefulStateManager;


import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class WitherSkeletonLogic {

    public static void tryPickupPumpkin(ServerWorld world, ItemEntity itemEntity) {

        if (itemEntity.isRemoved()) return;

        ItemStack stack = itemEntity.getStack();
        if (!stack.isOf(Items.CARVED_PUMPKIN)) return;

        MinecraftServer server = world.getServer();
        if (server == null) return;

        CustomPeacefulState state = CustomPeacefulStateManager.getServerState(server);
        if (!state.isCustomPeaceful()) return;

        List<WitherSkeletonEntity> skeletons =
                world.getEntitiesByClass(
                        WitherSkeletonEntity.class,
                        itemEntity.getBoundingBox().expand(1.2),
                        Entity::isAlive
                );

        if (skeletons.isEmpty()) return;

        for (WitherSkeletonEntity skeleton : skeletons) {

            if (skeleton.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.CARVED_PUMPKIN)) {continue;}
            double dx = skeleton.getX() - itemEntity.getX();
            double dy = skeleton.getY() - itemEntity.getY();
            double dz = skeleton.getZ() - itemEntity.getZ();

            if (dx * dx + dy * dy + dz * dz > 2.25) continue;
            ItemEntity skullDrop = new ItemEntity(
                    world,
                    skeleton.getX(),
                    skeleton.getY() + 1.0,
                    skeleton.getZ(),
                    new ItemStack(Items.WITHER_SKELETON_SKULL)
            );

            world.spawnEntity(skullDrop);
            
            stack.decrement(1);

            skeleton.equipStack(net.minecraft.entity.EquipmentSlot.HEAD, new ItemStack(Items.CARVED_PUMPKIN));
            
            world.playSound(
                    null,
                    skeleton.getBlockPos(),
                    SoundEvents.BLOCK_BEEHIVE_EXIT,
                    SoundCategory.NEUTRAL,
                    1.0f,
                    1.0f
            );
            if (stack.isEmpty()) {
                itemEntity.discard();
            } else {
                itemEntity.setStack(stack);
            }

            break;
        }
    }
}