package com.llama3d.math.vector;

public class Vector3 {

    // ===================================================================
    // Public Fields
    // ===================================================================

    public float x = 0;
    public float y = 0;
    public float z = 0;

    public float length = 0;

    // ===================================================================
    // Constructors
    // ===================================================================

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(float[] v) {
        this(v[0], v[1], v[2]);
    }

    // ===================================================================
    // Public Methdos
    // ===================================================================

    public void normalize() {
        float length = (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (length > 0f) {
            this.x /= length;
            this.y /= length;
            this.z /= length;
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public void add(Vector3 vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
    }

    public Vector3 cross(Vector3 vector) {
        return new Vector3(this.y * vector.z - this.z * vector.y, this.z * vector.x - this.x * vector.z, this.x * vector.y - this.y * vector.x);
    }

    public float dot(Vector3 vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

}
