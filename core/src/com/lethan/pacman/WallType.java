package com.lethan.pacman;

public enum WallType {
    a(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    },"null"),

    b(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {0, 1, 1}
    },"0"),

    c(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 0}
    },"1"),

    d(new int[][]{
            {2, 1, 1},
            {0, 1, 1},
            {0, 1, 1}
    },"2");


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
