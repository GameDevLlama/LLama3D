package com.llama3d.analog;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.llama3d.main.display.DisplayCache;
import com.llama3d.object.Object3D;
import com.llama3d.object.material.Material;
import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.geometry.Geometry;
import com.llama3d.object.mesh.geometry.fileloader.WaveFront;
import com.llama3d.shader.ShaderCache;

public class AnalogControllerObject extends Object3D {

	// ===================================================================
	// Private Fields
	// ===================================================================

	private Geometry controllerGeometry;
	private Material controllerMaterial;

	private float[] controllerProjection = new float[16];
	private int mainRenderMode = Analog.render3D;

	// ===================================================================
	// Constructors
	// ===================================================================

	protected AnalogControllerObject() {

		// ======== .JAR Intern WaveFront Loading ========
		this.controllerGeometry = WaveFront.load("models/analog.obj", true);
		// ======== Setup New Controller ========
		this.controllerGeometry.bufferGeometry();
		this.position(0f, 0f, -8f);
		this.scale(4.5f * DisplayCache.dfW);
		// ======== Controller Material ========
		this.controllerMaterial = new Material();
		this.controllerMaterial.setColor(100, 100, 100);
		this.controllerMaterial.setElumination(0.15f);
		for (Surface surface : this.controllerGeometry.surfaces) {
			surface.material = this.controllerMaterial;
		}
		// ======== Setup New GUI Matrix ========
		float lSet = (float) -DisplayCache.w / 20f;
		float rSet = (float) +DisplayCache.w / 20f;
		float tSet = (float) +DisplayCache.h / 20f;
		float bSet = (float) -DisplayCache.h / 20f;
		Matrix.orthoM(this.controllerProjection, 0, lSet, rSet, bSet, tSet, 0.1f, 12f);

	}

	// ===================================================================
	// Methods
	// ===================================================================

	protected void setRenderMode(int AnalogRenderMode) {

		this.mainRenderMode = AnalogRenderMode;

	}

	protected void render(float X, float Y) {

		if (this.visible) {
			switch (this.mainRenderMode) {
			case Analog.render2D:
			case Analog.render3D:

				this.alignToVector(X, -Y, 0.5f, 1, 0.8f, 0.001f);

				GLES20.glUniformMatrix4fv(ShaderCache.activeShader.modelMatrix, 1, false, this.translationMatrix, 0);
				GLES20.glUniformMatrix4fv(ShaderCache.activeShader.projectionMatrix, 1, false, this.controllerProjection, 0);

				for (Surface surface : this.controllerGeometry.surfaces) {
					surface.render();
				}

			}
		}

	}
}
