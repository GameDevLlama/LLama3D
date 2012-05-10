package com.llama3d.scene;

import java.util.ArrayList;
import java.util.List;

public class SceneCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Scene defaultScene;
	public static Scene activeScene;
	public static List<Scene> scenes = new ArrayList<Scene>();

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {

		SceneCache.defaultScene = new Scene();
		SceneCache.activeScene = SceneCache.defaultScene;

	}

	public static void update() {
		if (SceneCache.activeScene == SceneCache.defaultScene) {
			SceneCache.activeScene.update();
		}
	}
}
