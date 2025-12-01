package Day_6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private record Position(int row, int col) {}

    private static Position findTheGuard(char[][] lab, int rows, int cols){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(lab[i][j] == '^'){
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private static void go(char[][] lab, int direction, int X, int Y, List<Position> visitedPlaces){
        int pmX = 0, pmY = 0;
        switch (direction) {
            case 1: pmX = -1; direction = 2; break;
            case 2: pmY = 1; direction = 3; break;
            case 3: pmX = 1; direction = 4; break;
            case 4: pmY = -1; direction = 1; break;
        }

        while(X+pmX >= 0 && X+pmX < lab.length && Y+pmY >= 0 && Y+pmY < lab.length){
            if(lab[X+pmX][Y+pmY] == '#'){
                go(lab, direction, X, Y, visitedPlaces);
                break;
            } else if(lab[X][Y] != 'X'){
                lab[X][Y] = 'X';
                visitedPlaces.add(new Position(X, Y));
            }
            X += pmX;
            Y += pmY;
        }
        if(lab[X][Y] != 'X'){
            lab[X][Y] = 'X';
            visitedPlaces.add(new Position(X, Y));
        }
    }

    private static List<Position> checkPath(char[][] lab, int direction, int X, int Y) {
        List<Position> visitedPlaces = new ArrayList<>();
        go(lab, direction, X, Y, visitedPlaces);
        return visitedPlaces;
    }

    private static boolean checkForLoops(char[][] lab, int direction, int X, int Y, Position obstacle){
        int pmX = 0, pmY = 0;
        switch (direction) {
            case 1: pmX = -1; direction = 2; break;
            case 2: pmY = 1; direction = 3; break;
            case 3: pmX = 1; direction = 4; break;
            case 4: pmY = -1; direction = 1; break;
        }

        while(X+pmX >= 0 && X+pmX < lab.length && Y+pmY >= 0 && Y+pmY < lab.length){
            if(lab[X+pmX][Y+pmY] == '#' || X+pmX == obstacle.row && Y+pmY == obstacle.col){
                if (checkForLoops(lab, direction, X, Y, obstacle)) {
                    return true;
                } else {
                    return false;
                }
            } else if(lab[X][Y] == '.'){
                lab[X][Y] = '1';
            } else if(lab[X][Y] == '1'){
                lab[X][Y] = '2';
            } else if(lab[X][Y] == '2'){
                lab[X][Y] = '3';
            } else if(lab[X][Y] == '3'){
                lab[X][Y] = '4';
            } else if(lab[X][Y] == '4'){
                return true;
            }
            X += pmX;
            Y += pmY;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/Day_6/data.txt";
        int rows = 10;
        int cols = 10;
        List<Position> placesVisited;

        char[][] lab = new char[rows][cols];
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int rowIdx = 0;

        while ((line = br.readLine()) != null) {
            lab[rowIdx] = line.toCharArray();
            rowIdx++;
        }

        Position guardPos = findTheGuard(lab, rows, cols);
        char[][] labCopy = deepCopyLab(lab);

        placesVisited = checkPath(labCopy, 1, guardPos.row, guardPos.col);
        System.out.println(placesVisited.size());

        placesVisited.removeFirst();
        int totalLoops = 0;

        while(!placesVisited.isEmpty()){
            Position obstacle = placesVisited.removeFirst();
            labCopy = deepCopyLab(lab);

            boolean didLoop = checkForLoops(labCopy, 1, guardPos.row, guardPos.col, obstacle);
            if(didLoop){
                totalLoops++;
            }
        }
        System.out.println("Number of loops found: " + totalLoops);
    }

    private static char[][] deepCopyLab(char[][] original) {
        return Arrays.stream(original)
                .map(char[]::clone)
                .toArray(char[][]::new);
    }
}