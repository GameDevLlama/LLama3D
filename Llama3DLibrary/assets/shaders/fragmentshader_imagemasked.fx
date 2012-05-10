// ===================================================================
// Float Precisions
// ===================================================================

precision lowp float;

// ===================================================================
// Varyings
// ===================================================================

varying vec2 VARYING_UV_TEXTURE;

// ===================================================================
// Uniforms
// ===================================================================

uniform bool uMasked;
uniform ivec3 uMaskColor;
uniform struct material {

	vec3 diff;
	float alpha;
	
} mat;

// ===================================================================
// Locals
// ===================================================================

vec4 colorFactor;

// ===================================================================
// Textures
// ===================================================================

uniform sampler2D uTexture;

// ===================================================================
// Functions
// ===================================================================

void main() {
	
	vec4 colortex = texture2D(uTexture, VARYING_UV_TEXTURE);
	ivec3 colorCompare = ivec3(colortex.r * 255.0, colortex.g * 255.0, colortex.b * 255.0);
	if(all(equal(uMaskColor, colorCompare)) && uMasked == true){
		colorFactor = vec4(0.0, 0.0, 0.0, 0.0);
	}else {
		colorFactor = vec4(mat.diff, mat.alpha);
	}
	gl_FragColor = colortex * colorFactor;
	
}