package com.llama3d.scene;

import java.util.ArrayList;
import java.util.List;

import com.llama3d.analog.AnalogController;
import com.llama3d.object.Loader;
import com.llama3d.object.Object3D;
import com.llama3d.object.camera.Camera;
import com.llama3d.object.graphics.image.Image;
import com.llama3d.object.graphics.image.text.Text;
import com.llama3d.object.graphics.image.text.font.Font;
import com.llama3d.object.graphics.texture.Texture;
import com.llama3d.object.light.DirectionalLight;
import com.llama3d.object.light.PointLight;
import com.llama3d.object.material.Material;
import com.llama3d.object.mesh.Mesh;
import com.llama3d.object.physics.Physics;

/**
 * © 2011 Christian Ringshofer
 * 
 * @author Christian Ringshofer
 * @since 14:13:37 - 01.11.2011
 */
public class Scene {

	// ===================================================================
	// Fields
	// ===================================================================

	// ===================================================================
	// Object Fields
	// ===================================================================

	public Font font = null;
	public Physics physics = null;

	// ===================================================================
	// ArrayList Fields
	// ===================================================================

	public List<Loader> loader = new ArrayList<Loader>();
	public List<Material> materials = new ArrayList<Material>();
	public List<Camera> cameras = new ArrayList<Camera>();
	public List<PointLight> pointlights = new ArrayList<PointLight>();
	public List<DirectionalLight> dirlights = new ArrayList<DirectionalLight>();
	public List<Mesh> meshes = new ArrayList<Mesh>();
	public List<Object3D> objects = new ArrayList<Object3D>();
	public List<Texture> textures = new ArrayList<Texture>();
	public List<Image> images = new ArrayList<Image>();
	public List<Text> text = new ArrayList<Text>();

	public List<AnalogController> controllers = new ArrayList<AnalogController>();

	public List<Object3D> _toAdd = new ArrayList<Object3D>();
	public List<Object3D> _toRemove = new ArrayList<Object3D>();

	// ===================================================================
	// Constructors
	// ===================================================================

	public Scene() {

		this.physics = new Physics();
		// BaseActivity.internPackageLoadingPath = true;
		// this.font = new Font("fonts/sakkal.png", "fonts/sakkal.txt");
		// BaseActivity.internPackageLoadingPath = false;

		SceneCache.activeScene = this;
		SceneCache.scenes.add(this);

	}

	// ===================================================================
	// Methods
	// ===================================================================

	public boolean addObject(Object3D object) {
		_toAdd.add(object);
		return true;
	}

	public List<Object3D> getObjects() {
		return objects;
	}

	/**
	 * Updates the entire scene, by adding new created {@link Object3D}s to it.
	 */
	public void update() {
		this.updateObjectList();
		this.updateControllers();
	}

	/**
	 * Simulates the entire scene, by Bulletphysics-simulation.
	 */
	public void simulate() {
		if (this.physics != null) {
			this.physics.simulate();
		}
	}

	// ===================================================================
	// Private Methods
	// ===================================================================
	private void updateControllers() {
		for (AnalogController controller : this.controllers) {
			controller.update();
		}
	}

	// ===================================================================
	// Object Lists Methods
	// ===================================================================

	private void updateObjectList() {
		if (!this._toAdd.isEmpty()) {
			for (Object3D object : this._toAdd) {
				if (object instanceof Camera) {
					this.addCamera((Camera) object);
				} else if (object instanceof PointLight) {
					this.addPointLight((PointLight) object);
				} else if (object instanceof DirectionalLight) {
					this.addDirectionalLight((DirectionalLight) object);
				} else if (object instanceof Mesh) {
					this.addMesh((Mesh) object);
				}
				this.addSceneObject(object);
			}
			this._toAdd.clear();
		}
	}

	private void addCamera(Camera object) {
		this.cameras.add(object);
	}

	private void addPointLight(PointLight object) {
		this.pointlights.add(object);
	}

	private void addDirectionalLight(DirectionalLight object) {
		this.dirlights.add(object);
	}

	private void addMesh(Mesh object) {
		this.meshes.add(object);
	}

	private void addSceneObject(Object3D object) {
		this.objects.add(object);
	}

}
