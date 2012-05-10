package com.llama3d.elements.sensor;

import android.hardware.SensorManager;

public class Acceleration {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	// ======== Coordinates / Vectors ========
	public static float x = 0;
	public static float y = 0;
	public static float z = 0;

	public static float linearX = 0;
	public static float linearY = 0;
	public static float linearZ = 0;

	public static float axisX = 0;
	public static float axisY = 0;
	public static float axisZ = 0;

	public static float turnAxisX = 0;
	public static float turnAxisY = 0;
	public static float turnAxisZ = 0;

	public static float linearAxisX = 0;
	public static float linearAxisY = 0;
	public static float linearAxisZ = 0;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void enable(boolean enable) {
		if (enable) {
			// ======== Register Sensors ========
			AccelerationElementCache.element.manager.registerListener(AccelerationElementCache.element, AccelerationElementCache.element.AccelerometerLinear, SensorManager.SENSOR_DELAY_GAME);
			AccelerationElementCache.element.manager.registerListener(AccelerationElementCache.element, AccelerationElementCache.element.Accelerometer, SensorManager.SENSOR_DELAY_GAME);
		} else {
			// ======== Unregister Sensors ========
			AccelerationElementCache.element.manager.unregisterListener(AccelerationElementCache.element, AccelerationElementCache.element.AccelerometerLinear);
			AccelerationElementCache.element.manager.unregisterListener(AccelerationElementCache.element, AccelerationElementCache.element.Accelerometer);
		}
		// ======== Sensors Enable ========
		AccelerationElementCache.enabled = enable;
	}

}
