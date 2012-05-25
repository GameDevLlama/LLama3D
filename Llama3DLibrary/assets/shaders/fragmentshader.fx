// ===================================================================
// Constants
// ===================================================================

const int MAX_POINT_LIGHTS = LL.MAX_POINT_LIGHTS;
const int MAX_DIR_LIGHTS = LL.MAX_DIR_LIGHTS;
const int MAX_TEXTURES = LL.MAX_TEXTURES;

// ===================================================================
// Variable Precisions
// ===================================================================

precision mediump float;

// ===================================================================
// Varyings
// ===================================================================

varying vec2 VARYING_UV_TEXTURE;
varying vec3 VARYING_NORMAL;
varying vec3 VARYING_POSITION;
varying float depth;

// ===================================================================
// Uniforms
// ===================================================================

uniform vec4 UNIFORM_FOG_COLOR;
uniform float fogNear;
uniform float fogFar;

LL.import("c_fragment_dirlight_p1.fx");
LL.import("c_fragment_pointlight_p1.fx");
LL.import("c_fragment_material_p1.fx");

uniform bool cameraFog;
uniform vec3 camera;

LL.import("c_fragment_texture_p1.fx");

uniform vec4 ambient;

mediump vec3 R;
mediump float F;

mediump vec3 N = normalize(VARYING_NORMAL);
mediump vec3 V = normalize(VARYING_POSITION - camera);

mediump vec4 diff = vec4(0.0, 0.0, 0.0, 0.0);
mediump vec4 spec = vec4(0.0, 0.0, 0.0, 0.0);

highp vec4 colortex;

LL.import("c_fragment_fog_p1.fx");

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

void main() {

	LL.import("c_fragment_texture_p2.fx");
	LL.import("c_fragment_autofade_p2.fx");
	LL.import("c_fragment_specular_p2.fx");
	LL.import("c_fragment_fog_p2.fx");
	LL.import("c_fragment_final_p2.fx");

}