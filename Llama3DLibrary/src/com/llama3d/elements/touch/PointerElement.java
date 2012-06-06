package com.llama3d.elements.touch;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.llama3d.main.display.DisplayCache;

public class PointerElement implements OnTouchListener {

	// ===================================================================
	// Fields
	// ===================================================================

	private int mainAction;
	private int mainPointerID;
	private int mainPointerIndex;

	private int pointerCount = 0;

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void onTouchEvent(MotionEvent mainMotionEvent) {
		// ======== Get New Stuff / Rest Old Stuff ========
		Pointer.count = mainMotionEvent.getPointerCount();
		// ======== Get MotionEvent Action ========
		this.mainAction = mainMotionEvent.getAction() & MotionEvent.ACTION_MASK;
		this.mainPointerIndex = (mainMotionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		this.mainPointerID = mainMotionEvent.getPointerId(this.mainPointerIndex);
		// ======== Reset MotionEvents / Hit / Release ========
		Pointer.hit[this.mainPointerID] = false;
		// ======== Select MotionEvent Action ========
		switch (this.mainAction) {
		// ======== Any Down Event ========
		case MotionEvent.ACTION_DOWN:
			Pointer.hit[this.mainPointerID] = true;
			Pointer.down[this.mainPointerID] = true;
			Pointer.x[this.mainPointerID] = mainMotionEvent.getX(this.mainPointerIndex) - DisplayCache.w / 2;
			Pointer.y[this.mainPointerID] = mainMotionEvent.getY(this.mainPointerIndex) - DisplayCache.h / 2;
			break;
		// ======== Any Down Event ========
		case MotionEvent.ACTION_POINTER_DOWN:
			Pointer.hit[this.mainPointerID] = true;
			Pointer.down[this.mainPointerID] = true;
			Pointer.x[this.mainPointerID] = mainMotionEvent.getX(this.mainPointerIndex) - DisplayCache.w / 2;
			Pointer.y[this.mainPointerID] = mainMotionEvent.getY(this.mainPointerIndex) - DisplayCache.h / 2;
			break;
		// ======== Any Up Event ========
		case MotionEvent.ACTION_UP:
			Pointer.down[this.mainPointerID] = false;
			Pointer.speedX[this.mainPointerID] = 0;
			Pointer.speedY[this.mainPointerID] = 0;
			break;
		// ======== Any Up Event ========
		case MotionEvent.ACTION_POINTER_UP:
			Pointer.down[this.mainPointerID] = false;
			Pointer.speedX[this.mainPointerID] = 0;
			Pointer.speedY[this.mainPointerID] = 0;
			break;
		// ======== Any Move Event ========
		case MotionEvent.ACTION_MOVE:
			Pointer.x[this.mainPointerID] = mainMotionEvent.getX(this.mainPointerIndex) - DisplayCache.w / 2;
			Pointer.y[this.mainPointerID] = mainMotionEvent.getY(this.mainPointerIndex) - DisplayCache.h / 2;
			if (mainMotionEvent.getHistorySize() > 1) {
				Pointer.speedX[this.mainPointerID] = mainMotionEvent.getHistoricalX(this.mainPointerIndex, mainMotionEvent.getHistorySize() - 1)
						- mainMotionEvent.getHistoricalX(this.mainPointerIndex, mainMotionEvent.getHistorySize() - 2);
				Pointer.speedY[this.mainPointerID] = mainMotionEvent.getHistoricalY(this.mainPointerIndex, mainMotionEvent.getHistorySize() - 1)
						- mainMotionEvent.getHistoricalY(this.mainPointerIndex, mainMotionEvent.getHistorySize() - 2);
			}
			break;
		}

		// final int historySize = mainMotionEvent.getHistorySize();
		// final int pointerCount = mainMotionEvent.getPointerCount();
		// for (int h = 0; h < historySize; h++) {
		// Log.i("At time", "" + mainMotionEvent.getHistoricalEventTime(h));
		// for (int p = 0; p < pointerCount; p++) {
		// System.out.printf("pointer %d: (%f,%f)",
		// mainMotionEvent.getPointerId(p), mainMotionEvent.getHistoricalX(p,
		// h), mainMotionEvent.getHistoricalY(p, h));
		// }
		// }
		// Log.i("At time", "" + mainMotionEvent.getEventTime());
		// for (int p = 0; p < pointerCount; p++) {
		// Log.i("Pointer", "pointer " + mainMotionEvent.getPointerId(p) + ": ("
		// + mainMotionEvent.getX(p) + "," + mainMotionEvent.getY(p) + ")");
		// }
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void getPinch() {

		if (Pointer.count == 2) {
			// ======== Correct Pointers? ========
			if (Pointer.down[0] && Pointer.down[1]) {
				// ======== Save First Distance ========
				float distance1 = this.distance(Pointer.x[0] - Pointer.speedX[0], Pointer.y[0] - Pointer.speedY[0], Pointer.x[1] - Pointer.speedX[1], Pointer.y[1] - Pointer.speedY[1]);
				// ======== Get Start & Current Distance ========
				float distance2 = this.distance(Pointer.x[0], Pointer.y[0], Pointer.x[1], Pointer.y[1]);
				// ======== Calculate Pinch ========
				if (distance1 > 0) {
					Pointer.pinch = distance2 / distance1;

					Pointer.pinchX = (Pointer.x[0] + Pointer.x[1]) / 2f;
					Pointer.pinchY = (Pointer.y[0] + Pointer.y[1]) / 2f;
					Pointer.pinchXRelative = (Pointer.xRelative[0] + Pointer.xRelative[1]) / 2f;
					Pointer.pinchYRelative = (Pointer.yRelative[0] + Pointer.yRelative[1]) / 2f;

					Pointer.pinchSpeedX = +(Pointer.speedX[0] + Pointer.speedX[1]) / 2f;
					Pointer.pinchSpeedY = +(Pointer.speedY[0] + Pointer.speedY[1]) / 2f;
					Pointer.pinchSpeedXRelative = +(Pointer.speedXRelative[0] + Pointer.speedXRelative[1]) / 2f;
					Pointer.pinchSpeedYRelative = +(Pointer.speedYRelative[0] + Pointer.speedYRelative[1]) / 2f;

					if (Math.abs(Pointer.pinch - 1.0) > 0.01) {
						Pointer.pinched = true;
					}
					return;
				}
			}
		}
		// ======== Reset Pinch ========
		Pointer.pinch = 0;
		Pointer.pinched = false;
	}

	private void getAngle() {

		if (Pointer.count == 2) {
			// ======== Correct Pointers? ========
			if (Pointer.down[0] && Pointer.down[1]) {
				float angle1 = this.angle(Pointer.x[0] - Pointer.speedX[0], Pointer.y[0] - Pointer.speedY[0], Pointer.x[1] - Pointer.speedX[1], Pointer.y[1] - Pointer.speedY[1]);
				// ======== Get Start & Current Distance ========
				float angle2 = this.angle(Pointer.x[0], Pointer.y[0], Pointer.x[1], Pointer.y[1]);
				// ======== Calculate Angle ========
				Pointer.angle = angle1 - angle2;
				return;
			}
		}

		Pointer.angle = 0;

	}

	// ===================================================================
	// Private Helper Methods
	// ===================================================================

	private float distance(float px1, float py1, float px2, float py2) {
		float dx = px1 - px2;
		float dy = py1 - py2;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	private float angle(float px1, float py1, float px2, float py2) {
		float dx = px1 - px2;
		float dy = py1 - py2;
		return (float) Math.toDegrees(Math.atan2(dy, dx));
	}

	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
