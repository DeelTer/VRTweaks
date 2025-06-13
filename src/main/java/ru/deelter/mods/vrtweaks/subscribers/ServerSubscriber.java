package ru.deelter.mods.vrtweaks;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import ru.deelter.mods.vrtweaks.trackers.AbstractTracker;
import ru.deelter.mods.vrtweaks.trackers.ServerTrackers;

public class ServerSubscriber {

	public static MinecraftServer server;

	public static void onServerTick(MinecraftServer server) {
		ServerSubscriber.server = server;
		for (AbstractTracker tracker : ServerTrackers.GLOBAL_TRACKERS) {
			tracker.doTick(null);
		}
	}

	public static void onPlayerTick(@NotNull Player playerIn) {
		if (playerIn.level().isClientSide) {
			return;
		}

		ServerPlayer player = (ServerPlayer) playerIn;
		for (AbstractTracker tracker : ServerTrackers.PLAYER_TRACKERS) {
			tracker.doTick(player);
		}
		if (VRPlugin.enabled) {
			ServerVRSubscriber.vrPlayerTick(player);
		}

		// Get looking at immersive
		HitResult hit = player.pick(CommonConstants.registerImmersivePickRange, 0, false);
		if (hit instanceof BlockHitResult blockHit && blockHit.getType() != HitResult.Type.MISS) {
			TrackedImmersives.maybeTrackImmersive(player, blockHit.getBlockPos());
		}
	}

	public static void onPlayerJoin(Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			ActiveConfig config = ImmersiveMCPlayerStorages.isPlayerDisabled(serverPlayer) ? ActiveConfig.DISABLED : ActiveConfig.FILE_SERVER;
			Network.INSTANCE.sendToPlayer(serverPlayer,
					new ConfigSyncPacket(config,
							ImmersiveHandlers.HANDLERS.stream()
									.filter((handler) -> !handler.clientAuthoritative())
									.map(ImmersiveHandler::getID)
									.toList()
					));
		}
	}

	public static void onPlayerLeave(Player playerIn) {
		if (playerIn instanceof ServerPlayer player) {
			TrackedImmersives.clearForPlayer(player);
			ChestToOpenSet.clearForPlayer(player);
		}
	}
}