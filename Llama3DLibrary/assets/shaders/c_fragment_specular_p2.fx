// ===================================================================
// Specular Calculation
// ===================================================================
for (int i = 0; i < MAX_DIR_LIGHTS; i++) {
	diff += mat.diff * dl[i].color * max(dot(dl[i].direction, N), 0.0);
	
	//R = reflect(dl[i].direction, N);
	//spec += mat.spec * dl[i].specular * pow(max(dot(R, V), 0.0), mat.shine);
}