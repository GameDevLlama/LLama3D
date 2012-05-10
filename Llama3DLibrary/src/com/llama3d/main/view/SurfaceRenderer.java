package com.llama3d.main.view;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.llama3d.engine.EngineCache;
import com.llama3d.main.activity.BaseActivityCache;
import com.llama3d.main.display.DisplayCache;
import com.llama3d.main.display.Screen;
import com.llama3d.main.statistics.Taper;
import com.llama3d.object.graphics.image.ImageBaseCache;
import com.llama3d.object.graphics.image.ImageCache;
import com.llama3d.object.material.MaterialCache;
import com.llama3d.opengl.OpenGL;
import com.llama3d.scene.SceneCache;
import com.llama3d.shader.ShaderCache;

public class SurfaceRenderer implements Renderer {

	// ===================================================================
	// Methods
	// ===================================================================

	public void onDrawFrame(GL10 unused) {
		// ======== Opens The Draw Function Of The Game ========
		onSurfaceDraw();
	}

	public void onSurfaceChanged(GL10 unused, int surfaceWidth, int surfaceHeight) {
		// ======== Just Set Display ========
		DisplayCache.setDisplaySize();
		// ======== ViewPort & RenderMode ========
		OpenGL.setViewPort();
		// ======== Set Screen Size ========
		Screen.init();
		// ======== Load Imagematrix ========
		ImageCache.init();
		// ======== Set RenderMode For Natives ========
		OpenGL.renderMode(OpenGL.renderImages);
		// ======== Start Lifecycle ========
		onSurfaceStart();
		Log.i("Lifecycle", "surface changed.");
	}

	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// ======== Init Important Stuff In OpenGL ========
		ShaderCache.init();
		// ======== Load Default Material ========
		MaterialCache.init();
		// ======== Init Important ImageRectBuffer ========
		ImageBaseCache.init();
		// ======== Set SurfaceView Initiated ========
		SurfaceViewCache.initialized = true;
		Log.i("Lifecycle", "surface created.");
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void onSurfaceStart() {
		// ======= Splash Start ========
		if (BaseActivityCache.onCreate) {
			// ======== Create Default Scene ========
			SceneCache.init();
			// ======== Start Game ========
			BaseActivityCache.mainActivity.onGameStart();
			BaseActivityCache.onCreate = false;
		}
		if (BaseActivityCache.onRestart) {
			SurfaceViewCache.surfaceView.reloadRenderer();
			BaseActivityCache.mainActivity.onGameResume();
			BaseActivityCache.onRestart = false;
		}
		EngineCache.engineActivityStatus = 1;
	}

	private synchronized void onSurfaceDraw() {
		// ======== Render All ========
		Taper.start(0, true);
		switch (EngineCache.engineActivityStatus) {
		case 1:
			if (BaseActivityCache.mainActivity != null) {
				BaseActivityCache.mainActivity.onGameFrame();
			}
			break;
		}
		Taper.stop(0);
	}

	// ===================================================================
	// Implemented Methods
	// ===================================================================

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}