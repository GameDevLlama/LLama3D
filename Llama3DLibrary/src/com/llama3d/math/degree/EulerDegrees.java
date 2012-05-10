package com.llama3d.math.degree;

import javax.vecmath.Quat4f;

import com.llama3d.math.vector.Vector3;

public class EulerDegrees {
	
	public static Vector3 convertToEuler(Quat4f q) {
		Vector3 vector = new Vector3();
		vector.x = (float) Math.toDegrees(Math.atan2(2 * (q.y * q.z + q.w * q.x), q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z));
		vector.y = (float) Math.toDegrees(Math.asin(-2 * (q.x * q.z - q.w * q.y)));
		vector.z = (float) Math.toDegrees(Math.atan2(2 * (q.x * q.y + q.w * q.z), q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z));
		return vector;
	}
	
}
