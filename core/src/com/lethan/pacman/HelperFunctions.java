package com.lethan.pacman;

public class HelperFunctions {

    public static boolean arrayEqualsWithWildcard(int[][] arr1, int[][] arr2, int wildcard) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (!((arr1[y][x] == arr2[y][x]) || (arr1[y][x] == 2) || (arr2[y][x] == 2))) {
                    return false;
                }
            }
        }
        return true;
    }


}
