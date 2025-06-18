package Day_12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    static int ROWS = 0;
    static int COLS = 0;

    public static int getFences(char[][] grid, char type, int x, int y){
        int fencesTotal = 0;

        if(y+1 < COLS){
            if(grid[x][y+1] != type){
                fencesTotal++;
            }
        } else {
            fencesTotal++;
        }

        if(x+1 < ROWS){
            if(grid[x+1][y] != type){
                fencesTotal++;
            }
        } else {
            fencesTotal++;
        }

        if(y-1 >= 0){
            if(grid[x][y-1] != type){
                fencesTotal++;
            }
        } else {
            fencesTotal++;
        }

        if(x-1 >= 0){
            if(grid[x-1][y] != type){
                fencesTotal++;
            }
        } else {
            fencesTotal++;
        }
        System.out.println(fencesTotal + "  :  " + x + " " + y);

        return fencesTotal;
    }

    public static void main(String[] args) throws IOException {
        final String filePath = "Day_12/data.txt";
        HashMap<Character, Integer> map = new HashMap<>();

        // load data in
        List<String> data = Files.readAllLines(Paths.get(filePath));
        ROWS = data.size();
        COLS = data.getFirst().length();

        final char[][] grid = new char[ROWS][COLS];
        for(int i=0; i<ROWS; i++){
            String line = data.get(i);
            for(int j=0; j<COLS; j++){
                grid[i][j] = line.charAt(j);
            }
        }

        // count the fences
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                char c = grid[i][j];
                int currentCount = map.computeIfAbsent(c, k -> 0);
                currentCount += getFences(grid, c, i, j);
                map.put(c, currentCount);
            }
        }

        int sumTotal = map.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println(sumTotal);

        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
}