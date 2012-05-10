//vertexshader
precision lowp float;

uniform mat4 projectionMatrix;
uniform float uAngle;
uniform vec2 uScale;
uniform vec2 uPosition;

attribute vec4 Vertex;
attribute vec2 MultiTexCoord;
attribute vec3 Color;

varying vec2 VARYING_UV_TEXTURE;
vec4 position = vec4(0.0, 0.0, -1.0, 1.0);

void main() {
	position.x = Vertex.x * cos(uAngle) * uScale.x + Vertex.y * -sin(uAngle) * uScale.y + uPosition.x;
	position.y = Vertex.x * sin(uAngle) * uScale.x + Vertex.y * cos(uAngle) * uScale.y + uPosition.y;
	gl_Position = projectionMatrix * position;
	VARYING_UV_TEXTURE = MultiTexCoord;
}