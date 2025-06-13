package ru.deelter.mods.vrtweaks.util;

import net.blf02.vrapi.api.data.IVRPlayer;
import net.minecraft.world.phys.Vec3;

public class VRLastTickData {

	public final IVRPlayer lastPlayer;
	public final Vec3 lastPlayerPos;
	public final Vec3 doubleLastPlayerPos;

	public VRLastTickData(IVRPlayer lastPlayer, Vec3 lastPlayerPos, Vec3 doubleLastPlayerPos) {
		this.lastPlayer = lastPlayer;
		this.lastPlayerPos = lastPlayerPos;
		this.doubleLastPlayerPos = doubleLastPlayerPos;
	}
}
