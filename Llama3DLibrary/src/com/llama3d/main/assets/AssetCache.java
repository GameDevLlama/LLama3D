package com.llama3d.main.assets;

import android.content.res.AssetManager;

import com.llama3d.main.activity.BaseActivityCache;

public class AssetCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static AssetManager mainAssets;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Sets The Cache For Assets ========
		AssetCache.mainAssets = BaseActivityCache.mainActivity.getAssets();
	}
}
