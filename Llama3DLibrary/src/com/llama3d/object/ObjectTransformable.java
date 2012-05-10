package com.llama3d.object;

import java.util.ArrayList;
import java.util.List;

import android.opengl.Matrix;

import com.llama3d.math.matrix.MatrixEx;
import com.llama3d.object.physics.ObjectPhysical;

public class ObjectTransformable extends ObjectPhysical {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static float[] tVec = new float[9];
	private static float[] tVec1 = new float[3];
	private static float[] tVec2 = new float[3];

	// ===================================================================
	// Private Fields
	// ===================================================================

	private volatile float[] scale = new float[] { 1, 1, 1 };
	private volatile float[] rotation = new float[3];

	// ===================================================================
	// Protected Fields
	// ===================================================================

	protected List<Object3D> childs = new ArrayList<Object3D>();
	protected Object3D parent;

	// ===================================================================
	// Public Fields
	// ===================================================================

	public volatile float[] translationMatrix = new float[16];
	public volatile float[] rotationMatrix = new float[16];
	public volatile float[] matrix = new float[16];

	// ===================================================================
	// Constructors
	// ===================================================================

	public ObjectTransformable() {
		Matrix.setIdentityM(this.translationMatrix, 0);
		Matrix.setIdentityM(this.rotationMatrix, 0);
		this.transform();
	}

	// ===================================================================
	// Getter
	// ===================================================================

	public float posX() {

		return this.matrix[12];
	}

	public float posY() {
		return this.matrix[13];
	}

	public float posZ() {
		return this.matrix[14];
	}

	// ===================================================================
	// Methods
	// ===================================================================

	public float distance(Object3D object) {
		tVec[0] = this.translationMatrix[12] - object.translationMatrix[12];
		tVec[1] = this.translationMatrix[13] - object.translationMatrix[13];
		tVec[2] = this.translationMatrix[14] - object.translationMatrix[14];
		return (float) Math.sqrt(tVec[0] * tVec[0] + tVec[1] * tVec[1] + tVec[2] * tVec[2]);
	}

	public void point(Object3D object) {

		// ======== Old Position ========
		tVec[0] = this.translationMatrix[12];
		tVec[1] = this.translationMatrix[13];
		tVec[2] = this.translationMatrix[14];
		// ======== Center ========
		tVec[3] = -(object.translationMatrix[12] - tVec[0]);
		tVec[4] = -(object.translationMatrix[13] - tVec[1]);
		tVec[5] = +(object.translationMatrix[14] - tVec[2]);
		// ======== Up Vector ========
		tVec[6] = this.translationMatrix[4];
		tVec[7] = this.translationMatrix[5];
		tVec[8] = -this.translationMatrix[6];
		// ======== LookAt Global ========
		Matrix.setLookAtM(this.translationMatrix, 0, 0, 0, 0, tVec[3], tVec[4], tVec[5], tVec[6], tVec[7], tVec[8]);
		this.translationMatrix[12] = tVec[0];
		this.translationMatrix[13] = tVec[1];
		this.translationMatrix[14] = tVec[2];

		// ======== Old Position ========
		tVec[0] = this.translationMatrix[12];
		tVec[1] = this.translationMatrix[13];
		tVec[2] = this.translationMatrix[14];
		// ======== Center ========
		tVec[3] = -(object.translationMatrix[12] - tVec[0]);
		tVec[4] = -(object.translationMatrix[13] - tVec[1]);
		tVec[5] = +(object.translationMatrix[14] - tVec[2]);
		// ======== Up Vector ========
		tVec[6] = this.translationMatrix[4];
		tVec[7] = this.translationMatrix[5];
		tVec[8] = -this.translationMatrix[6];
		// ======== LookAt Global ========
		Matrix.setLookAtM(this.translationMatrix, 0, 0, 0, 0, tVec[3], tVec[4], tVec[5], tVec[6], tVec[7], tVec[8]);
		this.translationMatrix[12] = tVec[0];
		this.translationMatrix[13] = tVec[1];
		this.translationMatrix[14] = tVec[2];

	}

	public void turnAroundVector(float angle, float vecX, float vecY, float vecZ) {
		this.turnAroundVector(angle, new float[] { vecX, vecY, vecZ });
	}

