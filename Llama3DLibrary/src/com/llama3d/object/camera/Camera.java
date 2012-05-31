package com.llama3d.object.camera;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.llama3d.main.display.DisplayCache;
import com.llama3d.main.display.Screen;
import com.llama3d.math.color.ColorRGB;
import com.llama3d.math.color.ColorRGBA;
import com.llama3d.math.matrix.MatrixEx;
import com.llama3d.object.Object3D;
import com.llama3d.object.light.Ambient;
import com.llama3d.object.light.Light;
import com.llama3d.object.mesh.Mesh;
import com.llama3d.opengl.OpenGL;
import com.llama3d.scene.Scene;
import com.llama3d.scene.SceneCache;
import com.llama3d.shader.ShaderCache;

/**
 * © 2011 Christian Ringshofer
 * 
 * @author Christian Ringshofer
 * @since 14:13:37 - 01.11.2011
 * 
 */
public class Camera extends Object3D {

	// ===================================================================
	// Private Static Final Fields
	// ===================================================================

	private static final int defaultAngle = 45;
	private static final float defaultRangeNear = 0.1f;
	private static final float defaultRangeFar = 200f;

	// ===================================================================
	// Public Static Final Fields
	// ===================================================================

	public static final int PERSPECTIVE = 0;
	public static final int ORTHOGONAL = 1;

	// ===================================================================
	// Fields
	// ===================================================================

	private ColorRGB _clsColor = new ColorRGB(0, 0, 0);

	private volatile float[] projectionMatrix = new float[16];
	private volatile float[] viewMatrix = new float[16];

	private float angle, near, far, ratio, zoom = 1f;
	private int projectionMode;

	private boolean fog = true;
	private float fogNear = 5f;
	private float fogFar = 25f;
	private ColorRGBA fogColor = new ColorRGBA();

	private int viewportX;
	private int viewportY;
	private int viewportWidth;
	private int viewportHeight;

	// ===================================================================
	// Constructors
	// ===================================================================

	/**
	 * Creates Camera for the active scene.
	 * 
	 * @return a new {@link Camera} object with defaultsettings.
	 */
	public Camera() {
		// ======== Standard Values For Camera ========
		this(PERSPECTIVE, defaultAngle, defaultRangeNear, defaultRangeFar);
	}

	/**
	 * Creates Camera for the active scene.
	 * 
	 * @param projectionMode
	 *            - using perspective or orthogonal projection.
	 * @return a new {@link Camera} object with custom projectionmode &
	 *         defaultsettings.
	 */
	public Camera(int projectionMode) {
		// ======== Standard Values For Camera ========
		this(projectionMode, defaultAngle, defaultRangeNear, defaultRangeFar);
	}

	/**
	 * Creates Camera for the active scene.
	 * 
	 * @param projectionMode
	 *            - using perspective or orthogonal projection.
	 * @param angle
	 *            - the field of view of the camera.
	 * @return a new {@link Camera} object with custom projectionmode &
	 *         defaultsettings.
	 */
	public Camera(int projectionMode, float angle, float near, float far) {
		super();

		this.viewportWidth = Screen.width;
		this.viewportHeight = Screen.height;

		this.projectionMode = projectionMode;
		this.angle = (float) Math.toRadians(angle);
		this.ratio = this.viewportWidth / this.viewportHeight;
		this.near = near;
		this.far = far;

		this.setRatio();
		Matrix.setIdentityM(this.translationMatrix, 0);

	}

	// ===================================================================
	// Methods
	// ===================================================================

	public void clsColor(int r, int g, int b) {
		this._clsColor.r = (float) r / 255.0f;
		this._clsColor.g = (float) g / 255.0f;
		this._clsColor.b = (float) b / 255.0f;
	}

	/**
	 * Sets range of cameraprojection.
	 * 
	 * @param near
	 *            - nearvalue (default = 0.1f).
	 * @param far
	 *            - farvalue (default = defaultRangeFar).
	 */
	public void range(float near, float far) {
		this.near = near;
		this.far = far;
		this.setRatio();
	}

	/**
	 * Sets only near range of cameraprojection.
	 * 
	 * @param near
	 *            - nearvalue (default = 0.1f).
	 */
	public void rangeNear(float near) {
		this.near = near;
		this.setRatio();
	}

	/**
	 * Sets only far range of cameraprojection.
	 * 
	 * @param far
	 *            - farvalue (default = defaultRangeFar).
	 */
	public void rangeFar(float far) {
		this.far = far;
		this.setRatio();
	}

