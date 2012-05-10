package com.llama3d.shader;

import java.util.ArrayList;
import java.util.List;

public class ShaderCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static Shader activeShader;
	public static List<Shader> shaders = new ArrayList<Shader>();

	public static Shader modelShader;
	public static Shader imageShader;
	public static Shader guiShader;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ======== .JAR Intern Shader Loading ========
		ShaderCache.guiShader = new Shader("shaders/vertexshader_3dgui.fx", "shaders/fragmentshader_3dgui.fx", true);
		ShaderCache.imageShader = new Shader("shaders/vertexshader_image.fx", "shaders/fragmentshader_image.fx", true);
		ShaderCache.modelShader = new Shader("shaders/vertexshader.fx", "shaders/fragmentshader.fx", true);
		// ======== .JAR Intern Off ========
	}

}
