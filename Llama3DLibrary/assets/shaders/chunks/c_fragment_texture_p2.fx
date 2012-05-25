// ===================================================================
// TextureColor
// ===================================================================
if (uTextureExists == true) {
	colortex = texture2D(uTexture, VARYING_UV_TEXTURE);
} else {
	colortex = vec4(1.0, 1.0, 1.0, 1.0);
}