	/**
	 * Clears the screen on given viewport, with clsColor set by
	 * {@link clsColor}.
	 */
	public void clearScreen() {
		if (OpenGL.autoDither) {
			OpenGL.pushDither();
			GLES20.glDisable(GLES20.GL_DITHER);
			OpenGL.popDither();
		}

		GLES20.glViewport(this.viewportX, this.viewportY, this.viewportWidth, this.viewportHeight);
		GLES20.glClearColor(this._clsColor.r, this._clsColor.g, this._clsColor.b, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	}

	public void fogColor(int r, int g, int b) {
		this.fogColor.r = (float) r / 255.0f;
		this.fogColor.g = (float) g / 255.0f;
		this.fogColor.b = (float) b / 255.0f;
		this.fogColor.a = 1f;
	}

	public void fogColor(int r, int g, int b, float a) {
		this.fogColor.r = (float) r / 255.0f;
		this.fogColor.g = (float) g / 255.0f;
		this.fogColor.b = (float) b / 255.0f;
		this.fogColor.a = a;
	}

	protected void setRatio() {
		setRatio(this.viewportWidth, this.viewportHeight);
	}

	protected void setRatio(int width, int height) {
		this.ratio = ((float) width) / ((float) height);
		// setup matrices
		float left = (float) (-this.near * Math.tan(this.angle / 2f) / this.zoom);
		float right = (float) (+this.near * Math.tan(this.angle / 2f) / this.zoom);
		float top = +right / this.ratio;
		float bottom = -right / this.ratio;
		switch (this.projectionMode) {
		case PERSPECTIVE:
			Matrix.frustumM(this.projectionMatrix, 0, left / this.zoom, right / this.zoom, bottom / this.zoom, top / this.zoom, this.near, this.far);
			break;
		case ORTHOGONAL:
			Matrix.orthoM(this.projectionMatrix, 0, left, right, bottom, top, this.near, this.far);
			break;
		}
	}

	public void viewport(int x, int y, int width, int height) {
		this.viewportX = x;
		this.viewportY = y;
		this.viewportWidth = width;
		this.viewportHeight = height;
	}

	public void setZoom(float zoom) {
		if (zoom < 0.000001f) {
			zoom = 0.000001f;
		}
		this.zoom = zoom;
		setRatio(this.viewportWidth, this.viewportHeight);
	}

	public void zoom(float deltaZoom) {
		this.zoom += deltaZoom * this.zoom;
		if (zoom < 0.000001f) {
			zoom = 0.000001f;
		}
		setRatio(this.viewportWidth, this.viewportHeight);
	}

	/**
	 * Sets the fogmode for camera.
	 * 
	 * @param fogEnable
	 *            - determines if fog is enabled or disabled for camera.
	 */
	public void fogMode(boolean fogEnable) {
		this.fog = fogEnable;
	}

	/**
	 * Sets the fogrange of the camera.
	 * 
	 * @info Fog will be disabled, if near == far.
	 * 
	 * @param near
	 *            distance of fogeffect
	 * @param far
	 *            maximal distance of fogeffect
	 */
	public void fogRange(float near, float far) {
		this.fogNear = near;
		this.fogFar = far;
		if (this.fogNear == this.fogFar) {
			this.fog = false;
		}
	}

	public static void doUpdate() {
		for (Object3D object : SceneCache.activeScene.cameras) {
			Camera camera = (Camera) object;
			camera.update();
		}
	}

	public void update() {
		this.viewport(0, 0, DisplayCache.w, DisplayCache.h);
		this.setRatio(Screen.width, Screen.height);
	}

	public void render() {
		this.render(SceneCache.defaultScene);
	}

	/**
	 * Renders the entire scene with all visible {@link Camera}s and visible
	 * {@link Object3D}s.
	 */
	public synchronized void render(Scene scene) {
		// ========= Save Temporary Rendermode ========
		OpenGL.pushRenderMode();
		OpenGL.renderMode(OpenGL.renderModels);
		// ========= Pass Meshes ========
		Ambient.passUniform();
		Light.uniformLights(scene);
		passUniforms();
		Mesh.renderAllVisible(scene);
		// ========= Restore Temporary Rendermode ========
		OpenGL.popRenderMode();
	}

	public void passUniforms() {
		GLES20.glUniform3f(ShaderCache.activeShader.cameraPosition, this.matrix[12], this.matrix[13], this.matrix[14]);
		if (this.fog == true) {
			GLES20.glUniform1i(ShaderCache.activeShader.cameraFog, 1);
		} else {
			GLES20.glUniform1i(ShaderCache.activeShader.cameraFog, 0);
		}
		GLES20.glUniform4f(ShaderCache.activeShader.cameraFogColor, this.fogColor.r, this.fogColor.g, this.fogColor.b, this.fogColor.a);
		GLES20.glUniform1f(ShaderCache.activeShader.cameraFogNear, this.fogNear);
		GLES20.glUniform1f(ShaderCache.activeShader.cameraFogFar, this.fogFar);

		MatrixEx.invertM(this.viewMatrix, this.matrix);
		GLES20.glUniformMatrix4fv(ShaderCache.activeShader.viewMatrix, 1, false, this.viewMatrix, 0);
		GLES20.glUniformMatrix4fv(ShaderCache.activeShader.projectionMatrix, 1, false, this.projectionMatrix, 0);
	}
}
