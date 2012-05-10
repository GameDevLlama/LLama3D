package com.llama3d.elements.touch;

public class Pointer {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	// ======== Single Pointer Data ========
	public static int count = 0;
	// ======== Complete Angle Data ========
	public static float angle;
	// ======== Complete Pinch Data ========
	public static boolean pinched;
	public static float pinch = 0;
	public static float pinchX;
	public static float pinchY;
	public static float pinchSpeedX;
	public static float pinchSpeedY;
	public static float pinchXRelative;
	public static float pinchYRelative;
	public static float pinchSpeedXRelative;
	public static float pinchSpeedYRelative;
	// ======== Multiple Pointer ========
	public static float[] x = new float[5];
	public static float[] y = new float[5];
	public static float[] speedX = new float[5];
	public static float[] speedY = new float[5];
	// ======== Multiple Relative Pointer ========
	public static float[] xRelative = new float[5];
	public static float[] yRelative = new float[5];
	public static float[] speedXRelative = new float[5];
	public static float[] speedYRelative = new float[5];
	// ======== Multiple Pointerevents ========
	public static boolean[] hit = new boolean[5];
	public static boolean[] down = new boolean[5];
	public static boolean[] move = new boolean[5];
	public static boolean[] release = new boolean[5];

}
