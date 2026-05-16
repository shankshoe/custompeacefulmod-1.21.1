package com.custompeacefulmod.command;

import com.custompeacefulmod.state.CustomPeacefulStateManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CustomPeacefulCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(
                CommandManager.literal("custompeaceful")
                        .requires(source -> source.hasPermissionLevel(2))

                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .then(CommandManager.argument("spawnRates", DoubleArgumentType.doubleArg(0.0, 100.0))
                                        .executes(ctx -> {

                                            boolean enabled = BoolArgumentType.getBool(ctx, "enabled");
                                            double spawnRates = DoubleArgumentType.getDouble(ctx, "spawnRates");

                                            var server = ctx.getSource().getServer();
                                            var state = CustomPeacefulStateManager.getServerState(server);

                                            state.setCustomPeaceful(enabled);
                                            state.setSpawnrates(spawnRates);

                                            ctx.getSource().sendFeedback(
                                                    () -> Text.literal("Custom Peaceful set to " + enabled +
                                                            " | Spawn rates: " + spawnRates),
                                                    true
                                            );

                                            return 1;
                                        })
                                )
                        )
        );
    }
}