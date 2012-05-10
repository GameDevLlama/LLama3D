package com.llama3d.main.window;

import android.view.Window;
import android.view.WindowManager;

import com.llama3d.main.activity.BaseActivityCache;

public class WindowCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Window window;
	public static WindowManager windowManager;
	public static int mainOrientation;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init(int orientation) {
		BaseActivityCache.mainActivity.setRequestedOrientation(orientation);
		WindowCache.mainOrientation = orientation;

		WindowCache.window = BaseActivityCache.mainActivity.getWindow();
		WindowCache.window.requestFeature(Window.FEATURE_NO_TITLE);
		WindowCache.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowCache.window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		WindowCache.window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		WindowCache.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		WindowCache.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		WindowCache.windowManager = BaseActivityCache.mainActivity.getWindowManager();
	}

}
