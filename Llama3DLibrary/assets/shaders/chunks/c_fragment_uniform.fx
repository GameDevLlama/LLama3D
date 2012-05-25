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