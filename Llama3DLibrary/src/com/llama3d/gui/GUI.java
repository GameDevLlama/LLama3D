package com.llama3d.gui;

import com.llama3d.object.graphics.image.Image;

public class GUI {

	// ===================================================================
	// Private Fields
	// ===================================================================

	private Image guiTexture;
	private Image[] background = new Image[9];
	private Image[] symbol = new Image[20];

	// ===================================================================
	// Constructor
	// ===================================================================

	private GUI(String GUIPath) {
		this.guiTexture = Image.load(GUIPath);
		// ======== Initialize Imageparts ========
		this.init();
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void init() {
		// ======== Load Background Images ========
		for (int i = 0; i < 9; i++) {
			this.background[i] = Image.grab(this.guiTexture, (i % 3) * 16, (i / 3) * 16, 16, 16);
		}
		// ======== Load Extra Symbols ========
		this.symbol[0] = Image.grab(this.guiTexture, 48, 0, 16, 16);// Cross
	}

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static GUI load(String GUIPath) {
		return new GUI(GUIPath);
	}

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void drawFrame(int x, int y, int width, int height) {
		this.background[0].drawRect(x, y, 16, 16);
		this.background[1].drawRect(x + 16, y, width - 16 * 2, 16);
		this.background[2].drawRect(x + width - 16, y, 16, 16);
		this.background[3].drawRect(x, y + 16, 16, height - 16 * 2);
		this.background[4].drawRect(x + 16, y + 16, width - 16 * 2, height - 16 * 2);
		this.background[5].drawRect(x + width - 16, y + 16, 16, height - 16 * 2);
		this.background[6].drawRect(x, y + height - 16, 16, 16);
		this.background[7].drawRect(x + 16, y + height - 16, width - 16 * 2, 16);
		this.background[8].drawRect(x + width - 16, y + height - 16, 16, 16);
	}

	public void drawSymbol(int x, int y, int index) {
		this.symbol[index].draw(x, y);
	}

}
