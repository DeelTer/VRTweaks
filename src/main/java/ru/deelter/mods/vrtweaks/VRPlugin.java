package ru.deelter.mods.vrtweaks.util;

import net.blf02.vrapi.api.IVRAPI;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.util.List;

public class VRMod {

	public static boolean enabled;
	public static IVRAPI api;

	public static void initVR() {
		List<EntrypointContainer<IVRAPI>> apis = FabricLoader.getInstance().getEntrypointContainers("vrapi", IVRAPI.class);
		if (apis.isEmpty()) {
			return;
		}
		api = apis.getFirst().getEntrypoint();
		enabled = true;
	}

}
