precision mediump float;

attribute vec3 Vertex;
attribute vec3 Normal;
attribute vec2 MultiTexCoord;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;

varying vec2 VARYING_UV_TEXTURE;
varying vec3 VARYING_NORMAL;
varying vec3 VARYING_POSITION;

void main() {
	gl_Position = projectionMatrix * modelMatrix * vec4(Vertex,1.0);
	VARYING_POSITION = vec3(modelMatrix * vec4(Vertex,1.0));
	VARYING_UV_TEXTURE = MultiTexCoord;
	VARYING_NORMAL = vec3(modelMatrix * vec4(Vertex + Normal,1.0));
}