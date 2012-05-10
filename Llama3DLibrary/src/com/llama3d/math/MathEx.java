package com.llama3d.math;

public class MathEx {

    public static float clamp(float x, float min, float max) {
        return x < min ? min : (x > max ? max : x);
    }

    public static int clamp(int x, int min, int max) {
        return x < min ? min : (x > max ? max : x);
    }

}
