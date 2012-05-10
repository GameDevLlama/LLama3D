package com.llama3d.main.graphics;

import com.llama3d.main.display.DisplayCache;

public class Origin {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static int positionX;
	public static int positionY;

	public static final int center = 0x00001001;
	public static final int upperLeft = 0x00001002;

	// ===================================================================
	// Static Methods
	// ===================================================================

	public static void center() {
		Origin.positionX = 0;
		Origin.positionY = 0;
	}

	public static void upperLeftCorner() {
		Origin.positionX = -DisplayCache.w / 2;
		Origin.positionY = +DisplayCache.h / 2;
	}

	public static void upperRightCorner() {
		Origin.positionX = +DisplayCache.w / 2;
		Origin.positionY = +DisplayCache.h / 2;
	}

}
