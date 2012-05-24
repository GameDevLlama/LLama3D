uniform struct pointlight {
	bool active;
	vec3 range;
	vec3 color;
	vec3 position;
	vec3 specular;
} pl[MAX_POINT_LIGHTS + 1];