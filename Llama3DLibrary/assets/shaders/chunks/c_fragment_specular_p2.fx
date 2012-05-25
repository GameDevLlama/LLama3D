// ===================================================================
// Specular Calculation
// ===================================================================
for (int i = 0; i < MAX_DIR_LIGHTS; i++) {
	diff += mat.diff * dl[i].color * max(dot(dl[i].direction, N), 0.0);
	
	//R = reflect(dl[i].direction, N);
	//spec += mat.spec * dl[i].specular * pow(max(dot(R, V), 0.0), mat.shine);
}

//void pointLights(in pointlight pl,in vec4 N,in vec3 V,inout vec4 diff,inout vec4 spec) {
//	if(pl.active == true){
//		vec3 L = normalize(pl.position-position);
//    	float D = distance(position.xyz,pl.position.xyz);
//		float attenuation = clamp(1.0 - D/pl.range.x,0.0,1.0);
//float F = max(dot(L,N),0.0) * attenuation;
//	  	vec3 R = -normalize(reflect(L,N));
//    	diff += mat.diff * vec4(pl.color,1.0) * attenuation;
//	spec += vec4(attenuation * pow(max(dot(R,V),0.0),mat.shine) * mat.spec * pl.specular,1.0);
//	}
//}