package Day_10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public record Node (int val, int x, int y) { }

    public static int getScore(int[][] grid, Node trailhead){
        Stack<Node> toVisit = new Stack<>();
        int count = 0;

        int maxR = grid.length;
        int maxC = grid[0].length;

        toVisit.add(trailhead);

        while(!toVisit.isEmpty()){
            Node node = toVisit.pop();
            count += addPossiblePaths(grid, node, maxC,  maxR, toVisit);
        }

        return count;
    }

    private static int addPossiblePaths(int[][] grid, Node node, int maxC, int maxR, Stack<Node> toVisit) {
        int foundEnds = 0;
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};

        for(int[] dir : directions){
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];

            if(newX < maxR && newX >= 0 && newY < maxC && newY >= 0){
                int value = grid[newX][newY];
                if(value == node.val+1){
                    if(value == 9){
                        foundEnds++;
                    } else {
                        toVisit.add(new Node(value, newX, newY));
                    }
                }
            }
        }
        return foundEnds;
    }

    public static void main(String[] args) throws IOException {
        final String filePath = "Day_10/data.txt";

        // load the data in
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int numOfRows = lines.size();
        int numOfCols = lines.getFirst().length();

        int[][] grid = new int[numOfRows][numOfCols];
        for(int i=0; i<numOfRows; i++){
            String line = lines.get(i);
            for(int j=0; j<numOfCols; j++){
                grid[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        // count the score of each trail and sum up
        int count = 0;
        for(int i=0; i<numOfRows; i++){
            for(int j=0; j<numOfCols; j++){
                if(grid[i][j] == 0){
                    count += getScore(grid, new Node(grid[i][j], i, j));
                }
            }
        }

        System.out.println(count);
    }
}