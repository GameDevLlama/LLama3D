package com.llama3d.elements.touch;

public class PointerElementCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static PointerElement mainTouchElement;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Create New TouchElement ========
		PointerElementCache.mainTouchElement = new PointerElement();
	}

	public static void onPause() {
		Pointer.pinched = false;
		Pointer.pinch = 1;
		Pointer.count = 0;
	}

}
