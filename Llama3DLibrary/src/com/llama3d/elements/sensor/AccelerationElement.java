package com.llama3d.elements.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.llama3d.main.activity.BaseActivityCache;
import com.llama3d.main.window.WindowCache;
import com.llama3d.main.window.orientation.Orientation;

public class AccelerationElement extends Activity implements SensorEventListener {

	// ===================================================================
	// Private Static Final Fields
	// ===================================================================

	private static final float interpolation = 0.25f;

	// ===================================================================
	// Fields
	// ===================================================================

	private float gravity[];
	private float acceleration[];
	private float accelerationLinear[];
	private float magnetic[];
	private float rotationM[];
	private float inclinationM[];

	protected SensorManager manager;// sensor
	protected Sensor AccelerometerLinear;// sensor
	protected Sensor Accelerometer;// sensor

	private int accelerationLinearDataCount;
	private int accelerationDataCount;

	// ===================================================================
	// Constructors
	// ===================================================================

	public AccelerationElement() {

		// ======== Sensorvectors ========
		this.gravity = new float[3];
		this.acceleration = new float[3];
		this.accelerationLinear = new float[3];
		this.magnetic = new float[3];
		this.rotationM = new float[16];
		this.inclinationM = new float[16];
		// ======== Sensormanager ========
		this.manager = (SensorManager) BaseActivityCache.mainActivity.getSystemService(Context.SENSOR_SERVICE);
		// ======== Sensors ========
		this.AccelerometerLinear = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		this.Accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	}

	// ===================================================================
	// Methods
	// ===================================================================

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		// ======== Acceleration Linear ========
		case Sensor.TYPE_LINEAR_ACCELERATION:
			this.accelerationLinear[0] += event.values[0];
			this.accelerationLinear[1] += event.values[1];
			this.accelerationLinear[2] += event.values[2];
			this.accelerationLinearDataCount++;
			break;
		// ======== Acceleration ========
		case Sensor.TYPE_ACCELEROMETER:
			this.acceleration[0] += event.values[0];
			this.acceleration[1] += event.values[1];
			this.acceleration[2] += event.values[2];
			this.accelerationDataCount++;
			break;
		// ======== Orientation ========
		case Sensor.TYPE_ORIENTATION:
			break;
		// ======== Graviation ========
		case Sensor.TYPE_GRAVITY:
			this.gravity = event.values;
			break;
		// ======== Magnetic Field ========
		case Sensor.TYPE_MAGNETIC_FIELD:
			this.magnetic = event.values;
			break;
		}
		SensorManager.getRotationMatrix(this.rotationM, this.inclinationM, gravity, this.magnetic);
	}

	public synchronized void getSensorEvents() {
		if (AccelerationElementCache.enabled) {
			// ======== Get New Stuff / Rest Old Stuff / Orientation ========
			switch (WindowCache.mainOrientation) {
			case Orientation.LANDSCAPE:
				if (this.accelerationLinearDataCount > 0) {
					Acceleration.linearX = -this.accelerationLinear[1] / (float) this.accelerationLinearDataCount;
					Acceleration.linearY = +this.accelerationLinear[0] / (float) this.accelerationLinearDataCount;
					Acceleration.linearZ = -this.accelerationLinear[2] / (float) this.accelerationLinearDataCount;
				}
				if (this.accelerationDataCount > 0) {
					Acceleration.x += (-this.acceleration[1] / (float) this.accelerationDataCount - Acceleration.x) * interpolation;
					Acceleration.y += (+this.acceleration[0] / (float) this.accelerationDataCount - Acceleration.y) * interpolation;
					Acceleration.z += (-this.acceleration[2] / (float) this.accelerationDataCount - Acceleration.z) * interpolation;
				}
				break;
			case Orientation.PORTRAIT:
				if (this.accelerationLinearDataCount > 0) {
					Acceleration.linearX = +this.accelerationLinear[0] / (float) this.accelerationLinearDataCount;
					Acceleration.linearY = -this.accelerationLinear[1] / (float) this.accelerationLinearDataCount;
					Acceleration.linearZ = -this.accelerationLinear[2] / (float) this.accelerationLinearDataCount;
				}
				if (this.accelerationDataCount > 0) {
					Acceleration.x = (+this.acceleration[0] / (float) this.accelerationDataCount - Acceleration.x) * interpolation;
					Acceleration.y = (-this.acceleration[1] / (float) this.accelerationDataCount - Acceleration.y) * interpolation;
					Acceleration.z = (-this.acceleration[2] / (float) this.accelerationDataCount - Acceleration.z) * interpolation;
				}
				break;
			case Orientation.FLAT:
				if (this.accelerationLinearDataCount > 0) {
					Acceleration.linearX = +this.accelerationLinear[0] / (float) this.accelerationLinearDataCount;
					Acceleration.linearY = -this.accelerationLinear[1] / (float) this.accelerationLinearDataCount;
					Acceleration.linearZ = -this.accelerationLinear[2] / (float) this.accelerationLinearDataCount;
				}
				if (this.accelerationDataCount > 0) {
					Acceleration.x = (+this.acceleration[0] / (float) this.accelerationDataCount - Acceleration.x) * interpolation;
					Acceleration.y = (-this.acceleration[1] / (float) this.accelerationDataCount - Acceleration.y) * interpolation;
					Acceleration.z = (-this.acceleration[2] / (float) this.accelerationDataCount - Acceleration.z) * interpolation;
				}
				break;
			}
			// ======== LinearAxis ========
			Acceleration.linearAxisX = (float) Math.toDegrees(Math.atan2(-Acceleration.linearY, Acceleration.linearZ));
			Acceleration.linearAxisY = (float) Math.toDegrees(Math.atan2(-Acceleration.linearZ, Acceleration.linearX));
			Acceleration.linearAxisZ = (float) Math.toDegrees(Math.atan2(Acceleration.linearX, Acceleration.linearY));
			// ======== Axis ========
			Acceleration.axisX = (float) Math.toDegrees(Math.atan2(Acceleration.y, Acceleration.z));
			Acceleration.axisY = (float) Math.toDegrees(Math.atan2(-Acceleration.z, Acceleration.x));
			Acceleration.axisZ = (float) Math.toDegrees(Math.atan2(Acceleration.x, -Acceleration.y));
			// ======== TurnAxis ========
			Acceleration.turnAxisX = Acceleration.z;
			Acceleration.turnAxisY = 0;
			Acceleration.turnAxisZ = Acceleration.x;
			// ======== Reset Temporary Data ========
			this.accelerationLinear[0] = 0;
			this.accelerationLinear[1] = 0;
			this.accelerationLinear[2] = 0;
			this.acceleration[0] = 0;
			this.acceleration[1] = 0;
			this.acceleration[2] = 0;
			this.accelerationLinearDataCount = 0;
			this.accelerationDataCount = 0;
		}
	}

	@Override
	public void onPause() {

	}

	@Override
	public void onResume() {

	}

}
