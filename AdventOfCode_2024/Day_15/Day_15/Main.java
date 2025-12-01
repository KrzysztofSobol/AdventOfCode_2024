package Day_15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static int[] move(char[][] map, char move, int row, int col){
        int[] direction = getDirection(move);
        int newRow = row + direction[0];
        int newCol = col + direction[1];

        if(!canMove(map, move, row, col)){
            return new int[]{row, col};
        }

        performMove(map, move, row, col);
        return new int[]{newRow, newCol};
    }

    public static boolean canMove(char[][] map, char move, int row, int col) {
        int[] direction = getDirection(move);
        int newRow = row + direction[0];
        int newCol = col + direction[1];
        char nextTile = map[newRow][newCol];
        if(nextTile == '#'){
            return false;
        }

        if(nextTile == '.'){
            return true;
        }

        if(move == '<' || move == '>'){
            if (nextTile == '[' || nextTile == ']') {
                return canMove(map, move, newRow, newCol);
            }
        } else {
            if(nextTile == '['){
                return canMove(map, move, newRow, newCol) && canMove(map, move, newRow, newCol + 1);
            } else if (nextTile == ']') {
                return canMove(map, move, newRow, newCol) && canMove(map, move, newRow, newCol - 1);
            }
        }

        return false;
    }

    public static void performMove(char[][] map, char move, int row, int col){
        int[] direction = getDirection(move);
        int newRow = row + direction[0];
        int newCol = col + direction[1];
        char nextTile = map[newRow][newCol];

        if(nextTile == '.'){
            swap(map, newRow, newCol, row, col);
        }

        if(move == '<' || move == '>'){
            if (nextTile == '[' || nextTile == ']') {
                performMove(map, move, newRow, newCol);
                swap(map, newRow, newCol, row, col);
            }
        } else {
            if(nextTile == '['){
                performMove(map, move, newRow, newCol);
                performMove(map, move, newRow, newCol+1);
                swap(map, newRow, newCol, row, col);
            } else if (nextTile == ']') {
                performMove(map, move, newRow, newCol);
                performMove(map, move, newRow, newCol-1);
                swap(map, newRow, newCol, row, col);
            }
        }
    }

    public static void swap(char[][] map, int a, int b, int c, int d){
        char temp = map[a][b];
        map[a][b] = map[c][d];
        map[c][d] = temp;
    }

    public static int[] getDirection(char move){
        int[] direction = new int[2];
        switch (move){
            case '>':
                direction = new int[]{0, 1};
                break;
            case 'v':
                direction = new int[]{1, 0};
                break;
            case '<':
                direction = new int[]{0, -1};
                break;
            case '^':
                direction = new int[]{-1, 0};
                break;
        }

        return direction;
    }

    public static void main(String[] args) throws IOException {
        final String path = "Day_15/data.txt";
        StringBuilder sb = new StringBuilder();

        // load the data
        List<String> data = Files.readAllLines(Paths.get(path));

        int count = 0;
        for(String line : data){
            if(line.contains("#")){
                count++;
            } else {
                sb.append(line);
            }
        }

        final int COLS = data.getFirst().length();
        final int ROWS = count;

        char[] moves = sb.toString().toCharArray();
        char[][] warehouse = new char[ROWS][COLS * 2];
        int botX = 0;
        int botY = 0;

        for(int i=0; i<ROWS; i++){
            char[] line = data.get(i).toCharArray();
            for(int j=0; j<COLS; j++){
                char c = line[j];
                if(c == '.' || c == '#'){
                    warehouse[i][j*2] = line[j];
                    warehouse[i][j*2+1] = line[j];
                } else if (c == 'O'){
                    warehouse[i][j*2] = '[';
                    warehouse[i][j*2+1] = ']';
                } else {
                    warehouse[i][j*2] = '@';
                    warehouse[i][j*2+1] = '.';

                    botX = i;
                    botY = j*2;
                }
            }
        }

        int[] currentPos;
        for(char c : moves){
            currentPos = move(warehouse, c, botX, botY);
            botX = currentPos[0];
            botY = currentPos[1];
        }

        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS*2; j++){
                System.out.print(warehouse[i][j]);
            }
            System.out.println();
        }

        int sumGPS = 0;

        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS*2; j++){
                if(warehouse[i][j] == '['){
                    sumGPS += (100 * i) + j;
                }
            }
        }

        System.out.println(sumGPS);
    }
}