// ===================================================================
// Directional Lights
// ===================================================================

uniform struct directionallight {
	bool active;
	vec3 direction;
	vec4 color;
	vec4 specular;
} dl[MAX_DIR_LIGHTS + 1];