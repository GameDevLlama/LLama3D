package com.llama3d.math.matrix;

import android.opengl.Matrix;

public class MatrixEx {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	// ======== 1. Row ========
	private static final byte m11 = 0;
	private static final byte m12 = 4;
	private static final byte m13 = 8;
	private static final byte m14 = 12;
	// ======== 2. Row ========
	private static final byte m21 = 1;
	private static final byte m22 = 5;
	private static final byte m23 = 9;
	private static final byte m24 = 13;
	// ======== 3. Row ========
	private static final byte m31 = 2;
	private static final byte m32 = 6;
	private static final byte m33 = 10;
	private static final byte m34 = 14;
	// ======== 4. Row ========
	private static final byte m41 = 3;
	private static final byte m42 = 7;
	private static final byte m43 = 11;
	private static final byte m44 = 15;

	private static float s, c, t;
	private static float[] vec = new float[3];
	private static float[] m2 = new float[16];
	private static float[] v = new float[3];
	private static float[] l = new float[2];

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void alignToVector(float[] m, int offset, float[] v1, float[] v2, float factor) {
		MatrixEx.alignToVector(m, offset, v1, v2, factor, 0);
	}

	public static void alignToVector(float[] m, int offset, float[] v1, float[] v2, float factor, float threshold) {
		float angle;
		// ======== Crossproduct ========
		v[0] = v1[1] * v2[2] - v1[2] * v2[1];
		v[1] = v1[2] * v2[0] - v1[0] * v2[2];
		v[2] = v1[0] * v2[1] - v1[1] * v2[0];
		// ======== Normalize ========
		l[0] = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
		if (l[0] > 0f) {
			v[0] /= l[0];
			v[1] /= l[0];
			v[2] /= l[0];
			// ======== Angle Between Vectors ========
			l[0] = (float) Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1] + v1[2] * v1[2]);
			l[1] = (float) Math.sqrt(v2[0] * v2[0] + v2[1] * v2[1] + v2[2] * v2[2]);
			if (l[0] > 0 && l[1] > 0) {
				angle = (v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2]) / (l[0] * l[1]);
				if (angle > +1f) {
					angle = +1f;
				} else if (angle < -1f) {
					angle = -1f;
				}
				angle = (float) Math.acos(angle);
				angle *= factor;
			} else {
				angle = 0;
			}
			if (angle > threshold) {
				MatrixEx.turnM(m, offset, angle, v[0], v[1], v[2]);
			}
		}
	}

	public static void turnM(float[] m, int offset, float a, float x, float y, float z) {
		// ======== Old Origin ========
		vec[m11] = m[m14];
		vec[m21] = m[m24];
		vec[m31] = m[m34];
		s = (float) Math.sin(a);
		c = (float) Math.cos(a);
		t = 1f - c;
		// ======== Rotation Matrix ========
		// ======== Collumn1 ========
		m2[m11] = x * x + (y * y + z * z) * c;
		m2[m21] = x * y * t + z * s;
		m2[m31] = x * z * t - y * s;
		// ======== Collumn2 ========
		m2[m12] = x * y * t - z * s;
		m2[m22] = y * y + (x * x + z * z) * c;
		m2[m32] = y * z * t + x * s;
		// ======== Collumn3 ========
		m2[m13] = x * z * t + y * s;
		m2[m23] = y * z * t - x * s;
		m2[m33] = z * z + (x * x + y * y) * c;
		// ======== Collumn4 ========
		m2[m14] = (m[m14] * (y * y + z * z) - x * (m[m24] * y + m[m34] * z)) * t + (m[m24] * z - m[m34] * y) * s;
		m2[m24] = (m[m24] * (x * x + z * z) - y * (m[m14] * y + m[m34] * z)) * t + (m[m34] * x - m[m14] * z) * s;
		m2[m34] = (m[m34] * (x * x + y * y) - z * (m[m14] * x + m[m24] * y)) * t + (m[m14] * y - m[m24] * x) * s;
		m2[m44] = 1;
		// ======== Do Rotation ========
		Matrix.multiplyMM(m, offset, m2, 0, m, 0);
		m[m14] = vec[m11];
		m[m24] = vec[m21];
		m[m34] = vec[m31];
	}

	public static void invertM(float[] im, float[] m) {
		float det = 0f;
		// ======== Det Sum ========
		det += +m[m11] * m[m22] * m[m33] * m[m44] + m[m11] * m[m23] * m[m34] * m[m42] + m[m11] * m[m24] * m[m32] * m[m43];
		det += +m[m12] * m[m21] * m[m34] * m[m43] + m[m12] * m[m23] * m[m31] * m[m44] + m[m12] * m[m24] * m[m33] * m[m41];
		det += +m[m13] * m[m21] * m[m32] * m[m44] + m[m13] * m[m22] * m[m34] * m[m41] + m[m13] * m[m24] * m[m31] * m[m42];
		det += +m[m14] * m[m21] * m[m33] * m[m42] + m[m14] * m[m22] * m[m31] * m[m43] + m[m14] * m[m23] * m[m32] * m[m41];
		// ======== Det Subtraction ========
		det += -m[m11] * m[m22] * m[m34] * m[m43] - m[m11] * m[m23] * m[m32] * m[m44] - m[m11] * m[m24] * m[m33] * m[m42];
		det += -m[m12] * m[m21] * m[m33] * m[m44] - m[m12] * m[m23] * m[m34] * m[m41] - m[m12] * m[m24] * m[m31] * m[m43];
		det += -m[m13] * m[m21] * m[m34] * m[m42] - m[m13] * m[m22] * m[m31] * m[m44] - m[m13] * m[m24] * m[m32] * m[m41];
		det += -m[m14] * m[m21] * m[m32] * m[m43] - m[m14] * m[m22] * m[m33] * m[m41] - m[m14] * m[m23] * m[m31] * m[m42];
		// ======== Catch Det ========
		if (det != 0) {
			// ======== Existing Inverse Matrix ========
			// ======== 1. Row ========
			im[m11] = m[m22] * m[m33] * m[m44] + m[m23] * m[m34] * m[m42] + m[m24] * m[m32] * m[m43] - m[m22] * m[m34] * m[m43] - m[m23] * m[m32] * m[m44] - m[m24] * m[m33] * m[m42];
			im[m12] = m[m12] * m[m34] * m[m43] + m[m13] * m[m32] * m[m44] + m[m14] * m[m33] * m[m42] - m[m12] * m[m33] * m[m44] - m[m13] * m[m34] * m[m42] - m[m14] * m[m32] * m[m43];
			im[m13] = m[m12] * m[m23] * m[m44] + m[m13] * m[m24] * m[m42] + m[m14] * m[m22] * m[m43] - m[m12] * m[m24] * m[m43] - m[m13] * m[m22] * m[m44] - m[m14] * m[m23] * m[m42];
			im[m14] = m[m12] * m[m24] * m[m33] + m[m13] * m[m22] * m[m34] + m[m14] * m[m23] * m[m32] - m[m12] * m[m23] * m[m34] - m[m13] * m[m24] * m[m32] - m[m14] * m[m22] * m[m33];
			// ======== 2. Row ========
			im[m21] = m[m21] * m[m34] * m[m43] + m[m23] * m[m31] * m[m44] + m[m24] * m[m33] * m[m41] - m[m21] * m[m33] * m[m44] - m[m23] * m[m34] * m[m41] - m[m24] * m[m31] * m[m43];
			im[m22] = m[m11] * m[m33] * m[m44] + m[m13] * m[m34] * m[m41] + m[m14] * m[m13] * m[m43] - m[m11] * m[m34] * m[m43] - m[m13] * m[m31] * m[m44] - m[m14] * m[m33] * m[m41];
			im[m23] = m[m11] * m[m24] * m[m43] + m[m13] * m[m21] * m[m44] + m[m14] * m[m23] * m[m41] - m[m11] * m[m23] * m[m44] - m[m13] * m[m24] * m[m41] - m[m14] * m[m21] * m[m43];
			im[m24] = m[m11] * m[m23] * m[m34] + m[m13] * m[m24] * m[m31] + m[m14] * m[m21] * m[m33] - m[m11] * m[m24] * m[m33] - m[m13] * m[m21] * m[m34] - m[m14] * m[m23] * m[m31];
			// ======== 3. Row ========
			im[m31] = m[m21] * m[m32] * m[m44] + m[m22] * m[m34] * m[m41] + m[m24] * m[m31] * m[m42] - m[m21] * m[m34] * m[m42] - m[m22] * m[m31] * m[m44] - m[m24] * m[m32] * m[m41];
			im[m32] = m[m11] * m[m34] * m[m42] + m[m12] * m[m31] * m[m44] + m[m14] * m[m32] * m[m41] - m[m11] * m[m32] * m[m44] - m[m12] * m[m34] * m[m41] - m[m14] * m[m31] * m[m42];
			im[m33] = m[m11] * m[m22] * m[m44] + m[m12] * m[m24] * m[m41] + m[m14] * m[m21] * m[m42] - m[m11] * m[m24] * m[m42] - m[m12] * m[m21] * m[m44] - m[m14] * m[m22] * m[m41];
			im[m34] = m[m11] * m[m24] * m[m32] + m[m12] * m[m21] * m[m34] + m[m14] * m[m22] * m[m31] - m[m11] * m[m22] * m[m34] - m[m12] * m[m24] * m[m31] - m[m14] * m[m21] * m[m32];
			// ======== 4. Row ========
			im[m41] = m[m21] * m[m33] * m[m42] + m[m22] * m[m31] * m[m43] + m[m23] * m[m32] * m[m41] - m[m21] * m[m32] * m[m43] - m[m22] * m[m33] * m[m41] - m[m23] * m[m31] * m[m42];
			im[m42] = m[m11] * m[m32] * m[m43] + m[m12] * m[m33] * m[m41] + m[m13] * m[m31] * m[m42] - m[m11] * m[m33] * m[m42] - m[m12] * m[m31] * m[m43] - m[m13] * m[m32] * m[m41];
			im[m43] = m[m11] * m[m23] * m[m42] + m[m12] * m[m21] * m[m43] + m[m13] * m[m22] * m[m41] - m[m11] * m[m22] * m[m43] - m[m12] * m[m23] * m[m41] - m[m13] * m[m21] * m[m42];
			im[m44] = m[m11] * m[m22] * m[m33] + m[m12] * m[m23] * m[m31] + m[m13] * m[m21] * m[m32] - m[m11] * m[m23] * m[m32] - m[m12] * m[m21] * m[m33] - m[m13] * m[m22] * m[m31];
			// ======== Calculate Inverse Matrix ========
			det = 1f / det;
			for (int i = 0; i < 16; i++) {
				im[i] *= det;
			}
		} else {
			// ======== No Inverse Matrix ========
			Matrix.setIdentityM(im, 0);
		}
	}

	public static void setIdentityM(float[] m, int offset) {
		for (int i = 0; i < 16; i++) {
			m[i] = 0;
		}
		m[m11] = 1;
		m[m22] = 1;
		m[m33] = 1;
	m[m44] = 1;
	}

	public static float getPitch(float[] m) {
		return (float) Math.toDegrees(Math.atan(m[1] / m[0]));
	}

	public static float getYaw(float[] m) {
		return (float) Math.toDegrees(Math.atan((-m[2]) / (Math.sqrt(Math.pow(m[6], 2) + Math.pow(m[10], 2)))));
	}

	public static float getRoll(float[] m) {
		return (float) Math.toDegrees(Math.atan(m[1] / m[5]));
	}

}
