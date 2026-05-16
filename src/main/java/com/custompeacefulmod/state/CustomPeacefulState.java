package com.custompeacefulmod.state;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.PersistentState;

public class CustomPeacefulState extends PersistentState {

    private boolean isCustomPeaceful;
    private double spawnrates;

    public CustomPeacefulState() {
        this.isCustomPeaceful = false;
        this.spawnrates = 1.0;
    }

    public static CustomPeacefulState fromNbt(NbtCompound nbt, WrapperLookup lookup) {
        CustomPeacefulState state = new CustomPeacefulState();

        state.isCustomPeaceful = nbt.getBoolean("isCustomPeaceful");
        state.spawnrates = nbt.getDouble("spawnrates");

        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup lookup) {
        nbt.putBoolean("isCustomPeaceful", isCustomPeaceful);
        nbt.putDouble("spawnrates", spawnrates);
        return nbt;
    }

    public boolean isCustomPeaceful() {
        return isCustomPeaceful;
    }

    public void setCustomPeaceful(boolean customPeaceful) {
        this.isCustomPeaceful = customPeaceful;
        markDirty();
    }

    public double getSpawnrates() {
        return spawnrates;
    }

    public void setSpawnrates(double spawnrates) {
        this.spawnrates = Math.max(0.0, Math.min(100.0, spawnrates));
        markDirty();
    }

    // TYPE FACTORY (UPDATED LAMBDA SIGNATURE)
    public static Type<CustomPeacefulState> getType() {
        return new Type<>(
                CustomPeacefulState::new,
                CustomPeacefulState::fromNbt,
                null
        );
    }
}