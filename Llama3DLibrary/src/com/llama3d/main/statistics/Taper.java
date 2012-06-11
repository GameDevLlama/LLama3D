package com.llama3d.main.statistics;

import android.util.Log;

public class Taper {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static long[] timeStamp1 = new long[20];
	private static long[] timeStamp2 = new long[20];
	private static long[] temporaryTimes = new long[20];
	private static int[] tapes = new int[20];

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static long[] times = new long[20];

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void start(int index, boolean log) {

		// ======== After 1 minute ========
		if (System.nanoTime() > timeStamp1[index]) {
			timeStamp1[index] = System.nanoTime() + 60000000000l;
			// ======== New Taper Value ========
			if (tapes[index] > 0) {
				times[index] = temporaryTimes[index] / (long) tapes[index];
				temporaryTimes[index] = 0;
				tapes[index] = 0;
				// ======== Log Value ========
				if (log) {
					Log.i("TimeStamp", "taper " + index + "recorded certain processing durations in average time. [" + times[index] + " ns]");
				}
			}

		}
		// ======== Start Taping ========
		timeStamp2[index] = System.nanoTime();
	}

	public static void stop(int index) {
		// ======== Add temporary Times ========
		temporaryTimes[index] += System.nanoTime() - timeStamp2[index];
		tapes[index]++;
	}

}
