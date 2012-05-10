precision mediump float;
//varyings
varying vec2 VARYING_UV_TEXTURE;
varying vec3 VARYING_NORMAL;
varying vec3 VARYING_POSITION;

uniform struct material {
	vec4 diff;
	float elum;
} mat;

uniform sampler2D uTexture;

mediump vec3 N = normalize(VARYING_NORMAL - VARYING_POSITION);
mediump vec4 diff  = vec4(0.0,0.0,0.0,1.0);
mediump vec3 L = normalize(vec3(-0.9,0.9,0.9));

void main() {
	
	vec4 colortex = texture2D(uTexture,vec2( VARYING_UV_TEXTURE.x, VARYING_UV_TEXTURE.y ));
	diff = mat.diff * max(dot(L,N),0.0);
	gl_FragColor = colortex * (mat.diff * diff + mat.diff * mat.elum);
	
}