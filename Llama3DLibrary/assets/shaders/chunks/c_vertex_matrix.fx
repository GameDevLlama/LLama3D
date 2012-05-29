mat4 modelViewProjection = projectionMatrix * viewMatrix * modelMatrix;
mat3 rotationMatrix = mat3(modelMatrix);