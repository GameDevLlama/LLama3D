precision mediump float;

attribute vec3 Vertex;
attribute vec3 Color;
attribute vec3 Normal;
attribute vec2 MultiTexCoord;

uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
//uniform mat4 rotationMatrix;
uniform mat4 projectionMatrix;

varying vec2 VARYING_UV_TEXTURE;
varying vec3 VARYING_NORMAL;
varying vec3 VARYING_POSITION;
varying float depth;

mat4 modelViewProjection = projectionMatrix * viewMatrix * modelMatrix;
mat3 rotationMatrix = mat3(modelMatrix);

void main() {
	gl_Position = modelViewProjection * vec4(Vertex,1.0);
	VARYING_POSITION = vec3(modelMatrix * vec4(Vertex,1.0));
	depth = gl_Position.z;
	VARYING_UV_TEXTURE = MultiTexCoord;
	VARYING_NORMAL = rotationMatrix * Normal;
}