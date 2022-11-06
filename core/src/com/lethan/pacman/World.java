package com.lethan.pacman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class World {

    private int[][] worldLayout;
    private List<Wall> wallList;

    public World() {
        worldLayout = Levels.getLevel(Levels.LEVEL_1);
        for (int y=0; y < worldLayout.length; y++) {
            for (int x = 0; x < worldLayout[y].length; x++) {
                System.out.print(worldLayout[y][x]);
            }
            System.out.println();
        }
        wallList = new ArrayList<Wall>();
    }

}

