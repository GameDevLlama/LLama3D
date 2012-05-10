package com.llama3d.object.mesh;

public class Normal {
	public float x;
	public float y;
	public float z;
	public Normal() {
		this(0,0,0);
	}
	public Normal(float x,float y) {
		this(x,y,0);
	}
	public Normal(float x,float y,float z) {
		float length = (float) Math.sqrt(x*x+y*y+z*z);
		this.x = x/length;
		this.y = y/length;
		this.z = z/length;
	}
}
