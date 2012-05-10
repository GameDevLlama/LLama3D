package com.llama3d.object.mesh.geometry.fileloader;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.llama3d.main.assets.AssetFactory;
import com.llama3d.math.vector.Vector2;
import com.llama3d.math.vector.Vector3;
import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;
import com.llama3d.object.mesh.geometry.Geometry;

/**
 * © 2011 Christian Ringshofer
 * 
 * @author Christian Ringshofer
 * @since 23:23:00 - 30.01.2012
 * @version 1.0
 */
public class WaveFront {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static Geometry geometry;
	private static Surface surface;
	private static Vertex[] vertex = new Vertex[4];

	private static Vector3 position = null, normal = null;
	private static Vector2 uvset = null;

	private static float[] rawData = new float[3];
	private static String[] rawDataString;
	private static String[] faceIndexes;
	private static String[] faceIndex;

	private static int catchedVertices = 0;

	private static List<Vector3> positions = new ArrayList<Vector3>();
	private static List<Vector3> normals = new ArrayList<Vector3>();
	private static List<Vector2> uvsets = new ArrayList<Vector2>();

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static Geometry load(String modelPath) {
		return WaveFront.load(modelPath, false);
	}

	public static Geometry load(String modelPath, boolean libraryIntern) {
		if (modelPath.endsWith(".obj")) {
			try {
				// ======== Preparing Systemdata ========
				long milliseconds = System.currentTimeMillis();
				// ======== Preparing Geometrydata ========
				WaveFront.positions.clear();
				WaveFront.normals.clear();
				WaveFront.uvsets.clear();
				// ===================================================================
				// Geometrydata / Vertex- / Face- Lists
				// ===================================================================
				WaveFront.geometry = new Geometry();
				WaveFront.catchedVertices = 0;
				// ======== Reading File ========
				String[] rawModel = AssetFactory.loadAssetAsSingleLines(modelPath, libraryIntern);
				for (String rawLine : rawModel) {
					WaveFront.decodeStructure(rawLine);
				}
				// ======== Resulting Systemdata ========
				long tookMilliseconds = System.currentTimeMillis() - milliseconds;
				Log.d("WaveFront", "loaded wavefront; time: " + tookMilliseconds + " milliseconds. [" + modelPath + "]");
			} catch (Exception e) {

				e.printStackTrace();
				Log.w("WaveFront", "could not get some vertices. [" + WaveFront.catchedVertices + "] [catched it :)]");
				Log.e("WaveFront", "could not load wavefront. [.obj]");

			}
		}
		return WaveFront.geometry;
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	private static void decodeStructure(String rawLine) {

		// ===================================================================
		// Final Fields
		// ===================================================================

		// ======== Char Values ========
		final char VERTEX = 118;
		final char NORMAL = 110;
		final char TEXTURE = 116;
		final char USE = 117;
		final char FACE = 102;
		// ======== Prefixes ========
		final String USEMATERIAL = "usemtl";

		// ===================================================================
		// Main Decoding
		// ===================================================================

		switch (rawLine.charAt(0)) {
		// ======== Reading Vertexdata ========
		case VERTEX:
			// ======== Reading Position- / Normal- / UV- Data ========
			switch (rawLine.charAt(1)) {
			case NORMAL:
				WaveFront.decodeNormal(rawLine.substring(2));
				break;
			case TEXTURE:
				WaveFront.decodeTexture(rawLine.substring(2));
				break;
			default:
				WaveFront.decodePosition(rawLine.substring(2));
				break;
			}
			break;
		// ======== Reading Materialdata ========
		case USE:
			if (rawLine.startsWith(USEMATERIAL)) {
				WaveFront.surface = new Surface();
				WaveFront.geometry.surfaces.add(WaveFront.surface);
			}
			break;
		// ======== Reading Facedata ========
		case FACE:
			// ======== Checking current surface ========
			if (WaveFront.surface == null) {
				WaveFront.surface = new Surface();
				WaveFront.geometry.surfaces.add(WaveFront.surface);
			}
			WaveFront.decodeFace(rawLine.substring(2));
			break;
		}
	}

	// ===================================================================
	// Position Decoding
	// ===================================================================
	private static void decodePosition(String rawLine) {
		WaveFront.decodeValues(rawLine);
		Vector3 position = new Vector3(WaveFront.rawData[0], WaveFront.rawData[1], WaveFront.rawData[2]);
		WaveFront.positions.add(position);
	}

	// ===================================================================
	// Normal Decoding
	// ===================================================================
	private static void decodeNormal(String rawLine) {
		WaveFront.decodeValues(rawLine);
		Vector3 normal = new Vector3(WaveFront.rawData[0], WaveFront.rawData[1], WaveFront.rawData[2]);
		WaveFront.normals.add(normal);
	}

	// ===================================================================
	// Texture Decoding
	// ===================================================================
	private static void decodeTexture(String rawLine) {
		WaveFront.decodeValues(rawLine);
		Vector2 uvset = new Vector2(WaveFront.rawData[0], 1.0f - WaveFront.rawData[1]);
		WaveFront.uvsets.add(uvset);
	}

	// ===================================================================
	// Face Decoding
	// ===================================================================
	private static void decodeFace(String rawLine) {
		// ======== Reading Subdata ========
		WaveFront.faceIndexes = rawLine.trim().split(" ");
		switch (WaveFront.faceIndexes.length) {
		case 3:
			WaveFront.vertex[0] = WaveFront.decodeVertex(WaveFront.faceIndexes[0]);
			WaveFront.vertex[1] = WaveFront.decodeVertex(WaveFront.faceIndexes[1]);
			WaveFront.vertex[2] = WaveFront.decodeVertex(WaveFront.faceIndexes[2]);
			WaveFront.surface.addTriangle(WaveFront.vertex[0], WaveFront.vertex[1], WaveFront.vertex[2]);
			break;
		case 4:
			WaveFront.vertex[0] = WaveFront.decodeVertex(WaveFront.faceIndexes[0]);
			WaveFront.vertex[1] = WaveFront.decodeVertex(WaveFront.faceIndexes[1]);
			WaveFront.vertex[2] = WaveFront.decodeVertex(WaveFront.faceIndexes[2]);
			WaveFront.vertex[3] = WaveFront.decodeVertex(WaveFront.faceIndexes[3]);
			WaveFront.surface.addQuad(WaveFront.vertex[0], WaveFront.vertex[1], WaveFront.vertex[2], WaveFront.vertex[3]);
			break;
		}
	}

	// ===================================================================
	// Raw Float Value Decoding
	// ===================================================================
	private static void decodeValues(String rawLine) {
		// ======== Reading Subdata ========
		WaveFront.rawDataString = rawLine.trim().split(" ");
		WaveFront.rawData[0] = Float.parseFloat(WaveFront.rawDataString[0]);
		WaveFront.rawData[1] = Float.parseFloat(WaveFront.rawDataString[1]);
		if (WaveFront.rawDataString.length == 3) {
			WaveFront.rawData[2] = Float.parseFloat(WaveFront.rawDataString[2]);
		}
	}

	// ===================================================================
	// Raw Vertex Decoding
	// ===================================================================
	private static Vertex decodeVertex(String rawComponents) {
		// ======== Reading Subdata ========
		WaveFront.position = null;
		WaveFront.normal = null;
		WaveFront.uvset = null;
		// ======== Reading Subdata ========
		WaveFront.faceIndex = rawComponents.split("/");
		// ======== Try Getting Vertices ========
		try {
			WaveFront.position = WaveFront.positions.get((int) (Integer.parseInt(WaveFront.faceIndex[0]) - 1));
			WaveFront.uvset = WaveFront.uvsets.get((int) (Integer.parseInt(WaveFront.faceIndex[1]) - 1));
			WaveFront.normal = WaveFront.normals.get((int) (Integer.parseInt(WaveFront.faceIndex[2]) - 1));
		} catch (Exception mainException) {
			WaveFront.catchedVertices++;
		}
		// ======== Check Missing Vectors ========
		if (WaveFront.position == null) {
			WaveFront.position = new Vector3();
		}
		// ======== Returning Vertex ========
		return new Vertex(WaveFront.position, WaveFront.normal, WaveFront.uvset);
	}
}
