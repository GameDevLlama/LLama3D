package com.llama3d.object.physics;

import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.llama3d.object.Object3D;

public class MotionBody extends MotionState {

    // ===================================================================
    // Fields
    // ===================================================================

    Object3D object = null;

    // ===================================================================
    // Constructors
    // ===================================================================

    public MotionBody(Object3D object) {
        this.object = object;
    }

    // ===================================================================
    // Override Methods
    // ===================================================================

    @Override
    public Transform getWorldTransform(Transform transformation) {
        this.object.dynamicBody.setWorldTransform(transformation);
        transformation.getOpenGLMatrix(this.object.translationMatrix);
        return transformation;
    }

    @Override
    public void setWorldTransform(Transform transformation) {
        transformation.getOpenGLMatrix(this.object.translationMatrix);
    }

}
