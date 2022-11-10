package com.lethan.pacman;

public enum WallType {
    a(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 1, 0}
    }, "0" ),

    b(new int[][]{
            {0, 1, 0},
            {0, 1, 0},
            {0, 0, 0}
    }, "0" ),

    c(new int[][]{
            {0, 0, 0},
            {0, 1, 1},
            {0, 0, 0}
    }, "0" ),

    d(new int[][]{
            {0, 0, 0},
            {1, 1, 0},
            {0, 0, 0}
    }, "0" ),

    e(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    }, "0" );


    private final int[][] layout;
    private final String value;
    WallType(int[][] layout, String value1){
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
