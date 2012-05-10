package com.llama3d.main.content;

import com.llama3d.main.activity.BaseActivityCache;
import com.llama3d.main.view.SurfaceViewCache;

public class ContentCache {

    // ===================================================================
    // Public Static Methods
    // ===================================================================

    public static void setContentCache() {
        BaseActivityCache.mainActivity.setContentView(SurfaceViewCache.surfaceView);
    }

}
