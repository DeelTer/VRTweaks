package ru.deelter.mods.vrtweaks;

import net.blf02.vrapi.api.IVRAPI;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.util.List;

public class VRPlugin {

	public static boolean enabled;
	public static IVRAPI api;

	public static void initVR() throws ClassNotFoundException {
		Class.forName("net.blf02.vrapi.api.IVRAPI");

		List<EntrypointContainer<IVRAPI>> apis = FabricLoader.getInstance().getEntrypointContainers("vrapi", IVRAPI.class);
		if (apis.isEmpty()) {
			return;
		}
		api = apis.getFirst().getEntrypoint();
		enabled = true;
	}

}
