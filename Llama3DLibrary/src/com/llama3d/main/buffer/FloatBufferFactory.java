package com.llama3d.main.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class FloatBufferFactory {

    // ===================================================================
    // Public Static Methods
    // ===================================================================

    public static FloatBuffer create(int capacity) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capacity * 4);// faceVertices*vectorSize*4ByteSize
        byteBuffer.order(ByteOrder.nativeOrder());
        return byteBuffer.asFloatBuffer();
    }

}
