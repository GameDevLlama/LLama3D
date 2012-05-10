package com.llama3d.main.display;

import android.util.DisplayMetrics;
import android.view.Display;

import com.llama3d.main.window.WindowCache;

public class DisplayCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	// ======== Width, Height, Ratio, DPI, DPI-Factor ========
	public static volatile int w, h;
	public static float r = 1;
	public static float dW, dH;
	public static float dfW, dfH;

	public static Display display;
	public static DisplayMetrics metrics;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Get Device Metrics ========
		DisplayCache.metrics = new DisplayMetrics();
		// ======== Get Device Display ========
		DisplayCache.display = WindowCache.windowManager.getDefaultDisplay();
		DisplayCache.display.getMetrics(DisplayCache.metrics);
	}

	public static void setDisplaySize() {
		// ======== DisplayMetrics ========
		DisplayCache.w = DisplayCache.metrics.widthPixels;
		DisplayCache.h = DisplayCache.metrics.heightPixels;
		// ======== DisplayRatio ========
		DisplayCache.r = (float) DisplayCache.w / (float) DisplayCache.h;
		// ======== DisplayDpi ========
		DisplayCache.dW = DisplayCache.metrics.xdpi;
		DisplayCache.dH = DisplayCache.metrics.ydpi;
		// ======== Relative DisplayDpiFactor ========
		DisplayCache.dfW = DisplayCache.dW / 195f;
		DisplayCache.dfH = DisplayCache.dH / 195f;
		// ======== Info ========
		//Log.i(SurfaceView.class.getSimpleName(), "set resolution. [" + DisplayCache.w + "x" + DisplayCache.h + "]");
	}

}
