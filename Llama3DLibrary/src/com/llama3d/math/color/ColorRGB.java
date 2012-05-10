package com.llama3d.math.color;

public class ColorRGB extends Color {

    public ColorRGB() {
        this(255, 255, 255);
    }

    public ColorRGB(int r, int g, int b) {
        this.r = (float) r / 255f;
        this.g = (float) g / 255f;
        this.b = (float) b / 255f;
    }

}
