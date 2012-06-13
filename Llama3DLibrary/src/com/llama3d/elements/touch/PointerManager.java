package com.llama3d.elements.touch;

import java.util.ArrayList;
import java.util.List;

public class PointerManager {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Pointer mainTouchElement;
	protected static List<PointerListener> touchListeners = new ArrayList<PointerListener>();

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Create New TouchElement ========
		PointerManager.mainTouchElement = new Pointer();
	}

	/**
	 * Adds handler to list.
	 * @param handler
	 */
	
	public static void addTouchListener(PointerListener handler) {
		// ======== Registers Handler To Known Handlers ========
		if (!PointerManager.touchListeners.contains(handler)) {
			PointerManager.touchListeners.add(handler);
		}
	}

	/**
	 * Removes handler from list.
	 * @param handler
	 */
	
	public static void removeTouchListener(PointerListener handler) {
		// ======== Remove Handler If It's In The List ========
		PointerManager.touchListeners.remove(handler);
	}

	public static boolean isTouchListener(PointerListener handler) {
		if (PointerManager.touchListeners.contains(handler)) {
			return true;
		}
		return false;
	}

}
