package Day_12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class Main {
    static HashSet<String> visited = new HashSet<>();
    static HashSet<String> currentRegion = new HashSet<>();
    static int ROWS = 0;
    static int COLS = 0;

    public static void gardenScan(char[][] grid, int x, int y){
        visited.add(x + "," + y);
        currentRegion.add(x + "," + y);
        char type = grid[x][y];

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        for(int i=0; i<4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS && !visited.contains(nx + "," + ny) && grid[nx][ny] == type){
                gardenScan(grid, nx, ny);
            }
        }
    }

    public static boolean isInRegion(int x, int y){
        return currentRegion.contains(x + "," + y);
    }

    public static int countSides(char[][] grid){
        int corners = 0;

        for(String cell : currentRegion){
            String[] parts = cell.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            // top left corner
            boolean top = isInRegion(x-1, y);
            boolean left = isInRegion(x, y-1);
            boolean topleft = isInRegion(x-1, y-1);
            if((!top && !left) || (top && left && !topleft)){
                corners++;
            }

            boolean right = isInRegion(x, y+1);
            boolean topRight = isInRegion(x-1, y+1);
            if((!top && !right) || (top && right && !topRight)){
                corners++;
            }

            boolean bottom = isInRegion(x+1, y);
            boolean bottomLeft = isInRegion(x+1, y-1);
            if((!bottom && !left ) || (bottom && left && !bottomLeft)){
                corners++;
            }

            boolean bottomRight = isInRegion(x+1, y+1);
            if((!bottom && !right) || (bottom && right && !bottomRight)){
                corners++;
            }
        }

        return corners;
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
                    currentRegion.clear();
                    gardenScan(grid, i, j);

                    int areaSize = currentRegion.size();
                    int sides = countSides(grid);

                    sumTotal += (areaSize * sides);
                }
            }
        }

        System.out.println(sumTotal);
    }
}