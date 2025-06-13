package ru.deelter.mods.vrtweaks.trackers;

import ru.deelter.mods.vrtweaks.trackers.impl.FireHandTracker;

import java.util.LinkedList;
import java.util.List;

public class ServerTrackers {

	public static final List<AbstractTracker> PLAYER_TRACKERS = new LinkedList<>();
	public static final List<AbstractTracker> GLOBAL_TRACKERS = new LinkedList<>();
	public static final List<AbstractVRHandTracker> VR_PLAYER_TRACKERS = new LinkedList<>();

	public static void initTrackers() {

		PLAYER_TRACKERS.addAll(List.of(
				new FireHandTracker()
		));
	}

}
