package com.llama3d.object.graphics.image.text;

import com.llama3d.main.buffer.FloatBufferFactory;
import com.llama3d.object.graphics.image.ImageBase;
import com.llama3d.object.graphics.image.text.font.Font;
import com.llama3d.scene.SceneCache;

public class Text extends ImageBase {

    private static int maxChars = 100;
    private String text = "";
    private float length;
    public int curChars;

    public Text(String text) {
        super();
        this.vertexBuffer = FloatBufferFactory.create(6 * 5);
        this.text(text);
    }

    public void text(String text) {
        if (this.text.compareTo(text) != 0) {
            this.text = text;
            this.length = 0f;
            this.curChars = this.text.length();
            for (int i = 0; i < this.curChars; i++) {
                this.putChar(this.text.charAt(i), i);
            }
            for (int i = this.curChars; i < Text.maxChars; i++) {
                this.putZero(i);
            }
        }
    }

    private void putChar(int chr, int position) {
        this.vertexBuffer.position(position * 6 * 4);

        Font setFont = SceneCache.activeScene.font;

        float characterSizeInPixel = 32f;// 0.0745f;
        float characterWidth = (float) (setFont.settings.charWidth[chr] + 2) / (float) setFont.settings.tileWidth;
        float characterHeight = (float) (setFont.settings.tileCharHeight) / (float) setFont.settings.tileHeight;
        float offsetX = (float) setFont.settings.tileOffsetX / (float) setFont.settings.tileWidth;
        float offsetY = (float) setFont.settings.tileOffsetY / (float) setFont.settings.tileHeight;
        float ratio = 1f / 16f;
        float uvX = (float) (chr % 16) * ratio + offsetX * ratio;
        float uvY = 1.0f - (float) ((chr / 16) * ratio + offsetY * ratio);

        /*
         * 1---3/5 | // | | // | 2/6---7
         */

        // model
        this.vertexBuffer.put(this.length);// 1
        this.vertexBuffer.put(0f);
        this.vertexBuffer.put(+uvX);
        this.vertexBuffer.put(+1f - uvY);

        this.vertexBuffer.put(this.length);// 2
        this.vertexBuffer.put(-characterHeight * characterSizeInPixel);
        this.vertexBuffer.put(+uvX);
        this.vertexBuffer.put(+1f - uvY + ratio * characterHeight);

        this.vertexBuffer.put(this.length + characterWidth * characterSizeInPixel);// 3
        this.vertexBuffer.put(0f);
        this.vertexBuffer.put(+uvX + ratio * characterWidth);
        this.vertexBuffer.put(+1f - uvY);

        this.vertexBuffer.put(this.length + characterWidth * characterSizeInPixel);// 5
        this.vertexBuffer.put(0f);
        this.vertexBuffer.put(+uvX + ratio * characterWidth);
        this.vertexBuffer.put(+1f - uvY);

        this.vertexBuffer.put(this.length);// 6
        this.vertexBuffer.put(-characterHeight * characterSizeInPixel);
        this.vertexBuffer.put(+uvX);
        this.vertexBuffer.put(+1f - uvY + ratio * characterHeight);

        this.vertexBuffer.put(this.length + characterWidth * characterSizeInPixel);// 7
        this.vertexBuffer.put(-characterHeight * characterSizeInPixel);
        this.vertexBuffer.put(+uvX + ratio * characterWidth);
        this.vertexBuffer.put(+1f - uvY + ratio * characterHeight);
        // uv-coord
        this.length += characterWidth * characterSizeInPixel;
    }

    private void putZero(int position) {
        for (int i = 0; i < 18; i++) {
            this.vertexBuffer.put(0);
        }
        for (int i = 0; i < 12; i++) {
            this.vertexBuffer.put(0);
        }
    }
}
