package com.llama3d.main.statistics;

import android.util.Log;

public class Statistics {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	// ======== Frames ========
	private static int mainRenderedFrames;
	// ======== Milliseconds ========
	private static long mainMillisecondsPrevious;
	private static long mainMillisecondsCurrent;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void updateFPSCounter() {

		// ======== Increase Frame Size ========
		Statistics.mainRenderedFrames++;
		// ======== Get Current Time ========
		Statistics.mainMillisecondsCurrent = System.currentTimeMillis();
		// ======== Get Enlapsed Time ========
		if (Statistics.mainMillisecondsPrevious < Statistics.mainMillisecondsCurrent) {
			// ======== Calculate Frames Per Second ========
			StatisticsCache.framesPerSecond = (float) Statistics.mainRenderedFrames / 10f;
			StatisticsCache.millisecsPerFrame = 10000f / (float) Statistics.mainRenderedFrames;
			Statistics.mainRenderedFrames = 0;
			Statistics.mainMillisecondsPrevious = System.currentTimeMillis() + 10000;
			// ======== Debuginfo ========
			Log.d("Statistics", StatisticsCache.framesPerSecond + " fps , average " + StatisticsCache.millisecsPerFrame + " ms");
		}

	}
}
