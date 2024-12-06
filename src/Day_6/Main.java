package Day_6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private record GuardPosition(int row, int col) {}

    private static GuardPosition findTheGuard(char[][] lab, int rows, int cols){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(lab[i][j] == '^'){
                    return new GuardPosition(i, j);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/Day_6/data.txt";
        int rows = 10;
        int cols = 10;
        int totalMoves = 0;

        char[][] lab = new char[rows][cols];
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int rowIdx = 0;

        while ((line = br.readLine()) != null) {
            lab[rowIdx] = line.toCharArray();
            rowIdx++;
        }

        GuardPosition guardPos = findTheGuard(lab, rows, cols);

    }
}