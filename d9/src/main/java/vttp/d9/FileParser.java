package vttp.d9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
    public static final String GRID = "GRID";
    public static final String START = "START";
    public static final String DATA = "DATA"; 
    public static final String COMMENT = "#";

    FileReader fr;
    BufferedReader br;

    public FileParser(String fileName) throws Exception {
        fr = new FileReader(fileName);
        br = new BufferedReader(fr);
    }
    
    public char[][] parse() throws IOException {
        char[][] grid = null;
        int startX = 0;
        int startY = 0;
        int width = 0;
        int height = 0;

        boolean stop = false;

        while(!stop) {
            String line = nextLine();
            if (null == line) {
                stop = true;
                continue;
            }
            String terms[] = line.split(" ");

            switch(terms[0]) {
                case GRID:
                    width = Integer.parseInt(terms[1]);
                    height = Integer.parseInt(terms[2]);
                    grid = new char[height][width];
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[i].length; j++) {
                            grid[i][j] = ' ';
                        }
                    }
                    break;
                
                case START:
                    startX = Integer.parseInt(terms[1]);
                    startY = Integer.parseInt(terms[2]);
                    break;

                case DATA:
                    populateGrid(startX, startY, grid);
                    stop = true;
                    break;
                    
                case COMMENT:
                default: 
            }

        }
        return grid;
    }

    private void populateGrid(int startX, int startY, char[][] grid) throws IOException {
        int Y = startY;
        String line = nextLine();
        while (null != line) {
            char[] data = line.toCharArray();
            for (int i = 0; i < data.length; i++) {
                grid[Y][startX + i] = data[i];
            }
            Y++; // Access next row
            line = nextLine(); // Access next line of data to input
        }
    }

    private String nextLine() throws IOException {
        return br.readLine();
    }
}
