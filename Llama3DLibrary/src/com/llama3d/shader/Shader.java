package com.llama3d.shader;

import android.opengl.GLES20;
import android.util.Log;

import com.llama3d.main.activity.BaseActivity;
import com.llama3d.main.assets.AssetFactory;

public class Shader {

	// ===================================================================
	// Fields
	// ===================================================================

	public int shaderVertex;
	public int shaderFragment;
	public int shaderProgram;

	public int viewMatrix;
	public int modelMatrix;
	public int rotationMatrix;
	public int projectionMatrix;

	public int attributeVertex;
	public int attributeNormal;
	public int attributeColor;
	public int attributeUV;

	public int ambientLight;

	public int[] pointLightSpecular = new int[BaseActivity.MAX_POINT_LIGHTS];
	public int[] pointLightRange = new int[BaseActivity.MAX_POINT_LIGHTS];
	public int[] pointLightPosition = new int[BaseActivity.MAX_POINT_LIGHTS];
	public int[] pointLightColor = new int[BaseActivity.MAX_POINT_LIGHTS];
	public int[] pointLightActive = new int[BaseActivity.MAX_POINT_LIGHTS];

	public int[] dirLightSpecular = new int[BaseActivity.MAX_DIR_LIGHTS];
	public int[] dirLightDirection = new int[BaseActivity.MAX_DIR_LIGHTS];
	public int[] dirLightColor = new int[BaseActivity.MAX_DIR_LIGHTS];
	public int[] dirLightActive = new int[BaseActivity.MAX_DIR_LIGHTS];

	public int matFog;
	public int matDiffuse;
	public int matSpecular;
	public int matElumination;
	public int matShininess;
	public int matAlpha;

	public int matAutoFade;
	public int matAutoFadeNear;
	public int matAutoFadeFar;

	public int cameraFog;
	public int cameraFogColor;
	public int cameraFogNear;
	public int cameraFogFar;
	public int cameraPosition;

	public int textureExists;
	public int texture;

	public int angle;
	public int scale;
	public int position;

	public int masked;
	public int maskColor;

	// ===================================================================
	// Constructors
	// ===================================================================

	public Shader() {
		this("shaders/vertexshader.fx", "shaders/fragmentshader.fx");
	}

	public Shader(String vertexShader, String fragmentShader) {
		this(vertexShader, fragmentShader, false);
	}

	public Shader(String vertexShader, String fragmentShader, boolean libraryIntern) {
		// ======== Create Program And Load Source ========
		this.shaderProgram = GLES20.glCreateProgram();
		this.shaderVertex = loadShader(GLES20.GL_VERTEX_SHADER, openGLloadSourceCode(vertexShader, libraryIntern), vertexShader);
		this.shaderFragment = loadShader(GLES20.GL_FRAGMENT_SHADER, openGLloadSourceCode(fragmentShader, libraryIntern), fragmentShader);
		// ======== Attach And Link Shader ========
		GLES20.glAttachShader(this.shaderProgram, this.shaderVertex);
		GLES20.glAttachShader(this.shaderProgram, this.shaderFragment);
		GLES20.glLinkProgram(this.shaderProgram);
		// ======== Get All Possible Shader Uniformlocations ========
		this.getUniformLocations();
		// ======== Delte Shader Source ========
		GLES20.glDeleteShader(this.shaderVertex);
		GLES20.glDeleteShader(this.shaderFragment);
		// ======== Save Shader ========
		ShaderCache.shaders.add(this);

	}

	// ===================================================================
	// Methods
	// ===================================================================

