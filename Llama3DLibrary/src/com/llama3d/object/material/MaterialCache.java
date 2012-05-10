package com.llama3d.object.material;

public class MaterialCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Material defaultMaterial;
	public static Material currentMaterial = null;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== Load Default Material ========
		MaterialCache.defaultMaterial = new Material();
	}

}
