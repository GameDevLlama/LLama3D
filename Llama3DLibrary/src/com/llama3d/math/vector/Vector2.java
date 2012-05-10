package com.llama3d.math.vector;

public class Vector2 {

    // ===================================================================
    // Public Fields
    // ===================================================================

    public float x = 0;
    public float y = 0;

    public float length = 0;

    // ===================================================================
    // Constructors
    // ===================================================================

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2(float[] v) {
        this(v[0], v[1]);
    }

    // ===================================================================
    // Public Methods
    // ===================================================================

    public void normalize() {
        float length = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        if (length > 0f) {
            this.x /= length;
            this.y /= length;
        } else {
            this.x = 0;
            this.y = 0;
        }
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void add(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public Vector2 cross(Vector2 vector) {
        return new Vector2(this.y * vector.x - this.x * vector.y, this.x * vector.y - this.y * vector.x);
    }

    public float dot(Vector2 vector) {
        return this.x * vector.x + this.y * vector.y;
    }

}
