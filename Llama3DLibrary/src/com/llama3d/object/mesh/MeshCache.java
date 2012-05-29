package com.llama3d.object.mesh;

public class MeshCache {

	// ===================================================================
	// Protected Static Fields
	// ===================================================================

	protected static int lastUsedVBO = -1;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void reset() {
		// ======== Reset Temporary RenderStep Data ========
		MeshCache.lastUsedVBO = -1;
	}

}
