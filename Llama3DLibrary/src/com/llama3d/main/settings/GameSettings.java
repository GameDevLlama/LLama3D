package com.llama3d.main.settings;

import com.llama3d.main.display.Screen;
import com.llama3d.main.window.orientation.Orientation;

public class GameSettings {

	// ===================================================================
	// Static Fields
	// ===================================================================

	public static GameSettings mainSettings = null;

	// ===================================================================
	// Fields
	// ===================================================================

	private int windowOrientation = Orientation.LANDSCAPE;

	// ===================================================================
	// Constructors
	// ===================================================================

	public GameSettings() {

	}

	// ===================================================================
	// Static Methods
	// ===================================================================

	public static void init() {
		GameSettings.mainSettings = new GameSettings();
	}

	// ===================================================================
	// Methods
	// ===================================================================

	public void setOrientation(int orientation) {
		this.windowOrientation = orientation;
	}

	public int getOrientation() {
		return this.windowOrientation;
	}

	public void setScreenSize(int width, int height) {
		Screen.setSize(width, height);
	}

}
