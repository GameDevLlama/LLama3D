package com.llama3d.main.view;

import com.llama3d.main.activity.BaseActivityCache;

public class SurfaceViewCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static SurfaceView surfaceView;
	public static boolean initialized = false;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Initialize SurfaceView ========
		SurfaceViewCache.surfaceView = new SurfaceView(BaseActivityCache.mainActivity);
	}

	public static void initRenderer() {
		// ======== Initialize SurfaceViewRenderer ========
		SurfaceViewCache.surfaceView.createRenderer();
	}

}
