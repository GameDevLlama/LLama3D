package com.llama3d.object.graphics.image.text.font;

import com.llama3d.main.assets.AssetFactory;

public class FontSettings {

    // ===================================================================
    // Fields
    // ===================================================================

    public int tileOffsetX;
    public int tileOffsetY;

    public int tileWidth = 32;
    public int tileHeight = 32;

    public int tileCharWidth = 32;
    public int tileCharHeight = 32;

    public int[] charWidth = new int[256];

    // ===================================================================
    // Constructors
    // ===================================================================

    public FontSettings(String settingsPath) {
        for (int i = 0; i < 256; i++) {
            this.charWidth[i] = 32;
        }
        try {
            String[] lines = AssetFactory.loadAssetAsSingleLines(settingsPath);

            int position;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() > 0) {
                    position = lines[i].indexOf("=");
                    if (position > -1) {
                        if (lines[i].startsWith("FrameWidth")) {
                            this.tileWidth = Integer.parseInt(lines[i].substring(position + 1).trim());
                        } else if (lines[i].startsWith("FrameHeight")) {
                            this.tileHeight = Integer.parseInt(lines[i].substring(position + 1).trim());
                        } else if (lines[i].startsWith("Width")) {
                            this.tileCharWidth = Integer.parseInt(lines[i].substring(position + 1).trim());
                        } else if (lines[i].startsWith("Height")) {
                            this.tileCharHeight = Integer.parseInt(lines[i].substring(position + 1).trim());
                        } else if (lines[i].startsWith("FrameX")) {
                            this.tileOffsetX = Integer.parseInt(lines[i].substring(position + 1).trim());
                        } else if (lines[i].startsWith("FrameY")) {
                            this.tileOffsetY = Integer.parseInt(lines[i].substring(position + 1).trim());
                        } else {
                            try {
                                int index = Integer.parseInt(lines[i].substring(0, position - 1).trim());
                                this.charWidth[index] = Integer.parseInt(lines[i].substring(position + 1).trim());
                            } catch (Exception e) {
                                // System.out.println(FontSettings.class.getSimpleName()+": Problem on parsing fontsettings values. ["+lines[i].substring(0,
                                // position - 1).trim()+"]");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load fontsettings file.");
        }
    }

}
