package com.llama3d.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.llama3d.engine.EngineCache;
import com.llama3d.main.LLama3D;

/**
 * (c) 2011 Christian Ringshofer
 * 
 * @author Christian Ringshofer
 * @since 14:13:37 - 01.11.2011
 * @version Alpha 1.0
 */
public class BaseActivity extends Activity {

	// ===================================================================
	// Fields
	// ===================================================================

	public static int MAX_POINT_LIGHTS = 1;
	public static int MAX_DIR_LIGHTS = 1;
	public final static int MAX_TEXTURES = 10;

	public final static String[] GL_VALUES = { "MAX_VERTEX_UNIFORM_VECTORS", "MAX_FRAGMENT_UNIFORM_VECTORS", "MAX_VARYING_VECTORS", "MAX_TEXTURE_SIZE", "MAX_VERTEX_ATTRIBS", "MAX_VIEWPORT_DIMS",
			"MAX_TEXTURE_IMAGE_UNITS", "MAX_COMBINED_TEXTURE_IMAGE_UNITS" };

	// ===================================================================
	// Override Methods
	// ===================================================================

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		restoreInstanceState(savedInstanceState);
		// savedInstanceState.putByte("activityStatus",
		// EngineCache.engineActivityStatus);
		BaseActivityCache.mainActivity.onGameSave(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// EngineCache.engineActivityStatus =
		// savedInstanceState.getByte("activityStatus");
		BaseActivityCache.mainActivity.onGameRestore(savedInstanceState);
	}

	// @Override
	// public void onConfigurationChanged(Configuration newConfig) {
	// // Ignore orientation change not to restart activity
	// super.onConfigurationChanged(newConfig);
	// }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// ======== Load Engine & Allocate Memory, etc. ========
		super.onCreate(savedInstanceState);
		Log.i("Activity", "onCreate");
		// ======== Pass Activity ========
		BaseActivityCache.mainActivity = (LLama3D) this;
		// ======== Init Engine ========
		EngineCache.onCreate();
	}

	@Override
	public void onStart() {
		Log.i("Activity", "onStart");
		// ======== Free Memory ========
		super.onStart();
	}

	@Override
	public void onPause() {
		Log.i("Activity", "onPause");
		// ======== Disallocate Memory ========
		EngineCache.onPause();
		// ======== Pause Activity ========
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i("Activity", "onResume");
		// ======== Allocate Memory ========
		EngineCache.onResume();
		// ======== Resume Activity ========
		super.onResume();
	}

	@Override
	public void onRestart() {
		super.onRestart();
		BaseActivityCache.onRestart = true;
	}

	@Override
	public void onStop() {
		Log.i("Activity", "onStop");
		// ======== Free Memory ========
		// ======== Stop Activity ========
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void restoreInstanceState(Bundle savedInstanceState) {
		// ======== If There's A Bundle ========
		if (savedInstanceState != null) {
			EngineCache.engineActivityStatus = savedInstanceState.getByte("activityStatus");
			BaseActivityCache.mainActivity.onGameRestore(savedInstanceState);
		}
	}

}
