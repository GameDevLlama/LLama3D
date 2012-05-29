package com.llama3d.object.mesh;

import android.opengl.GLES20;

import com.llama3d.object.Object3D;
import com.llama3d.object.material.Material;
import com.llama3d.object.mesh.geometry.Geometry;
import com.llama3d.object.mesh.geometry.fileloader.LL3D;
import com.llama3d.object.mesh.geometry.fileloader.WaveFront;
import com.llama3d.scene.Scene;
import com.llama3d.shader.ShaderCache;

public class Mesh extends Object3D {

	// ===================================================================
	// Fields
	// ===================================================================

	public Geometry geometry;

	// ===================================================================
	// Constructors
	// ===================================================================

	public Mesh() {
		this(new Geometry());
	}

	public Mesh(Geometry geometry) {
		this.geometry = geometry;
		if (!this.geometry.buffered) {
			this.bufferSurfaces();
		}
	}

	public Mesh(Mesh mesh) {
		this.geometry = mesh.geometry;
		if (!this.geometry.buffered) {
			this.bufferSurfaces();
		}
	}

	// ===================================================================
	// Methods
	// ===================================================================

	public Material material() {
		return this.geometry.surfaces.get(0).material;
	}

	public void setMaterial(Material mat) {
		if (geometry == null) {
			throw new RuntimeException("Mesh does not have any geometry data!");
		}
		for (Surface surface : this.geometry.surfaces) {
			surface.material = mat;
		}
	}

	public Mesh copy() {
		return new Mesh(this);
	}

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public synchronized static void renderAllVisible(Scene scene) {
		for (Mesh mesh : scene.meshes) {
			mesh.render();
		}
	}

	public static Mesh loadWaveFront(String modelPath) {
		return new Mesh(WaveFront.load(modelPath));
	}

	public static Mesh loadLLamaModel(String modelPath) {
		return new Mesh(LL3D.load(modelPath));
	}

	// ===================================================================
	// Protected Methods
	// ===================================================================

	protected void bufferSurfaces() {
		if (this.geometry != null) {
			this.geometry.bufferGeometry();
		}
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void render() {
		if (this.visible == true && this.geometry != null) {
			this.passUniforms();
			for (Surface surface : this.geometry.surfaces) {
				surface.render();
			}
		}
	}

	private void passUniforms() {
		GLES20.glUniformMatrix4fv(ShaderCache.activeShader.modelMatrix, 1, false, this.matrix, 0);
	}
}