	public void turnAroundVector(float angle, float[] v) {
		MatrixEx.turnM(this.translationMatrix, 0, (float) Math.toRadians(angle), v[1], v[2], v[3]);
		// ======== Parential Transformation ========
		this.transform();
	}

	public void alignToVector(float vecX, float vecY, float vecZ) {
		this.alignToVector(vecX, vecY, vecZ, 0, 1f, 0f);
	}

	public void alignToVector(float vecX, float vecY, float vecZ, int axis) {
		this.alignToVector(vecX, vecY, vecZ, axis, 1f, 0f);
	}

	public void alignToVector(float vecX, float vecY, float vecZ, int axis, float factor, float threshold) {
		// ======== Vector1 ========
		tVec1[0] = -this.translationMatrix[axis * 4];
		tVec1[1] = -this.translationMatrix[axis * 4 + 1];
		tVec1[2] = -this.translationMatrix[axis * 4 + 2];
		// ======== Vector2 ========
		tVec2[0] = -vecX;
		tVec2[1] = -vecY;
		tVec2[2] = -vecZ;
		// ======== MatrixAlign ========
		MatrixEx.alignToVector(this.translationMatrix, 0, tVec1, tVec2, factor, threshold);
		// ======== Parential Transformation ========
		this.transform();
	}

	public void alignToVector(Object3D object) {
		this.alignToVector(object, 2, 1f);
	}

	public void alignToVector(Object3D object, int axis) {
		this.alignToVector(object, axis, 1f);
	}

	public void alignToVector(Object3D object, int axis, float factor) {
		// ======== Vector1 ========
		tVec1[0] = -this.translationMatrix[axis * 4];
		tVec1[1] = -this.translationMatrix[axis * 4 + 1];
		tVec1[2] = -this.translationMatrix[axis * 4 + 2];
		// ======== Vector2 ========
		tVec2[0] = +(object.translationMatrix[12] - this.translationMatrix[12]);
		tVec2[1] = +(object.translationMatrix[13] - this.translationMatrix[13]);
		tVec2[2] = +(object.translationMatrix[14] - this.translationMatrix[14]);
		// ======== MatrixAlign ========
		MatrixEx.alignToVector(this.translationMatrix, 0, tVec1, tVec2, factor);
		// ======== Parential Transformation ========
		this.transform();
	}

	public void scale(float scaleComplete) {
		this.scale(scaleComplete, scaleComplete, scaleComplete);
	}

	public void scale(float scaleX, float scaleY, float scaleZ) {
		this.scaleCollumn(0, scaleX);
		this.scaleCollumn(1, scaleY);
		this.scaleCollumn(2, scaleZ);
		this.transform();
	}

	private void scaleCollumn(int Axis, float scaleFactor) {
		float vectorLength;
		if (scaleFactor != 0f) {
			vectorLength = this.getScaleCollumn(Axis);
			if (vectorLength == 0) {
				this.translationMatrix[Axis * 4 + Axis] = scaleFactor;
			} else {
				this.translationMatrix[Axis * 4] /= vectorLength / scaleFactor;
				this.translationMatrix[Axis * 4 + 1] /= vectorLength / scaleFactor;
				this.translationMatrix[Axis * 4 + 2] /= vectorLength / scaleFactor;
			}
		} else {
			this.translationMatrix[Axis * 4] = 0;
			this.translationMatrix[Axis * 4 + 1] = 0;
			this.translationMatrix[Axis * 4 + 2] = 0;
		}
		this.scale[Axis] *= scaleFactor;
	}

	private float getScaleCollumn(int Axis) {
		return (float) Math.sqrt(this.translationMatrix[Axis * 4] * this.translationMatrix[Axis * 4] + this.translationMatrix[Axis * 4 + 1] * this.translationMatrix[Axis * 4 + 1]
				+ this.translationMatrix[Axis * 4 + 2] * this.translationMatrix[Axis * 4 + 2]);
	}

	public void translate(float translateX, float translateY, float translateZ) {
		this.translationMatrix[12] += translateX;
		this.translationMatrix[13] += translateY;
		this.translationMatrix[14] += translateZ;
		this.transform();
	}

