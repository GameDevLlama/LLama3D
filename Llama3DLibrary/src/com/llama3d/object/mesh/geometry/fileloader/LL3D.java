package com.llama3d.object.mesh.geometry.fileloader;

import java.io.DataInputStream;

import android.util.Log;

import com.llama3d.main.assets.AssetFactory;
import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;
import com.llama3d.object.mesh.geometry.Geometry;

public class LL3D {

	private static Geometry geometry;
	private static Surface surface;

	public static Geometry load(String modelPath) {
		return LL3D.load(modelPath, false);
	}

	public static Geometry load(String modelPath, boolean libraryIntern) {

		// ======== TimeStamp ========
		long nano = System.nanoTime();

		// ======== Binaryloader ========
		DataInputStream mainFileStream = AssetFactory.loadAssetAsDataStream(modelPath);

		// ======== Prepare Data ========
		int faces = 0;
		float x = 0, y = 0, z = 0;
		float nx = 0, ny = 0, nz = 0;
		Vertex[] v = new Vertex[3];
		// ===================================================================
		// Geometrydata
		// ===================================================================
		LL3D.geometry = new Geometry();
		LL3D.surface = new Surface();
		LL3D.geometry.surfaces.add(LL3D.surface);

		// ======== Read Data ========
		if (mainFileStream != null) {
			try {
				faces = mainFileStream.readInt();
				Log.i("TEST", "Faces: " + faces);
				for (int u = 0; u < faces; u++) {
					for (int i = 0; i < 3; i++) {
						x = mainFileStream.readFloat();
						y = mainFileStream.readFloat();
						z = mainFileStream.readFloat();
						// nx = mainFileStream.readFloat();
						// ny = mainFileStream.readFloat();
						// nz = mainFileStream.readFloat();
						v[i] = new Vertex(x, y, z, nx, ny, nz, 0, 0, 0, 0, 0);
						LL3D.surface.vertices.add(v[i]);
					}
					LL3D.surface.addTriangle(v[0], v[1], v[2]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Log.i("*.ll3d Loader", modelPath + ": " + (System.nanoTime() - nano) / 1000000 + " ms");

		return LL3D.geometry;
	}
}
