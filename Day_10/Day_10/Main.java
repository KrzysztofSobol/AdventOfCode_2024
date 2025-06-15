package Day_10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main {
    public record Node (int val, int x, int y) { }

    public static int getScore(int[][] grid, Node trailhead){
        Stack<Node> toVisit = new Stack<>();
        int maxR = grid.length;
        int maxC = grid[0].length;
        int score = 0;

        toVisit.add(trailhead);

        while(!toVisit.isEmpty()){
            Node node = toVisit.pop();
            score += addPossiblePaths(grid, node, maxC,  maxR, toVisit);
        }

        return score;
    }

    private static int addPossiblePaths(int[][] grid, Node node, int maxC, int maxR, Stack<Node> toVisit) {
        int foundEnds = 0;
        if(node.y+1 < maxC){
            int value = grid[node.x][node.y+1];
            if(value == node.val+1){
                if(value == 9){
                    foundEnds++;
                } else {
                    toVisit.add(new Node(value, node.x, node.y+1));
                }
                grid[node.x][node.y+1] = -1;
            }
        }
        if(node.x+1 < maxR){
            int value = grid[node.x+1][node.y];
            if(value == node.val+1){
                if(value == 9){
                    foundEnds++;
                } else {
                    toVisit.add(new Node(value, node.x+1, node.y));
                }
                grid[node.x+1][node.y] = -1;
            }
        }
        if(node.y-1 >= 0){
            int value = grid[node.x][node.y-1];
            if(value == node.val+1){
                if(value == 9){
                    foundEnds++;
                } else {
                    toVisit.add(new Node(value, node.x, node.y-1));
                }
                grid[node.x][node.y-1] = -1;
            }
        }
        if(node.x-1 >= 0){
            int value = grid[node.x-1][node.y];
            if(value == node.val+1){
                if(value == 9){
                    foundEnds++;
                } else {
                    toVisit.add(new Node(value, node.x-1, node.y));
                }
                grid[node.x-1][node.y] = -1;
            }
        }
        return foundEnds;
    }

    public static int[][] copyGrid(int[][] original) {
        return Arrays.stream(original)
                .map(int[]::clone)
                .toArray(int[][]::new);
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

        // e
        int count = 0;
        for(int i=0; i<numOfRows; i++){
            for(int j=0; j<numOfCols; j++){
                if(grid[i][j] == 0){
                    count += getScore(copyGrid(grid), new Node(grid[i][j], i, j));
                }
            }
        }

        System.out.println(count);
    }
}