	public void move(float moveX, float moveY, float moveZ) {
		this.move(moveX, moveY, moveZ, false);
	}

	public void move(float moveX, float moveY, float moveZ, boolean global) {
		if (global) {
			this.translationMatrix[12] += moveX;
			this.translationMatrix[13] += moveY;
			this.translationMatrix[14] += moveZ;
		} else {
			Matrix.translateM(this.translationMatrix, 0, +moveX, +moveY, +moveZ);
		}
		this.transform();
	}

	public void position(float posX, float posY, float posZ) {
		this.position(posX, posY, posZ, true);
	}

	public void position(float posX, float posY, float posZ, boolean global) {
		this.translationMatrix[12] = +posX;
		this.translationMatrix[13] = +posY;
		this.translationMatrix[14] = +posZ;
		this.transform();
	}

	public void rotate(float pitch, float yaw, float roll) {
		// ======== X-Axis ========
		this.translationMatrix[0] = this.scale[0];
		this.translationMatrix[1] = 0;
		this.translationMatrix[2] = 0;
		// ======== Y-Axis ========
		this.translationMatrix[4] = 0;
		this.translationMatrix[5] = this.scale[1];
		this.translationMatrix[6] = 0;
		// ======== Z-Axis ========
		this.translationMatrix[8] = 0;
		this.translationMatrix[9] = 0;
		this.translationMatrix[10] = this.scale[2];
		// ======== Rotation ========
		Matrix.rotateM(this.translationMatrix, 0, +pitch, 1f, 0, 0);
		Matrix.rotateM(this.translationMatrix, 0, +yaw, 0, 1f, 0);
		Matrix.rotateM(this.translationMatrix, 0, +roll, 0, 0, 1f);
		this.rotation[0] = pitch;
		this.rotation[1] = yaw;
		this.rotation[2] = roll;
		// ======== Hierarchial Transformation ========
		this.transform();
	}

	public void turn(float deltapitch, float deltayaw, float deltaroll) {
		this.turn(deltapitch, deltayaw, deltaroll, false);
	}

	public void turn(float deltapitch, float deltayaw, float deltaroll, boolean global) {
		this.rotation[0] = (this.rotation[0] + deltapitch) % 360f;
		this.rotation[1] = (this.rotation[1] + deltayaw) % 360f;
		this.rotation[2] = (this.rotation[2] + deltaroll) % 360f;
		Matrix.setIdentityM(this.rotationMatrix, 0);
		MatrixEx.turnM(this.rotationMatrix, 0, (float) Math.toRadians(this.rotation[0]), 1, 0, 0);
		MatrixEx.turnM(this.rotationMatrix, 0, (float) Math.toRadians(this.rotation[1]), 0, 1, 0);
		MatrixEx.turnM(this.rotationMatrix, 0, (float) Math.toRadians(this.rotation[2]), 0, 0, 1);
		this.transform();
	}

	protected void transform() {
		// ======== Transform Parential ========
		Matrix.setIdentityM(this.matrix, 0);
		Matrix.multiplyMM(this.matrix, 0, this.rotationMatrix, 0, this.matrix, 0);
		Matrix.multiplyMM(this.matrix, 0, this.translationMatrix, 0, this.matrix, 0);
		if (this.parent != null) {
			Matrix.multiplyMM(this.matrix, 0, this.parent.matrix, 0, this.matrix, 0);
		}
		// ======== Transform Children ========
		for (Object3D object : this.childs) {
			object.transform();
		}
	}

	protected void changeCoordinateSystem() {
		Matrix.setIdentityM(this.rotationMatrix, 0);
		Matrix.setIdentityM(this.translationMatrix, 0);
		Matrix.multiplyMM(this.rotationMatrix, 0, this.parent.matrix, 0, this.matrix, 0);
		this.translationMatrix[12] = this.rotationMatrix[12];
		this.translationMatrix[13] = this.rotationMatrix[13];
		this.translationMatrix[14] = this.rotationMatrix[14];
		Matrix.setIdentityM(this.translationMatrix, 0);
		this.rotationMatrix[12] = 0;
		this.rotationMatrix[13] = 0;
		this.rotationMatrix[14] = 0;
	}
}
