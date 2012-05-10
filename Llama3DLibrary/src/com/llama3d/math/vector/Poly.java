package com.llama3d.math.vector;

public class Poly {
	public static boolean isInside(float posX0, float posY0, float posX1, float posY1, float posX2, float posY2, float pointX, float pointY) {
		// ======== Area 1 Is ========
		float area1 = (float) Math.abs((posX1 - posX0) * (posY2 - posY0) - (posY1 - posY0) * (posX2 - posX0));
		// ======== Area Within Pointer ========
		float area2A = (float) Math.abs((posX0 - pointX) * (posY1 - pointY) - (posY0 - pointY) * (posX1 - pointX));
		float area2B = (float) Math.abs((posX1 - pointX) * (posY2 - pointY) - (posY1 - pointY) * (posX2 - pointX));
		float area2C = (float) Math.abs((posX2 - pointX) * (posY0 - pointY) - (posY2 - pointY) * (posX0 - pointX));
		// ======== Area 2 Is ========
		float area2 = area2A + area2B + area2C;
		// ======== Resulting ========
		if ((float) Math.abs(area1 - area2) < 0.0001f) {
			return true;
		} else {
			return false;
		}
	}
}
