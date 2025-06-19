package Day_12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class Main {
    static HashSet<String> visited = new HashSet<>();
    static int ROWS = 0;
    static int COLS = 0;


    public static int gardenScan(char[][] grid, int x, int y){
        visited.add(x + "," + y);
        char type = grid[x][y];
        int fencesTotal = 0;

        if(y+1 < COLS){
            if(!visited.contains(x + "," + (y+1)) && grid[x][y+1] == type){
                fencesTotal += gardenScan(grid, x, y+1);
            } else {
                if(grid[x][y+1] != type){
                    fencesTotal++;
                }
            }
        } else {
            fencesTotal++;
        }

        if(x+1 < ROWS){
            if(!visited.contains((x+1) + "," + y) && grid[x+1][y] == type){
                fencesTotal += gardenScan(grid, x+1, y);
            } else {
                if(grid[x+1][y] != type){
                    fencesTotal++;
                }
            }
        } else {
            fencesTotal++;
        }

        if(y-1 >= 0){
            if(!visited.contains(x + "," + (y-1)) && grid[x][y-1] == type){
                fencesTotal += gardenScan(grid, x, y-1);
            } else {
                if(grid[x][y-1] != type){
                    fencesTotal++;
                }
            }
        } else {
            fencesTotal++;
        }

        if(x-1 >= 0){
            if(!visited.contains((x-1) + "," + y) && grid[x-1][y] == type){
                fencesTotal += gardenScan(grid, x-1, y);
            } else {
                if(grid[x-1][y] != type){
                    fencesTotal++;
                }
            }
        } else {
            fencesTotal++;
        }

        return fencesTotal;
    }

    public static void main(String[] args) throws IOException {
        final String filePath = "Day_12/data.txt";

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
        int lastVisitedSize = 0;
        int sumTotal = 0;

        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                char c = grid[i][j];
                if(!visited.contains(i + "," +j)){

                    int perimeter = gardenScan(grid, i, j);

                    int visitedSize = visited.size();
                    int region = visitedSize - lastVisitedSize;
                    System.out.println(region + " * " + perimeter);
                    sumTotal += (region * perimeter);

                    lastVisitedSize = visitedSize;
                }
            }
        }

        System.out.println(sumTotal);
    }
}