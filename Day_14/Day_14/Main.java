package Day_14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int safetyFactor(int[][] space){
        int ROWS = space.length;
        int COLS = space[0].length;
        int qWidth = COLS/2;
        int qHeight = ROWS/2;

        int topLeft = getSum(space, 0, qHeight, 0, qWidth);
        int topRight = getSum(space, 0, qHeight, qWidth+1, COLS);
        int bottomLeft = getSum(space, qHeight+1, ROWS, 0, qWidth);
        int bottomRight = getSum(space, qHeight+1, ROWS, qWidth+1, COLS);

        return topLeft * topRight * bottomLeft * bottomRight;
    }

    private static int getSum(int[][] space, int startRow, int endRow, int startCol, int endCol) {
        int sum = 0;
        for(int i=startRow; i<endRow; i++){
            for(int j=startCol; j<endCol; j++){
                sum += space[i][j];
            }
        }
        return sum;
    }

    // funny that it worked
    private static boolean findTree(int[][] space){
        int ROWS = space.length;
        int COLS = space[0].length;

        for(int i=0; i<ROWS-3; i=i+3){
            for(int j=0; j<COLS-3; j=j+3){
                boolean a = space[i][j] > 0;
                boolean b = space[i][j+1] > 0;
                boolean c = space[i][j+2] > 0;

                boolean d = space[i+1][j] > 0;
                boolean e = space[i+1][j+1] > 0;
                boolean f = space[i+1][j+2] > 0;

                boolean g = space[i+2][j] > 0;
                boolean h = space[i+2][j+1] > 0;
                boolean k = space[i+2][j+2] > 0;

                if(a && b && c && d && e && f && g && h && k){
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        final String path = "Day_14/data.txt";
        final int ROWS = 103;
        final int COLS = 101;

        List<String> data = Files.readAllLines(Paths.get(path));
        ArrayList<Bot> bots = new ArrayList<>();
        int[][] space = new int[ROWS][COLS];

        for(String p : data){
            String[] values = p.replaceAll("[p=v]", "").split("[ ,]");
            int posY = Integer.parseInt(values[0]);
            int posX = Integer.parseInt(values[1]);
            int dy = Integer.parseInt(values[2]);
            int dx = Integer.parseInt(values[3]);

            Bot bot = new Bot(posX, posY, dx, dy);
            bots.add(bot);
        }


        for(int k=1; k<=100000; k++){
            for(Bot bot : bots){
                bot.tick(k, ROWS, COLS);
                space[bot.posX][bot.posY]++;
            }

            if(findTree(space)){
                System.out.println("Found the tree after " + k + " seconds");
                break;
            }

            for (int i=0; i<ROWS; i++){
                for (int j=0; j<COLS; j++){
                    space[i][j] = 0;
                }
            }
        }

        for (int i=0; i<ROWS; i++){
            for (int j=0; j<COLS; j++){
                if(space[i][j] == 0){
                    System.out.print(".");
                } else {
                    System.out.print(space[i][j]);
                }
            }
            System.out.println();
        }

        System.out.println();

        int safetyFactor = safetyFactor(space);
        System.out.println(safetyFactor);
    }
}