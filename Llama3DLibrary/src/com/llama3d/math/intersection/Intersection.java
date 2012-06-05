package com.llama3d.math.intersection;

public class Intersection {

	// ===================================================================
	// Fields
	// ===================================================================

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static boolean lineToLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		// ======== Calculate Normalform ========
		double a1 = y2 - y1;
		double b1 = x1 - x2;
		double c1 = a1 * x1 + b1 * y1;
		// ======== Calculate Normalform ========
		double a2 = y4 - y3;
		double b2 = x3 - x4;
		double c2 = a2 * x3 + b2 * y3;
		// ======== Determine ========
		double det = a1 * b2 - a2 * b1;
		if (det == 0) {
			// ======== Lines Parallel ========
			return false;
		} else {
			// ======== Calculate Intersection Point ========
			double x = (b2 * c1 - b1 * c2) / det;
			double y = (a1 * c2 - a2 * c1) / det;
			// ======== Get Line Segment Intersection ========
			if (Math.min(x1, x2) <= x && x <= Math.max(x1, x2) && Math.min(x1, x2) <= y && y <= Math.max(x1, x2)) {
				if (Math.min(x3, x4) <= x && x <= Math.max(x3, x4) && Math.min(x3, x4) <= y && y <= Math.max(x3, x4)) {
					// ======== Point On Both Line Segements ========
					return true;
				} else {
					// ======== Point Not On Second Line Segment ========
					return false;
				}
			} else {
				// ======== Point Not On First Line Segment ========
				return false;
			}
		}
	}

	public static boolean lineToRect(double x1, double y1, double x2, double y2, double x, double y, double width, double height) {
		return Intersection.lineToRect(x1, y1, x2, y2, x, y, width, height, true);
	}

	public static boolean lineToRect(double x1, double y1, double x2, double y2, double x, double y, double width, double height, boolean fill) {
		// ======== Rect Not Just Border ========
		if (fill) {
			if (x1 >= x && x1 <= x + width && y1 >= y && y1 <= y + height) {
				return true;
			} else {
				if (x2 >= x && x2 <= x + width && y2 >= y && y2 <= y + height) {
					return true;
				} else {
					return false;
				}
			}
		}
		// ======== Intersection Variable ========
		boolean intersection = false;
		// ======== Line 1 Intersection ========
		intersection = Intersection.lineToLine(x1, y1, x2, y2, x, y, x, y + height);
		if (intersection) {
			return true;
		}
		// ======== Line 2 Intersection ========
		intersection = Intersection.lineToLine(x1, y1, x2, y2, x + width, y, x + width, y + height);
		if (intersection) {
			return true;
		}
		// ======== Line 3 Intersection ========
		intersection = Intersection.lineToLine(x1, y1, x2, y2, x, y, x + width, y);
		if (intersection) {
			return true;
		}
		// ======== Line 4 Intersection ========
		intersection = Intersection.lineToLine(x1, y1, x2, y2, x, y + height, x + width, y + height);
		if (intersection) {
			return true;
		}
		// ======== No Intersection ========
		return false;
	}
}
