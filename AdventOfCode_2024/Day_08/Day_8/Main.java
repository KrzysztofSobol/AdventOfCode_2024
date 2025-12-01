package Day_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    record Pos(int row, int col) {
        public Pos sub(Pos other){
            return new Pos(this.row - other.row, this.col - other.col);
        }
        public Pos add(Pos other){
            return new Pos(this.row + other.row, this.col + other.col);
        }
    }

    public static void main(String[] args) throws IOException {
        final String filePath = "Day_8/data.txt";

        // 1. load data
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int numOfRows = lines.size();
        int numOfCols = lines.getFirst().length();

        char[][] grid = new char[numOfRows][numOfCols];
        for(int i = 0; i < numOfRows; i++){
            String line = lines.get(i);
            for (int j = 0; j < numOfCols; j++){
                grid[i][j] = line.charAt(j);
            }
        }

        // 2. node reference structure
        HashMap<Character, LinkedList<Pos>> nodeSet = new HashMap<>();

        // fill the nodeSet
        for(int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfCols; j++){
                char c = grid[i][j];
                if(c != '.'){
                    nodeSet.computeIfAbsent(c,n -> new LinkedList<>()).add(new Pos(i, j));
                }
            }
        }

        // count the antinodes
        int count = 0;
        for(Map.Entry<Character, LinkedList<Pos>> entry : nodeSet.entrySet()){
            LinkedList<Pos> list = entry.getValue();
            int lSize = list.size();
            count += antinodesForFrequency(lSize, list, numOfRows, numOfCols, grid); // antinodes
            count += lSize; // antennas (part 2 includes them as antinodes too)
        }

        for(int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfCols; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println(count);
    }

    private static int antinodesForFrequency(int lSize, LinkedList<Pos> list, int numOfRows, int numOfCols, char[][] grid) {
        int count = 0;
        for(int i = 0; i < lSize; i++){
            Pos start = list.get(i);
            for(int j = i+1; j < lSize; j++){
                Pos end = list.get(j);

                int rowDiff = start.row - end.row;
                int colDiff = start.col - end.col;
                Pos diff = new Pos(rowDiff, colDiff);

                count += getAntinode(numOfRows, numOfCols, grid, start, diff, true);
                count += getAntinode(numOfRows, numOfCols, grid, end, diff, false);
            }
        }
        return count;
    }

    private static int getAntinode(int numOfRows, int numOfCols, char[][] grid, Pos start, Pos diff, boolean up) {
        int count = 0;
        Pos pos;

        if(up){
            pos = start.add(diff);
        } else {
            pos = start.sub(diff);
        }

        while(pos.row >= 0 && pos.col >= 0 && pos.row < numOfRows && pos.col < numOfCols) {
            if(grid[pos.row][pos.col] == '.'){
                grid[pos.row][pos.col] = '#';
                count++;
            }

            if(up){
                pos = pos.add(diff);
            } else {
                pos = pos.sub(diff);
            }
        }

        return count;
    }
}