	private void getUniformLocations() {
		// ======== Ambientlight Uniform ========
		this.ambientLight = GLES20.glGetUniformLocation(this.shaderProgram, "ambient");
		// ======== Pointlight Uniforms ========
		for (int i = 0; i < BaseActivity.MAX_POINT_LIGHTS; i++) {
			this.pointLightSpecular[i] = GLES20.glGetUniformLocation(this.shaderProgram, "pl[" + i + "].specular");
			this.pointLightRange[i] = GLES20.glGetUniformLocation(this.shaderProgram, "pl[" + i + "].range");
			this.pointLightPosition[i] = GLES20.glGetUniformLocation(this.shaderProgram, "pl[" + i + "].position");
			this.pointLightColor[i] = GLES20.glGetUniformLocation(this.shaderProgram, "pl[" + i + "].color");
			this.pointLightActive[i] = GLES20.glGetUniformLocation(this.shaderProgram, "pl[" + i + "].active");
		}
		// ======== Dirlight Uniforms ========
		for (int i = 0; i < BaseActivity.MAX_DIR_LIGHTS; i++) {
			this.dirLightSpecular[i] = GLES20.glGetUniformLocation(this.shaderProgram, "dl[" + i + "].specular");
			this.dirLightDirection[i] = GLES20.glGetUniformLocation(this.shaderProgram, "dl[" + i + "].direction");
			this.dirLightColor[i] = GLES20.glGetUniformLocation(this.shaderProgram, "dl[" + i + "].color");
			this.dirLightActive[i] = GLES20.glGetUniformLocation(this.shaderProgram, "dl[" + i + "].active");
		}
		// ======== Camera Uniforms ========
		this.cameraFog = GLES20.glGetUniformLocation(this.shaderProgram, "cameraFog");
		this.cameraPosition = GLES20.glGetUniformLocation(this.shaderProgram, "camera");
		this.cameraFogColor = GLES20.glGetUniformLocation(this.shaderProgram, "UNIFORM_FOG_COLOR");
		this.cameraFogNear = GLES20.glGetUniformLocation(this.shaderProgram, "fogNear");
		this.cameraFogFar = GLES20.glGetUniformLocation(this.shaderProgram, "fogFar");
		// ======== Material Uniforms ========
		this.matFog = GLES20.glGetUniformLocation(this.shaderProgram, "mat.fog");
		this.matDiffuse = GLES20.glGetUniformLocation(this.shaderProgram, "mat.diff");
		this.matSpecular = GLES20.glGetUniformLocation(this.shaderProgram, "mat.spec");
		this.matElumination = GLES20.glGetUniformLocation(this.shaderProgram, "mat.elum");
		this.matShininess = GLES20.glGetUniformLocation(this.shaderProgram, "mat.shine");
		this.matAlpha = GLES20.glGetUniformLocation(this.shaderProgram, "mat.alpha");
		// ======== Autofade ========
		this.matAutoFade = GLES20.glGetUniformLocation(this.shaderProgram, "mat.autofade");
		this.matAutoFadeNear = GLES20.glGetUniformLocation(this.shaderProgram, "mat.aNear");
		this.matAutoFadeFar = GLES20.glGetUniformLocation(this.shaderProgram, "mat.aFar");
		// ======== Texture Uniforms ========
		this.textureExists = GLES20.glGetUniformLocation(this.shaderProgram, "uTextureExists");
		this.texture = GLES20.glGetUniformLocation(this.shaderProgram, "uTexture");
		// ======== Native Uniforms ========
		this.angle = GLES20.glGetUniformLocation(this.shaderProgram, "uAngle");
		this.scale = GLES20.glGetUniformLocation(this.shaderProgram, "uScale");
		this.position = GLES20.glGetUniformLocation(this.shaderProgram, "uPosition");
		// ======== ColorMask Uniforms ========
		this.masked = GLES20.glGetUniformLocation(this.shaderProgram, "uMasked");
		this.maskColor = GLES20.glGetUniformLocation(this.shaderProgram, "uMaskColor");
		// ======== Vertex Attributes ========
		this.attributeVertex = GLES20.glGetAttribLocation(this.shaderProgram, "Vertex");
		this.attributeNormal = GLES20.glGetAttribLocation(this.shaderProgram, "Normal");
		this.attributeColor = GLES20.glGetAttribLocation(this.shaderProgram, "Color");
		this.attributeUV = GLES20.glGetAttribLocation(this.shaderProgram, "MultiTexCoord");
		// ======== Matrices ========
		this.viewMatrix = GLES20.glGetUniformLocation(this.shaderProgram, "viewMatrix");
		this.modelMatrix = GLES20.glGetUniformLocation(this.shaderProgram, "modelMatrix");
		this.rotationMatrix = GLES20.glGetUniformLocation(this.shaderProgram, "rotationMatrix");
		this.projectionMatrix = GLES20.glGetUniformLocation(this.shaderProgram, "projectionMatrix");

	}

	private int loadShader(int type, String shaderCode, String shaderPath) {
		// ======== Create Shader ========
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glValidateProgram(shader);
		GLES20.glCompileShader(shader);
		// ======== Check For Compile Error ========
		int[] compiled = new int[1];
		GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
		if (compiled[0] == 0) {
			Log.e("OpenGL Shader", "Error. [" + shaderPath + "] " + GLES20.glGetShaderInfoLog(shader));
			Log.e("OpenGL Shader", GLES20.glGetProgramInfoLog(shader));
		}
		return shader;

	}

	private String openGLloadSourceCode(String shaderPath, boolean libraryIntern) {
		String rawShader = AssetFactory.loadAssetAsString(shaderPath, libraryIntern);
		rawShader = rawShader.replaceAll("LL.MAX_POINT_LIGHTS", "" + BaseActivity.MAX_POINT_LIGHTS);
		rawShader = rawShader.replaceAll("LL.MAX_DIR_LIGHTS", "" + BaseActivity.MAX_DIR_LIGHTS);
		rawShader = rawShader.replaceAll("LL.MAX_TEXTURES", "" + BaseActivity.MAX_TEXTURES);
		return rawShader;
	}

	public void use() {
		// ======== Use the current Shader ========
		GLES20.glUseProgram(this.shaderProgram);
		ShaderCache.activeShader = this;
	}
}
