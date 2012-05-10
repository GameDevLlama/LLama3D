package com.llama3d.opengl;

import android.opengl.GLES20;

public class OpenGLSettings {
    // ===================================================================
    // OpenGL Fields
    // ===================================================================

    private boolean BLEND_ENABLE;
    private boolean BLEND_ALPHA_ENABLE;
    private int BLEND_S_FACTOR;
    private int BLEND_D_FACTOR;

    private boolean CULLFACE_ENABLE;
    private int CULLFACE_SIDE;
    private int CULLFACE_DIRECTION;

    private boolean DEPTH_TEST_ENABLE;
    private int DEPTH_TEST_COLOR_BUFFER_BIT;
    private int DEPTH_TEST_DEPTH_BUFFER_BIT;
    private int DEPTH_TEST_FUNCTION;
    private float DEPTH_TEST_DEPTH;

    // ===================================================================
    // Static Fields
    // ===================================================================

    private static final int BLEND_SOLID = 0x00000301;
    private static final int BLEND_ALPHA = 0x00000302;
    private static final int BLEND_ADD = 0x00000303;
    private static final int BLEND_SUBTRACT = 0x00000304;
    private static final int BLEND_NO_BLEND = 0x00000305;

    private static final int CULLING_BACKFACE = 0x00000101;
    private static final int CULLING_FRONTFACE = 0x00000102;
    private static final int CULLING_NO_CULLING = 0x00000103;

    private static final int DEPTH_TEST_NORMAL = 0x00000201;
    private static final int DEPTH_TEST_DRAW_ALWAYS = 0x00000202;
    private static final int DEPTH_TEST_NO_DEPTH_TEST = 0x00000203;

    // ===================================================================
    // Fields
    // ===================================================================

    // ===================================================================
    // Constructors
    // ===================================================================

    public OpenGLSettings() {
        this.defaultSettings();
    }

    // ===================================================================
    // Settings Methods
    // ===================================================================

    protected void defaultSettings() {
        this.setBlending(BLEND_ALPHA);
        this.setCulling(CULLING_BACKFACE);
        this.setDepthTest(DEPTH_TEST_NORMAL);

        // GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        // GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        GLES20.glEnable(GLES20.GL_ARRAY_BUFFER);
    }

    private void setBlending(int modeBlend) {
        switch (modeBlend) {

        case BLEND_SOLID:
            this.BLEND_ENABLE = true;
            this.BLEND_ALPHA_ENABLE = false;
            break;

        case BLEND_ALPHA:
            this.BLEND_ENABLE = true;
            this.BLEND_ALPHA_ENABLE = true;
            this.BLEND_S_FACTOR = GLES20.GL_ONE;
            this.BLEND_D_FACTOR = GLES20.GL_ONE_MINUS_SRC_ALPHA;
            break;

        case BLEND_ADD:
            this.BLEND_ENABLE = true;
            this.BLEND_ALPHA_ENABLE = true;
            this.BLEND_S_FACTOR = GLES20.GL_ONE;
            this.BLEND_D_FACTOR = GLES20.GL_ONE;
            break;

        case BLEND_SUBTRACT:
            this.BLEND_ENABLE = true;
            this.BLEND_ALPHA_ENABLE = true;
            this.BLEND_S_FACTOR = GLES20.GL_DST_COLOR;
            this.BLEND_D_FACTOR = GLES20.GL_SRC_COLOR;
            break;

        case BLEND_NO_BLEND:
            this.BLEND_ENABLE = false;
            break;

        }

        this.doBlending();

    }

    private void setCulling(int modeCulling) {
        switch (modeCulling) {

        case CULLING_BACKFACE:
            this.CULLFACE_ENABLE = true;
            this.CULLFACE_SIDE = GLES20.GL_BACK;
            this.CULLFACE_DIRECTION = GLES20.GL_CCW;
            break;

        case CULLING_FRONTFACE:
            this.CULLFACE_ENABLE = true;
            this.CULLFACE_SIDE = GLES20.GL_FRONT;
            this.CULLFACE_DIRECTION = GLES20.GL_CCW;
            break;

        case CULLING_NO_CULLING:
            this.CULLFACE_ENABLE = false;
            break;

        }

        this.doCulling();

    }

    private void setDepthTest(int modeDepthTest) {
        switch (modeDepthTest) {

        case DEPTH_TEST_NORMAL:
            this.DEPTH_TEST_ENABLE = true;
            this.DEPTH_TEST_COLOR_BUFFER_BIT = GLES20.GL_COLOR_BUFFER_BIT;
            this.DEPTH_TEST_DEPTH_BUFFER_BIT = GLES20.GL_DEPTH_BUFFER_BIT;
            this.DEPTH_TEST_FUNCTION = GLES20.GL_LESS;
            this.DEPTH_TEST_DEPTH = 1f;
            break;

        case DEPTH_TEST_DRAW_ALWAYS:
            this.DEPTH_TEST_ENABLE = true;
            this.DEPTH_TEST_COLOR_BUFFER_BIT = GLES20.GL_COLOR_BUFFER_BIT;
            this.DEPTH_TEST_DEPTH_BUFFER_BIT = GLES20.GL_DEPTH_BUFFER_BIT;
            this.DEPTH_TEST_FUNCTION = GLES20.GL_ALWAYS;
            this.DEPTH_TEST_DEPTH = 1f;
            break;

        case DEPTH_TEST_NO_DEPTH_TEST:
            this.DEPTH_TEST_ENABLE = false;
            this.DEPTH_TEST_FUNCTION = GLES20.GL_NEVER;
            break;

        }

        this.doDepthTest();

    }

    // ===================================================================
    // Methods
    // ===================================================================

    public void doSettings() {
        this.doBlending();
        this.doCulling();
        this.doDepthTest();
    }

    private void doBlending() {
        this.glEnable(this.BLEND_ENABLE, GLES20.GL_BLEND);
        this.glEnable(this.BLEND_ALPHA_ENABLE, GLES20.GL_ALPHA);
        GLES20.glBlendFunc(this.BLEND_S_FACTOR, this.BLEND_D_FACTOR);
    }

    private void doCulling() {
        this.glEnable(this.CULLFACE_ENABLE, GLES20.GL_CULL_FACE);
        GLES20.glCullFace(this.CULLFACE_SIDE);
        GLES20.glFrontFace(this.CULLFACE_DIRECTION);
    }

    private void doDepthTest() {
        this.glEnable(this.DEPTH_TEST_ENABLE, GLES20.GL_DEPTH_TEST);
        GLES20.glClear(this.DEPTH_TEST_COLOR_BUFFER_BIT | this.DEPTH_TEST_DEPTH_BUFFER_BIT);
        GLES20.glDepthFunc(this.DEPTH_TEST_FUNCTION);
        GLES20.glClearDepthf(this.DEPTH_TEST_DEPTH);
    }

    private void glEnable(boolean GL_ENABLE, int GL_CONSTANT) {
        if (GL_ENABLE) {
            GLES20.glEnable(GL_CONSTANT);
        } else {
            GLES20.glDisable(GL_CONSTANT);
        }
    }
}