package com.llama3d.audio.sound;

public class Curve {

	// ===================================================================
	// Public Static Final Fields
	// ===================================================================

	// ======== Curve Types ========
	public final static int SINE = 0x000a00c1;
	public final static int RECT = 0x000a00c2;
	public final static int SAW = 0x000a00c3;
	public final static int RANDOM = 0x000a00c4;
	public final static int UNSIGNED_SINE = 0x000a00c5;
	public final static int UNSIGNED_RECT = 0x000a00c6;
	public final static int UNSIGNED_SAW = 0x000a00c7;
	public final static int UNSIGNED_RANDOM = 0x000a00c8;
	public final static int LINEAR = 0x000a00c9;
	public final static int HYPERBEL = 0x000a00ca;
	public final static int LOG = 0x000a00cb;
	public final static int CONST = 0x000a00cc;

	// ======== Mix Types ========
	public final static int MODULATE = 0x000b00c1;
	public final static int MULTIPLY = 0x000b00c2;
	public final static int DIVIDE = 0x000b00c3;
	public final static int ADD = 0x000b00c4;
	public final static int SUBTRACT = 0x000b00c5;
	public final static int REPLACE = 0x000b00c6;
	public final static int FADE = 0x000b00c7;

}
