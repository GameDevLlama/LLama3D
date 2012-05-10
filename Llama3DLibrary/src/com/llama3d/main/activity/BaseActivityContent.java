package com.llama3d.main.activity;

import com.llama3d.main.view.SurfaceViewCache;


public class BaseActivityContent {

 // ===================================================================
    // Public Static Methods
    // ===================================================================

    public static void init() {
        // ======== Initialize ContentView ========
        BaseActivityCache.mainActivity.setContentView(SurfaceViewCache.surfaceView);
    }
    
}
