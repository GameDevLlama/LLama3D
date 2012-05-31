package com.llama3d.vibrator;

import android.content.Context;
import android.os.Vibrator;

import com.llama3d.main.activity.BaseActivityCache;

public class Vibes {

	private static Vibrator vibratorService;

	public static void init() {
		vibratorService = (Vibrator) BaseActivityCache.mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public static void vibrate(long millisecs) {
		vibratorService.vibrate(new long[] { 0, millisecs }, -1);
	}

	public static void vibrate(long[] pattern) {
		vibratorService.vibrate(pattern, 0);
	}

}
