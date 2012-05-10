package com.llama3d.object.graphics.image;

import com.llama3d.object.graphics.texture.TextureUnit;

public class ImageTexture extends TextureUnit {

	protected ImageTexture(String imagePath, boolean libraryIntern) {
		super(imagePath, Image.antiAliasing, libraryIntern);
		// TODO Auto-generated constructor stub
	}

	public static ImageTexture load(String imagePath, boolean libraryIntern) {
		// search for existing image

		// for (LLImageTexture imagetexture : imagetextures) {
		// if (imagetexture.imagePath == imagePath) {
		// // if image was found -> return imagetexture
		// return imagetexture;
		// }
		// }

		// otherwise load new imagetexture and return
		ImageTexture imagetexture = new ImageTexture(imagePath, libraryIntern);
		return imagetexture;
	}
}
