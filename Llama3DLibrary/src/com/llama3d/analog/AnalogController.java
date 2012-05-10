package com.llama3d.analog;

import com.llama3d.elements.touch.Pointer;
import com.llama3d.opengl.OpenGL;
import com.llama3d.scene.SceneCache;

public class AnalogController {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static float pointerDistance;

	// ===================================================================
	// Private Fields
	// ===================================================================

	private AnalogControllerObject mainControllerObject;
	private boolean mainPointerRegistered = false;
	private int mainPointer = -1;

	private float mainOnScreenX, mainOnScreenY;
	public float x, y;
	public float axisX, axisY;

	// ===================================================================
	// Constructors
	// ===================================================================

	public AnalogController() {

		// ======== Create Renderable Controller Object ========
		this.mainControllerObject = new AnalogControllerObject();
		// ======== Add Controller To Scene ========
		SceneCache.activeScene.controllers.add(this);

	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void registerPointer(int pointerIndex) {

		this.mainPointerRegistered = true;
		this.mainPointer = pointerIndex;

	}

	private void unregisterPointer() {

		this.mainPointerRegistered = false;
		this.mainPointer = -1;

		this.x = 0f;
		this.y = 0f;
		this.axisX = 0f;
		this.axisY = 0f;

	}

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void setRenderMode(int AnalogRenderMode) {
		// ======== Set Rendermode For Analogstick ========
		this.mainControllerObject.setRenderMode(AnalogRenderMode);

	}

	public void setOnScreen(float onScreenX, float onScreenY) {
		// ======== Set Onscreen Coordinates ========
		this.mainOnScreenX = onScreenX;
		this.mainOnScreenY = onScreenY;
		this.mainControllerObject.position(this.mainOnScreenX / 10f, this.mainOnScreenY / 10f, -10f);

	}

	public void update() {

		if (this.mainPointerRegistered) {
			// ======== Pointer Registered On Stick ========
			if (Pointer.down[this.mainPointer]) {
				AnalogController.pointerDistance = (float) Math.sqrt(Math.pow(+Pointer.x[this.mainPointer] - mainOnScreenX, 2) + Math.pow(-Pointer.y[this.mainPointer] - mainOnScreenY, 2));
				if (AnalogController.pointerDistance < 150f) {
					this.x = +(+Pointer.x[this.mainPointer] - mainOnScreenX) / 150f;
					this.y = -(-Pointer.y[this.mainPointer] - mainOnScreenY) / 150f;
					this.axisX = +this.y;
					this.axisY = +this.x;
				} else {
					this.unregisterPointer();
				}
			} else {
				this.unregisterPointer();
			}
		} else {
			// ======== No Pointer Registered On Stick ========
			if (Pointer.count > 0) {
				for (int i = 0; i < 5; i++) {
					if (Pointer.hit[i]) {
						AnalogController.pointerDistance = (float) Math.sqrt(Math.pow(Pointer.x[i] - mainOnScreenX, 2) + Math.pow(-Pointer.y[i] - mainOnScreenY, 2));
						if (AnalogController.pointerDistance < 60f) {
							this.registerPointer(i);
						}
					}
				}
			}
		}

	}

	public void render() {

		// ========= Save Temporary Rendermode ========
		OpenGL.pushRenderMode();
		OpenGL.renderMode(OpenGL.renderInterface);
		// ========= Render Controller ========
		this.mainControllerObject.render(this.x, this.y);
		// ========= Restore Temporary Rendermode ========
		OpenGL.popRenderMode();

	}

	public void hide() {
		this.mainControllerObject.hide();
	}

	public void show() {
		this.mainControllerObject.show();
	}

	public void visible() {
		this.mainControllerObject.visible();
	}

	public void visible(boolean visible) {
		this.mainControllerObject.visible(visible);
	}

}
