package ru.deelter.mods.vrtweaks.subscribers;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import ru.deelter.mods.vrtweaks.VRPlugin;
import ru.deelter.mods.vrtweaks.trackers.AbstractVRHandTracker;
import ru.deelter.mods.vrtweaks.trackers.ServerTrackers;
import ru.deelter.mods.vrtweaks.util.LastTickData;
import ru.deelter.mods.vrtweaks.util.LastTickVRData;

public class ServerVRSubscriber {

	private static final double IMMERSIVE_DISTANCE = 15;

	public static void vrPlayerTick(ServerPlayer player) {
		if (!VRPlugin.api.playerInVR(player))
			return;

		IVRPlayer vrPlayer = VRPlugin.api.getVRPlayer(player);

//		Vec3 start = vrPlayer.getHMD().position();
//		Vec3 look = vrPlayer.getHMD().getLookAngle();
//		Vec3 end = vrPlayer.getHMD().position().add(look.x * IMMERSIVE_DISTANCE, look.y * IMMERSIVE_DISTANCE, look.z * IMMERSIVE_DISTANCE);
//		BlockHitResult blockHit = player.level().clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE,
//				player));

		for (AbstractVRHandTracker tracker : ServerTrackers.VR_PLAYER_TRACKERS) {
			tracker.preTick(player);
			if (LastTickVRData.lastTickVRData.get(player.getGameProfile().getName()) != null) {
				tracker.tick(player, vrPlayer, LastTickVRData.lastTickVRData.get(player.getGameProfile().getName()));
			}
		}
		LastTickData data = LastTickVRData.lastTickVRData.get(player.getGameProfile().getName());
		Vec3 doubleLast = data == null ? player.position() : data.lastPlayerPos;
		LastTickVRData.lastTickVRData.put(player.getGameProfile().getName(), new LastTickData(vrPlayer, player.position(), doubleLast));
	}

}