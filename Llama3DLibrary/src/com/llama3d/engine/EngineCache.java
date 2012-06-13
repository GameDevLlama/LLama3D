package com.llama3d.engine;

import android.util.Log;

import com.llama3d.elements.sensor.AccelerationElementCache;
import com.llama3d.elements.touch.PointerManager;
import com.llama3d.main.activity.BaseActivityCache;
import com.llama3d.main.activity.BaseActivityContent;
import com.llama3d.main.assets.AssetCache;
import com.llama3d.main.display.DisplayCache;
import com.llama3d.main.settings.GameSettings;
import com.llama3d.main.view.SurfaceViewCache;
import com.llama3d.main.window.WindowCache;
import com.llama3d.object.graphics.image.ImageVBOFactory;
import com.llama3d.opengl.OpenGL;
import com.llama3d.vibrator.Vibes;

public class EngineCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Engine engine;
	public static volatile byte engineActivityStatus;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void onCreate() {
		// ======== Create Settings Object ========
		GameSettings.init();
		// ======== Optional Change Of Settings Object ========
		BaseActivityCache.mainActivity.onGameSettings(GameSettings.mainSettings);
		// ======== Initialize Window ========
		WindowCache.init(GameSettings.mainSettings.getOrientation());
		// ======== Initialize Display & Metrics ========
		DisplayCache.init();
		// ======== Initialize TouchElement ========
		PointerManager.init();
		// ======== Initialize AccelerationElement ========
		AccelerationElementCache.init();
		// ======== Initialize Vibration-Function ========
		Vibes.init();
		// ======== Initialize SurfaceView ========
		SurfaceViewCache.init();
		// ======== Initialize ContentView ========
		BaseActivityContent.init();
		// ======== Initialize SurfaceViewRenderer ========
		SurfaceViewCache.initRenderer();
		// ======== Initialize AssetCache ========
		AssetCache.init();
		// ======== Initialize Engine ========
		if (EngineCache.engine == null) {
			EngineCache.engine = new Engine();
			// ======== Start Engine Main Thread =========
			EngineCache.engine.start();
			BaseActivityCache.onCreate = true;
		} else {
		}
	}

	public static void onPause() {
		// ======== Pause Surfaceview ========
		SurfaceViewCache.surfaceView.onPause();
		// ======== Clear All GL-Buffers And Temp Values ========
		EngineCache.engineActivityStatus = 0;
		ImageVBOFactory.clearVBOSpace();
		OpenGL.renderMode(0);
		Log.i("EngineCache", "pause surfaceview.");
		// ======== Engine Is Paused ========
	}

	public static void onResume() {
		// ======== Resume Surfaceview ========
		SurfaceViewCache.surfaceView.onResume();
		Log.i("EngineCache", "resume surfaceview.");
	}

}
