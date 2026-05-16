package com.custompeacefulmod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.custompeacefulmod.command.CustomPeacefulCommand;
import com.custompeacefulmod.state.CustomPeacefulState;
import com.custompeacefulmod.util.ServerHolder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CustomPeacefulMod implements ModInitializer {
	public static final String MOD_ID = "custompeacefulmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Global state instance
	public static final CustomPeacefulState STATE = new CustomPeacefulState();

	@Override
	public void onInitialize() {
		LOGGER.info("Hope the night's not scary after this!");

		// Register commands
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CustomPeacefulCommand.register(dispatcher);
		});
		ServerRegistry();
	}


	private void ServerRegistry(){
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
		ServerHolder.SERVER = server;
	});

	ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
		ServerHolder.SERVER = null;
	});
	}
}