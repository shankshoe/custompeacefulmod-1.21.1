package com.custompeacefulmod.state;

import net.minecraft.datafixer.DataFixTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

public class CustomPeacefulState extends PersistentState {

    private boolean isCustomPeaceful;
    private double spawnrates;

    public CustomPeacefulState() {
        this(false, 1.0);
    }

    public CustomPeacefulState(boolean isCustomPeaceful, double spawnrates) {
        this.isCustomPeaceful = isCustomPeaceful;
        this.spawnrates = spawnrates;
    }

    public static final Codec<CustomPeacefulState> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.BOOL.fieldOf("isCustomPeaceful")
                            .forGetter(s -> s.isCustomPeaceful),
                    Codec.DOUBLE.fieldOf("spawnrates")
                            .forGetter(s -> s.spawnrates)
            ).apply(instance, CustomPeacefulState::new));


public static final PersistentStateType<CustomPeacefulState> TYPE =
        new PersistentStateType<CustomPeacefulState>(
                "custompeaceful_state",
                CustomPeacefulState::new,
                CODEC,
                DataFixTypes.LEVEL
        );
            

    public boolean isCustomPeaceful() {
        return isCustomPeaceful;
    }

    public void setCustomPeaceful(boolean value) {
        this.isCustomPeaceful = value;
        markDirty();
    }

    public double getSpawnrates() {
        return spawnrates;
    }

    public void setSpawnrates(double spawnrates) {
        this.spawnrates = Math.max(0.0, Math.min(100.0, spawnrates));
        markDirty();
    }
}