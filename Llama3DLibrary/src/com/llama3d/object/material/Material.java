package com.llama3d.object.material;

import android.opengl.GLES20;

import com.llama3d.main.graphics.Effects;
import com.llama3d.main.graphics.blend.Blend;
import com.llama3d.math.MathEx;
import com.llama3d.math.color.ColorRGBA;
import com.llama3d.object.graphics.texture.Texture;
import com.llama3d.shader.ShaderCache;

public class Material {

	// ===================================================================
	// Fields
	// ===================================================================

	private ColorRGBA diffuseColor = new ColorRGBA();
	private ColorRGBA specularColor = new ColorRGBA();

	private Texture[] textures = new Texture[8];

	private float shine = 0.0f;
	private float elumination = 0.0f;

	private float autoFadeNear = 150f;
	private float autoFadeFar = 300f;

	private int blendMode = Blend.SOLID;
	private int fx = 0;

	private boolean changed = false;

	// ===================================================================
	// Constructor
	// ===================================================================

	public Material() {
		// ======== Load Default Material ========
		this.setColor(255, 255, 255);
		this.setShininess(0);
		this.setElumination(0.0f);
	}

	// ===================================================================
	// Public Methods
	// ===================================================================

	public float[] get(ColorRGBA colorVec) {
		float[] getVec = new float[4];
		getVec[0] = colorVec.r;
		getVec[1] = colorVec.g;
		getVec[2] = colorVec.b;
		getVec[3] = colorVec.a;
		return getVec;
	}

	public void setTexture(Texture texture) {
		this.setTexture(texture, 0);
		this.changed = true;
	}

	public void setTexture(Texture texture, int layer) {
		if (layer < 0) {
			layer = 0;
		} else if (layer > 7) {
			layer = 7;
		}
		this.textures[layer] = texture;
	}

	public void setAlpha(float alpha) {
		this.diffuseColor.a = alpha;
		if (this.diffuseColor.a < 1.0f) {
			if (this.blendMode == Blend.SOLID) {
				this.setBlend(Blend.ALPHA);
			}
		}
		this.changed = true;
	}

	public void setBlend(int blendMode) {
		this.blendMode = blendMode;
		this.changed = true;
	}

	/**
	 * Sets diffusecolor of given material.
	 * 
	 * @param r
	 *            - [ Red] Byte ( 0 - 255).
	 * @param g
	 *            - [ Green] Byte ( 0 - 255).
	 * @param b
	 *            - [ Blue] Byte ( 0 - 255).
	 */
	public void setColor(int r, int g, int b) {
		this.diffuseColor.r = (float) r / 255.0f;
		this.diffuseColor.g = (float) g / 255.0f;
		this.diffuseColor.b = (float) b / 255.0f;
		this.changed = true;
	}

	public void setFx(int FX) {
		this.fx = FX;
		this.changed = true;
	}

	/**
	 * Sets specularcolor of given material.
	 * 
	 * @param r
	 *            - [ Red] Byte ( 0 - 255).
	 * @param g
	 *            - [ Green] Byte ( 0 - 255).
	 * @param b
	 *            - [ Blue] Byte ( 0 - 255).
	 */
	public void setSpecularColor(int r, int g, int b) {
		this.specularColor.r = (float) r / 255.0f;
		this.specularColor.g = (float) g / 255.0f;
		this.specularColor.b = (float) b / 255.0f;
		this.changed = true;
	}

	/**
	 * Sets eluminationvalue of given material.
	 * 
	 * @param elumination
	 *            - Float (0.0 - 1.0)
	 */
	public void setElumination(float elumination) {
		this.elumination = elumination;
		this.changed = true;
	}

	/**
	 * Sets shininessvalue of given material.
	 * 
	 * @param shininess
	 */
	public void setShininess(float shininess) {
		shininess = MathEx.clamp(shininess, 0f, 1f);
		if (shininess == 0f) {
			this.shine = 0f;
		} else {
			this.shine = 1f + (float) Math.pow(shininess, 2f) * 99f;
		}
		this.changed = true;
	}

	public void passUniforms() {
		if (this != MaterialCache.currentMaterial || this.changed) {
			Blend.setBlend(this.blendMode);
			this.fx();
			GLES20.glUniform4fv(ShaderCache.activeShader.matDiffuse, 1, this.get(this.diffuseColor), 0);
			GLES20.glUniform1f(ShaderCache.activeShader.matAlpha, this.diffuseColor.a);

			GLES20.glUniform1f(ShaderCache.activeShader.matElumination, this.elumination);
			if (this.shine != 0f) {
				GLES20.glUniform1f(ShaderCache.activeShader.matShininess, this.shine);
				GLES20.glUniform4fv(ShaderCache.activeShader.matSpecular, 1, this.get(this.specularColor), 0);
			} else {
				GLES20.glUniform1f(ShaderCache.activeShader.matShininess, 1f);
				GLES20.glUniform4f(ShaderCache.activeShader.matSpecular, 0f, 0f, 0f, 0f);
			}

			if (this.textures[0] != null) {
				GLES20.glUniform1i(ShaderCache.activeShader.textureExists, 1);
				GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, this.textures[0].unit);
				GLES20.glUniform1i(ShaderCache.activeShader.texture, 0);
			} else {
				GLES20.glUniform1i(ShaderCache.activeShader.textureExists, 0);
			}

			MaterialCache.currentMaterial = this;
			this.changed = false;
		}
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private void fx() {
		// ======== Autofading ========
		if ((this.fx & Effects.AUTOFADE) == Effects.AUTOFADE) {
			if (this.blendMode == Blend.SOLID) {
				Blend.setBlend(Blend.ALPHA);
			}
			GLES20.glUniform1f(ShaderCache.activeShader.matAutoFade, 1);
			GLES20.glUniform1f(ShaderCache.activeShader.matAutoFadeNear, this.autoFadeNear);
			GLES20.glUniform1f(ShaderCache.activeShader.matAutoFadeFar, this.autoFadeFar);
		} else {
			GLES20.glUniform1f(ShaderCache.activeShader.matAutoFade, 0);
		}
		// ======== Material Fog ========
		if ((this.fx & Effects.FOG_OFF) == Effects.FOG_OFF) {
			GLES20.glUniform1i(ShaderCache.activeShader.matFog, 0);// fog
		} else {
			GLES20.glUniform1i(ShaderCache.activeShader.matFog, 1);// fog
		}
		// ======== Cull For Backface Or Both ========
		if ((this.fx & Effects.CULL_OFF) == Effects.CULL_OFF) {
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		} else {
			GLES20.glEnable(GLES20.GL_CULL_FACE);
			GLES20.glCullFace(GLES20.GL_BACK);
			GLES20.glFrontFace(GLES20.GL_CCW);
		}
	}
}