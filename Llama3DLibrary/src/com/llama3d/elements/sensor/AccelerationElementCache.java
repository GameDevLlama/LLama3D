package com.llama3d.elements.sensor;

public class AccelerationElementCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static AccelerationElement element;

	// ===================================================================
	// Protected Static Fields
	// ===================================================================

	protected static boolean enabled = false;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		AccelerationElementCache.element = new AccelerationElement();
	}

}
