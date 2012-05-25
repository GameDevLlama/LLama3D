// ======== Light Via Directional Light ========
vec4 calcDirLight(in directionallight dirLight,in material mat,in vec3 normal){
	return mat.diff * dl[i].color * max(dot(dl[i].direction, N), 0.0);;
}