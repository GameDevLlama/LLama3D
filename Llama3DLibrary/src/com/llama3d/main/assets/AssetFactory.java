package com.llama3d.main.assets;

import java.io.DataInputStream;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.util.Log;

public class AssetFactory {

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	/**
	 * Loads the asset as an datainputstream.
	 * 
	 * @param assetPath
	 *            - relative path of asset.
	 * @return
	 */
	public static synchronized DataInputStream loadAssetAsDataStream(String assetPath) {
		return AssetFactory.loadAssetAsDataStream(assetPath, false);
	}

	public static synchronized DataInputStream loadAssetAsDataStream(String assetPath, boolean libraryIntern) {
		return new DataInputStream(AssetFactory.loadAssetAsStream(assetPath, libraryIntern));
	}

	/**
	 * Loads the asset as an inputstream.
	 * 
	 * @param assetPath
	 *            - relative path of asset.
	 * @return
	 */
	public static synchronized InputStream loadAssetAsStream(String assetPath) {
		return AssetFactory.loadAssetAsStream(assetPath, false);
	}

	public static synchronized InputStream loadAssetAsStream(String assetPath, boolean libraryIntern) {
		try {

			// Log.d("AssetFactory", "loaded asset. [" + assetPath +
			// "];[library/" + libraryIntern + "]");
			// ======== Intern + Extern Loader ========
			if (libraryIntern == false) {
				return AssetCache.mainAssets.open(assetPath, AssetManager.ACCESS_STREAMING);
			} else {
				return AssetCache.class.getClassLoader().getResource(assetPath).openStream();
			}

		} catch (Exception mainException) {

			mainException.printStackTrace();
			Log.w("AssetFactory", "could not load asset. [" + assetPath + "];[library/" + libraryIntern + "]");
			return null;

		}
	}

	/**
	 * Loads the asset as a complete raw string.
	 * 
	 * @param assetPath
	 *            - relative path of asset.
	 * @return
	 */
	public static synchronized String loadAssetAsString(String assetPath) {
		return AssetFactory.loadAssetAsString(assetPath, false);
	}

	public static synchronized String loadAssetAsString(String assetPath, boolean libraryIntern) {
		try {

			// ======== Stringloader ========
			InputStream mainFileStream = AssetFactory.loadAssetAsStream(assetPath, libraryIntern);
			byte[] mainByteStream = new byte[mainFileStream.available()];
			mainFileStream.read(mainByteStream);
			return new String(mainByteStream);

		} catch (Exception mainException) {

			mainException.printStackTrace();
			Log.w("AssetFactory", "could not split asset.");
			return null;

		}
	}

	/**
	 * Loads the asset as an array of lines.
	 * 
	 * @param assetPath
	 *            - relative path of asset.
	 * @return
	 */
	public static synchronized String[] loadAssetAsSingleLines(String assetPath) {
		return AssetFactory.loadAssetAsSingleLines(assetPath, false);
	}

	public static synchronized String[] loadAssetAsSingleLines(String assetPath, boolean libraryIntern) {

		// ======== String Splitter ========
		return AssetFactory.loadAssetAsString(assetPath, libraryIntern).split("\\n");

	}

}
