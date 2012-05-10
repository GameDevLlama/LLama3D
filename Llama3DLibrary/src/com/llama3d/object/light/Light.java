package com.llama3d.object.light;

import android.opengl.GLES20;

import com.llama3d.main.activity.BaseActivity;
import com.llama3d.math.color.ColorRGB;
import com.llama3d.math.vector.Vector3;
import com.llama3d.object.Object3D;
import com.llama3d.scene.Scene;
import com.llama3d.shader.ShaderCache;

public class Light extends Object3D {

	// ===================================================================
	// Public Fields
	// ===================================================================

	protected ColorRGB color;
	protected ColorRGB specularColor;
	protected float range;
	protected boolean active;

	protected static Vector3 vector = new Vector3();

	// ===================================================================
	// Constructors
	// ===================================================================

	public Light() {
		color = new ColorRGB();
		specularColor = new ColorRGB();
		active = true;
	}

	// ===================================================================
	// Methods
	// ===================================================================

	public void setColor(int r, int g, int b) {
		this.color.r = (float) ((float) r / 255.0);
		this.color.g = (float) ((float) g / 255.0);
		this.color.b = (float) ((float) b / 255.0);
		this.range = 60.0f;
	}

	public void specularColor(int r, int g, int b) {
		this.specularColor.r = (float) ((float) r / 255.0);
		this.specularColor.g = (float) ((float) g / 255.0);
		this.specularColor.b = (float) ((float) b / 255.0);
	}

	public static void uniformLights(Scene scene) {
		int i;
		i = 0;
		for (DirectionalLight dirLight : scene.dirlights) {
			if (i == BaseActivity.MAX_DIR_LIGHTS) {
				break;
			}
			if (dirLight.active == true) {
				Light.vector.x = dirLight.translationMatrix[4];
				Light.vector.y = dirLight.translationMatrix[5];
				Light.vector.z = dirLight.translationMatrix[6];
				Light.vector.normalize();
				GLES20.glUniform4f(ShaderCache.activeShader.dirLightColor[i], dirLight.color.r, dirLight.color.g, dirLight.color.b, 1f);
				GLES20.glUniform3f(ShaderCache.activeShader.dirLightDirection[i], Light.vector.x, Light.vector.y, Light.vector.z);
				GLES20.glUniform4f(ShaderCache.activeShader.dirLightSpecular[i], dirLight.specularColor.r, dirLight.specularColor.g, dirLight.specularColor.b, 1f);
			} else {
				GLES20.glUniform4f(ShaderCache.activeShader.dirLightColor[i], 0, 0, 0, 1f);
				GLES20.glUniform3f(ShaderCache.activeShader.dirLightDirection[i], 0, 1, 0);
				GLES20.glUniform4f(ShaderCache.activeShader.dirLightSpecular[i], 0, 0, 0, 1f);
			}
			i++;
		}
	}

}
