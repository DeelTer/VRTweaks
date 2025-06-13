package ru.deelter.mods.vrtweaks.trackers;

import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractTracker {

	protected abstract void tick(ServerPlayer player);

	protected abstract boolean shouldTick(ServerPlayer player);

	public void doTick(ServerPlayer player) {
		if (shouldTick(player)) {
			tick(player);
		}
	}
}
