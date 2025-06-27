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
        char nextTile = map[newRow][newCol];
        int[] newPos = new int[]{newRow, newCol};

        if (nextTile == 'O'){
            move(map, move, newRow, newCol);
        }

        nextTile = map[newRow][newCol];

        if(nextTile == '.'){
            swap(map, newRow, newCol, row, col);
            return newPos;
        }

        return new int[]{row, col};
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
        char[][] warehouse = new char[ROWS][COLS];
        int botX = 0;
        int botY = 0;

        for(int i=0; i<ROWS; i++){
            char[] line = data.get(i).toCharArray();
            for(int j=0; j<COLS; j++){
                warehouse[i][j] = line[j];
                if(line[j] == '@'){
                    botX = i;
                    botY = j;
                }
            }
        }

        int[] currentPos;
        for(char c : moves){
            currentPos = move(warehouse, c, botX, botY);
            botX = currentPos[0];
            botY = currentPos[1];
        }

        int sumGPS = 0;

        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                if(warehouse[i][j] == 'O'){
                    sumGPS += (100 * i) + j;
                }
            }
        }

        System.out.println(sumGPS);
    }
}