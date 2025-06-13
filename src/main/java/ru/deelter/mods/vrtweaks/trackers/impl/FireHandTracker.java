package ru.deelter.mods.vrtweaks.trackers.impl;

import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import ru.deelter.mods.vrtweaks.VRPlugin;
import ru.deelter.mods.vrtweaks.trackers.AbstractTracker;

public class FireHandTracker extends AbstractTracker {

	private static final int FIRE_TICKS = 3 * 20;

	@Override
	protected void tick(ServerPlayer player) {
		IVRPlayer vrPlayer = VRPlugin.api.getVRPlayer(player);

		int controllerInFireIndex = getControllerInFire(player, vrPlayer);
		if (controllerInFireIndex < 0) {
			return;
		}

		VRPlugin.api.triggerHapticPulse(controllerInFireIndex, 1, player);
		player.setRemainingFireTicks(FIRE_TICKS);
	}

	private int getControllerInFire(Player player, IVRPlayer vrPlayer) {
		for (int controllerIndex = 0; controllerIndex <= 1; controllerIndex++) {

			IVRData controller = vrPlayer.getController(controllerIndex);
			BlockPos controllerPosition = BlockPos.containing(controller.position());
			BlockState state = player.level().getBlockState(controllerPosition);

			if (state.is(Blocks.LAVA) || state.is(Blocks.LAVA_CAULDRON) || state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE)) {
				return controllerIndex;
			}
		}
		return -1;
	}

	@Override
	protected boolean shouldTick(ServerPlayer player) {
		return VRPlugin.enabled && VRPlugin.api.playerInVR(player) && VRPlugin.api.apiActive(player);
	}

}
