package com.llama3d.math.color;

public class ColorRGBA extends Color {
	public float a;

	public ColorRGBA() {
		this(1f, 1f, 1f, 1f);
	}

	public ColorRGBA(float r, float g, float b) {
		this(r, g, b, 1f);
	}

	public ColorRGBA(int r, int g, int b) {
		this(r, g, b, 255);
	}

	public ColorRGBA(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public ColorRGBA(int r, int g, int b, int a) {
		this.r = (float) ((float) r / 255.0);
		this.g = (float) ((float) g / 255.0);
		this.b = (float) ((float) b / 255.0);
		this.a = (float) ((float) a / 255.0);
	}
}