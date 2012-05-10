package com.llama3d.object;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import android.opengl.GLES20;

public class ObjectBuffer {

    // ===================================================================
    // Fields
    // ===================================================================

    private int[] mainBufferVBO = new int[1];
    private int[] mainBufferIBO = new int[1];

    // ===================================================================
    // Constructors
    // ===================================================================

    public ObjectBuffer(FloatBuffer arrayVBO, IntBuffer arrayIBO) {
        GLES20.glGenBuffers(1, this.mainBufferVBO, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.mainBufferVBO[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, arrayVBO.capacity() * 4, arrayVBO, GLES20.GL_STATIC_DRAW);

        GLES20.glGenBuffers(1, this.mainBufferIBO, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, this.mainBufferIBO[0]);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, arrayIBO.capacity() * 4, arrayIBO, GLES20.GL_STATIC_DRAW);
    }
    // ===================================================================
    // Methods
    // ===================================================================

}
