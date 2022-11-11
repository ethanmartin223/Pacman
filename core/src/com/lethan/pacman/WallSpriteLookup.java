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

    CORNER_BOTTOM_LEFT_INNER(new int[][]{
            {2, 0, 0},
            {1, 1, 0},
            {1, 1, 2}
    },"corner_bottom_left_inner"),

    CORNER_BOTTOM_RIGHT_INNER(new int[][]{
            {0, 0, 2},
            {0, 1, 1},
            {2, 1, 1}
    },"corner_bottom_right_inner"),

    CORNER_TOP_LEFT_INNER(new int[][]{
            {1, 1, 2},
            {1, 1, 0},
            {2, 0, 0}
    },"corner_top_left_inner"),

    CORNER_TOP_RIGHT_INNER(new int[][]{
            {2, 1, 1},
            {0, 1, 1},
            {0, 0, 2}
    },"corner_top_right_inner"),

    DOUBLE_WALL_CORNER_BOTTOM_LEFT(new int[][]{
            {0, 1, 0},
            {0, 1, 1},
            {0, 0, 0}
    },"double_wall_corner_bottom_left"),

    DOUBLE_WALL_CORNER_BOTTOM_RIGHT(new int[][]{
            {0, 1, 0},
            {1, 1, 0},
            {0, 0, 0}
    },"double_wall_corner_bottom_right"),

    DOUBLE_WALL_CORNER_TOP_RIGHT(new int[][]{
        {0, 0, 0},
        {1, 1, 0},
        {0, 1, 0}
    },"double_wall_corner_top_right"),

    DOUBLE_WALL_CORNER_TOP_LEFT(new int[][]{
        {0, 0, 0},
        {0, 1, 1},
        {0, 1, 0}
    },"double_wall_corner_top_left"),

    DOUBLE_WALL_END_LEFT(new int[][]{
        {2, 0, 0},
        {1, 1, 0},
        {2, 0, 0}
    },"double_wall_end_left"),

    DOUBLE_WALL_END_RIGHT(new int[][]{
        {0, 0, 2},
        {0, 1, 1},
        {0, 0, 2}
    },"double_wall_end_right"),

    HORIZONTAL_DOUBLE_WALL(new int[][]{
        {2, 0, 2},
        {1, 1, 1},
        {2, 0, 2}
    },"horizontal_double_wall"),

    VERTICAL_DOUBLE_WALL(new int[][]{
        {2, 1, 2},
        {0, 1, 0},
        {2, 1, 2}
    },"vertical_double_wall"),

    VERTICAL_WALL_RIGHT(new int[][]{
        {2, 1, 1},
        {0, 1, 1},
        {2, 1, 1}
    },"vertical_wall_right"),

    VERTICAL_WALL_LEFT(new int[][]{
            {1, 1, 2},
            {1, 1, 0},
            {1, 1, 2}
    },"vertical_wall_left"),

    HORIZONTAL_WALL_BOTTOM(new int[][]{
            {2, 0, 2},
            {1, 1, 1},
            {1, 1, 1}
    },"horizontal_wall_bottom"),

    HORIZONTAL_WALL_TOP(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {2, 0, 2}
    },"horizontal_wall_top");

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
