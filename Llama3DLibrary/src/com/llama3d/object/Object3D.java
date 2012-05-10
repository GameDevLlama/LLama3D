package com.llama3d.object;

import com.llama3d.scene.SceneCache;

public class Object3D extends ObjectTransformable {

	// ===================================================================
	// Fields
	// ===================================================================

	protected String name;
	protected boolean visible = true;

	// ===================================================================
	// Constructors
	// ===================================================================

	public Object3D() {
		SceneCache.activeScene.addObject(this);
	}

	// ===================================================================
	// Methods
	// ===================================================================

	public void hide() {
		this.visible = false;
	}

	public void show() {
		this.visible = true;
	}

	public void visible() {
		this.visible(true);
	}

	public void visible(boolean visible) {
		this.visible = visible;
	}

	public void parent() {
		this.parent(null);
	}

	public void parent(Object3D parent) {
		if (parent != null) {
			// ======== Attach Parent ========
			this.parent = parent;
			parent.childs.add(this);
			// ======== Set Parential Transformation ========
			//this.changeCoordinateSystem();
			parent.transform();
		} else if (this.parent != null) {
			// ======== Release Parent ========
			this.parent.childs.remove(this);
			this.parent = null;
		}
	}

	public void attachChild(Object3D object) {
		this.childs.add(object);
		object.parent = this;
	}

	public void removeChild(Object3D object) {
		this.childs.remove(object);
		object.parent = null;
	}
}
