package ru.deelter.mods.vrtweaks.subscribers;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import ru.deelter.mods.vrtweaks.VRPlugin;
import ru.deelter.mods.vrtweaks.trackers.AbstractTracker;
import ru.deelter.mods.vrtweaks.trackers.ServerTrackers;

public class ServerSubscriber {

	public static MinecraftServer server;

	public static void onServerTick(MinecraftServer server) {
		ServerSubscriber.server = server;
		for (AbstractTracker tracker : ServerTrackers.GLOBAL_TRACKERS) {
			tracker.doTick(null);
		}
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			onPlayerTick(player);
		}
	}

	public static void onPlayerTick(@NotNull ServerPlayer playerIn) {
		if (playerIn.level().isClientSide) {
			return;
		}
		for (AbstractTracker tracker : ServerTrackers.PLAYER_TRACKERS) {
			tracker.doTick(playerIn);
		}
		if (VRPlugin.enabled) {
			ServerVRSubscriber.vrPlayerTick(playerIn);
		}
	}
}