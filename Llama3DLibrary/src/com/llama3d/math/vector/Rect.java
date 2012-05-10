package com.llama3d.math.vector;

public class Rect {

	public static boolean overlap(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2) {
		// ======== Resulting ========
		if (x1 + width1 >= x2 && x2 + width2 >= x1 && y1 + height1 >= y2 && y2 + height2 >= y1) {
			return true;
		} else {
			return false;
		}
	}

}
