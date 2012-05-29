gl_Position = modelViewProjection * vec4(Vertex,1.0);
depth = gl_Position.z;