package com.llama3d.elements.touch;

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

	private boolean[] temporaryDown = new boolean[5];
	private boolean[] temporaryMove = new boolean[5];
	private boolean[] temporaryRelease = new boolean[5];

	private float[][] temporaryPositions = new float[5][4];

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void onTouchEvent(MotionEvent mainMotionEvent) {
		// ======== Get MotionEvent Action ========
		this.mainAction = mainMotionEvent.getAction() & MotionEvent.ACTION_MASK;
		this.mainPointerIndex = (mainMotionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		this.mainPointerID = mainMotionEvent.getPointerId(this.mainPointerIndex);
		// ======== Select MotionEvent Action ========
		switch (this.mainAction) {
		// ======== Any Down Event ========
		case MotionEvent.ACTION_DOWN:
			this.temporaryDown[this.mainPointerID] = true;
			break;
		// ======== Any Down Event ========
		case MotionEvent.ACTION_POINTER_DOWN:
			this.temporaryDown[this.mainPointerID] = true;
			break;
		// ======== Any Up Event ========
		case MotionEvent.ACTION_UP:
			this.temporaryRelease[this.mainPointerID] = true;
			break;
		// ======== Any Up Event ========
		case MotionEvent.ACTION_POINTER_UP:
			this.temporaryRelease[this.mainPointerID] = true;
			break;
		// ======== Any Move Event ========
		case MotionEvent.ACTION_MOVE:
			this.temporaryMove[this.mainPointerID] = true;
			break;
		// ======== Canceled Motion Event ========
		case MotionEvent.ACTION_CANCEL:
			this.temporaryDown[this.mainPointerID] = false;
			this.temporaryMove[this.mainPointerID] = false;
			this.temporaryRelease[this.mainPointerID] = false;
			break;
		}

		for (int i = 0; i < mainMotionEvent.getPointerCount(); i++) {
			// ======== New Pointer Coordinates ========
			// this.temporaryPositions[i][0] = +(mainMotionEvent.getX(i) *
			// mainMotionEvent.getXPrecision() - (float)
			// BaseActivity.mainSurface.mainViewer.width / 2.0f);
			// this.temporaryPositions[i][1] = +(mainMotionEvent.getY(i) *
			// mainMotionEvent.getYPrecision() - (float)
			// BaseActivity.mainSurface.mainViewer.height / 2.0f);

			this.temporaryPositions[i][0] = +(mainMotionEvent.getX(i) - (float) DisplayCache.w / 2.0f);
			this.temporaryPositions[i][1] = +(mainMotionEvent.getY(i) - (float) DisplayCache.h / 2.0f);

		}
		this.getPointerEvents();
	}

	public void getPointerEvents() {
		// ======== Get New Stuff / Rest Old Stuff ========
		Pointer.count = 0;
		for (int i = 0; i < 5; i++) {
			// ======== Reset MotionEvents / Hit / Release ========
			Pointer.hit[i] = false;
			Pointer.move[i] = false;
			Pointer.release[i] = false;
			// ======== Hit & Down ========
			if (Pointer.down[i] == false && this.temporaryDown[i] == true) {
				Pointer.hit[i] = true;
				Pointer.down[i] = true;
			}
			// ======== Down & Release ========
			if (Pointer.down[i] == true && this.temporaryRelease[i] == true) {
				Pointer.down[i] = false;
				Pointer.release[i] = true;
			}
			// ======== Down & Move ========
			if (Pointer.down[i] == true && this.temporaryMove[i] == true) {
				Pointer.move[i] = true;
			}
			// ======== Pointercount ========
			if (Pointer.down[i]) {
				Pointer.count++;
			}
			// ======== Reset Temporary Data ========
			this.temporaryDown[i] = false;
			this.temporaryMove[i] = false;
			this.temporaryRelease[i] = false;
			// ======== Save Old Position ========
			this.temporaryPositions[i][2] = Pointer.x[i];
			this.temporaryPositions[i][3] = Pointer.y[i];
			// ======== New Position ========
			Pointer.x[i] = this.temporaryPositions[i][0];
			Pointer.y[i] = this.temporaryPositions[i][1];
			Pointer.xRelative[i] = Pointer.x[i] / (float) DisplayCache.w;
			Pointer.yRelative[i] = Pointer.y[i] / (float) DisplayCache.h;
			// ======== Speed Calculation ========
			if (Pointer.hit[i]) {
				// ======== Old Position = New Position ========
				this.temporaryPositions[i][2] = Pointer.x[i];
				this.temporaryPositions[i][3] = Pointer.y[i];
			}
			Pointer.speedX[i] = +(Pointer.x[i] - this.temporaryPositions[i][2]);
			Pointer.speedY[i] = +(Pointer.y[i] - this.temporaryPositions[i][3]);
			Pointer.speedXRelative[i] = Pointer.speedX[i] / (float) DisplayCache.w;
			Pointer.speedYRelative[i] = Pointer.speedY[i] / (float) DisplayCache.h;
		}
		// ======== Get Other Stuff ========
		this.getPinch();
		this.getAngle();
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
