package com.llama3d.elements.touch;

import java.util.ArrayList;
import java.util.List;

public class TouchManager {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Touch mainTouchElement;
	protected static List<TouchListener> touchListeners = new ArrayList<TouchListener>();

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Create New TouchElement ========
		TouchManager.mainTouchElement = new Touch();
	}

	/**
	 * Adds handler to list.
	 * @param handler
	 */
	
	public static void addTouchListener(TouchListener handler) {
		// ======== Registers Handler To Known Handlers ========
		if (!TouchManager.touchListeners.contains(handler)) {
			TouchManager.touchListeners.add(handler);
		}
	}

	/**
	 * Removes handler from list.
	 * @param handler
	 */
	
	public static void removeTouchListener(TouchListener handler) {
		// ======== Remove Handler If It's In The List ========
		TouchManager.touchListeners.remove(handler);
	}

	public static boolean isTouchListener(TouchListener handler) {
		if (TouchManager.touchListeners.contains(handler)) {
			return true;
		}
		return false;
	}

}
