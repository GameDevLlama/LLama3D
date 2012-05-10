package com.llama3d.main.display;

public class Screen {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static int width = 0;
	public static int height = 0;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		if (Screen.width == 0 && Screen.height == 0) {
			// ======== Set Default Size Of Display ========
			Screen.width = DisplayCache.w;
			Screen.height = DisplayCache.h;
		}
	}

	public static void setSize(int width, int height) {
		// ======== Set Custom Size Of Display ========
		Screen.width = width;
		Screen.height = height;
	}

}
