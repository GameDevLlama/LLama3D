package com.llama3d.audio.sound;

import java.io.IOException;

import com.llama3d.main.assets.AssetCache;

public class SoundFactory {

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static Sound createSFX() {

		// ======== Create New Sound Object ========
		Sound sfx = new Sound();
		// ======== Return Sound Object ========
		return sfx;

	}

	public static Sound loadSFX(String soundPath) {

		// ======== Create New Sound Object ========
		Sound sfx = new Sound();
		try {
			sfx.soundDescription = AssetCache.mainAssets.openFd(soundPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sfx;
	}

}
