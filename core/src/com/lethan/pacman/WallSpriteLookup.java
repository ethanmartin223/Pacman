package com.lethan.pacman;

public enum WallSpriteLookup {
    NULL(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    },"null"),

    CORNER_BOTTOM_LEFT(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {0, 1, 1}
    },"corner_bottom_left"),

    CORNER_BOTTOM_RIGHT(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 0}
    },"corner_bottom_right"),

    CORNER_TOP_LEFT(new int[][]{
            {0, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    },"corner_top_left"),

    CORNER_TOP_RIGHT(new int[][]{
            {1, 1, 0},
            {1, 1, 1},
            {1, 1, 1}
    },"corner_top_right"),

    CORNER_TOP_LEFT(new int[][]{
            {0, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    },"corner_top_left"),


    private final int[][] layout;
    private final String value;
    WallSpriteLookup(int[][] layout, String value1){
        this.layout = layout;
        this.value = value1;
    }

    public String getTextureName() {
        return value;
    }

    public int[][] getLayout() {
        return layout;
    }
}
