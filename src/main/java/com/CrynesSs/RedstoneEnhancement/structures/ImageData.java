package com.CrynesSs.RedstoneEnhancement.structures;

public class ImageData {
    private String teststring;    // the ResourceLocation of a texture file
    private Integer width;    // how much of the texture to draw in width
    private Integer height;    // how much of the texture to draw in height
    private String[] lol;

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public String getTeststring() {
        return teststring;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setTeststring(String teststring) {
        this.teststring = teststring;
    }
}
