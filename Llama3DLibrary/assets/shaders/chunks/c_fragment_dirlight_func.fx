// ======== Light Via Directional Light ========
vec4 calcDirLight(in directionallight dirLight,in material mat,in vec3 normal){
	return mat.diff * dirLight.color * max(dot(dirLight.direction, normal), 0.0);
}