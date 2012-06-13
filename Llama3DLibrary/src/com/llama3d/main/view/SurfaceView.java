package com.llama3d.main.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

import com.llama3d.elements.touch.PointerManager;
import com.llama3d.object.graphics.image.Image;
import com.llama3d.object.graphics.image.ImageTexture;
import com.llama3d.object.graphics.texture.Texture;
import com.llama3d.object.mesh.Mesh;
import com.llama3d.object.mesh.Surface;
import com.llama3d.scene.Scene;
import com.llama3d.scene.SceneCache;

public class SurfaceView extends GLSurfaceView implements OnTouchListener, SurfaceHolder.Callback {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static List<ImageTexture> temporaryTextures = new ArrayList<ImageTexture>();

	// ===================================================================
	// Fields
	// ===================================================================

	private SurfaceRenderer renderer = null;
	SurfaceHolder holder;

	// ===================================================================
	// Constructors
	// ===================================================================

	public SurfaceView(Context context) {
		super(context);
		// this.setDisplaySize();
		// holder = getHolder();
		// holder.addCallback(this);
		// holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);

		// this.setSaveEnabled(true);
		// this.setId(1);

		// ======== TouchElement ========
		this.setOnTouchListener(this);
		// ======== Notify Waiting Main Thread ========
	}

	// ===================================================================
	// Methods
	// ===================================================================

	public void createRenderer() {
		this.setEGLContextClientVersion(2);
		// ======== Creates New GL SurfaceRenderer ========
		this.renderer = new SurfaceRenderer();
		this.setRenderer(this.renderer);
		this.setRenderMode(SurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	public void reloadRenderer() {
		for (Scene scene : SceneCache.scenes) {
			// ======== Reload Geometries ========
			for (Mesh mesh : scene.meshes) {
				for (Surface surface : mesh.geometry.surfaces) {
					surface.setBuffer();
				}
			}
			// ======== Reload Textures ========
			for (Texture texture : scene.textures) {
				texture = Texture.load(texture.path);
			}
			// ======== Reload Images ========
			for (Image image : scene.images) {
				if (!temporaryTextures.contains(image.imagetexture)) {
					image.imagetexture.reload();
					temporaryTextures.add(image.imagetexture);
				}
				image.setVBOBuffer();
			}
			temporaryTextures.clear();
		}
	}

	// ======== OnTouchEvent ========
	public boolean onTouch(View arg0, MotionEvent mainMotionEvent) {
		PointerManager.mainTouchElement.onTouchEvent(mainMotionEvent);
		return true;
	}

}