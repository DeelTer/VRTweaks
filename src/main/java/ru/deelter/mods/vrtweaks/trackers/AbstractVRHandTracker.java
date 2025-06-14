package ru.deelter.mods.vrtweaks.trackers;


import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import ru.deelter.mods.vrtweaks.util.LastTickData;


public abstract class AbstractVRHandTracker {

	public AbstractVRHandTracker() {
		ServerTrackers.VR_PLAYER_TRACKERS.add(this);
	}

	protected abstract boolean shouldRunForHand(Player player, InteractionHand hand, ItemStack stackInHand,
												IVRPlayer currentVRData, LastTickData lastVRData);

	protected abstract void runForHand(Player player, InteractionHand hand, ItemStack stackInHand,
									   IVRPlayer currentVRData, LastTickData lastVRData);

	public void preTick(Player player) {

	}

	public void tick(Player player, IVRPlayer currentVRData, LastTickData lastVRData) {
		for (InteractionHand hand : InteractionHand.values()) {
			if (shouldRunForHand(player, hand, player.getItemInHand(hand), currentVRData, lastVRData)) {
				runForHand(player, hand, player.getItemInHand(hand), currentVRData, lastVRData);
			}
		}
	}

	protected BlockPos getBlockPosAtHand(@NotNull IVRPlayer vrPlayer, @NotNull InteractionHand hand) {
		IVRData data = vrPlayer.getController(hand.ordinal());
		return BlockPos.containing(data.position());
	}

	protected BlockState getBlockStateAtHand(@NotNull Player player, IVRPlayer vrPlayer, InteractionHand hand) {
		return player.level().getBlockState(getBlockPosAtHand(vrPlayer, hand));
	}

	protected Block getBlockAtHand(Player player, IVRPlayer vrPlayer, InteractionHand hand) {
		return getBlockStateAtHand(player, vrPlayer, hand).getBlock();
	}

	protected boolean movingInDirectionWithThreshold(@NotNull Direction direction, @NotNull Vec3 handVelocity, double threshold) {
		Vec3i blockFacing = direction.getUnitVec3i();
		// Check velocity requirement with hand velocity for x, y, and z. If the signs match, absolute value them
		// and check that we're moving faster than the threshold. If we are, return true.
		// If we fail for all three, return false.

		// Only one of these paths is travelled, since a Direction only has one non-zero value.
		if (signsMatch(blockFacing.getX(), handVelocity.x) && blockFacing.getX() != 0) {
			return Math.abs(handVelocity.x) >= threshold;
		} else if (signsMatch(blockFacing.getY(), handVelocity.y) && blockFacing.getY() != 0) {
			return Math.abs(handVelocity.y) >= threshold;
		} else if (signsMatch(blockFacing.getZ(), handVelocity.z) && blockFacing.getZ() != 0) {
			return Math.abs(handVelocity.z) >= threshold;
		}
		return false;
	}

	protected boolean signsMatch(double a, double b) {
		return (a < 0d && b < 0d) || (a >= 0d && b >= 0d);
	}
}
