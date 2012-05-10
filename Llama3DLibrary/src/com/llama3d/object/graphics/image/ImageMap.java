package com.llama3d.object.graphics.image;

public class ImageMap extends ImageBase {

    // ===================================================================
    // Protected Static Fields
    // ===================================================================

    protected static boolean imageMapOpen = false;
    protected static ImageMap activeImageMap = null;

    // ===================================================================
    // Constructors
    // ===================================================================

    // ===================================================================
    // Methods
    // ===================================================================

    public void open() {
        if (ImageMap.imageMapOpen == false) {
            ImageMap.imageMapOpen = true;
            ImageMap.activeImageMap = this;
        }
    }

    public void close() {
        if (ImageMap.imageMapOpen == true && ImageMap.activeImageMap == this) {
            ImageMap.imageMapOpen = false;
            ImageMap.activeImageMap = null;
        }
    }

}
