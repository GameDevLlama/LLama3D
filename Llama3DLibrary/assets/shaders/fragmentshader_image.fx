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
	colorFactor = vec4(mat.diff, mat.alpha);
	gl_FragColor = colortex * colorFactor;
	
}