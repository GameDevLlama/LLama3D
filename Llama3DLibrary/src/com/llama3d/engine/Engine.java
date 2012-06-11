package com.llama3d.engine;

import com.llama3d.elements.sensor.AccelerationElementCache;
import com.llama3d.main.activity.BaseActivityCache;
import com.llama3d.main.view.SurfaceViewCache;
import com.llama3d.scene.SceneCache;

public class Engine extends Thread {

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	private int frameSkip = 4;
	private int frameMax = 60;
	private long frameSleep = 200l;
	private int frameTimeN = 1000000000 / this.frameMax;

	// ===================================================================
	// Thread Program
	// ===================================================================

	@Override
	public void run() {
		// ======== Set The Name For The Current Thread ========
		this.setName("Engine");
		// ======== Wait For The SurfaceView ========
		while (SurfaceViewCache.initialized == false) {
			try {
				Thread.sleep(frameSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// ======== Timestamps ========
		long[] timeStamp = new long[] { 0, 0, 0 };
		int frameSkipped;
		// ======== Main Thread Loop ========
		while (true) {
			timeStamp[0] = System.nanoTime();
			frameSkipped = 0;
			// =================================
			// ======== Update Gameloop ========
			//frame independent Engine.updateGame();
			// ======== End Update Gameloop ========
			// =====================================

			// =================================
			// ======== Update Elements ========
			AccelerationElementCache.element.getSensorEvents();
			// ======== End Update Elements ========
			// =====================================

			// ======== Request Render ========
			if (SurfaceViewCache.surfaceView != null) {
				SurfaceViewCache.surfaceView.requestRender();
			}
			// ======== Calculate Timestamps ========
			timeStamp[1] = System.nanoTime() - timeStamp[0];
			timeStamp[2] = this.frameTimeN - timeStamp[1];
			if (timeStamp[2] > 0) {
				try {
					Thread.sleep(timeStamp[2] / 1000000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// ======== Skip Frames ========
			while (timeStamp[2] < 0 && frameSkipped < this.frameSkip) {
				// =================================
				// ======== Update Gameloop ========
				//frame independent Engine.updateGame();
				// ======== End Update Gameloop ========
				// =====================================

				// ======== Update Every Skipped Frame ========
				timeStamp[2] += this.frameTimeN;
				frameSkipped++;
			}
		}
	}

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void setFPS(int FPS) {
		this.frameMax = FPS;
		this.frameTimeN = 1000000000 / this.frameMax;
	}

	// ===================================================================
	// Private Static Methods
	// ===================================================================

	private synchronized static void updateGame() {
		// ======== Update All Active Gameloops ========
		switch (EngineCache.engineActivityStatus) {
		// ======== Activity Update ========
		case 1:
			SceneCache.update();
			BaseActivityCache.mainActivity.onGameUpdate();
			break;
		}
	}
}
