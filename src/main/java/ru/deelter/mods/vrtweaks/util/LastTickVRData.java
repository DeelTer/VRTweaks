package ru.deelter.mods.vrtweaks.util;

import net.blf02.vrapi.api.data.IVRData;
import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LastTickVRData {
	public static final Map<String, LastTickData> lastTickVRData = new HashMap<>();

	public static Vec3 getVelocity(IVRData last, IVRData current, LastTickData data) {
		if (last == null) {
			return Vec3.ZERO;
		}
		Vec3 playerVelocity = getPlayerVelocity(data.doubleLastPlayerPos, data.lastPlayerPos);
		double x = moveTowardsZero(current.position().x - last.position().x, playerVelocity.x);
		double y = moveTowardsZero(current.position().y - last.position().y, playerVelocity.y);
		double z = moveTowardsZero(current.position().z - last.position().z, playerVelocity.z);
		return new Vec3(x, y, z);
	}

	public static double getAllVelocity(IVRData last, IVRData current, LastTickData data) {
		Vec3 vel = getVelocity(last, current, data);
		return Math.abs(vel.x) + Math.abs(vel.y) + Math.abs(vel.z);
	}

	public static double moveTowardsZero(double num, double subtract) {
		subtract = Math.abs(subtract);
		if (subtract >= Math.abs(num)) {
			return 0;
		} else if (num < 0) {
			return num + subtract;
		}
		return num - subtract;
	}

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull Vec3 getPlayerVelocity(@NotNull Vec3 lastTickPos, @NotNull Vec3 currentTickPos) {
		return new Vec3(currentTickPos.x - lastTickPos.x, currentTickPos.y - lastTickPos.y,
				currentTickPos.z - lastTickPos.z);
	}

}
